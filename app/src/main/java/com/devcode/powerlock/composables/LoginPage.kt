package com.devcode.powerlock.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController




import com.devcode.powerlock.theme.primaryColor
import com.devcode.powerlock.theme.whiteBackground


@Composable
fun LoginPage(navController: NavController) {

    //val image = imageResource(id= R.drawable.logo)
    val image= painterResource(com.devcode.powerlock.R.drawable.logo)
    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment =
        Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White), contentAlignment =
            Alignment.TopCenter
        ) {

            Image(painter = image, contentDescription = "")
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.60f)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                .background(whiteBackground)
                .padding(10.dp)
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Sign In",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        //letterSpacing = TextUnit.Companion.Sp(2)
                    ),

                    //fontSize = TextUnit.Companion.Sp(30)
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    OutlinedTextField(

                        value = emailValue.value,
                        onValueChange = { emailValue.value = it },
                        label = {
                            Text(
                                text = "Email Address",
                                color = Color.Black
                            )
                        },
                        placeholder = { Text(text = "Email Address", color = Color.Black) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),

                    )

                    OutlinedTextField(

                        value = passwordValue.value,
                        onValueChange = { passwordValue.value = it },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(imageVector = ImageVector.vectorResource(
                                    id = com.devcode.powerlock.R.drawable.password_eye), contentDescription ="",
                                    tint=if (passwordVisibility.value) primaryColor else Color.Gray
                                )

                            }
                        },
                        label = { Text(text="Password", color = Color.Black) },
                        placeholder = { Text(text = "Password", color = Color.Black) },
                        singleLine = true,
                        visualTransformation = if (passwordVisibility.value) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .focusRequester(focusRequester = focusRequester),
                        //onImeActionPerformed = { _, controller ->
                            //controller?.hideSoftwareKeyboard()
                        //}

                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .height(50.dp)
                    ) {
                        Text(text = "Sign In", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.padding(20.dp))
                    Text(
                        text = "Create An Account",
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate("register_page") {
                                //popUpTo = navController.graph.startDestination
                                //launchSingleTop = true
                            }
                        })
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                }


            }
        }

    }
}





















