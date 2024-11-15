package com.mm.weatherapp.auth.domain.useCase

import android.content.Context
import android.util.Log
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mm.weatherapp.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

class GoogleSignInUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val credentialManager: CredentialManager,
    @ApplicationContext val context: Context
) {
    private val TAG = "googleSignInUseCase"

    suspend operator fun invoke(): Boolean = signIn()

    private suspend fun signIn(): Boolean {
        if (firebaseAuth.currentUser != null) {
            return true
        }
        try {
            val result = buildCredentialRequest()
            return handleSingIn(result)
        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) throw e
            Log.d(TAG , "signIn error: ${e.message}")
            return false
        }
    }

    private suspend fun handleSingIn(result: GetCredentialResponse): Boolean {
        val credential = result.credential
        if (credential is CustomCredential &&
            credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                Log.d(TAG , "name: ${tokenCredential.displayName}")
                Log.d(TAG , "email: ${tokenCredential.id}")
                Log.d(TAG , "image: ${tokenCredential.profilePictureUri}")
                val authCredential = GoogleAuthProvider.getCredential(
                    tokenCredential.idToken, null
                )
                val authResult = firebaseAuth.signInWithCredential(authCredential).await()
                return authResult.user != null
            } catch (e: GoogleIdTokenParsingException) {
                Log.d(TAG , "GoogleIdTokenParsingException: ${e.message}")
                return false
            }
        } else {
            Log.d(TAG , "credential is not GoogleIdTokenCredential")
            return false
        }

    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(BuildConfig.WEB_CLIENT_ID)
                    .setAutoSelectEnabled(false)
                    .build()
            )
            .build()
        return credentialManager.getCredential(
            request = request, context = context
        )
    }


}
