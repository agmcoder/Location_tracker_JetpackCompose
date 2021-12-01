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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.devcode.powerlock.R
import com.devcode.powerlock.composables.components.SwitchOptionItem
import com.devcode.powerlock.theme.whiteBackground
import com.orhanobut.logger.Logger

@Composable
fun Menu(navController: NavController) {
    val checkedStateGps = rememberSaveable { mutableStateOf(false) }
    val checkedStatePowerMenu = rememberSaveable { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground),
        contentAlignment = Alignment.Center,


    )
    {
        LazyColumn {
            item {
                SwitchOptionItem(
                    text = stringResource(R.string.location_gps),
                    checkedValue = checkedStateGps
                )

            }
            item {
                SwitchOptionItem(
                    text = stringResource(R.string.block_power_menu),
                    checkedValue = checkedStatePowerMenu
                )
            }
        }
    }
}
