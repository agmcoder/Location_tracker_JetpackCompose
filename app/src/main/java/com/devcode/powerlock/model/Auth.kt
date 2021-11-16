package com.devcode.powerlock.model

import com.google.firebase.auth.FirebaseAuth

fun emailPasswordRegister(emailValue:String,
                          passwordValue:String): Boolean {
    var resultado: Boolean=true
    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
        emailValue,
        passwordValue
    ).addOnCompleteListener {
        if (!it.isSuccessful)
        {
            resultado=false
        }

    }
    return resultado

}