package com.devcode.powerlock.view.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devcode.powerlock.R

@Composable
fun FloatingButtonGPS(navController : NavController) {
	val context = LocalContext.current
	FloatingActionButton(
		modifier = Modifier
			.size(100.dp),
		backgroundColor = MaterialTheme.colors.primary,

		onClick = {
			Toast.makeText(context, "open MapView", Toast.LENGTH_SHORT).show()
			navController.navigate("map_page")

		}

	)
	{
		Box {
			Icon(
				modifier = Modifier.fillMaxSize(0.6f),
				painter = painterResource(id = R.drawable.map_icon),
				contentDescription = null
			)
		}

	}

}