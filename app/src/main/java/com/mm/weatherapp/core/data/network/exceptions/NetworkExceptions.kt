package com.mm.weatherapp.core.data.network.exceptions

class FailResponseException(message: String?) : Exception(message ?: "Failed to call network")

class EmptyResponseException : Exception("Empty response!")

class GeneralException(message: String?) : Exception(message ?: "Something went wrong!")
