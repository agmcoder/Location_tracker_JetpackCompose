package com.devcode.powerlock.view.screens.menu

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.devcode.powerlock.model.getAndroidId
import com.devcode.powerlock.theme.whiteBackground
import com.devcode.powerlock.view.components.GPSPowerButton
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@SuppressLint("CommitPrefEdits")
@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi
@Composable
fun Menu(sharedPreferences : SharedPreferences) {
	val context : Context = LocalContext.current
	getAndroidId(context)
	//val context = LocalContext.current

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
				modifier = Modifier.size(100.dp)
			) {
				GPSPowerButton(sharedPreferences)
			}
		}

	}

}

