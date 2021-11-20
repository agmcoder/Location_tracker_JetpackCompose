package com.devcode.powerlock.composables.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devcode.powerlock.composables.BottomBar
import com.devcode.powerlock.composables.Toolbar
import com.devcode.powerlock.navigation.getListDestination


@Composable()
fun MenuPage(navController: NavController) {

    ScaffoldItem(navController)

}

@Composable
fun ScaffoldItem(navController: NavController) {

    Scaffold(
        topBar = { Toolbar("Menu PowerLock") },
        bottomBar = {
            ButtonBottomBar(navController = navController)

        },
        content = { Menu(navController = navController) }
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










