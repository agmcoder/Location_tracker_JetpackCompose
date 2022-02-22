package com.devcode.powerlock.view.screens

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.devcode.powerlock.view.MenuToolbar
import com.devcode.powerlock.view.components.FloatingButtonGPS
import com.devcode.powerlock.view.screens.menu.Menu
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi
@Composable()
fun MenuPage(navController : NavController, sharedPreferences : SharedPreferences) {

	ScaffoldItem(navController, sharedPreferences)

}

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi
@Composable
fun ScaffoldItem(navController : NavController, sharedPreferences : SharedPreferences) {

	Scaffold(
		topBar = { MenuToolbar("Menu PowerLock") },
		content = { Menu(sharedPreferences) },
        floatingActionButton ={FloatingButtonGPS(navController = navController)}
		)

}










