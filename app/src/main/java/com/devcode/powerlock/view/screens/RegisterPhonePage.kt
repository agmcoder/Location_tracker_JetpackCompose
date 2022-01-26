package com.devcode.powerlock.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devcode.powerlock.R
import com.devcode.powerlock.view.Divisor
import com.devcode.powerlock.view.MenuToolbar
import com.devcode.powerlock.model.getAndroidId

import com.devcode.powerlock.theme.whiteBackground
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger


@Composable
fun RegisterPhonePage(navController: NavController) {
    Scaffold(
        topBar = { MenuToolbar(title = "Registro de dispositivo") },
        content = { BodyContentRegisterPhone(navController) }

    )

}


@Composable
fun BodyContentRegisterPhone(navController: NavController) {
    val context = LocalContext.current
    val androidID = getAndroidId(context)
    var userEmail = ""

    //FirebaseApp.initializeApp(ContextAmbient.current)
    val user = Firebase.auth.currentUser
    Logger.d("current user: ${user.toString()}")
    user?.let {
        userEmail = user.email.toString()
        val uid = user.uid
        Logger.d("uid: " + uid.toString())
    }


    if (user != null) {
        Logger.d("usuario actual $user")
    } else {
        Logger.e("usuario nulo en current user")
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(whiteBackground)
    )
    {
        Column() {


            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .clip(
                        RoundedCornerShape(
                            bottomEnd = 20.dp,
                            bottomStart = 20.dp
                        )
                    )
                    .background(Color.Gray)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.pixelphone),
                    contentDescription = "",
                    modifier = Modifier.padding(20.dp)
                )


            }
            Box(modifier = Modifier.fillMaxSize()) {


                LazyColumn {
                    item {
                        Spacer(modifier = Modifier.height(20.dp))


                    }
                    item {
                        Text(
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth(),
                            text = "Datos de Registro",
                            fontSize = 30.sp,
                            textAlign = TextAlign.Center
                        )

                    }
                    item {
                        Column {
                            Divisor()
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    text = "usuario",
                                    modifier = Modifier.padding(start = 20.dp)
                                )
                            }


                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    color = Color.Black,
                                    text = userEmail,
                                    fontSize = 25.sp,
                                    modifier = Modifier.padding(20.dp)
                                )
                            }
                            Divisor()

                        }

                    }
                    item {
                        Column {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    color = Color.Black,
                                    fontSize = 20.sp,
                                    text = "Numero de serie",
                                    modifier = Modifier.padding(start = 20.dp)
                                )
                            }


                            Row(modifier = Modifier.fillMaxWidth()) {
                                if (androidID != null) {
                                    Text(

                                        color = Color.Black,
                                        text = androidID,
                                        fontSize = 25.sp,
                                        modifier = Modifier.padding(20.dp)
                                    )
                                }
                            }


                        }
                        Divisor()
                    }
                    item {
                        Button(
                            modifier = Modifier
                                .padding(10.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .fillMaxWidth()
                                .align(alignment = Alignment.Center),
                            onClick = {
                                Logger.d("entrando en boton guardar device")
                                Logger.d("db getDb")
                                Logger.d("android id: $androidID")
                                val phone = hashMapOf(
                                    "user" to userEmail,
                                    "androidID" to androidID


                                )
                                Logger.d("val device hasmapof")
                                if (androidID != null) {
                                    Firebase.firestore.collection("phones")
                                        .document(androidID)
                                        .set(phone)
                                        .addOnSuccessListener {
                                            Logger.d("device añadido a coleccion con id: ")
                                            navController.popBackStack()
                                            navController.navigate("menu_page")
                                        }
                                        .addOnFailureListener {
                                            Logger.d("error añadiendo documento device  ")
                                            navController.popBackStack()
                                            navController.navigate("register_page")
                                        }
                                }


                            })
                        {
                            Text(text = "GUARDAR")

                        }
                    }


                }
            }
        }
    }
}






