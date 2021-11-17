package com.learnandroid.powerlock.composables

import android.content.Context
import android.provider.Settings
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Divider
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.devcode.powerlock.R
import com.devcode.powerlock.composables.Toolbar
import com.devcode.powerlock.model.getDb
import com.devcode.powerlock.model.getDeviceLocation
import com.devcode.powerlock.theme.whiteBackground
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger


@Composable
fun RegisterPhonePage(navController: NavController) {
    Scaffold(
        topBar = { Toolbar(title = "Registro de dispositivo") },
        content = { BodyContentRegisterPhone(navController) }

    )

}


@Composable
fun BodyContentRegisterPhone(navController: NavController) {
    val androidID = getAndroidId(LocalContext.current)
    var userEmail = ""
    val context= LocalContext.current
    //FirebaseApp.initializeApp(ContextAmbient.current)
    val user = Firebase.auth.currentUser
    user?.let {
        userEmail = user.email.toString()
        val uid = user.uid
        Logger.d("uid: " + uid.toString())
    }


    if (user != null) {
        Logger.d("usuario actual " + user)
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
                                val db= getDb()
                                var geo= getDeviceLocation(context)
                                val device= hashMapOf(
                                    "user" to userEmail,
                                    "id_android" to androidID,
                                    "coordenadas" to geo

                                )
                                db.collection("devices")
                                    .add(device)
                                    .addOnSuccessListener {
                                        Logger.d("device añadido a coleccion con id: $it")
                                        navController.navigate("menu_page")
                                    }
                                    .addOnFailureListener{
                                        Logger.w("error añadiendo documento device",it)
                                        navController.navigate("register_page")
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


@Composable
fun Divisor() {
    Spacer(modifier = Modifier.padding(top = 5.dp))
    Divider(color = Color.Black, thickness = 1.dp)
    Spacer(modifier = Modifier.padding(top = 5.dp))

}

fun getAndroidId(context: Context): String? {
    return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
}



