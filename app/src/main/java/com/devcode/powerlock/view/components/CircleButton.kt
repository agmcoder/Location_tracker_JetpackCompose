package com.devcode.powerlock.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun CircleButton() {
	OutlinedButton(
		modifier= Modifier
			.size(50.dp),
		shape=CircleShape,
		border= BorderStroke(2.dp, MaterialTheme.colors.primary),
		contentPadding = PaddingValues(0.dp),
		colors= ButtonDefaults.outlinedButtonColors(contentColor=MaterialTheme.colors.primary),
		onClick = {
			//change the state of gps and colors

		}
	) {
		Icon(
			Icons.Default.LocationOn,
			contentDescription = "icon inside button"
		)
	}
}