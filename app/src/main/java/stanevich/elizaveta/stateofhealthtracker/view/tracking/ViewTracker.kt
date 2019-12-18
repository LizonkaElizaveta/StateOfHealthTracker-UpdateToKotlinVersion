package stanevich.elizaveta.stateofhealthtracker.view.tracking

import android.graphics.Point
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.os.Handler
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import kotlin.math.pow
import kotlin.math.sqrt


/**
 * Wrapping for tracked views which was added by [TrackingViewGroup.addTrackedView] method
 * Обертка над View для отслеживания косаний.
 * Каждое view которое добавлено как отслеживаемое в [TrackingViewGroup] методом [TrackingViewGroup.addTrackedView],
 * оборачивается в ViewTracker.
 * Когда в TrackingViewGroup происходит косание, это косание передается
 * всем имеющимся ViewTracked-ам.
 * View трекер определяет на сколько близко было косание к элементу и было ли нажатие на сам отслеживаемый элемент
 */
class ViewTracker(
    val view: View,
    private val trackingTimeout: Long,
    private val fetchMaxDistance: () -> Double,
    var onMissClick: (timestamp: Long, clickDistanceFromCenter: Double, closestEvents: ArrayList<ClosestTouchEvent>) -> Unit = { _, _, _ -> }
) : Runnable {

    data class ClosestTouchEvent(
        val timestamp: Long,
        val distanceFromCenter: Double
    )

    override fun run() {
        closeEvents = clearOldEvents(closeEvents, trackingTimeout)
    }

    private fun clearOldEvents(
        list: ArrayList<ClosestTouchEvent>,
        timeout: Long
    ): ArrayList<ClosestTouchEvent> =
        ArrayList(list.filter { it.timestamp < currentTime() - timeout })

    private val densityDivider: Float =
        view.context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT.toFloat()

    private var lastAction = MotionEvent.ACTION_CANCEL

    private var closeEvents = ArrayList<ClosestTouchEvent>()

    private var lastAddedActionTimestamp: Long = 0L

    private val handler: Handler by lazy { Handler(view.handler.looper) }

    fun onMotionEvent(event: MotionEvent) {
        val isEventOnView = isOnView(event)
        val timeStamp = currentTime()
        val currentDistance = getDistanceFromViewInDpi(event)
        if (isEventOnView) {
            if (isClickOnView(event)) {
                if (hasNearestEvents(timeStamp)) {
                    onMissClick(
                        currentTime(),
                        distanceFromViewCenter(event),
                        getCloseEvents(timeStamp)
                    )
                }
                closeEvents.clear()
            }
            lastAction = event.action
        } else {
            val currentTime = currentTime()
            if (currentDistance <= fetchMaxDistance() && currentTime - lastAddedActionTimestamp >= 100L) {
                handler.removeCallbacks(this)
                handler.postDelayed(this, trackingTimeout)
                lastAddedActionTimestamp = currentTime
                closeEvents.add(ClosestTouchEvent(currentTime, distanceFromViewCenter(event)))
            }
        }
    }

    private fun distanceFromViewCenter(event: MotionEvent): Double {
        val viewRect = getViewRect()
        val viewCenter = Point(viewRect.centerX(), viewRect.centerY())
        val eventPoint = Point(getXFromEvent(event), getYFromEvent(event))
        return inDpi(dist(PointF(viewCenter), PointF(eventPoint)))
    }

    private fun getCloseEvents(timeStamp: Long): ArrayList<ClosestTouchEvent> =
        ArrayList(closeEvents.filter { timeStamp - it.timestamp < trackingTimeout })

    private fun getDistanceFromViewInDpi(event: MotionEvent): Double {
        val rectF = RectF(getViewRect())
        val eventPoint = PointF(getXFromEvent(event).toFloat(), getYFromEvent(event).toFloat())

        val leftTop = PointF(rectF.left, rectF.top)
        val leftBottom = PointF(rectF.left, rectF.bottom)
        val rightTop = PointF(rectF.right, rectF.top)
        val rightBottom = PointF(rectF.right, rectF.bottom)
        return inDpi(
            listOf(
                dist(eventPoint, leftTop, leftBottom),
                dist(eventPoint, rightTop, rightBottom),
                dist(eventPoint, leftTop, rightTop),
                dist(eventPoint, leftBottom, rightBottom)
            ).min() ?: 0.0
        )
    }

    private fun inDpi(value: Double): Double = value / densityDivider.toDouble()

    private fun dist(event: PointF, lineP1: PointF, lineP2: PointF): Double {
        val xmin = lineP1.x.coerceAtMost(lineP2.x)
        val ymin = lineP1.y.coerceAtMost(lineP2.y)
        val xmax = lineP1.x.coerceAtLeast(lineP2.x)
        val ymax = lineP1.y.coerceAtLeast(lineP2.y)
        val closest = PointF()
        if (event.x in xmin..xmax) {
            closest.x = event.x
        } else {
            closest.x = if (event.x <= xmin) xmin else xmax
        }
        if (event.y in ymin..ymax) {
            closest.y = event.y
        } else {
            closest.y = if (event.y <= ymin) ymin else ymax
        }
        return dist(event, closest)
    }

    private fun dist(a: PointF, b: PointF): Double {
        return sqrt((a.x - b.x).toDouble().pow(2.0) + (a.y - b.y).toDouble().pow(2.0))
    }

    private fun currentTime(): Long = System.currentTimeMillis()

    private fun hasNearestEvents(timeStamp: Long): Boolean = closeEvents.isNotEmpty() &&
            closeEvents.any { timeStamp - it.timestamp < trackingTimeout }

    private fun isClickOnView(event: MotionEvent): Boolean {
        return (lastAction == MotionEvent.ACTION_DOWN || lastAction == MotionEvent.ACTION_MOVE)
                && event.action == MotionEvent.ACTION_UP
    }

    private fun isOnView(event: MotionEvent): Boolean {
        val x = getXFromEvent(event)
        val y = getYFromEvent(event)
        return getViewRect().contains(x, y)
    }

    private fun getYFromEvent(event: MotionEvent): Int = event.rawY.toInt()

    private fun getXFromEvent(event: MotionEvent): Int = event.rawX.toInt()

    fun getViewRect(): Rect {
        val rect = Rect()
        view.getGlobalVisibleRect(rect)
        return rect
    }
}