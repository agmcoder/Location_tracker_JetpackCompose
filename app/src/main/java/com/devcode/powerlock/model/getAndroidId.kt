package com.devcode.powerlock.model

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
//we get the installation android id app in the current device.
@SuppressLint("HardwareIds")
fun getAndroidId(context : Context): String? {
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}