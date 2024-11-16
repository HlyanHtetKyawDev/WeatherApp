package com.mm.weatherapp.sports.data.repository

import com.mm.weatherapp.core.data.network.utils.Resource
import com.mm.weatherapp.sports.data.dataSource.SportsNetworkDataSource
import com.mm.weatherapp.sports.data.dto.SportItemDto
import com.mm.weatherapp.sports.data.dto.SportsDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SportsRepositoryImplTest {

    private lateinit var sportsNetworkDataSource: SportsNetworkDataSource
    private lateinit var sportsRepository: SportsRepositoryImpl

    @Before
    fun setup() {
        sportsNetworkDataSource = mockk()
        sportsRepository = SportsRepositoryImpl(sportsNetworkDataSource)
    }

    @Test
    fun `get sports with valid query London returns success data`() = runBlocking {
        val query = "London"
        val mockResponse = SportsDto(
            football = listOf(
                SportItemDto(
                    stadium = "Arsenal",
                    country = "United Kingdom",
                    region = "",
                    tournament = "Premier League",
                    start = "2024-11-23 15:00",
                    match = "Arsenal vs Nottingham Forest"
                ),
                SportItemDto(
                    stadium = "Millwall Fc",
                    country = "United Kingdom",
                    region = "",
                    tournament = "Sky Bet Championship",
                    start = "2024-11-23 15:00",
                    match = "Millwall vs Sunderland"
                ),
                SportItemDto(
                    stadium = "Queens Park Rangers Fc",
                    country = "United Kingdom",
                    region = "",
                    tournament = "Sky Bet Championship",
                    start = "2024-11-23 15:00",
                    match = "Queens Park Rangers vs Stoke City"
                ),
            ),
            cricket = emptyList(),
            golf = emptyList(),
            error = null
        )
        coEvery {
            sportsNetworkDataSource.getSports(query)
        } returns mockResponse

        val result = sportsRepository.getSports(query).toList()
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)

        val successData = (result[1] as Resource.Success).data
        assertEquals(false, successData == null)
        assertEquals(3, successData?.football?.size)
        assertEquals(true, successData?.cricket.isNullOrEmpty())
        assertEquals(true, successData?.golf.isNullOrEmpty())
    }

    @Test
    fun `get sports with valid query Yangon returns success data`() = runBlocking {
        val query = "Yangon"
        val mockResponse = SportsDto(
            football = emptyList(),
            cricket = emptyList(),
            golf = emptyList(),
            error = null
        )
        coEvery {
            sportsNetworkDataSource.getSports(query)
        } returns mockResponse

        val result = sportsRepository.getSports(query).toList()
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)

        val successData = (result[1] as Resource.Success).data
        assertEquals(false, successData == null)
        assertEquals(true, successData?.cricket.isNullOrEmpty())
        assertEquals(true, successData?.football.isNullOrEmpty())
        assertEquals(true, successData?.golf.isNullOrEmpty())
    }

    @Test
    fun `get sports with non existent query then returns error`() = runBlocking {
        val query = "NonExistentCity"
        val errorMessage = "No matching location found."
        val errorCode = 1006
        val mockResponse = SportsDto(
            football = emptyList(),
            cricket = emptyList(),
            golf = emptyList(),
            error = com.mm.weatherapp.core.data.network.dto.Error(
                code = errorCode,
                message = errorMessage
            ),
        )
        coEvery {
            sportsNetworkDataSource.getSports(query)
        } returns mockResponse

        val result = sportsRepository.getSports(query).toList()
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)

        val errorResult = result[1] as Resource.Error
        assertEquals(errorMessage, errorResult.message)
        assertEquals(errorCode, errorResult.errorCode)
    }

    @Test
    fun `get sports with empty query then returns error`() = runBlocking {
        val query = ""
        val errorMessage = "Parameter q is missing."
        val errorCode = 1003
        val mockResponse = SportsDto(
            football = emptyList(),
            cricket = emptyList(),
            golf = emptyList(),
            error = com.mm.weatherapp.core.data.network.dto.Error(
                code = errorCode,
                message = errorMessage
            ),
        )
        coEvery {
            sportsNetworkDataSource.getSports(query)
        } returns mockResponse

        val result = sportsRepository.getSports(query).toList()
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)

        val errorResult = result[1] as Resource.Error
        assertEquals(errorMessage, errorResult.message)
        assertEquals(errorCode, errorResult.errorCode)
    }
}

