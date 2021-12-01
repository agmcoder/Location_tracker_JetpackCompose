package com.devcode.powerlock.model

import android.content.ContentValues.TAG
import android.content.Context
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

fun getAndroidId(context: Context): String? {
    return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID)
}
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
            navController.navigate("register_phone")

        } else {
            // If sign in fails, display a message to the user.
            Toast.makeText(
                context, "Authentication failed.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

}

fun emailPasswordLogin(
    context: Context, emailValue: String,
    passwordValue: String
): Boolean {
    var result=false
    val mAuth = FirebaseAuth.getInstance()
    mAuth
        .signInWithEmailAndPassword(emailValue, passwordValue)
        .addOnCompleteListener {
            if (it.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.d(TAG, "signInWithEmail:success")
                val user = mAuth.currentUser
                result=true
            } else {
                // If sign in fails, display a message to the user.
                Log.w(TAG, "signInWithEmail:failure", it.exception)
                Toast.makeText(
                    context,
                    "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
                result = false
            }
        }

    return result
}