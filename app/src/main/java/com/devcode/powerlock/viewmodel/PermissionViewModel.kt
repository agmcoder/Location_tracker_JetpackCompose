package com.devcode.powerlock.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PermissionViewModel : ViewModel() {
	private val _performLocationAction : MutableStateFlow<Boolean> = MutableStateFlow(false)
	val performLocationAction=_performLocationAction.asStateFlow()
	fun setPerformLocationAction(request:Boolean){
		_performLocationAction.value=request
	}
}