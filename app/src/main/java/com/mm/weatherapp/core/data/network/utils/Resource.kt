package com.mm.weatherapp.core.data.network.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : Resource<T>(data)
    class Loading<T>(data: T? = null, message: String? = null) : Resource<T>(data, message)
    class Error<T>(message: String, val errorCode: Int = GeneralError.CODE_NONE, data: T? = null) :
        Resource<T>(data, message) {
        val error: GeneralError
            get() = GeneralError(
                message = message.orEmpty(),
                code = errorCode
            )
    }

    inline fun getOrElse(onElse: (message: String) -> T): T {
        return data ?: onElse(message.orEmpty())
    }
}
