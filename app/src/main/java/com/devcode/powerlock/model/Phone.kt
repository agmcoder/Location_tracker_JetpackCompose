package com.devcode.powerlock.model

data class Phone(
	val gpsLocationState : Boolean?,
	val androidId : String?,
	val location : Ubicacion,
	val user :String?
	)