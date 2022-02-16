package com.devcode.powerlock.view.screens.menu

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcode.powerlock.data.network.LocationServices
import com.devcode.powerlock.model.getAndroidId
import com.devcode.powerlock.model.getGPSStateFirebase
import com.devcode.powerlock.model.saveLocation
import com.devcode.powerlock.model.setGPSStateFirebase
import com.google.android.libraries.maps.model.LatLng
import com.orhanobut.logger.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.prefs.Preferences
import javax.inject.Inject
import javax.inject.Singleton


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

init {
	getGPSStateViewModel()
}

// ------------------------------GPSSTATE ------------------------------
private fun getGPSStateViewModel() {
		viewModelScope.launch(IO) {
				checkIfGPSStateIsTheSame(getGPSStateFirebase())

		}
	}
	fun setGPSSTATEviewModel(state:Boolean) {
		viewModelScope.launch(IO) {
			if (!checkIfGPSStateIsTheSame(state))
				setGPSStateFirebase(state)


		}
	}

	private fun checkIfGPSStateIsTheSame(state : Boolean):Boolean {
		Logger.i("inside checkIfGPSStateIsTheSame")
		var diffState = state
		if (diffState != __GPSPowerState.value) {
			__GPSPowerState.value = diffState
			return false
		}
		return true

	}
//--------------------------Location---------------------------------------
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

