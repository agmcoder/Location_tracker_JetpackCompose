package com.devcode.powerlock.composables.screens

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.devcode.powerlock.composables.MapToolBar

@Composable
fun MapPage(navController: NavController) {
    Scaffold(
        topBar={ MapToolBar()},
        content = {MapContent()},



    )
}
@Preview(name="map content")
@Composable
fun MapContent() {
    
}
