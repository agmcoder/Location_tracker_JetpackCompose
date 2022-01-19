package com.devcode.powerlock.model

data class Phone(
	val gpsLocationState : Boolean?,
	val androidId : String?,
	val ubicacion : Ubicacion,
	val user :String
	)