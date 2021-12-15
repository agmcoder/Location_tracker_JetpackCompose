package com.devcode.powerlock.composables

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.devcode.powerlock.R

@Composable
fun MenuToolbar(title : String) {
	TopAppBar(
		title = { Text(text = title) },
		backgroundColor = colorResource(id = R.color.purple_200)
	)
}

@Composable
fun MapToolBar() {
	TopAppBar(
		title = { Text(text = stringResource(id = R.string.titleMapToolBar)) }
	)
}
