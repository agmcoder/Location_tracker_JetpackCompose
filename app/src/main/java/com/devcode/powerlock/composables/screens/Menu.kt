package com.devcode.powerlock.composables.screens

import android.Manifest
import android.content.Context.LOCATION_SERVICE
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.navigation.NavController
import com.devcode.powerlock.R
import com.devcode.powerlock.theme.whiteBackground
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.orhanobut.logger.Logger

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi
@Composable
fun Menu(navController: NavController, sharedPreferences: SharedPreferences) {

    val finePermissionState = rememberPermissionState(
        permission =
        Manifest.permission.ACCESS_FINE_LOCATION
    )
    val gpsMultiplePermissionsState =
        rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        )


    val context = LocalContext.current
    val initialValueGPS = sharedPreferences.getBoolean("gps", false)
    val initialValuePowerMenu = sharedPreferences.getBoolean("power", false)

    val checkedStateGps = rememberSaveable { mutableStateOf(initialValueGPS) }
    val checkedStatePowerMenu = rememberSaveable { mutableStateOf(initialValuePowerMenu) }

    val permisoGPSFineInicial = ActivityCompat.checkSelfPermission(
        LocalContext.current,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    val permisoGPSCoarseInicial = ActivityCompat.checkSelfPermission(
        LocalContext.current,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

    var permisoGPSFine = rememberSaveable {
        mutableStateOf(permisoGPSFineInicial)
    }

    var permisoGPSCoarse = rememberSaveable {
        mutableStateOf(permisoGPSCoarseInicial)
    }

    val ed: SharedPreferences.Editor = sharedPreferences.edit()

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

    val gpsListener = object : LocationListener {
        override fun onLocationChanged(location: Location) {
            TODO("Not yet implemented")
        }

    }



/*val multiPermisos = rememberMultiplePermissionsState(listOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION ))


	PermissionsRequired(
		multiplePermissionsState = multiPermisos,
		permissionsNotGrantedContent = { /*TODO*/ },
		permissionsNotAvailableContent = { /*TODO*/ }) {
		
	}


	when{
		multiPermisos.allPermissionsGranted -> {
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
		else -> {


		}

	}*/

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground),
        contentAlignment = Alignment.Center,

        )
    {


        ed.putBoolean("gps", checkedStateGps.value)
        ed.putBoolean("power", checkedStatePowerMenu.value)
        ed.commit()
        LazyColumn {
            item {

                Row {
                    Box(
                        modifier =
                        Modifier.fillMaxWidth(0.8f)
                    )
                    {
                        Text(
                            color = Color.Black,
                            text = stringResource(R.string.location_gps),
                            fontSize = 30.sp,
                            modifier = Modifier.padding(20.dp)
                        )

                    }
                    Box(
                        modifier =
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Switch(
                            checked = checkedStateGps.value,
                            onCheckedChange =
                            {
                                checkedStateGps.value = it
                                if (it) {
                                    Logger.d("gpsmultiplestate.launch $it")
                                    gpsMultiplePermissionsState.launchMultiplePermissionRequest()
                                }
                            },
                            modifier = Modifier
                                .padding(20.dp),
                            colors = SwitchDefaults.colors(
                                //color of switches
                                checkedThumbColor = Color(0xFF00CC99),
                                checkedTrackColor = Color(0xFF7BB661),
                                uncheckedThumbColor = Color(0xFF83010B),
                                uncheckedTrackColor = Color(0xFFBB4C4C)
                            )

                        )
                    }
                }


            }
            item {
                Button(onClick = {
                    Logger.d("launch multiplepermissionreuqest")
                    gpsMultiplePermissionsState.launchMultiplePermissionRequest()
                }) {

                }
            }
            item {
                Row {
                    Box(
                        modifier =
                        Modifier.fillMaxWidth(0.8f)
                    )
                    {
                        Text(
                            color = Color.Black,
                            text = stringResource(R.string.block_power_menu),
                            fontSize = 30.sp,
                            modifier = Modifier.padding(20.dp)
                        )

                    }
                    Box(
                        modifier =
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Switch(
                            checked = checkedStatePowerMenu.value,
                            onCheckedChange = { checkedStatePowerMenu.value = it },
                            modifier = Modifier
                                .padding(20.dp),
                            colors = SwitchDefaults.colors(
                                //color of switches
                                checkedThumbColor = Color(0xFF00CC99),
                                checkedTrackColor = Color(0xFF7BB661),
                                uncheckedThumbColor = Color(0xFF83010B),
                                uncheckedTrackColor = Color(0xFFBB4C4C)
                            )

                        )
                    }
                }
            }
        }
    }
}

