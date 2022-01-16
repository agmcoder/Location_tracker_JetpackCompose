package com.devcode.powerlock.model

import android.annotation.SuppressLint
import android.location.Location
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@SuppressLint("StaticFieldLeak")
private var db = Firebase.firestore

fun saveLocation(location : Location,androidID:String) {
	val locationList = hashMapOf(
		"longitud" to "${location.latitude}",
		"latitud" to "${location.longitude}"
	)
    db.collection("phones").document(androidID).set(locationList, SetOptions.merge())

}

fun getDb() : FirebaseFirestore {
	return Firebase.firestore

}
