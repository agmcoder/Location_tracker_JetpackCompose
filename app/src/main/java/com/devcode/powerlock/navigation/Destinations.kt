package com.devcode.powerlock.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    //object Login: Destinations("login_page", "login", null)
    //object Register: Destinations("register_page", "Pantalla 2", null)
    object MenuPage: Screen("menu_page", "menu_page", Icons.Filled.Menu)
    //object RegisterPhone:Destinations("register_phone","registro_phone",null)
    object MapPage:Screen("Map_Page","Map",Icons.Filled.LocationOn)
    object Menu: Screen("menu","menu",Icons.Filled.Menu)

}
fun getListDestination():List<Screen>{
    var list= listOf<Screen>(Screen.Menu,
            Screen.MapPage
        )
    return list
}