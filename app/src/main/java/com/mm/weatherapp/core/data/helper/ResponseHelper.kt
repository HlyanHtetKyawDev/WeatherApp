package com.mm.weatherapp.core.data.helper

import android.util.Log
import com.mm.weatherapp.core.data.network.exceptions.GeneralException
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> getResultOrThrow(
    caller: suspend () -> T,
): T {
    try {
        return caller()
    } catch (e: HttpException) {
        throw GeneralException(e.message ?: "Unknown Http error")
    } catch (e: IOException) {
        Log.e("GeneralException", "IO: ${e.stackTraceToString()}")
        throw GeneralException("No internet connection")
    } catch (e: Exception) {
        Log.e("GeneralException", "Network exception: ${e.stackTraceToString()}")
        throw GeneralException(e.message ?: "Something went wrong")
    }
}
