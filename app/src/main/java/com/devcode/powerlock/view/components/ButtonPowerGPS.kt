package com.devcode.powerlock.view.components

import androidx.compose.foundation.background
import androidx.compose.material.Icon
import androidx.compose.material.IconToggleButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun ButtonPowerGPS() {
	val colorVerde = Color.Green
	val colorRojo = Color.Red
	IconToggleButton(
		checked = true,
		onCheckedChange = {

		},
		modifier = Modifier.background()
	)
	{
		Icon(Icons.Rounded.LocationOn, "")
	}
}