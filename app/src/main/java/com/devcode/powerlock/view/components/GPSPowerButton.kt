package com.devcode.powerlock.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun GPSPowerButton() {
	val onColor = Color.Green
	val offColor = Color.Red
	val initialStateButton=rememberSaveable { mutableStateOf(false)}
	val buttonState=rememberSaveable { mutableStateOf(initialStateButton)}
	Button(
		modifier = Modifier
			.clip(RoundedCornerShape(100))
			.fillMaxSize(),
		colors=ButtonDefaults.buttonColors(backgroundColor =onColor, contentColor = Color.Black),
		onClick={

		}
		//modifier = Modifier.background()
	)
	{
		Icon(Icons.Rounded.LocationOn, ""
		,modifier=Modifier.fillMaxSize(0.6f))
	}
}