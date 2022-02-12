package com.devcode.powerlock.view.screens.menu

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcode.powerlock.data.network.LocationServices
import com.devcode.powerlock.model.getAndroidId
import com.devcode.powerlock.model.getGPSStateFirebase
import com.devcode.powerlock.model.saveLocation
import com.devcode.powerlock.model.setGPSStateFirebase
import com.google.android.libraries.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(private val locationServices : LocationServices) :
	ViewModel() {
	//private val _userLocation = MutableLiveData<LatLng>()
	//val userLocation:LiveData<LatLng>
	//	get() = _userLocation
	//private lateinit var location:LatLng
	private var __GPSPowerState = MutableStateFlow(false)
	val GPSPowerState : StateFlow<Boolean>
		get() = __GPSPowerState
	private var __diffState = false

	fun getGPSStateViewModel() {
		viewModelScope.launch(IO) {
			getGPSStateFirebase().collect { state ->
				checkIfGPSStateIsTheSame(state)

			}

		}
	}
	fun setGPSSTATEviewModel(state:Boolean) {
		viewModelScope.launch(IO) {
			checkIfGPSStateIsTheSame(state)
			setGPSStateFirebase(state)
		}
	}

	private fun checkIfGPSStateIsTheSame(state : Boolean) {
		__diffState = state
		if (__diffState != __GPSPowerState.value) {
			__GPSPowerState.value = __diffState
		}
	}

	fun startLocationUpdates(context : Context) {
		viewModelScope.launch(IO) {
			val result = locationServices.startLocationUpdates()
			result.collect {
				it?.let {
					saveLatlngInFirebase(context, it)
				}
			}
		}
	}

	private suspend fun saveLatlngInFirebase(context : Context, location : LatLng) {

		saveLocation(
			LatLng(location.latitude, location.longitude),
			getAndroidId(context).orEmpty()
		)

	}

	fun stopLocationUpdates() {
		viewModelScope.launch(IO) {
			locationServices.stopLocationUpdates()
		}

	}
}

