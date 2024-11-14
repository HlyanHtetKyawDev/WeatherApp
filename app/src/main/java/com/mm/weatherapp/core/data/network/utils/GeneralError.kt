package com.mm.weatherapp.core.data.network.utils

data class GeneralError(
    val message: String = "",
    val code: Int = CODE_NONE
) {
    companion object {
        const val CODE_NONE = -1
    }
}

fun GeneralError?.orEmpty() = GeneralError(
    message = "Unknown Error",
)

fun Throwable.toError() = GeneralError(
    message = this.localizedMessage.orEmpty()
)
