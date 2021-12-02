package com.devcode.powerlock.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devcode.powerlock.composables.screens.MapPage
import com.devcode.powerlock.composables.screens.Menu

/*@Composable
fun NavigationBottomBarHost() {
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = "menu",builder= {
        composable("map_page",content={ MapPage(navController=navController) })
        composable("menu", content = { Menu(navController = navController) })
    })

}*/