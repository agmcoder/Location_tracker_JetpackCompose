package com.devcode.powerlock.model

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.rememberPermissionState

@ExperimentalPermissionsApi
@Composable
 fun GPSPermission(){

// Track if the user doesn't want to see the rationale any more.
	var doNotShowRationale = rememberSaveable { mutableStateOf(false) }

	val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)
	PermissionRequired(
		permissionState = cameraPermissionState,
		permissionNotGrantedContent = {
			if (doNotShowRationale.value) {
				Text("Feature not available")
			} else {
				Column {
					Text("The camera is important for this app. Please grant the permission.")
					Spacer(modifier = Modifier.height(8.dp))
					Row {
						Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
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
		permissionNotAvailableContent = {
			Column {
				Text(
					"Camera permission denied. See this FAQ with information about why we " +
							"need this permission. Please, grant us access on the Settings screen."
				)
				Spacer(modifier = Modifier.height(8.dp))

			}
		}
	) {
		Text("Camera permission Granted")
	}}