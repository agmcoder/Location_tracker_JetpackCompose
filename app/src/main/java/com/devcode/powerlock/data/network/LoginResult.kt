package com.devcode.powerlock.data.network

sealed class LoginResult  {
	object Error : LoginResult()
	object Success : LoginResult()
}

/*
sealed class GenericResult <out T> {
	data class Error <T> (val e: Exception): GenericResult<T>()
	data class Success <T> (val anyData: T): GenericResult<T>()
}*/
