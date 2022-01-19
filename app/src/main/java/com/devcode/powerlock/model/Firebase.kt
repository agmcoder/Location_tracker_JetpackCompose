package com.devcode.powerlock.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.LatLng
import com.orhanobut.logger.Logger

@SuppressLint("StaticFieldLeak")
private var db = Firebase.firestore

fun saveLocation(location : Location, androidID : String) {
	val locationList = hashMapOf(
		"longitud" to "${location.latitude}",
		"latitud" to "${location.longitude}"
	)
	db.collection("phones").document(androidID).set(locationList, SetOptions.merge())

}

fun setGPSLocationState(state : Boolean, androidID : String) {
	val state = hashMapOf(
		"GPSLocationState" to state
	)
	db.collection("phones").document(androidID).set(state, SetOptions.merge())

}

fun getGPSLocationStateToSharedPreferences(
	androidID : String?,
	sharedPreferences : SharedPreferences
) {
	// Create a reference to the document wanted
	val ed : SharedPreferences.Editor = sharedPreferences.edit()
	val docRef = db.collection("phones").document(androidID!!)
	// Create a query against the collection.
	var state : Boolean? = false
	Logger.d("step before docRef.get")
	docRef.get()
		.addOnSuccessListener { document ->
			if (document.exists()) {
				state = document.getBoolean("GPSLocationState")
				Logger.d("document exist getGPSLocationState value --> $state")
				ed.putBoolean("GPS", state!!)
				ed.apply()

			} else {
				Logger.e("document does not exists")
			}
		}
		.addOnFailureListener {
			Logger.e("failure getGPSLocatonState--> ${it.toString()}")
		}

}

fun getLatLong(userName : String) : HashMap<String?, String?> {
	var location : HashMap<String?, String?> = hashMapOf()

	db.collection("phones").whereEqualTo("user", userName).get()
		.addOnSuccessListener { documents ->
			for (document in documents) {
				Logger.d("${document.id}=>${document.data}")
				db.collection("phones").document(document.id).get()
					.addOnSuccessListener { documentSnapShot ->
						if (documentSnapShot.exists()) {
							var latitud =
								location.put("latitud", documentSnapShot.getString("latitud"))
							Logger.d("latitud >>> $latitud")
							var longitud =
								location.put("longitud", documentSnapShot.getString("longitud"))
							Logger.d("latitud >>> $longitud")
						}
					}
			}
		}


	return location
}

fun getCurrentUserName(context : Context) : String? {
	val user = Firebase.auth.currentUser
	user?.let {
		// Name, email address, and profile photo Url
		//val name = user.displayName
		//val photoUrl = user.photoUrl

		val email = user.email

		// Check if user's email is verified
		//val emailVerified = user.isEmailVerified

		// The user's ID, unique to the Firebase project. Do NOT use this value to
		// authenticate with your backend server, if you have one. Use
		// FirebaseUser.getToken() instead.
		//val uid = user.uid
	}
	if (user != null) {
		return user.email.toString()
	}
	return null
}


//we will get all phones that the current user has associated
fun getPhonesByUser(curretUser:String):Array<Phone>{
	val phones:MutableList<Phone>
	db.collection("phones").whereEqualTo("user",curretUser)
		.get().addOnSuccessListener { documents->
			for (document in documents)
				phones.add(
					Phone(
					document.getBoolean("GPSLocationState") ,
					document.getString("androidID"),
						Ubicacion()

				)
				)

		}


}


fun getDb() : FirebaseFirestore {
	return Firebase.firestore

}
