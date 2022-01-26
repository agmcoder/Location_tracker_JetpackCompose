package com.devcode.powerlock.view.screens.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devcode.powerlock.model.checkIfThereIsAnotherEqualAndroidId
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginPageViewModel @Inject constructor():ViewModel() {

	private var __isThereAnotherEqualAndroidID= MutableLiveData<Boolean>()
	val isThereAnotherEqualAndroidID:LiveData<Boolean>
		get() = __isThereAnotherEqualAndroidID



	fun checkAndroidIdExistence(context : Context){
		viewModelScope.launch {
			__isThereAnotherEqualAndroidID.value= withContext(Dispatchers.IO) {
				checkIfThereIsAnotherEqualAndroidId(context)
			}?:false
		}

	}

}