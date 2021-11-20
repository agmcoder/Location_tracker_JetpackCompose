package com.devcode.powerlock.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devcode.powerlock.composables.LoginPage
import com.devcode.powerlock.composables.MapPage
import com.devcode.powerlock.composables.MenuPage
import com.learnandroid.powerlock.composables.RegisterPage
import com.devcode.powerlock.composables.screens.RegisterPhonePage

@Composable
fun NavigationHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login_page", builder = {
        composable("login_page", content = { LoginPage(navController = navController) })
        composable("register_page", content = { RegisterPage(navController = navController) })
        composable("menu_page", content = { MenuPage(navController = navController) })
        composable(
            "register_phone",
            content = { RegisterPhonePage(navController = navController) })
        composable("map_page", content = { MapPage(navController = navController)})

    })
    
}