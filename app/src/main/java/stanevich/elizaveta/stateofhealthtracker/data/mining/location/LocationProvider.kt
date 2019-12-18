package stanevich.elizaveta.stateofhealthtracker.data.mining.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*


class LocationProvider constructor(
    private val context: Context,
    private val intervalMs: Int = 1000,
    private val onLocationChanged: (location: Location) -> Unit = { _ -> }
) : LocationCallback() {

    private val locationPermission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    var isTrackingLocation: Boolean = false
        private set
    private val fusedLocationClient: FusedLocationProviderClient


    /**
     * Sets up the location request.
     *
     * @return The LocationRequest object containing the desired parameters.
     */
    private val locationRequest: LocationRequest
        get() {
            val locationRequest = LocationRequest()
            locationRequest.interval = intervalMs.toLong()
            locationRequest.fastestInterval = (intervalMs / 2).toLong()
            locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
            return locationRequest
        }

    init {
        isTrackingLocation = false
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    fun stopTrackingLocation() {
        if (isTrackingLocation) {
            isTrackingLocation = false
            fusedLocationClient.removeLocationUpdates(this)
        }
    }

    fun startTrackingLocation() {
        if (itHaveAllPermissions()) {
            isTrackingLocation = true
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                this, null
            ).addOnCompleteListener { println("LOCATION COMPLETE") }
                .addOnCanceledListener { println("LOCATION CANCELED") }
                .addOnFailureListener { println("LOCATION FAILURE") }
                .addOnSuccessListener { println("LOCATION SUCCESS") }
        }
    }

    private fun itHaveAllPermissions(): Boolean {
        return locationPermission.all {
            ActivityCompat.checkSelfPermission(
                context,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    override fun onLocationResult(locationResult: LocationResult?) {
        this.onLocationChanged(locationResult!!.lastLocation)
    }
}