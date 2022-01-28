package com.devcode.powerlock.view.screens.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcode.powerlock.data.network.LocationServices
import com.devcode.powerlock.model.latLngSnapshotObserver
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MapPageViewModel @Inject constructor() : ViewModel() {

	private var __location= MutableStateFlow(LatLng(0.0,0.0))
	val location=__location.asStateFlow()

	private var __diffLocations=LatLng(0.0,0.0)

	private var _ejemplo = MutableStateFlow(false)
	val ejemplo: StateFlow<Boolean>
		get() = _ejemplo

	init {
		getPhoneLocation()

	}

	private fun getPhoneLocation() {
		viewModelScope.launch(IO) {
			latLngSnapshotObserver(isObserving = true).collect { latLng->
				checkIfLocationsAreTheSame(latLng)
			}

		}

	}

	private fun checkIfLocationsAreTheSame(latLng : LatLng) {

		__diffLocations=latLng
		if (__diffLocations!=__location.value){
			__location.value=__diffLocations
		}

	}

}