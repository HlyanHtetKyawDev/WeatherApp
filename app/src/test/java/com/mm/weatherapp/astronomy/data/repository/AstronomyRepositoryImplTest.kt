package com.mm.weatherapp.astronomy.data.repository

import com.mm.weatherapp.astronomy.data.dataSource.AstronomyNetworkDataSource
import com.mm.weatherapp.astronomy.data.dto.Astro
import com.mm.weatherapp.astronomy.data.dto.AstronomyDto
import com.mm.weatherapp.astronomy.data.dto.AstronomyVO
import com.mm.weatherapp.astronomy.data.dto.Location
import com.mm.weatherapp.core.data.network.dto.Error
import com.mm.weatherapp.core.data.network.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class AstronomyRepositoryImplTest {

    private lateinit var astronomyNetworkResource: AstronomyNetworkDataSource
    private lateinit var astronomyRepository: AstronomyRepositoryImpl

    @Before
    fun setup() {
        astronomyNetworkResource = mockk()
        astronomyRepository = AstronomyRepositoryImpl(astronomyNetworkResource)
    }

    @Test
    fun `get astronomy with valid query London returns success data`() = runBlocking {
        val query = "London"
        val mockResponse = AstronomyDto(
            location = Location(
                name = "London",
                region = "City of London, Greater London",
                country = "United Kingdom",
                lat = 51.5171,
                lon = -0.1062,
                tz_id = "Europe/London",
                localtime_epoch = 1731738064,
                localtime = "2024-11-16 06:21"
            ),
            astronomy = AstronomyVO(
                Astro(
                    sunrise = "07:19 AM",
                    sunset = "04:10 PM",
                    moonrise = "03:31 PM",
                    moonset = "06:51 AM",
                    moon_phase = "Full Moon",
                    moon_illumination = 99,
                    is_moon_up = 1,
                    is_sun_up = 0
                )
            ),
            error = null
        )
        coEvery {
            astronomyNetworkResource.getAstronomy(query)
        } returns mockResponse

        val result = astronomyRepository.getAstronomy(query).toList()
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)

        val successData = (result[1] as Resource.Success).data
        assertEquals(false, successData == null)
    }

    @Test
    fun `get astronomy with valid query Yangon returns success data`() = runBlocking {
        val query = "Yangon"
        val mockResponse = AstronomyDto(
            location = Location(
                name = "Yangon",
                region = "Yangon",
                country = "Myanmar",
                lat = 16.7833,
                lon = 96.1667,
                tz_id = "Asia/Yangon",
                localtime_epoch = 1731739036,
                localtime = "2024-11-16 13:07"
            ),
            astronomy = AstronomyVO(
                Astro(
                    sunrise = "06:09 AM",
                    sunset = "05:31 PM",
                    moonrise = "04:55 PM",
                    moonset = "05:15 AM",
                    moon_phase = "Waxing Gibbous",
                    moon_illumination = 98,
                    is_moon_up = 1,
                    is_sun_up = 0
                )
            ),
            error = null
        )
        coEvery {
            astronomyNetworkResource.getAstronomy(query)
        } returns mockResponse

        val result = astronomyRepository.getAstronomy(query).toList()
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)

        val successData = (result[1] as Resource.Success).data
        assertEquals(false, successData == null)
    }

    @Test
    fun `get astronomy with non existent query then returns error`() = runBlocking {
        val query = "NonExistentCity"
        val errorMessage = "No matching location found."
        val errorCode = 1006
        val mockResponse = AstronomyDto(
            error = Error(
                code = errorCode,
                message = errorMessage
            ),
            astronomy = null,
            location = null
        )
        coEvery {
            astronomyNetworkResource.getAstronomy(query)
        } returns mockResponse

        val result = astronomyRepository.getAstronomy(query).toList()
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)

        val errorResult = result[1] as Resource.Error
        assertEquals(errorMessage, errorResult.message)
        assertEquals(errorCode, errorResult.errorCode)
    }

    @Test
    fun `get astronomy with empty query then returns error`() = runBlocking {
        val query = ""
        val errorMessage = "Parameter q is missing."
        val errorCode = 1003
        val mockResponse = AstronomyDto(
            error = Error(
                code = errorCode,
                message = errorMessage
            ),
            astronomy = null,
            location = null
        )
        coEvery {
            astronomyNetworkResource.getAstronomy(query)
        } returns mockResponse

        val result = astronomyRepository.getAstronomy(query).toList()
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)

        val errorResult = result[1] as Resource.Error
        assertEquals(errorMessage, errorResult.message)
        assertEquals(errorCode, errorResult.errorCode)
    }

}