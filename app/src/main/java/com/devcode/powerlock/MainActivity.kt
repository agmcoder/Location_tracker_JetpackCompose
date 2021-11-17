package com.devcode.powerlock

import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devcode.powerlock.composables.LoginPage
import com.devcode.powerlock.theme.SecurePhoneAppTheme
import com.google.android.gms.location.LocationServices
import com.learnandroid.powerlock.composables.MenuPage
import com.learnandroid.powerlock.composables.RegisterPage
import com.learnandroid.powerlock.composables.RegisterPhonePage
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecurePhoneAppTheme {
                Logger.addLogAdapter(AndroidLogAdapter())
                Nav()
            }
        }
    }


    @Composable
    fun Nav() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "login_page", builder = {
            composable("login_page", content = { LoginPage(navController = navController) })
            composable("register_page", content = { RegisterPage(navController = navController) })
            composable("menu_page", content = { MenuPage(navController = navController) })
            composable(
                "register_phone",
                content = { RegisterPhonePage(navController = navController) })
        })
    }


}

