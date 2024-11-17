package com.mm.weatherapp.auth.domain.useCase

import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.exceptions.ClearCredentialProviderConfigurationException
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class GoogleSignOutUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val credentialManager: CredentialManager,
) {
    suspend operator fun invoke() {
        try {
            credentialManager.clearCredentialState(
                ClearCredentialStateRequest()
            )
            firebaseAuth.signOut()
        } catch (e: ClearCredentialProviderConfigurationException) {
            Log.e("TAG", "invoke: " + e.localizedMessage)
        }

    }

}
