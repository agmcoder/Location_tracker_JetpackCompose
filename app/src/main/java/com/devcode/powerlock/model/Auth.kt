package com.devcode.powerlock.model

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.orhanobut.logger.Logger

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
            Logger.d("error al autenticar")
            // If sign in fails, display a message to the user.
            Toast.makeText(
                context, "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}

fun emailPasswordLogin(
    context: Context, userMail: String,
    userPassword: String,
    navController : NavController,
    ed:SharedPreferences.Editor
) {
    val mAuth = FirebaseAuth.getInstance()
    mAuth
        .signInWithEmailAndPassword(userMail, userPassword)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {

                ed.putString("user", userMail)
                ed.putString("password", userPassword)
                ed.apply()
                Logger.d(ContentValues.TAG, "signInWithEmail:success")
                navController.popBackStack()
                navController.navigate("menu_page")
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
