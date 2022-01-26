package com.devcode.powerlock.model

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings
//we get the installation android id app in the current device.

@SuppressLint("HardwareIds", "NewApi")
fun getAndroidId(context : Context): String? {
    return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
}