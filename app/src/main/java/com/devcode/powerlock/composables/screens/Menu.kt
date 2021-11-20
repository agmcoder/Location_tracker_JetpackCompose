package com.devcode.powerlock.composables.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.devcode.powerlock.composables.components.SwitchOptionItem
import com.devcode.powerlock.theme.whiteBackground

@Composable
fun Menu(navController: NavController) {
    val checkedStateGps = rememberSaveable { mutableStateOf(false) }
    val checkedStatePowerMenu = rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground)
    )
    {
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            item {
                SwitchOptionItem(
                    text = "localización GPS",
                    checkedValue = checkedStateGps
                )

            }
            item {
                SwitchOptionItem(
                    text = "Bloqueo Power Menu",
                    checkedValue = checkedStatePowerMenu
                )
            }
        }
    }
}
