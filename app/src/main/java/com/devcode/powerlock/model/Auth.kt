package com.devcode.powerlock.model

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.devcode.powerlock.data.network.LoginResult
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

fun emailPasswordRegister(
    context: Context,
    navController: NavController,
    emailValue: String,
    passwordValue: String,

    ) {
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
        emailValue, passwordValue
    ).addOnCompleteListener {
        if (it.isSuccessful) {
            // Sign in success, update UI with the signed-in user's information
            navController.popBackStack()
            navController.navigate("register_phone")


        } else {
            Logger.d("error al REGISTRAR")
            // If sign in fails, display a message to the user.
            Toast.makeText(
                context, "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}

 @OptIn(DelicateCoroutinesApi::class)
 fun emailPasswordLogin(
    context: Context, userMail: String,
    userPassword: String,
    navController : NavController,
    ed:SharedPreferences.Editor
) {
    val mAuth = FirebaseAuth.getInstance()
    val i = mAuth
        .signInWithEmailAndPassword(userMail, userPassword)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                ed.putString("user", userMail)
                ed.putString("password", userPassword)
                ed.apply()
                Logger.d("signInWithEmail:success")

            } else {
                Logger.i("else is susccessful")
                // If sign in fails, display a message to the user.
                Logger.d("signInWithEmail:failure ${task.exception}")
                Toast.makeText(
                    context,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
}

suspend fun login(context: Context, userMail: String, userPassword: String, ed:SharedPreferences.Editor) = runCatching {
    FirebaseAuth.getInstance().signInWithEmailAndPassword(userMail,userPassword).await()
}.toLoginResult()

private fun Result<AuthResult>.toLoginResult() = when(getOrNull()) {
    null -> LoginResult.Error
    else -> LoginResult.Success
}
