package com.mm.weatherapp.auth.domain.useCase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.mm.weatherapp.auth.data.LoginResponse
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PasswordSignInUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) {
    private val TAG = "passwordSignInUseCase"

    suspend operator fun invoke(email: String, password: String): LoginResponse =
        signInWithPassword(email, password)

    private suspend fun signInWithPassword(email: String, password: String): LoginResponse {
        if (email.isEmpty()) {
            return LoginResponse(message = "Email is required.")
        }
        if (password.isEmpty()) {
            return LoginResponse(message = "Password is required.")
        }
        if (!email.isEmailValid()) {
            return LoginResponse(message = "Email is not valid.")
        }
        if (firebaseAuth.currentUser != null) {
            return LoginResponse(isSuccess = true)
        }
        try {
            var message: String? = null
            val result = suspendCoroutine { continuation ->
                firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        message = "Login Success"
                        continuation.resume(true)
                    }
                    .addOnFailureListener {
                        message = it.message
                        continuation.resume(false)
                    }
            }

            return LoginResponse(isSuccess = result, message = message)

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            Log.d(TAG, "login exception ${e.message}")
            return LoginResponse(isSuccess = false, message = e.message)
        }
    }
}

fun String.isEmailValid(): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return emailRegex.toRegex().matches(this)
}