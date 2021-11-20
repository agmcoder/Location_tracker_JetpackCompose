package com.devcode.powerlock.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.devcode.powerlock.composables.screens.Menu
import com.devcode.powerlock.navigation.NavigationBottomBarHost
import com.devcode.powerlock.navigation.getListDestination
import com.devcode.powerlock.theme.whiteBackground


@Composable()
fun MenuPage(navController: NavController) {

    ScaffoldItem(navController)

}

@Composable
fun ScaffoldItem(navController: NavController) {

    Scaffold(
        topBar = { Toolbar("Menu PowerLock") },
        bottomBar = { Button(onClick = {navController.navigate("map_page")}) {
            
        } },
        content = { Menu(navController = navController)}
    )

}

@Composable
fun NavBottomBar(navController: NavController) {
    val items = getListDestination()


    BottomBar(navController, items)


}








