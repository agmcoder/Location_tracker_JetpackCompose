package com.devcode.powerlock.view.screens

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devcode.powerlock.R
import com.devcode.powerlock.data.network.LoginDeviceState
import com.devcode.powerlock.view.components.CustomSpacer
import com.devcode.powerlock.model.loginChecker
import com.devcode.powerlock.theme.primaryColor
import com.devcode.powerlock.view.screens.login.LoginPageViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CommitPrefEdits", "NewApi")
@ExperimentalPermissionsApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginPage(navController : NavController, sharedPreferences : SharedPreferences, loginPageViewModel : LoginPageViewModel  = hiltViewModel()) {
	val ed = sharedPreferences.edit()
	val coroutineScope= rememberCoroutineScope()
	val rememberUser = sharedPreferences.getString("user", "")
	val rememberPassword = sharedPreferences.getString("password", "")
	val initialCheckBoxState = sharedPreferences.getBoolean("rememberUser", false)
	val checkBoxState = rememberSaveable { mutableStateOf(initialCheckBoxState) }
	val (emailRequest, passwordRequest) = FocusRequester.createRefs()
	//val image = imageResource(id= R.drawable.logo)
	val context = LocalContext.current
	val focusManager = LocalFocusManager.current
	val image = painterResource(R.drawable.logo)
	var emailValue : MutableState<String>
	var passwordValue : MutableState<String>
	val passwordVisibility = remember { mutableStateOf(false) }
	val focusRequester = remember { FocusRequester() }
	val scrollState = rememberScrollState()



	emailValue = if (rememberUser != null && checkBoxState.value) {
		rememberSaveable { mutableStateOf(rememberUser) }

	} else
		rememberSaveable { mutableStateOf("") }


	passwordValue = if (rememberPassword != null && checkBoxState.value) {
		rememberSaveable { mutableStateOf(rememberPassword) }

	} else
		rememberSaveable { mutableStateOf("") }

	//we start to implement login screen UI-->

	Box(

		modifier = Modifier
			.fillMaxSize(),
		contentAlignment =
		Alignment.BottomCenter
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.clickable { focusManager.clearFocus() },
			horizontalAlignment = Alignment.CenterHorizontally
		) {

			Box(
				modifier = Modifier
					.fillMaxHeight(0.3f)
					.background(Color.White), contentAlignment =
				Alignment.Center

			) {

				Image(
					painter = image, contentDescription = "",
					alignment = Alignment.Center
				)

			}
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				modifier =
				Modifier
					.fillMaxSize()
					.clip(RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp))
					.background(Color.Gray)

			) {

				Text(
					modifier = Modifier.padding(5.dp),
					text = "Sign In",
					style = TextStyle(
						fontWeight = FontWeight.Bold,
						letterSpacing = 2.sp
					),

					fontSize = 30.sp
				)

				Column(
					horizontalAlignment = Alignment.CenterHorizontally,
					verticalArrangement = Arrangement.Center,
					modifier = Modifier
						.fillMaxSize()
						.padding(10.dp)
						.verticalScroll(scrollState)

				) {

					Spacer(modifier = Modifier.padding(20.dp))




					Column(horizontalAlignment = Alignment.CenterHorizontally)
					{
						OutlinedTextField(//USER

							keyboardOptions = KeyboardOptions(
								keyboardType = KeyboardType.Email,
								imeAction = ImeAction.Next
							),
							keyboardActions = KeyboardActions(onNext = {
								focusManager.moveFocus(
									FocusDirection.Next
								)
							}),
							value = emailValue.value,
							onValueChange = {
								emailValue.value = it
							},
							label = {
								Text(
									text = "Email Address",
									color = Color.Black
								)
							},
							placeholder = { Text(text = "Email Address", color = Color.Black) },
							singleLine = true,
							modifier = Modifier
								.fillMaxWidth(0.8f)
								.focusable()
								.focusOrder(emailRequest) { next = passwordRequest }

						)
					}



					OutlinedTextField(

						value = passwordValue.value,
						keyboardOptions = KeyboardOptions(
							keyboardType = KeyboardType.Password
						),
						keyboardActions = KeyboardActions(),
						onValueChange = { passwordValue.value = it },
						trailingIcon = {
							IconButton(onClick = {
								passwordVisibility.value = !passwordVisibility.value
							}) {

								Icon(
									imageVector = ImageVector.vectorResource(
										id = R.drawable.password_eye
									),
									contentDescription = "",
									tint = if (passwordVisibility.value)
										primaryColor else Color.Black

								)

							}
						},
						label = { Text(text = "Password", color = Color.Black) },
						placeholder = { Text(text = "Password", color = Color.Black) },
						singleLine = true,
						visualTransformation = if (passwordVisibility.value) VisualTransformation.None
						else PasswordVisualTransformation(),
						modifier = Modifier
							.fillMaxWidth(0.8f)
							.focusable()

						//onImeActionPerformed = { _, controller ->
						//controller?.hideSoftwareKeyboard()
						//}

					)



					Spacer(modifier = Modifier.padding(10.dp))
					Button(
						onClick = {
							coroutineScope.launch {
								loginChecker(emailValue, passwordValue, context, navController, ed).collect {
									when(it) {
										LoginDeviceState.OBSERVER -> navController.navigate("map_page")
										LoginDeviceState.EMITTER -> navController.navigate("menu_page")
										LoginDeviceState.ERROR ->  Toast.makeText(context, "Authentication failed.", Toast.LENGTH_SHORT).show()
									}
								}

							}

						},
						modifier = Modifier
							.fillMaxWidth(0.8f)
							.height(50.dp)
					) {
						Text(text = "Sign In", fontSize = 20.sp)
					}


					CustomSpacer()
					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.Center
					) {
						Text(
							text = "remember user and password?"
						)
						Checkbox(checked = checkBoxState.value,
							onCheckedChange =
							{
								checkBoxState.value = it
								if (!checkBoxState.value) {
									ed.putString("user", "")
									ed.putString("password", "")
									ed.apply()
								}
								ed.putBoolean("rememberUser", checkBoxState.value)
								ed.apply()

							}
						)

					}
					CustomSpacer()
					Text(
						text = "Create An Account",
						modifier = Modifier.clickable(onClick = {
							navController.navigate("register_page") {
								//popUpTo = navController.graph.startDestination
								//launchSingleTop = true
							}
						})
					)


					CustomSpacer()

				}
			}
		}
	}
}






















