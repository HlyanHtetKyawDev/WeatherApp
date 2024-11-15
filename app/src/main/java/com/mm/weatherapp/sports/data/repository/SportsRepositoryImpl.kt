package com.mm.weatherapp.sports.data.repository

import android.util.Log
import com.mm.weatherapp.core.data.helper.getResultOrThrow
import com.mm.weatherapp.core.data.network.exceptions.GeneralException
import com.mm.weatherapp.core.data.network.utils.Resource
import com.mm.weatherapp.sports.data.dataSource.SportsNetworkDataSource
import com.mm.weatherapp.sports.data.mapper.toSportItem
import com.mm.weatherapp.sports.domain.Sports
import com.mm.weatherapp.sports.domain.repository.SportsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SportsRepositoryImpl @Inject constructor(
    private val networkDataSource: SportsNetworkDataSource,
) : SportsRepository {

    override fun getSports(query: String): Flow<Resource<Sports>> =
        flow {
            emit(Resource.Loading())
            try {
                val data = getResultOrThrow {
                    networkDataSource.getSports(query)
                }
                if (data.error != null) {
                    emit(
                        Resource.Error(
                            message = data.error.message,
                            errorCode = data.error.code
                        )
                    )
                } else {
                    // convert each item to be used in presentation layer
                    val sports = Sports(
                        football = data.football.map { it.toSportItem(1) },
                        golf = data.golf.map { it.toSportItem(2) },
                        cricket = data.cricket.map { it.toSportItem(3) }
                    )
                    emit(Resource.Success(sports))
                }
            } catch (e: GeneralException) {
                Log.e("SearchRepositoryImpl", "sException: ${e.message}")
                emit(Resource.Error(e.message.orEmpty()))
            }
        }

}