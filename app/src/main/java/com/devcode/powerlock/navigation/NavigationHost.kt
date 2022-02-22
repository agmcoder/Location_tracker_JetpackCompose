package com.devcode.powerlock.navigation

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devcode.powerlock.view.screens.MenuPage
import com.devcode.powerlock.view.screens.RegisterPage
import com.devcode.powerlock.view.screens.RegisterPhonePage
import com.devcode.powerlock.view.screens.login.LoginPage
import com.devcode.powerlock.view.screens.map.MapPage
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi
@Composable
fun NavigationHost(sharedPreference : SharedPreferences) {
	val navController = rememberNavController()

	NavHost(navController = navController, startDestination = "login_page", builder = {
		composable(route = "login_page",
			content = {
				LoginPage(navController = navController, sharedPreference)
			})
		composable(
			route = "register_page",
			content = { RegisterPage(navController = navController) })
		composable(
			route = "menu_page",
			content = { MenuPage(navController = navController, sharedPreference) })
		composable(
			route = "register_phone",
			content = { RegisterPhonePage(navController = navController) })
		composable(route = "map_page",
			content = { MapPage(navController = navController) })

	})

}