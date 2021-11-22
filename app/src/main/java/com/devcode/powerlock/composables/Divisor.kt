package com.devcode.powerlock.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Divisor() {
    Spacer(modifier = Modifier.padding(top = 5.dp))
    Divider(color = Color.Black, thickness = 1.dp)
    Spacer(modifier = Modifier.padding(top = 5.dp))

}