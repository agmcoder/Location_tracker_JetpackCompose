package com.devcode.powerlock.view.components

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.devcode.powerlock.view.screens.menu.MenuViewModel
import com.orhanobut.logger.Logger

@SuppressLint("CoroutineCreationDuringComposition", "UnusedTransitionTargetStateParameter")
@Composable
fun GPSPowerButton(
	sharedPreferences : SharedPreferences,
	menuViewModel : MenuViewModel = hiltViewModel()
) {
	val onColor = Color.Green
	val offColor = Color.Red
	val context = LocalContext.current
	val initialStateButton = sharedPreferences.getBoolean("GPSState", false)
	var buttonState = rememberSaveable { mutableStateOf(initialStateButton) }

	val transition = updateTransition(targetState = buttonState, label = "")
	val color = transition.animateColor(label = "") { state ->
		when (state.value) {
			true -> onColor

			else -> offColor
		}

	}
	val size = transition.animateDp(label = "",

		) { state->

		when (state.value) {
			true -> 300.dp
			else -> 100.dp
		}
	}
	Logger.d("initial state button $initialStateButton")


	Button(
		modifier = Modifier
			.clip(MaterialTheme.shapes.large)
			.size(size.value)
			,

		colors = ButtonDefaults.buttonColors(
			backgroundColor = color.value,
			contentColor = Color.Black
		),
		onClick = {
			buttonState.value =when(buttonState.value){
				true-> {
					menuViewModel.stopLocationUpdates()
					false

				}
				else->{
					menuViewModel.startLocationUpdates(context)
					true
				}
			}

			menuViewModel.setGPSStateViewModel(buttonState.value)

		}
	)
	{
		Icon(
			imageVector = Icons.Rounded.LocationOn,
			"",
			modifier = Modifier.fillMaxSize()
		)
	}

}





