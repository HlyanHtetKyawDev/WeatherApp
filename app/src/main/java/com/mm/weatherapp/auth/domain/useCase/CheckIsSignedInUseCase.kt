package com.mm.weatherapp.auth.domain.useCase

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class CheckIsSignedInUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) {

    operator fun invoke(): Boolean = isSingedIn()

    private fun isSingedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }
}
