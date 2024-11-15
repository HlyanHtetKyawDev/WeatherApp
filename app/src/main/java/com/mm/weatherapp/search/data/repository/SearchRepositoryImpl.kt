package com.mm.weatherapp.search.data.repository

import android.util.Log
import com.mm.weatherapp.core.data.helper.getResultOrThrow
import com.mm.weatherapp.core.data.network.exceptions.GeneralException
import com.mm.weatherapp.core.data.network.utils.Resource
import com.mm.weatherapp.search.data.dataSource.SearchNetworkDataSource
import com.mm.weatherapp.search.data.mapper.toSearch
import com.mm.weatherapp.search.domain.Search
import com.mm.weatherapp.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val networkDataSource: SearchNetworkDataSource,
) : SearchRepository {

    override fun searchCities(query: String): Flow<Resource<List<Search>>> =
        flow {
            emit(Resource.Loading())
            try {
                val data = getResultOrThrow {
                    networkDataSource.searchCities(query)
                }
                if (data.isNotEmpty()) {
                    emit(Resource.Success(data.map { it.toSearch() }))
                } else {
                    emit(
                        Resource.Error(
                            message = "List is empty",
                            errorCode = -1
                        )
                    )
                }
            } catch (e: GeneralException) {
                Log.e("SearchRepositoryImpl", "sException: ${e.message}")
                emit(Resource.Error(e.message.orEmpty()))
            }
        }
}