package com.devcode.powerlock.composables.screens

import android.Manifest
import android.content.SharedPreferences
import android.os.Build
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.devcode.powerlock.R
import com.devcode.powerlock.model.getGPS
import com.devcode.powerlock.model.getLocation
import com.devcode.powerlock.theme.whiteBackground
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.orhanobut.logger.Logger

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi
@Composable
fun Menu(navController : NavController, sharedPreferences : SharedPreferences) {
	val ed : SharedPreferences.Editor = sharedPreferences.edit()
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

								checkedStateGps.value = it
								if (it){
									getLocation()
									var location= getGPS()
									Logger.d("latitud: ${location.latitude}")
									Logger.d("longitud ${location.longitude}")
								}else if (!it){
									Logger.d("gps off")
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

