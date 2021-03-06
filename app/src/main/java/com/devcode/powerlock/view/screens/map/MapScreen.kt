package com.devcode.powerlock.view.screens.map

import android.os.Bundle
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleObserver
import androidx.navigation.NavController
import com.devcode.powerlock.view.MapToolBar
import com.devcode.powerlock.model.*
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.MarkerOptions
import com.orhanobut.logger.Logger
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@Composable
fun MapPage(navController : NavController,viewModel : MapPageViewModel = hiltViewModel()) {

	Scaffold(
		topBar = { MapToolBar() },
		content = { MapContent(viewModel = viewModel) },

		)
}

@Composable
fun MapContent(viewModel : MapPageViewModel) {
	val context = LocalContext.current
	//GoogleMaps()

	MyMap(viewModel = viewModel)
}

//-------------------------------------------------------------------------------------------
@Composable
fun MyMap(viewModel : MapPageViewModel) {
	val context = LocalContext.current
	val coroutineScope= rememberCoroutineScope()
	Logger.d("we are in mymap")

	val mapView = remember { MapView(context) }
	val lifecycle = LocalLifecycleOwner.current.lifecycle
	lifecycle.addObserver(rememberMapLifeCycle(map = mapView))
	AndroidView(
		factory = {
			mapView.apply {
				mapView.getMapAsync { googleMap ->
					val zoomLevel = 3f//rememberSaveable{ mutableStateOf(googleMap.cameraPosition.zoom)}
					//inicio de coordenadas
					coroutineScope.launch {
						viewModel.location.collect {latLng->
							googleMap.clear()
							googleMap
								.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel))
							googleMap.addMarker(latLng.let { it1 ->
								MarkerOptions().position(it1)
									.title(getAndroidId(context).toString())
									.snippet(latLng.toString())
									//.icon(Icons.Default.Phone)
							})

						}
					}




				}
			}
		}
	)

}

@Composable
fun rememberMapLifeCycle(map : MapView) : LifecycleObserver {

	return remember {
		LifecycleEventObserver { source, event ->
			when (event) {
				Lifecycle.Event.ON_CREATE -> map.onCreate(Bundle())
				Lifecycle.Event.ON_START -> map.onStart()
				Lifecycle.Event.ON_RESUME -> map.onResume()
				Lifecycle.Event.ON_PAUSE -> map.onPause()
				Lifecycle.Event.ON_STOP -> map.onStop()
				Lifecycle.Event.ON_DESTROY -> map.onDestroy()
				Lifecycle.Event.ON_ANY -> throw java.lang.IllegalStateException()
			}
		}
	}
}

/*//---------------------------------------------------------------------------------------
@Preview(name = "googlemaps")
@Composable
fun GoogleMaps() {

	var mapView = rememberMapViewWithLifeCycle()
	var latlng = remember { mutableStateOf("lat : 17.385,lang: 78.4867") }
	Box() {

		Column(
			modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
		) {
			AndroidView(
				{ mapView }
			) { mapView ->
				CoroutineScope(Dispatchers.Main).launch {

					mapView.getMapAsync {

						it.mapType = 1
						it.uiSettings.isZoomControlsEnabled = true
						val mark1 = LatLng(17.385, 78.4867) //Hyderabad


						it.moveCamera(CameraUpdateFactory.newLatLngZoom(mark1, 12f))
						it.setOnCameraIdleListener {
							it.clear()
							it.addMarker(
								MarkerOptions()
									.position(it.getCameraPosition().target)
							)
							latlng.value = ("lat: ${it.getCameraPosition().target.latitude}," +
									" \n lang: ${it.getCameraPosition().target.longitude}")
						}
						val markerOptions = MarkerOptions()
							.title("Hyderabad")
							.position(mark1)
						it.addMarker(markerOptions)

					}

				}
			}
		}

		Card(
			modifier = Modifier
                .background(Color.Transparent)
                .padding(12.dp), elevation = 10.dp
		) {
			OutlinedTextField(
				value = latlng.value,

				onValueChange = {

				},
				textStyle = TextStyle(
					color = Color.Red,

					fontFamily = FontFamily.SansSerif
				),
				modifier = Modifier.background(Color.Transparent),
				leadingIcon = {
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

@Composable
fun rememberMapViewWithLifeCycle() : MapView {
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
fun rememberMapLifecycleObserver(mapView : MapView) : LifecycleEventObserver =
	remember(mapView) {
		LifecycleEventObserver { _, event ->
			when (event) {
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

 */