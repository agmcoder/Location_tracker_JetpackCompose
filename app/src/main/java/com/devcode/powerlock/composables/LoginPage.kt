package com.devcode.powerlock.composables

import android.content.ContentValues
import android.util.Log
import android.util.Log.i
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devcode.powerlock.model.emailPasswordLogin


import com.devcode.powerlock.theme.primaryColor
import com.devcode.powerlock.theme.whiteBackground
import com.google.firebase.auth.FirebaseAuth
import java.util.logging.Logger


@Composable
fun LoginPage(navController: NavController) {

    //val image = imageResource(id= R.drawable.logo)
    val context = LocalContext.current

    val image = painterResource(com.devcode.powerlock.R.drawable.logo)
    val emailValue = remember { mutableStateOf("") }
    val passwordValue = remember { mutableStateOf("") }

    val passwordVisibility = remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    Box(

        modifier = Modifier
            .fillMaxSize(),
        contentAlignment =
        Alignment.BottomCenter
    ) {
        Column(modifier = Modifier.fillMaxSize()
            , horizontalAlignment = Alignment.CenterHorizontally) {


            Box(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .background(Color.White), contentAlignment =
                Alignment.Center

            ) {

                Image(
                    painter = image, contentDescription = "logo",
                    alignment = Alignment.Center
                )
            }

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.70f)
                    .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
                    .background(whiteBackground)
                    .padding(10.dp)
            ) {
                item {


                    Text(
                        text = "Sign In",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            //letterSpacing = TextUnit.Companion.Sp(2)
                        ),

                        //fontSize = TextUnit.Companion.Sp(30)
                    )
                    Spacer(modifier = Modifier.padding(20.dp))
                }
                item {


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
                    }
                }
                item {

                    OutlinedTextField(

                        value = passwordValue.value,
                        onValueChange = { passwordValue.value = it },
                        trailingIcon = {
                            IconButton(onClick = {
                                passwordVisibility.value = !passwordVisibility.value
                            }) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(
                                        id = com.devcode.powerlock.R.drawable.password_eye
                                    ), contentDescription = "",
                                    tint = if (passwordVisibility.value) primaryColor else Color.Gray
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
                            .focusRequester(focusRequester = focusRequester),
                        //onImeActionPerformed = { _, controller ->
                        //controller?.hideSoftwareKeyboard()
                        //}

                    )
                }
                item {

                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = {
                            if (emailValue.value.isEmpty() ||
                                passwordValue.value.isEmpty()
                            ) {
                                Toast.makeText(
                                    context, "campos vacios",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                com.orhanobut.logger.Logger.i("else del boton signup")
                                var userMail: String = emailValue.value.toString()
                                var userPassword: String = passwordValue.value.toString()
                                val mAuth = FirebaseAuth.getInstance()
                                mAuth
                                    .signInWithEmailAndPassword(userMail, userPassword)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful) {
                                            com.orhanobut.logger.Logger.i("if task issuccesfull")
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d(ContentValues.TAG, "signInWithEmail:success")
                                            navController.navigate("menu_page")
                                        } else {
                                            com.orhanobut.logger.Logger.i("else is susccessful")
                                            // If sign in fails, display a message to the user.
                                            Log.w(
                                                ContentValues.TAG,
                                                "signInWithEmail:failure",
                                                task.exception
                                            )
                                            Toast.makeText(
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
                    ) {
                        Text(text = "Sign In", fontSize = 20.sp)
                    }
                }
                item {
                    Spacer(modifier = Modifier.padding(20.dp))
                }
                item {
                    Text(
                        text = "Create An Account",
                        modifier = Modifier.clickable(onClick = {
                            navController.navigate("register_page") {
                                //popUpTo = navController.graph.startDestination
                                //launchSingleTop = true
                            }
                        })
                    )
                }
                item {
                    Spacer(modifier = Modifier.padding(200.dp))
                }
                item {

                }


            }

        }
    }
}






















