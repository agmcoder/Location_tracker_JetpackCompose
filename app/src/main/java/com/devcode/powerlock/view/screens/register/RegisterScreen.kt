package com.devcode.powerlock.view.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devcode.powerlock.R
import com.devcode.powerlock.model.registerChecker
import com.devcode.powerlock.theme.primaryColor

@Composable
fun RegisterPage(navController : NavController) {

	val image = painterResource(id = R.drawable.firebase)

	remember { mutableStateOf("") }
	val emailValue = remember { mutableStateOf("") }
	remember { mutableStateOf("") }
	val passwordValue = remember { mutableStateOf("") }
	val confirmPasswordValue = remember { mutableStateOf("") }
	val context = LocalContext.current
	val scrollState = rememberScrollState()
	val passwordVisibility = remember { mutableStateOf(false) }
	val confirmPasswordVisibility = remember { mutableStateOf(false) }
	val focusRequester = remember { FocusRequester() }

	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.BottomCenter
	) {
		Column {

			Box(
				modifier = Modifier
					.fillMaxWidth()
					.fillMaxHeight(0.3f)
					.background(Color.White),
				contentAlignment = Alignment.TopCenter
			) {
				Image(
					painter = image,
					contentDescription = ""
				)
			}


			Column(
				modifier = Modifier
					.fillMaxSize()
					.clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
					.background(Color.Gray)
					.padding(10.dp),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center

			) {
				Text(
					text = "Sign Up",
					fontSize = 30.sp,
					style = TextStyle(
						fontWeight = FontWeight.Bold,
						letterSpacing = 2.sp
					)
				)
				Column(
					modifier = Modifier
						.fillMaxSize()
						.verticalScroll(state = scrollState),
					verticalArrangement = Arrangement.Center,
					horizontalAlignment = Alignment.CenterHorizontally
				) {

					com.devcode.powerlock.view.components.CustomSpacer()
					Column(horizontalAlignment = Alignment.CenterHorizontally) {
						//userName
						OutlinedTextField(
							value = emailValue.value,
							keyboardOptions = KeyboardOptions(
								keyboardType = KeyboardType.Email
							),
							onValueChange = { emailValue.value = it },
							label = { Text(text = "Email Address", color = Color.Black) },
							placeholder = { Text(text = "Email Address", color = Color.Black) },
							singleLine = true,
							modifier = Modifier
								.fillMaxWidth(0.8f)
								.focusRequester(focusRequester = focusRequester)
						)

						OutlinedTextField(
							value = passwordValue.value,
							onValueChange = { passwordValue.value = it },
							keyboardOptions = KeyboardOptions(
								keyboardType = KeyboardType.Password
							),
							label = { Text(text = "Password", color = Color.Black) },
							placeholder = { Text(text = "Password", color = Color.Black) },
							singleLine = true,
							modifier = Modifier
								.fillMaxWidth(0.8f)
								.focusRequester(focusRequester = focusRequester),
							trailingIcon = {
								IconButton(onClick = {
									passwordVisibility.value = !passwordVisibility.value
								}) {
									Icon(
										imageVector = ImageVector.vectorResource(id = R.drawable.password_eye),
										contentDescription = "",
										tint = if (passwordVisibility.value) primaryColor else Color.Black
									)

								}
							},
							visualTransformation = if (passwordVisibility.value) VisualTransformation.None
							else PasswordVisualTransformation()
						)
						//confirmPassword
						OutlinedTextField(
							value = confirmPasswordValue.value,
							onValueChange = { confirmPasswordValue.value = it },
							keyboardOptions = KeyboardOptions(
								keyboardType = KeyboardType.Password
							),
							label = { Text(text = "Confirm Password", color = Color.Black) },
							placeholder = { Text(text = "Confirm Password", color = Color.Black) },
							singleLine = true,
							modifier = Modifier.fillMaxWidth(0.8f),
							trailingIcon = {
								IconButton(onClick = {
									confirmPasswordVisibility.value =
										!confirmPasswordVisibility.value
								}) {
									Icon(
										imageVector = ImageVector.vectorResource(id = R.drawable.password_eye),
										contentDescription = "",
										tint = if (confirmPasswordVisibility.value) primaryColor else Color.Black

									)

								}
							},
							visualTransformation = if (confirmPasswordVisibility.value) VisualTransformation.None
							else PasswordVisualTransformation()
						)
						Spacer(modifier = Modifier.padding(10.dp))
						Button(
							onClick = {
								registerChecker(
									context,
									navController,
									emailValue,
									passwordValue,
									confirmPasswordValue
								)
							}, modifier = Modifier
								.fillMaxWidth(0.8f)
								.height(50.dp)
						) {
							Text(text = "Sign Up", fontSize = 20.sp)
						}
						Spacer(modifier = Modifier.padding(20.dp))
						Text(
							text = "Login Instead",
							modifier = Modifier.clickable(onClick = {
								navController.navigate("login_page") {
									// popUpTo = navController.graph.startDestination
									//launchSingleTop = true
								}
							})
						)
						com.devcode.powerlock.view.components.CustomSpacer()

					}
					com.devcode.powerlock.view.components.CustomSpacer()
				}

			}
		}
	}
}























