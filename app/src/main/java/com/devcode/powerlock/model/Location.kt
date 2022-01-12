package com.devcode.powerlock.model

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

@SuppressLint("StaticFieldLeak")
private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(
	LocalContext as Activity
)

fun getLastLocation() {
	if (ActivityCompat.checkSelfPermission(
			LocalContext as Activity,
			Manifest.permission.ACCESS_FINE_LOCATION
		) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
			LocalContext as Activity,
			Manifest.permission.ACCESS_COARSE_LOCATION
		) != PackageManager.PERMISSION_GRANTED
	) {
		// TODO: Consider calling
		//    ActivityCompat#requestPermissions
		// here to request the missing permissions, and then overriding
		//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
		//                                          int[] grantResults)
		// to handle the case where the user grants the permission. See the documentation
		// for ActivityCompat#requestPermissions for more details.

	} else {
		fusedLocationClient.lastLocation.addOnSuccessListener {
			return@addOnSuccessListener
		}
	}
}


