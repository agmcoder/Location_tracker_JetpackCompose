package com.devcode.powerlock.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector
) {

    //object Login: Destinations("login_page", "login", null)
    //object Register: Destinations("register_page", "Pantalla 2", null)
    object MenuPage: Destinations("menu_page", "menu_page", Icons.Filled.Menu)
    //object RegisterPhone:Destinations("register_phone","registro_phone",null)
    object MapPage:Destinations("Map_Page","Map",Icons.Filled.LocationOn)
    object Menu: Destinations("menu","menu",Icons.Filled.Menu)

}
fun getListDestination():List<Destinations>{
    var list= listOf<Destinations>(Destinations.Menu,
            Destinations.MapPage
        )
    return list
}