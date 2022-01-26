package com.devcode.powerlock.model

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController

 fun loginChecker(
	emailValue : MutableState<String>,
	passwordValue : MutableState<String>,
	context : Context,
	navController : NavController,
	ed : SharedPreferences.Editor
) {
	if (emailValue.value.isEmpty() ||
		passwordValue.value.isEmpty()
	) {
		Toast.makeText(
			context, "campos vacios",
			Toast.LENGTH_SHORT
		).show()
	} else {
		com.orhanobut.logger.Logger.i("else del boton signup")
		//var userMail : String = emailValue.value
		//var userPassword : String = passwordValue.value
		emailPasswordLogin(
			context,
			emailValue.value,
			passwordValue.value,
			navController,
			ed
		)

	}

}

fun registerChecker(
	context : Context,
	navController : NavController,
	emailValue : MutableState<String>,
	passwordValue : MutableState<String>,
	confirmPasswordValue :MutableState<String>
) {
	if (emailValue.value.isEmpty() ||
		passwordValue.value.isEmpty() ||
		confirmPasswordValue.value.isEmpty() ||
		passwordValue.value != confirmPasswordValue.value
	) {
		Toast.makeText(
			context, "incorrect fields",
			Toast.LENGTH_SHORT
		).show()

	} else {
		emailPasswordRegister(
			context,
			navController,
			emailValue.value,
			passwordValue.value
		)

	}
}