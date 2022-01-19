package com.devcode.powerlock.model

import com.google.type.LatLng

data class Ubicacion(
	val ubicacion:LatLng,
	val titulo:String,
	val descripcion:String
	)

