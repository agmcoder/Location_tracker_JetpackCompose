package com.devcode.powerlock.composables.permisos

import android.Manifest
import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionsRequired
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("CommitPrefEdits")
@ExperimentalPermissionsApi
@Composable
fun Permission(sharedPreferences : SharedPreferences) {
	var devolver : Boolean
	val ed : SharedPreferences.Editor = sharedPreferences.edit()
	val initialValueShowed = sharedPreferences.getBoolean("showed", false)
	val showed = rememberSaveable { mutableStateOf(initialValueShowed) }

	var doNotShowRationale = rememberSaveable { mutableStateOf(false) }
	val gpsPermissionState = rememberMultiplePermissionsState(
		listOf(
			android.Manifest.permission.ACCESS_COARSE_LOCATION,
			android.Manifest.permission.ACCESS_FINE_LOCATION,
		)
	)
	val gpsBackgroundPermissionState= rememberPermissionState(Manifest.permission.ACCESS_BACKGROUND_LOCATION)


	if (!showed.value) {
		PermissionsRequired(
			multiplePermissionsState = gpsPermissionState,
			permissionsNotGrantedContent = {
				if (doNotShowRationale.value) {
					//mostrar porque la app necesita los permisos
				} else {
					if (!showed.value) {
						Column {
							Text("The GPS is important for this app. Please grant the permission.")
							Spacer(modifier = Modifier.height(8.dp))
							ed.putBoolean("showed", true)
							ed.apply()

						}
					}
				}
			},
			permissionsNotAvailableContent = {
				Column {
					Text(
						"GPS permission denied. See this FAQ with information about why we " +
								"need this permission. Please, grant us access on the Settings screen."
					)
					Spacer(modifier = Modifier.height(8.dp))

				}
			}
		) {
			Text("Camera permission Granted")
		}
	} else {
		Column() {
			Text(text = "solicitud de permisos ya mostrados ${showed.value}")
		}

	}

}





