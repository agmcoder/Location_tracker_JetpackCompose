package com.devcode.powerlock.composables.permisos

import android.content.Context
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect

@Composable
fun PermissionUI(
	context : Context,
	permission : String,
	permissionRationale : String,
	scaffoldState : ScaffoldState,
	permissionAction : (PermissionAction) -> Unit
) {
	val permissionGranted = Common.checkIfPermissionGranted(context, permission)
	if (permissionGranted) {
		permissionAction(PermissionAction.OnPermissionGranted)
		return
	}
	val launcher = rememberLauncherForActivityResult(
		ActivityResultContracts.RequestPermission()
	) { isGranted : Boolean ->
		if (isGranted) {
			permissionAction(PermissionAction.OnPermissionGranted)
		} else {
			permissionAction(PermissionAction.OnPermissionDenied)
		}
	}
	val showPermissionRationale = Common.shouldShowPermissionRationale(context, permission)
	if (showPermissionRationale) {
		LaunchedEffect(showPermissionRationale) {
			val snackBarResult = scaffoldState.snackbarHostState.showSnackbar(
				message = permissionRationale,
				actionLabel = "grant Access",
				duration = SnackbarDuration.Long
			)
			when (snackBarResult) {
				SnackbarResult.Dismissed -> {
					permissionAction(PermissionAction.OnPermissionDenied)
				}
				SnackbarResult.ActionPerformed -> {
					launcher.launch(permission)
				}
			}
		}

	} else {
		SideEffect {
			launcher.launch(permission)
		}
	}
}