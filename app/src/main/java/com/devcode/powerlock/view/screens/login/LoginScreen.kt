package com.devcode.powerlock.view.screens.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devcode.powerlock.R
import com.devcode.powerlock.data.firebaseprovider.LoginDeviceState
import com.devcode.powerlock.model.loginChecker
import com.devcode.powerlock.theme.primaryColor
import com.devcode.powerlock.view.components.CustomSpacer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@SuppressLint("CommitPrefEdits", "NewApi")
@ExperimentalPermissionsApi
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginPage(
	navController : NavController,
	sharedPreferences : SharedPreferences
) {
	val coroutineScope = rememberCoroutineScope()
	val ed = sharedPreferences.edit()
	val checkBoxState = rememberSaveable { mutableStateOf(false) }
	val (emailRequest, passwordRequest) = FocusRequester.createRefs()
	val context = LocalContext.current
	val image = painterResource(R.drawable.logo2)
	val emailValue = rememberSaveable { mutableStateOf("") }
	val passwordValue = rememberSaveable { mutableStateOf("") }
	val passwordVisibility = remember { mutableStateOf(false) }
	val scrollState = rememberScrollState()
	val focusManager = LocalFocusManager.current
	val focusRequester = FocusRequester()

	//we start to implement login screen UI-->

	Column(
		modifier = Modifier
			.fillMaxSize()
			.background(MaterialTheme.colors.background)
			.clickable { focusManager.clearFocus() }
			.pointerInput(Unit) {
				detectTapGestures(
					onTap = {
						focusManager.clearFocus()
					}
				)

			},
		horizontalAlignment = Alignment.CenterHorizontally
	) {

		Box(//this is the logo box
			modifier = Modifier
				.fillMaxHeight(0.3f)
				.background(MaterialTheme.colors.background),
			contentAlignment = Alignment.Center

		) {

			Image(
				painter = image, contentDescription = "logo",
				alignment = Alignment.Center,
				modifier = Modifier.fillMaxSize()
			)

		}
		LazyColumn(
			verticalArrangement = Arrangement.Center,
			horizontalAlignment = Alignment.CenterHorizontally
		) {

			item {
				Text(
					modifier = Modifier.padding(5.dp),
					text = stringResource(id = R.string.signIn),
					style = MaterialTheme.typography.h2,
					color = MaterialTheme.colors.onPrimary

				)
			}


			item {

				Spacer(modifier = Modifier.padding(20.dp))
			}



			item {
				OutlinedTextField(

					value = emailValue.value,
					keyboardOptions = KeyboardOptions(
						imeAction = ImeAction.Next,
						keyboardType = KeyboardType.Password
					),
					keyboardActions = KeyboardActions(
					),
					onValueChange = { emailValue.value = it },

					label = {
						Text(
							text = "user email",
							color = MaterialTheme.colors.onPrimary
						)
					},
					placeholder = {
						Text(
							text = "user email",
							color = MaterialTheme.colors.secondaryVariant
						)
					},
					singleLine = true,
					modifier = Modifier
						.fillMaxWidth(0.8f)
						.focusRequester(focusRequester = focusRequester),
					textStyle = TextStyle(color = MaterialTheme.colors.onPrimary)

				)
			}
			item {
				OutlinedTextField(

					value = passwordValue.value,
					keyboardOptions = KeyboardOptions(
						imeAction = ImeAction.Done,
						keyboardType = KeyboardType.Password
					),
					keyboardActions = KeyboardActions(
						onDone = {
							focusManager.clearFocus()
							coroutineScope.launch {
								loginChecker(
									emailValue,
									passwordValue,
									context,
									ed
								).collect {
									when (it) {
										LoginDeviceState.OBSERVER -> navController.navigate("map_page")
										LoginDeviceState.EMITTER -> navController.navigate("menu_page")
										LoginDeviceState.ERROR -> Toast.makeText(
											context,
											"Authentication failed.",
											Toast.LENGTH_SHORT
										).show()
									}
								}

							}

						}
					),
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
									primaryColor else MaterialTheme.colors.onPrimary

							)

						}
					},
					label = {
						Text(
							text = "Password",
							color = MaterialTheme.colors.onPrimary
						)
					},
					placeholder = {
						Text(
							text = "Password",
							color = MaterialTheme.colors.secondaryVariant
						)
					},
					singleLine = true,
					visualTransformation = if (passwordVisibility.value) VisualTransformation.None
					else PasswordVisualTransformation(),
					modifier = Modifier
						.fillMaxWidth(0.8f)
						.focusRequester(focusRequester),
					textStyle = TextStyle(color = MaterialTheme.colors.onPrimary)

				)

			}


			item {
				CustomSpacer()
				Button(

					onClick = {
						coroutineScope.launch {
							loginChecker(
								emailValue,
								passwordValue,
								context,
								ed
							).collect {
								when (it) {
									LoginDeviceState.OBSERVER -> navController.navigate("map_page")
									LoginDeviceState.EMITTER -> navController.navigate("menu_page")
									LoginDeviceState.ERROR -> Toast.makeText(
										context,
										"Authentication failed.",
										Toast.LENGTH_SHORT
									).show()
								}
							}

						}
					},
					modifier = Modifier
						.fillMaxWidth(0.8f)
						.height(50.dp)
						.clip(MaterialTheme.shapes.medium),
					colors = ButtonDefaults.buttonColors(
						contentColor = MaterialTheme.colors.primaryVariant
					)

				) {
					Text(text = "Sign In", fontSize = 20.sp)
				}
			}

			item {
				CustomSpacer()
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.Center
				) {
					Text(
						text = "remember user and password?",
						color = MaterialTheme.colors.onPrimary
					)
					Checkbox(checked = checkBoxState.value,
						modifier = Modifier.border(
							width = 1.dp,
							color = MaterialTheme.colors.onPrimary
						),
						onCheckedChange =
						{
							checkBoxState.value = it

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
					}
					),
					color = MaterialTheme.colors.onPrimary

				)
				Spacer(modifier = Modifier.height(200.dp))

			}
		}
	}
}
//it is not used jet but i am working to use it an reuse code.
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun PressButton(
	emailValue : MutableState<String>,
	passwordValue : MutableState<String>,
	context : Context,
	ed : SharedPreferences.Editor,
	navController : NavController
) {
	val coroutineScope = rememberCoroutineScope()
	coroutineScope.launch {
		loginChecker(
			emailValue,
			passwordValue,
			context,
			ed
		).collect {
			when (it) {
				LoginDeviceState.OBSERVER -> navController.navigate("map_page")
				LoginDeviceState.EMITTER -> navController.navigate("menu_page")
				LoginDeviceState.ERROR -> Toast.makeText(
					context,
					"Authentication failed.",
					Toast.LENGTH_SHORT
				).show()
			}
		}

	}

}


























