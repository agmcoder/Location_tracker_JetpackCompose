package com.devcode.powerlock.view.components

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.lang.reflect.Modifier
import java.nio.file.Files.size

@Composable
fun FloatingButtonGPS(navController : NavController) {
	val context= LocalContext.current
	FloatingActionButton(
		onClick = {
			Toast.makeText(context,"open MapView",Toast.LENGTH_SHORT ).show()
			navController.navigate("map_page")

		}


	)
	{
		Icon(Icons.Default.LocationOn,"icon gps")
	}

}