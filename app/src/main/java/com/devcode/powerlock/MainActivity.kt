package com.devcode.powerlock

import android.Manifest
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.devcode.powerlock.model.getAndroidId
import com.devcode.powerlock.model.getGPSLocationStateToSharedPreferences
import com.devcode.powerlock.navigation.NavigationHost
import com.devcode.powerlock.theme.SecurePhoneAppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	private val FINE_LOCATION_CODE = 123
	private val COARSE_LOCATION_CODE = 113
	lateinit var fusedLocationProviderClient : FusedLocationProviderClient

	@SuppressLint("CommitPrefEdits")
	@RequiresApi(Build.VERSION_CODES.Q)
	@ExperimentalPermissionsApi
	override fun onCreate(savedInstanceState : Bundle?) {
		super.onCreate(savedInstanceState)
		//not implemented yet. fusedLocation
		//fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

		setContent {
			SecurePhoneAppTheme {


				//checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION,COARSE_LOCATION_CODE)
				checkPermission(Manifest.permission.ACCESS_FINE_LOCATION, FINE_LOCATION_CODE)
				Logger.addLogAdapter(AndroidLogAdapter())
				val sharedPreferences=getSharedPreferences("sharedPreferences", MODE_PRIVATE)
				val ed : SharedPreferences.Editor = sharedPreferences.edit()
				val androidID= getAndroidId(LocalContext.current)
				//getGPSLocationStateToSharedPreferences( androidID,sharedPreferences)



				NavigationHost(sharedPreferences)
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




