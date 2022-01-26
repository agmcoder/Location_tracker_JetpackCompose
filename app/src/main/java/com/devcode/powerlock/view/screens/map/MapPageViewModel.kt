package com.devcode.powerlock.view.screens.map

import androidx.lifecycle.ViewModel
import com.devcode.powerlock.data.network.LocationServices
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class MapPageViewModel @Inject constructor(val locationServices : LocationServices) : ViewModel() {

	private var __location= MutableStateFlow(LatLng(0.0,0.0))
	val location=__location.asStateFlow()
	private var __diffLocations=LatLng(0.0,0.0)

	init {
		getPhoneLocation()

	}

	private fun getPhoneLocation() {



	}

}