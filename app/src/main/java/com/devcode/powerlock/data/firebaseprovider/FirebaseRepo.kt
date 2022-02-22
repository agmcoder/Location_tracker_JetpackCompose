package com.devcode.powerlock.data.firebaseprovider

import android.annotation.SuppressLint
import com.google.android.libraries.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

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
suspend fun getGPSStateFirebase() =
	withContext(Dispatchers.Default) {

		val document = db.collection("phones")
			.document(getFirebaseID()).get().await()
		document.getBoolean("GPSState") ?: false
	}

fun getDb() : FirebaseFirestore {
	return Firebase.firestore

}

interface MyCallback {
	fun onCallback(value : LatLng)
}
