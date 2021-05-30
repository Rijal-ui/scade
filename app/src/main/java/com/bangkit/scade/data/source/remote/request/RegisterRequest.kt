package com.bangkit.scade.data.source.remote.request

data class RegisterRequest(
	val password: String,
	val address: String,
	val phone: String,
	val name: String,
	val email: String
)