package com.devcode.powerlock.model

import com.google.android.libraries.maps.model.LatLng

data class Ubicacion(
	val ubicacion : LatLng?,
	val titulo : String?,
	val descripcion : String?
)

