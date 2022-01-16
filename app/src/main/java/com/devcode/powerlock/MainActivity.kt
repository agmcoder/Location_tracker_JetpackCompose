package com.devcode.powerlock

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.devcode.powerlock.navigation.NavigationHost
import com.devcode.powerlock.theme.SecurePhoneAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import java.util.*

class MainActivity : ComponentActivity() {
	private val FINE_LOCATION_CODE = 123
	private val COARSE_LOCATION_CODE = 113
	lateinit var fusedLocationProviderClient : FusedLocationProviderClient

	@ExperimentalPermissionsApi
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		//not implemented yet. fusedLocation
		//fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

		setContent {
			SecurePhoneAppTheme {

				//location

				//fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)

				//checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION,COARSE_LOCATION_CODE)
				checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION_CODE)
				Logger.addLogAdapter(AndroidLogAdapter())
				NavigationHost(getSharedPreferences("mispreferencias", MODE_PRIVATE))
			}
		}

	}


    //we have implemented coarse and fine request permission when we start the app
    //we must to implement this one in runtime permissions

	private fun checkPermission(permission : String, requestCode : Int) {
		if (ContextCompat.checkSelfPermission(
				this@MainActivity,
				permission
			) == PackageManager.PERMISSION_DENIED
		) {
			//take permission
			ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
		} else {
			Toast.makeText(this@MainActivity, "Permission Granted already", Toast.LENGTH_LONG)
				.show()
		}
	}

	override fun onRequestPermissionsResult(
		requestCode : Int,
		permissions : Array<out String>,
		grantResults : IntArray
	) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode == COARSE_LOCATION_CODE) {
			if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(this@MainActivity, "coarse permission granted", Toast.LENGTH_SHORT)
					.show()
			} else {
				Toast.makeText(this@MainActivity, "coarse permission denied", Toast.LENGTH_SHORT)
					.show()
			}
		} else if (requestCode == FINE_LOCATION_CODE) {
			if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Toast.makeText(this@MainActivity, "fine permission granted", Toast.LENGTH_SHORT)
					.show()
			} else {
				Toast.makeText(this@MainActivity, "fine permission denied", Toast.LENGTH_SHORT)
					.show()
			}
		}
	}


}




