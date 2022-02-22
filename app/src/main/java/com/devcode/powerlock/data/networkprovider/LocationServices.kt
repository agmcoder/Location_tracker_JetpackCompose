package com.devcode.powerlock.data.networkprovider

import android.annotation.SuppressLint
import android.content.Context
import android.os.HandlerThread
import android.os.Looper
import com.google.android.gms.location.*
import com.google.android.gms.location.LocationServices
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationServices @Inject constructor(@ApplicationContext private val context : Context) {
	lateinit var fusedLocationProviderClient : FusedLocationProviderClient
	lateinit var locationRequest : LocationRequest
	lateinit var locationCallback : LocationCallback

	private var userLocation: LatLng? = LatLng(0.0, 0.0)
	private var boolean = true

	private fun getLocationUpdates() {
		return try {
			fusedLocationProviderClient = LocationServices
				.getFusedLocationProviderClient(context)
			locationRequest = LocationRequest.create().apply {
				interval = 3000
				fastestInterval = 3000
				priority = LocationRequest.PRIORITY_HIGH_ACCURACY

			}
			locationCallback = object : LocationCallback() {
				override fun onLocationResult(locationResult : LocationResult) {
					return if (locationResult.locations.isNotEmpty()) {
						userLocation = LatLng(
								locationResult.lastLocation.latitude,
								locationResult.lastLocation.longitude
							)
					} else {
						userLocation = null
					}
				}
			}
		} catch (ex : Exception) {
			userLocation = null

		}
	}

	@SuppressLint("MissingPermission")
	suspend fun startLocationUpdates(): Flow<LatLng?> = flow {
		getLocationUpdates()
		val handlerThread=HandlerThread("locationThread")
		handlerThread.start()
		val looper:Looper=handlerThread.looper
		fusedLocationProviderClient
			.requestLocationUpdates(locationRequest,locationCallback,looper).await()

		while (boolean) {
			emit(userLocation)
			delay(5000)
		}


	}
	suspend fun stopLocationUpdates(){
		if (::fusedLocationProviderClient.isInitialized) {
			boolean = false
			fusedLocationProviderClient.removeLocationUpdates(locationCallback).await()
		}

	}



}