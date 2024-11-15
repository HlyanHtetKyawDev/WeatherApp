package com.mm.weatherapp.astronomy.data.repository

import android.util.Log
import com.mm.weatherapp.astronomy.data.dataSource.AstronomyNetworkDataSource
import com.mm.weatherapp.astronomy.data.mapper.toAstronomy
import com.mm.weatherapp.astronomy.domain.Astronomy
import com.mm.weatherapp.astronomy.domain.repository.AstronomyRepository
import com.mm.weatherapp.core.data.helper.getResultOrThrow
import com.mm.weatherapp.core.data.network.exceptions.GeneralException
import com.mm.weatherapp.core.data.network.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AstronomyRepositoryImpl @Inject constructor(
    private val networkDataSource: AstronomyNetworkDataSource,
) : AstronomyRepository {

    override fun getAstronomy(query: String): Flow<Resource<Astronomy>> = flow {
        emit(Resource.Loading())
        try {
            val data = getResultOrThrow {
                networkDataSource.getAstronomy(query)
            }
            if (data.error != null) {
                emit(
                    Resource.Error(
                        message = data.error.message,
                        errorCode = data.error.code
                    )
                )
            } else {
                emit(Resource.Success(data.toAstronomy()))
            }
        } catch (e: GeneralException) {
            Log.e("AstronomyRepositoryImpl", "sException: ${e.message}")
            emit(Resource.Error(e.message.orEmpty()))
        }
    }


}
