package com.learnandroid.powerlock.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devcode.powerlock.composables.Toolbar
import com.devcode.powerlock.theme.whiteBackground


@Composable
fun MenuPage(navController: NavController) {
    Scaffold(
        topBar = { Toolbar("Menu PowerLock") },
        content = { BodyContentMenu() }
    )


}

@Preview
@Composable
fun BodyContentMenu() {
    val checkedStateGps = remember { mutableStateOf(true) }
    val checkedStatePowerMenu = remember { mutableStateOf(true) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground)
    )
    {
        LazyColumn() {
            item {
                Spacer(modifier = Modifier.height(50.dp))
            }
            item {

                Row {
                    Box(
                        modifier =
                        Modifier.fillMaxWidth(0.8f)
                    )
                    {
                        Text(
                            color = Color.Black,
                            text = "Localización GPS",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(20.dp)
                        )


                    }
                    Box(
                        modifier =
                        Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Switch(
                            checked = checkedStateGps.value,
                            onCheckedChange = { checkedStateGps.value = it },
                            modifier = Modifier
                                //.fillMaxSize()
                                .padding(20.dp)

                        )
                    }
                }

            }
            item {
                Row {
                    Box(modifier = Modifier.fillMaxWidth(0.8f)) {
                        Text(
                            text = "Bloqueo Power Menu",
                            fontSize = 30.sp,
                            modifier = Modifier.padding(20.dp),
                            color = Color.Black


                        )
                    }
                    Box(contentAlignment = Alignment.CenterEnd) {


                        Switch(
                            checked = checkedStatePowerMenu.value,
                            onCheckedChange = { checkedStatePowerMenu.value = it },
                            modifier = Modifier
                                //.fillMaxSize()
                                .padding(20.dp)
                        )
                    }
                }

            }
        }


    }


}





