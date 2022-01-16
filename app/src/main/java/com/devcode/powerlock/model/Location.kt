package com.devcode.powerlock.model

import android.location.LocationRequest
import java.net.URI.create

fun createLocationRequest() {
	val locationRequest = LocationRequest.create()?.apply {
		var interval = 10000
		fastestInterval = 5000
		priority = LocationRequest.PRIORITY_HIGH_ACCURACY
	}
}
