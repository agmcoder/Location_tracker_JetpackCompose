package com.devcode.powerlock

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.devcode.powerlock.theme.SecurePhoneAppTheme
import com.devcode.powerlock.navigation.NavigationHost
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import android.content.Intent




private const val TAG ="MainActivity"
lateinit var fusedLocationClient:FusedLocationProviderClient

private const val REQUEST_FOREGROUND_ONLY_PERMISSIONS_REQUEST_CODE=35
class MainActivity : ComponentActivity() {
    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            SecurePhoneAppTheme {
                Logger.addLogAdapter(AndroidLogAdapter())
                NavigationHost(getSharedPreferences("mispreferencias", MODE_PRIVATE))
            }
        }



    }


}




