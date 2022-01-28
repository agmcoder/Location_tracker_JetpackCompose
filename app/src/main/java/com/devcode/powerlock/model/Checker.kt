package com.devcode.powerlock.model

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.navigation.NavController
import com.devcode.powerlock.data.network.LoginDeviceState
import com.devcode.powerlock.data.network.LoginResult
import com.google.firebase.firestore.FirebaseFirestore
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

suspend fun loginChecker(
	emailValue : MutableState<String>,
	passwordValue : MutableState<String>,
	context : Context,
	navController : NavController,
	ed : SharedPreferences.Editor
) = flow {
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
		when(login(context, emailValue.value, passwordValue.value, ed)) {
			is LoginResult.Success -> {
				FirebaseFirestore.getInstance().collection("phones").document(
					getFirebaseID()
				)
					.get().await()?.let { documentSnapshot ->
						if (documentSnapshot.getString("androidID").equals(getAndroidId(context))) emit(LoginDeviceState.EMITTER) else emit(LoginDeviceState.OBSERVER)


					}
			}
			is LoginResult.Error -> {
				emit(LoginDeviceState.ERROR)
			}
		}

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