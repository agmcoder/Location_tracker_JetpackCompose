package com.devcode.powerlock.composables.permisos

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@SuppressLint("CommitPrefEdits")
@ExperimentalPermissionsApi
@Composable
fun Permission(sharedPreferences : SharedPreferences) {
	val ed : SharedPreferences.Editor = sharedPreferences.edit()
	val initialValueShowed = sharedPreferences.getBoolean("showed", false)
	val showed= rememberSaveable { mutableStateOf(initialValueShowed)}

	var doNotShowRationale = rememberSaveable { mutableStateOf(false) }
	val gpsPermissionState = rememberMultiplePermissionsState(
		listOf(
			android.Manifest.permission.ACCESS_COARSE_LOCATION,
			android.Manifest.permission.ACCESS_FINE_LOCATION
		)
	)
	if (!showed.value) {
		PermissionsRequired(
			multiplePermissionsState = gpsPermissionState,
			permissionsNotGrantedContent = {
				if (doNotShowRationale.value) {

				} else {
					Column {
						Text("The camera is important for this app. Please grant the permission.")
						Spacer(modifier = Modifier.height(8.dp))
						Row {
							Button(onClick = {
								ed.putBoolean("showed", true)
								ed.apply()
								gpsPermissionState.launchMultiplePermissionRequest()

							}) {
								Text("Ok!")
							}
							Spacer(Modifier.width(8.dp))
							Button(onClick = { doNotShowRationale.value = true }) {
								Text("Nope")
							}
						}
					}
				}

			},
			permissionsNotAvailableContent = {
				Column {
					Text(
						"Camera permission denied. See this FAQ with information about why we " +
								"need this permission. Please, grant us access on the Settings screen."
					)
					Spacer(modifier = Modifier.height(8.dp))
					Button(onClick = {}) {
						Text("Open Settings")
					}
				}
			}
		) {
			Text("Camera permission Granted")
		}
	}
	else{
		Text(text = "solicitud de permisos ya mostrados ${showed.value}")

	}

}




