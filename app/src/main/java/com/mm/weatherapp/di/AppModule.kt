package com.mm.weatherapp.di

import com.mm.weatherapp.astronomy.data.dataSource.AstronomyNetworkDataSource
import com.mm.weatherapp.astronomy.data.repository.AstronomyRepositoryImpl
import com.mm.weatherapp.astronomy.domain.repository.AstronomyRepository
import com.mm.weatherapp.core.data.network.dataSource.NetworkDataSourceImpl
import com.mm.weatherapp.search.data.dataSource.SearchNetworkDataSource
import com.mm.weatherapp.search.data.repository.SearchRepositoryImpl
import com.mm.weatherapp.search.domain.repository.SearchRepository
import com.mm.weatherapp.sports.data.dataSource.SportsNetworkDataSource
import com.mm.weatherapp.sports.data.repository.SportsRepositoryImpl
import com.mm.weatherapp.sports.domain.repository.SportsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    abstract fun bindSearchNetworkDataSource(
        dataSource: NetworkDataSourceImpl,
    ): SearchNetworkDataSource

    @Binds
    abstract fun bindAstronomyNetworkDataSource(
        dataSource: NetworkDataSourceImpl,
    ): AstronomyNetworkDataSource

    @Binds
    abstract fun bindSportsNetworkDataSource(
        dataSource: NetworkDataSourceImpl,
    ): SportsNetworkDataSource

    @Binds
    abstract fun bindSearchRepository(
        repository: SearchRepositoryImpl,
    ): SearchRepository

    @Binds
    abstract fun bindAstronomyRepository(
        repository: AstronomyRepositoryImpl,
    ): AstronomyRepository

    @Binds
    abstract fun bindSportsRepository(
        repository: SportsRepositoryImpl,
    ): SportsRepository
}