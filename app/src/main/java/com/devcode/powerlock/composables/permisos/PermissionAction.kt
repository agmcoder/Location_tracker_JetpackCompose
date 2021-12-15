package com.devcode.powerlock.composables.permisos

sealed class PermissionAction
{
	object OnPermissionGranted:PermissionAction()
	object OnPermissionDenied:PermissionAction()
}