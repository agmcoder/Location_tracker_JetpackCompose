package com.devcode.powerlock.model

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.location.Location
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.orhanobut.logger.Logger
import java.sql.Time

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

fun getGPSLocationStateToSharedPreferences(androidID : String?,sharedPreferences : SharedPreferences)   {
	// Create a reference to the document wanted
	val ed:SharedPreferences.Editor=sharedPreferences.edit()
	val docRef = db.collection("phones").document(androidID!!)
	// Create a query against the collection.
	var state: Boolean?=false
	Logger.d("step before docRef.get")
	docRef.get()
		.addOnSuccessListener { document->
			if(document.exists()){
				state= document.getBoolean("GPSLocationState")
				Logger.d("document exist getGPSLocationState value --> $state")
				ed.putBoolean("GPS", state!!)
				ed.apply()

			}else{
				Logger.e("document does not exists")
			}
		}
		.addOnFailureListener {
			Logger.e("failure getGPSLocatonState--> ${it.toString()}")
		}


}

fun getDb() : FirebaseFirestore {
	return Firebase.firestore

}
