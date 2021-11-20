package com.devcode.powerlock.composables.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SwitchOptionItem(text: String, checkedValue: MutableState<Boolean>) {
    Row {
        Box(
            modifier =
            Modifier.fillMaxWidth(0.8f)
        )
        {
            Text(
                color = Color.Black,
                text = text,
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
                checked = checkedValue.value,
                onCheckedChange = { checkedValue.value = it },
                modifier = Modifier
                    //.fillMaxSize()
                    .padding(20.dp)

            )
        }
    }

}