package com.mm.weatherapp.di

import android.content.Context
import androidx.credentials.CredentialManager
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {

    @Singleton
    @Provides
    fun providesCredentialManager(@ApplicationContext context: Context) =
        CredentialManager.create(context)

    @Singleton
    @Provides
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

}