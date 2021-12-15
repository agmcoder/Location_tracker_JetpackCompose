package com.devcode.powerlock.model

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MyBackgroundService : Service() {

	private val CHANNEL_ID = "my_channel"
	private

	override fun onBind(intent : Intent) : IBinder {
		TODO("Return the communication channel to the service.")
	}
}