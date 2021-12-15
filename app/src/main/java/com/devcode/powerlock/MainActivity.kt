package com.devcode.powerlock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devcode.powerlock.composables.LoginPage
import com.devcode.powerlock.composables.screens.MapPage
import com.devcode.powerlock.theme.SecurePhoneAppTheme
import com.devcode.powerlock.composables.screens.MenuPage
import com.devcode.powerlock.navigation.NavigationHost
import com.learnandroid.powerlock.composables.RegisterPage
import com.devcode.powerlock.composables.screens.RegisterPhonePage
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

import android.content.SharedPreferences
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.util.prefs.Preferences

class MainActivity : ComponentActivity() {
    @ExperimentalPermissionsApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecurePhoneAppTheme {
                Logger.addLogAdapter(AndroidLogAdapter())
                NavigationHost(getSharedPreferences("mispreferencias", MODE_PRIVATE))
            }
        }



    }
}


