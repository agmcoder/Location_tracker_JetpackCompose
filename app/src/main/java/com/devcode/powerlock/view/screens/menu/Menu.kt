package com.devcode.powerlock.view.screens

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devcode.powerlock.model.getAndroidId
import com.devcode.powerlock.theme.whiteBackground
import com.devcode.powerlock.view.components.GPSPowerButton
import com.devcode.powerlock.view.screens.menu.MenuViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.orhanobut.logger.Logger

@SuppressLint("CommitPrefEdits")
@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi
@Composable
fun Menu(sharedPreferences : SharedPreferences, menuViewModel : MenuViewModel = hiltViewModel()) {
	val context : Context = LocalContext.current
	getAndroidId(context)
	val ed : SharedPreferences.Editor = sharedPreferences.edit()
	//val context = LocalContext.current
	val initialValueGpsState = sharedPreferences.getBoolean("GPS", false)
	val checkedStateGps = rememberSaveable { mutableStateOf(initialValueGpsState) }

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(whiteBackground),
		contentAlignment = Alignment.Center,

		)
	{
		Column(
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {
				Box(
					modifier =Modifier.size(100.dp)
				){
					GPSPowerButton()
				}
		}

	}
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

