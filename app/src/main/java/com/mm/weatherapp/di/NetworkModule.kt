package com.mm.weatherapp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mm.weatherapp.BuildConfig
import com.mm.weatherapp.core.data.network.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient(
        @LoggingInterceptor httpLoggingInterceptor: HttpLoggingInterceptor,
        @Code400Interceptor code400Interceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor { chain ->
                val url = chain.request().url
                    .newBuilder()
                    .addQueryParameter("key", BuildConfig.API_KEY)
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }
            .addInterceptor(code400Interceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideGsonConverter() = GsonBuilder()
        .setLenient()
        .create()!!


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gSon: Gson): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gSon))
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    // skip 400 bad request to show error message from response
    @Provides
    @Singleton
    @Code400Interceptor
    fun provideCode400Interceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            if (response.code == 400) {
                response.newBuilder().code(200).build()
            } else {
                response
            }
        }
    }

    @Singleton
    @Provides
    @LoggingInterceptor
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }


    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class LoggingInterceptor

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class Code400Interceptor
}