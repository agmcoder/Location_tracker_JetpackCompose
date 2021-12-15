package com.devcode.powerlock.composables.screens

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.devcode.powerlock.R
import com.devcode.powerlock.model.getAndroidId
import com.devcode.powerlock.model.getFusedLocationProviderClient
import com.devcode.powerlock.theme.whiteBackground
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi
@Composable
fun Menu(navController : NavController, sharedPreferences : SharedPreferences) {
	val ed : SharedPreferences.Editor = sharedPreferences.edit()
	val initialValueShowed = sharedPreferences.getBoolean("showed", false)

	var showed = rememberSaveable { mutableStateOf(initialValueShowed) }
	val gpsPermissionState = rememberMultiplePermissionsState(
		listOf(
			android.Manifest.permission.ACCESS_COARSE_LOCATION,
			android.Manifest.permission.ACCESS_FINE_LOCATION,
		)
	)
	var granted : Int
	var doNotShowRationale = rememberSaveable { mutableStateOf(false) }
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
	val gpsBackgroundPermissionState =
		rememberPermissionState(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
	val context = LocalContext.current
	val initialValueGPS = sharedPreferences.getBoolean("gps", false)
	val initialValuePowerMenu = sharedPreferences.getBoolean("power", false)
	val checkedStateGps = rememberSaveable { mutableStateOf(initialValueGPS) }
	val checkedStatePowerMenu = rememberSaveable { mutableStateOf(initialValuePowerMenu) }

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
							{
								//request permissions if not is showed before
								gpsMultiplePermissionsState.launchMultiplePermissionRequest()
								when {
									gpsMultiplePermissionsState.allPermissionsGranted -> {
										gpsBackgroundPermissionState.launchPermissionRequest()
									}

								}
								when {
									//when all gps permissions and background location are granted do this
									gpsMultiplePermissionsState.allPermissionsGranted &&
											gpsBackgroundPermissionState.hasPermission -> {
										checkedStateGps.value = it
										ed.putBoolean("gps", checkedStateGps.value)
										ed.apply()
										val fusedLocationClient=getFusedLocationProviderClient(context)
										if (ActivityCompat.checkSelfPermission(
												context,
												Manifest.permission.ACCESS_FINE_LOCATION
											) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
												context,
												Manifest.permission.ACCESS_COARSE_LOCATION
											) != PackageManager.PERMISSION_GRANTED
										) {
											var location =fusedLocationClient.lastLocation
											getAndroidId(context)?.let { it1 ->
												Firebase.firestore.collection("devices")
													.document(it1).set(location.result.latitude,
														SetOptions.merge())
											}
											getAndroidId(context)?.let { it1 ->
												Firebase.firestore.collection("devices")
													.document(it1).set(location.result.longitude,
														SetOptions.merge())
											}

										}

									}
									//when all gps permissions and background are not granted do this                                                                                                                                                                         ºwhen all gps permission are not granted do this
									!gpsMultiplePermissionsState.allPermissionsGranted ||
											!gpsBackgroundPermissionState.hasPermission -> {
										checkedStateGps.value = !it
										ed.putBoolean("gps", checkedStateGps.value)
										ed.apply()
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

