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
import com.devcode.powerlock.composables.MapPage
import com.devcode.powerlock.theme.SecurePhoneAppTheme
import com.devcode.powerlock.composables.screens.MenuPage
import com.devcode.powerlock.navigation.NavigationHost
import com.learnandroid.powerlock.composables.RegisterPage
import com.devcode.powerlock.composables.screens.RegisterPhonePage
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecurePhoneAppTheme {
                Logger.addLogAdapter(AndroidLogAdapter())
                NavigationHost()
            }
        }
    }





}
@Composable
fun Nav() {
    val navController:NavHostController = rememberNavController()

    NavHost(navController = navController, startDestination = "login_page", builder = {
        composable("login_page", content = { LoginPage(navController = navController) })
        composable("register_page", content = { RegisterPage(navController = navController) })
        composable("menu_page", content = { MenuPage(navController = navController) })
        composable(
            "register_phone",
            content = { RegisterPhonePage(navController = navController) })
        composable("map_page",content={MapPage(navController=navController)})
    })
}

