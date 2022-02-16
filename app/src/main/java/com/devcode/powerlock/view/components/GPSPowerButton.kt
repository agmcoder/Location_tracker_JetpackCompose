package com.devcode.powerlock.view.components

import android.annotation.SuppressLint
import android.content.SharedPreferences
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.devcode.powerlock.view.screens.menu.MenuViewModel
import com.orhanobut.logger.Logger

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GPSPowerButton(
	sharedPreferences : SharedPreferences,
	menuViewModel : MenuViewModel = hiltViewModel()
) {
	val onColor = Color.Green
	val offColor = Color.Red
	val context = LocalContext.current

	val initialStateButton = sharedPreferences.getBoolean("GPSState", false)
	val buttonState = rememberSaveable { mutableStateOf(initialStateButton) }

	Logger.d("initial state button ${initialStateButton}")
	fun getColor() : Color {
		return if (buttonState.value)
			onColor
		else offColor

	}

	Button(
		modifier = Modifier
			.clip(RoundedCornerShape(100))
			.fillMaxSize(),
		colors = ButtonDefaults.buttonColors(
			backgroundColor = getColor(),
			contentColor = Color.Black
		),
		onClick = {

			when (buttonState.value) {
				true -> {
					buttonState.value = false
					menuViewModel.stopLocationUpdates()
				}

				false -> {
					buttonState.value = true
					menuViewModel.startLocationUpdates(context)

				}
			}
			menuViewModel.setGPSSTATEviewModel(buttonState.value)

		}
		//modifier = Modifier.background()
	)
	{
		Icon(
			imageVector = Icons.Rounded.LocationOn,
			"",
			modifier = Modifier.fillMaxSize(0.6f)
		)
	}

}





