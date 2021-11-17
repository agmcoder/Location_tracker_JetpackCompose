package com.devcode.powerlock.model

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.compose.material.LocalContentAlpha
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.google.android.gms.location.*
import com.orhanobut.logger.Logger
import io.grpc.InternalChannelz.id

class Geopoint{



}
fun getDeviceLocation(context: Context): Array<String> {
    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    var coordenadas=arrayOf<String>()

    try {
        val locationResult = fusedLocationProviderClient.lastLocation
        locationResult.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val lastKnownLocation = task.result
                if (lastKnownLocation != null) {
                    coordenadas = (arrayOf(
                        lastKnownLocation.latitude.toString(),
                        lastKnownLocation.longitude.toString()
                    ))



                }
            } else {
                Logger.d("exception", " current user location is null")
            }

        }

    } catch (e: SecurityException) {
        Logger.d("exception ", e)
    }
    return coordenadas

}







