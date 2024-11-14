package com.mm.weatherapp.di

import com.mm.weatherapp.core.data.network.dataSource.NetworkDataSourceImpl
import com.mm.weatherapp.core.data.network.service.ApiService
import com.mm.weatherapp.search.data.dataSource.SearchNetworkDataSource
import com.mm.weatherapp.search.data.repository.SearchRepositoryImpl
import com.mm.weatherapp.search.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindSearchNetworkDataSource(
        dataSource: NetworkDataSourceImpl,
    ): SearchNetworkDataSource

    @Binds
    abstract fun bindSearchRepository(
        repository: SearchRepositoryImpl,
    ): SearchRepository

    @Module
    @InstallIn(SingletonComponent::class)
    object Provide {

        @Provides
        @Singleton
        fun provideApiService(retrofit: Retrofit): ApiService {
            return retrofit.create(ApiService::class.java)
        }
    }
}