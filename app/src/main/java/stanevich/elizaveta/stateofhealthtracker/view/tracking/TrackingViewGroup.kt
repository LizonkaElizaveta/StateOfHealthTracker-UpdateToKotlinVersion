package stanevich.elizaveta.stateofhealthtracker.view.tracking

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.widget.FrameLayout


/**
 * Tracking for all touch events in this view.
 * For start tracking wrap all views that should be tracked into <TrackingViewGroup...> tag.
 * You could wrap not direct children of that view as well.
 * Example:
 * <layout...>
 *     <TrackingViewGroup...>
 *         <ConstraintLayout...>
 *             ...
 *             <View...> <!--View that should be tracked-->
 *             <View...> <!--View that should be tracked-->
 *             ...
 *         </ConstraintLayout>
 *     </TrackingViewGroup>
 * </layout...>
 *
 * @property maxDistanceValue - is used for define area around of tracked view
 * @property timeout ms - is used for track only actual miss clicks
 * For track view you should add it with [addTrackedView] method.
 * This view track only that elements which user clicked twice or more around of it and then clicked on tracked element.
 */
class TrackingViewGroup(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    companion object {
        const val DEFAULT_TIMEOUT = 5000L
        const val DEFAULT_MAX_DISTANCE = 50.0
    }

    private var timeout = DEFAULT_TIMEOUT
    private var maxDistanceValue = DEFAULT_MAX_DISTANCE
    private var drawRect = false
    private var borderDrawer: BorderDrawer? = null

    private val views: MutableList<ViewTracker> = ArrayList()

    private fun onTouch(event: MotionEvent?): Boolean {
        if (event == null) {
            return false
        }
        for (tracker in views) {
            tracker.onMotionEvent(event)
        }
        return false
    }

    var consumer: (timestamp: Long, clickDistanceFromCenter: Double, closestEvents: List<ViewTracker.ClosestTouchEvent>) -> Unit =
        { _, _, _ -> }
        set(newValue) {
            for (tracker in views) {
                tracker.onMissClick = newValue
            }
            field = newValue
        }

    fun addTrackedView(view: View) {
        this.views.add(ViewTracker(view, timeout, { maxDistanceValue }, consumer))
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return this.onTouch(ev) || super.onInterceptTouchEvent(ev)
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        if (canvas != null && drawRect) {
            if (borderDrawer == null) {
                borderDrawer = BorderDrawer(this, views, maxDistanceValue)
            }
            borderDrawer?.drawViewsBounds(canvas)
        }
    }

    internal class BorderDrawer(
        private val frameLayout: FrameLayout,
        private val views: MutableList<ViewTracker> = ArrayList(),
        private val maxDistanceValue: Double = DEFAULT_MAX_DISTANCE
    ) {

        private val boundsPaint = Paint()

        private val densityDivider: Float =
            frameLayout.context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT.toFloat()

        fun drawViewsBounds(canvas: Canvas) {
            val myRect = Rect()
            frameLayout.getGlobalVisibleRect(myRect)
            for (tracker in views) {
                val viewRect = tracker.getViewRect()
                val width = viewRect.right - viewRect.left
                val height = viewRect.bottom - viewRect.top
                val distance = fromDpi(maxDistanceValue)
                viewRect.top = 0.coerceAtLeast(viewRect.top - myRect.top - distance.toInt())
                viewRect.bottom = viewRect.top + height + distance.toInt() * 2
                viewRect.left = 0.coerceAtLeast(viewRect.left - myRect.left - distance.toInt())
                viewRect.right = viewRect.left + width + distance.toInt() * 2
                canvas.drawRect(viewRect, boundsPaint)
            }
        }

        private fun fromDpi(value: Double): Double = densityDivider * value

        init {
            boundsPaint.color = Color.rgb(0, 255, 0)
            boundsPaint.strokeWidth = 5f
            boundsPaint.style = Paint.Style.STROKE
        }
    }
}
