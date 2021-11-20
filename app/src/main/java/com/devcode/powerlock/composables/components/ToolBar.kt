package com.devcode.powerlock.composables

import android.widget.Toolbar
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.devcode.powerlock.R


@Composable
fun Toolbar(title:String) {
    TopAppBar(
        title = { Text(text = title)},
        backgroundColor = colorResource(id = R.color.purple_200)
    ) 
        
    
}
