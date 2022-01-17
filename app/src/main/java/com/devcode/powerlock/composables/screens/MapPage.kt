package com.devcode.powerlock.composables.screens

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.devcode.powerlock.composables.MapToolBar
import com.devcode.powerlock.model.getCurrentUserName
import com.devcode.powerlock.model.getLatLong
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MarkerOptions
import com.google.maps.android.ktx.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MapPage(navController: NavController) {
    Scaffold(
        topBar={ MapToolBar()},
        content = {MapContent()},



    )
}
@Preview(name="map content")
@Composable
fun MapContent() {
GoogleMaps()
    
}
@Preview(name = "googlemaps")
@Composable
fun GoogleMaps() {

    var mapView = rememberMapViewWithLifeCycle()
    var locations= getLatLong(getCurrentUserName(LocalContext.current).toString())

    var latlng= remember {   mutableStateOf("lat : 17.385,lang: 78.4867") }
    Box(){

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            AndroidView(
                {mapView}
            ) { mapView ->
                CoroutineScope(Dispatchers.Main).launch {


                    mapView.getMapAsync {

                        it.mapType=1
                        it.uiSettings.isZoomControlsEnabled = true
                        val mark1 = LatLng(locations.get("latitud")!!.toDouble(), locations.get("longitud")!!.toDouble()) //Hyderabad


                        it.moveCamera(CameraUpdateFactory.newLatLngZoom(mark1, 12f))
                        it.setOnCameraIdleListener {
                            it.clear()
                            it.addMarker(
                                MarkerOptions()
                                .position(it.getCameraPosition().target))
                            latlng.value=("lat: ${it.getCameraPosition().target.latitude}," +
                                    " \n lang: ${it.getCameraPosition().target.longitude}")
                        }
                        val markerOptions =  MarkerOptions()
                            .title("Hyderabad")
                            .position(mark1)
                        it.addMarker(markerOptions)




                    }




                }
            }
        }

        Card (modifier = Modifier
            .background(Color.Transparent)
            .padding(12.dp)
            ,elevation = 10.dp
        ){
            OutlinedTextField( value =latlng.value,

                onValueChange = {

                },
                textStyle = TextStyle(
                    color = Color.Red,

                    fontFamily = FontFamily.SansSerif
                ),
                modifier = Modifier.background(Color.Transparent)
                , leadingIcon = {
                    // In this method we are specifying
                    // our leading icon and its color.
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "image",
                        tint = Color.Green
                    )
                },
            )

        }


    }

}

@Preview(name = "rememberMapView")
@Composable
fun rememberMapViewWithLifeCycle(): MapView {
    val context = LocalContext.current
    val mapView = remember {
        MapView(context).apply {
            id = R.id.map_frame
        }
    }
    val lifeCycleObserver = rememberMapLifecycleObserver(mapView)
    val lifeCycle = LocalLifecycleOwner.current.lifecycle
    DisposableEffect(lifeCycle) {
        lifeCycle.addObserver(lifeCycleObserver)
        onDispose {
            lifeCycle.removeObserver(lifeCycleObserver)
        }
    }

    return mapView
}

@Composable
fun rememberMapLifecycleObserver(mapView: MapView): LifecycleEventObserver =
    remember(mapView) {
        LifecycleEventObserver { _, event ->
            when(event) {
                Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                Lifecycle.Event.ON_START -> mapView.onStart()
                Lifecycle.Event.ON_RESUME -> mapView.onResume()
                Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                Lifecycle.Event.ON_STOP -> mapView.onStop()
                Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                else -> throw IllegalStateException()
            }
        }
    }