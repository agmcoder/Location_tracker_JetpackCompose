package com.devcode.powerlock.model

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

fun emailPasswordRegister(
    emailValue: String,
    passwordValue: String
): Boolean {
    var resultado = true
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
        emailValue,
        passwordValue
    ).addOnCompleteListener {
        if (!it.isSuccessful) {
            resultado = false
        }

    }
    return resultado

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