package com.devcode.powerlock.view.screens

import android.content.Context
import android.content.SharedPreferences
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devcode.powerlock.R
import com.devcode.powerlock.view.screens.viewmodel.MenuViewModel
import com.devcode.powerlock.model.getAndroidId
import com.devcode.powerlock.theme.whiteBackground
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.orhanobut.logger.Logger

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi
@Composable
fun Menu(navController : NavController, sharedPreferences : SharedPreferences, menuViewModel : MenuViewModel= hiltViewModel()) {
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
								if (state) menuViewModel.startLocationUpdates(context) else menuViewModel.stopLocationUpdates()



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

