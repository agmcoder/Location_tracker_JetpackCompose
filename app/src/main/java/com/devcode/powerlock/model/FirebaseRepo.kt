package com.devcode.powerlock.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.google.android.libraries.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

@SuppressLint("StaticFieldLeak")
private var db = getDb()

suspend fun latLngSnapshotObserver(isObserving : Boolean) = flow {
	while (isObserving) {
		val document = db.collection("phones").document(getFirebaseID()).get().await()
		val latitude = document.getString("latitud") ?: "0.0"
		val longitude = document.getString("longitud") ?: "0.0"
		emit(LatLng(latitude.toDouble(), longitude.toDouble()))
		delay(1000)
	}

}

fun getFirebaseID() : String {

	return FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

}

suspend fun saveLocation(location : LatLng, androidID : String) = runCatching {
	val locationList = hashMapOf(
		"latitud" to "${location.latitude}",
		"longitud" to "${location.longitude}"
	)
	Logger.d("${location.latitude} ,  ${location.longitude}")
	db.collection("phones")
		.document(getFirebaseID()).set(locationList, SetOptions.merge()).await()

}.isSuccess

fun setGPSStateFirebase(state : Boolean) {
	Logger.d("setGPSStateFirebase $state")
	val estado = hashMapOf(
		"GPSState" to state
	)
	db.collection("phones").document(getFirebaseID()).set(estado, SetOptions.merge())

}

@OptIn(DelicateCoroutinesApi::class)
suspend fun getGPSStateFirebase() = GlobalScope.async {

		val document = db.collection("phones")
			.document(getFirebaseID()).get().await()
		document.getBoolean("GPSState")?:false
}.await()

fun getGPSLocationStateToSharedPreferences(
	androidID : String?,
	sharedPreferences : SharedPreferences
) {
	try {
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

	} catch (e : Exception) {

	}

}

fun getLocationByAndroidID(context : Context, myCallback : MyCallback) {
	Logger.d("we entry into getLocationByandroidid")
	val androidID = getAndroidId(context = context)
	if (androidID != null) {
		Logger.d("if android id not null")
		db.collection("phones")
			.document(androidID)
			.get().addOnSuccessListener { documentSnapShot ->
				var location : LatLng
				Logger.d("location =Latln")
				location = LatLng(
					documentSnapShot.getString("latitud")!!.toDouble(),
					documentSnapShot.getString("longitud")!!.toDouble()
				)
				myCallback.onCallback(location)
				Logger.d("addonsucceslistener passed")
			}
			.addOnFailureListener {
				Logger.d("error getting location android id -> $it")
			}

	}
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
fun getPhonesByUser(context : Context) : MutableList<Phone> {
	val currentUser = getCurrentUserName(context)
	Logger.d("we are in getPhonesByUser and current user is $currentUser")
	val phones = mutableListOf<Phone>()
	var location : LatLng? = null
	db.collection("phones")
		.whereEqualTo("user", currentUser)
		.get().addOnSuccessListener { documents ->
			for (document in documents) {
				Logger.d("document--> ${document.getString("androidID")}")
				phones.add(
					Phone(
						gpsLocationState = document.getBoolean("GPSLocationState"),
						androidId = document.getString("androidID"),
						ubicacion = Ubicacion(
							ubicacion = document.getString("latitud")?.let {
								document.getString("longitud")?.let { it1 ->
									LatLng(
										it.toDouble(),
										it1.toDouble()
									)
								}
							},
							titulo = document.getString("androidID"),
							descripcion = "device location"
						),
						user = document.getString("user")

					)
				)

			}
		}
		.addOnFailureListener { Logger.d("error getting usersbyphone--->  $it") }
	return phones

}

fun getDb() : FirebaseFirestore {
	return Firebase.firestore

}

interface MyCallback {
	fun onCallback(value : LatLng)
}
