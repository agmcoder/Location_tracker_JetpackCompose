package com.devcode.powerlock.composables.screens

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.devcode.powerlock.R
import com.devcode.powerlock.composables.components.SwitchOptionItem
import com.devcode.powerlock.theme.whiteBackground
import com.orhanobut.logger.Logger

@Composable
fun Menu(navController: NavController, sharedPreferences : SharedPreferences) {

    val initialValueGPS = sharedPreferences.getBoolean("gps", false)
    val initialValuePowerMenu = sharedPreferences.getBoolean("power", false)

    val checkedStateGps = rememberSaveable { mutableStateOf(initialValueGPS) }
    val checkedStatePowerMenu = rememberSaveable { mutableStateOf(initialValuePowerMenu) }

    val ed : SharedPreferences.Editor = sharedPreferences.edit()

    val locationManager: LocationManager =
        LocalContext.current.getSystemService(LOCATION_SERVICE) as LocationManager

// getting GPS status
    val isGPSEnabled = locationManager
        .isProviderEnabled(LocationManager.GPS_PROVIDER);

    // getting network status
    val isNetworkEnabled = locationManager
        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

    val MIN_TIME_BW_UPDATES = 1000 * 60 * 5
    val MIN_DISTANCE_CHANGE_FOR_UPDATES = 10

    var location: Location?

    val gpsListener = object: LocationListener {
        override fun onLocationChanged(location : Location) {
            TODO("Not yet implemented")
        }

    }


    if (isGPSEnabled || isNetworkEnabled) {

        if (isGPSEnabled) {

					  if (ActivityCompat.checkSelfPermission(
							  LocalContext.current,
							  Manifest.permission.ACCESS_FINE_LOCATION
						  ) != PackageManager.PERMISSION_GRANTED
					  ) {

					      if (ActivityCompat.checkSelfPermission(
                              LocalContext.current,
                              Manifest.permission.ACCESS_COARSE_LOCATION
                          ) != PackageManager.PERMISSION_GRANTED){
                              // TODO: Consider calling
                              //    ActivityCompat#requestPermissions
                              // here to request the missing permissions, and then overriding
                              //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                              //                                          int[] grantResults)
                              // to handle the case where the user grants the permission. See the documentation
                              // for ActivityCompat#requestPermissions for more details.
                              return
                          }
					  }

					  locationManager.requestLocationUpdates(
							  LocationManager.GPS_PROVIDER,
							  MIN_TIME_BW_UPDATES.toLong(),
							  MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), gpsListener)
						  Log.d("GPS Enabled", "GPS Enabled")
						  if (locationManager != null) {
							  location = locationManager
								  .getLastKnownLocation(LocationManager.GPS_PROVIDER)
							  if (location != null) {
								  val latitude = location.getLatitude();
								  val longitude = location.getLongitude();
								  Log.i("GPS", "Ubicacion $latitude $longitude")
							  }
						  }


            }

    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground),
        contentAlignment = Alignment.Center,


    )
    {

        if(checkedStateGps.value) {

        }

        ed.putBoolean("gps", checkedStateGps.value)
        ed.putBoolean("power", checkedStatePowerMenu.value)
        ed.commit()
        LazyColumn {
            item {
                SwitchOptionItem(
                    text = stringResource(R.string.location_gps),
                    checkedValue = checkedStateGps
                )

            }
            item {
                SwitchOptionItem(
                    text = stringResource(R.string.block_power_menu),
                    checkedValue = checkedStatePowerMenu
                )
            }
        }
    }
}
