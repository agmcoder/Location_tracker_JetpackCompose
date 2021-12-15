package com.devcode.powerlock.model

import android.app.Activity
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.internal.ContextUtils.getActivity


fun getFusedLocationProviderClient(context:Context): FusedLocationProviderClient {
    return LocationServices.getFusedLocationProviderClient(context)
}
