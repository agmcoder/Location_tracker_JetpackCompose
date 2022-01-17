package com.devcode.powerlock.composables.screens

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.devcode.powerlock.model.getAndroidId
import com.devcode.powerlock.model.saveLocation
import com.devcode.powerlock.model.setGPSLocationState
import com.devcode.powerlock.theme.whiteBackground
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.orhanobut.logger.Logger

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi
@Composable
fun Menu(navController : NavController, sharedPreferences : SharedPreferences) {
	val context : Context = LocalContext.current
	var fusedLocationProviderClient : FusedLocationProviderClient
	val androidID : String? = getAndroidId(context)
	val ed : SharedPreferences.Editor = sharedPreferences.edit()
	//val context = LocalContext.current
	val initialValueGpsState = sharedPreferences.getBoolean("GPS", false)
	val initialValuePowerMenu = sharedPreferences.getBoolean("power", false)
	val checkedStateGps = rememberSaveable { mutableStateOf(initialValueGpsState) }
	val checkedStatePowerMenu = rememberSaveable { mutableStateOf(initialValuePowerMenu) }
	Logger.d("initial value gpsState Menu.kt -> $initialValueGpsState")

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(whiteBackground),
		contentAlignment = Alignment.Center,

		)
	{

		ed.apply()
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
							{ state ->

								checkedStateGps.value = state


								if (checkedStateGps.value) {
									fusedLocationProviderClient =
										LocationServices.getFusedLocationProviderClient(context)
									if (ActivityCompat.checkSelfPermission(
											context,
											Manifest.permission.ACCESS_FINE_LOCATION
										) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
											context,
											Manifest.permission.ACCESS_COARSE_LOCATION
										) == PackageManager.PERMISSION_GRANTED
									) {
										//after checking permissions and they are granted we can
										//get the location.
										fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->

											Logger.d("location in switch ${location.latitude} <--> ${location.longitude}")

											if (androidID != null) {
												saveLocation(location, androidID)
												setGPSLocationState(state,androidID)
											}

										}

										//the next parragraph is if the conditional if has != instead of ==

										// TODO: Consider calling
										//    ActivityCompat#requestPermissions
										// here to request the missing permissions, and then overriding
										//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
										//                                          int[] grantResults)
										// to handle the case where the user grants the permission. See the documentation
										// for ActivityCompat#requestPermissions for more details.

									} else {
										checkedStateGps.value = !state

									}

								}
								else{
									if (androidID != null) {
										setGPSLocationState(state, androidID = androidID)
									}
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
							onCheckedChange = {
								checkedStatePowerMenu.value = it
								ed.putBoolean("power", checkedStatePowerMenu.value)
								ed.apply()
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
		}
	}
}

