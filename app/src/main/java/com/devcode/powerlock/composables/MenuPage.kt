package com.learnandroid.powerlock.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.Text
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
import com.devcode.powerlock.composables.Toolbar
import com.devcode.powerlock.theme.whiteBackground


@Composable()
fun MenuPage(navController : NavController) {
	ScaffoldItem()

}

@Preview(name="scaffold preview")
@Composable
fun ScaffoldItem() {
	Scaffold(
		topBar = { Toolbar("Menu PowerLock") },
		content = { BodyContentMenu() }
	)
}

@Preview(name="bodyContent")
@Composable
fun BodyContentMenu() {
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

@Composable
fun SwitchOptionItem(text : String, checkedValue : MutableState<Boolean>) {
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





