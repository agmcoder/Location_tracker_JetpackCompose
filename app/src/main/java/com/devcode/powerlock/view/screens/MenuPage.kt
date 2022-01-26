package com.devcode.powerlock.view.screens

import android.content.SharedPreferences
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devcode.powerlock.view.BottomBar
import com.devcode.powerlock.view.MenuToolbar
import com.devcode.powerlock.navigation.getListDestination
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi
@Composable()
fun MenuPage(navController: NavController, sharedPreferences : SharedPreferences) {

    ScaffoldItem(navController, sharedPreferences)

}

@RequiresApi(Build.VERSION_CODES.Q)
@ExperimentalPermissionsApi
@Composable
fun ScaffoldItem(navController: NavController, sharedPreferences : SharedPreferences) {

    Scaffold(
        topBar = { MenuToolbar("Menu PowerLock") },
        bottomBar = {
            ButtonBottomBar(navController = navController)

        },
        content = { Menu(navController = navController, sharedPreferences) }
    )

}

@Composable
fun ButtonBottomBar(navController: NavController) {
    Row() {
        Button(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .padding(5.dp)
                .clip(RoundedCornerShape(5.dp)),
            onClick = { navController.navigate("map_page") }) {
            Text(text = "MAP")
        }
        Button(
            modifier = Modifier

                .fillMaxWidth(1F)
                .padding(5.dp)
                .clip(RoundedCornerShape(5.dp)),
            onClick = {}
        ) {
            Text(text = "otra opcion")
        }
    }
}

@Composable
fun NavBottomBar(navController: NavController) {
    val items = getListDestination()


    BottomBar(navController, items)


}










