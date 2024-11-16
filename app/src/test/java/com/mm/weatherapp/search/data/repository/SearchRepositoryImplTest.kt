import com.mm.weatherapp.core.data.network.utils.Resource
import com.mm.weatherapp.search.data.dataSource.SearchNetworkDataSource
import com.mm.weatherapp.search.data.dto.SearchDto
import com.mm.weatherapp.search.data.repository.SearchRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchRepositoryImplTest {
    private lateinit var searchNetworkDataSource: SearchNetworkDataSource
    private lateinit var searchRepository: SearchRepositoryImpl

    @Before
    fun setup() {
        searchNetworkDataSource = mockk()
        searchRepository = SearchRepositoryImpl(searchNetworkDataSource)
    }

    @Test
    fun `searchCities with valid query London returns success data with size 3`() = runBlocking {
        val query = "London"
        val mockResponse = listOf(
            SearchDto(
                id = 2801268,
                name = "London",
                region = "City of London, Greater London",
                country = "United Kingdom",
                lat = 51.52,
                lon = -0.11,
                url = "london-city-of-london-greater-london-united-kingdom"
            ),
            SearchDto(
                id = 315398,
                name = "London",
                region = "Ontario",
                country = "Canada",
                lat = 42.98,
                lon = -81.25,
                url = "london-ontario-canada"
            ),
            SearchDto(
                id = 2610925,
                name = "Londonderry",
                region = "New Hampshire",
                country = "United States of America",
                lat = 42.87,
                lon = -71.37,
                url = "londonderry-new-hampshire-united-states-of-america"
            )
        )

        coEvery {
            searchNetworkDataSource.searchCities(query)
        } returns mockResponse

        val result = searchRepository.searchCities(query).toList()
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)

        val successData = (result[1] as Resource.Success).data
        assertEquals(3, successData?.size)
    }

    @Test
    fun `searchCities with valid query Yangon returns success data with size 1`() = runBlocking {
        val query = "Yangon"
        val mockResponse = listOf(
            SearchDto(
                id = 1586549,
                name = "Yangon",
                region = "Yangon",
                country = "Myanmar",
                lat = 16.78,
                lon = 96.17,
                url = "yangon-yangon-myanmar"
            ),
        )

        coEvery {
            searchNetworkDataSource.searchCities(query)
        } returns mockResponse

        val result = searchRepository.searchCities(query).toList()
        assertEquals(2, result.size) // Loading + Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)

        val successData = (result[1] as Resource.Success).data
        assertEquals(1, successData?.size)
    }

    @Test
    fun `searchCities with non existent query then empty response returns error`() = runBlocking {
        val query = "NonExistentCity"
        coEvery {
            searchNetworkDataSource.searchCities(query)
        } returns emptyList()

        // When
        val result = searchRepository.searchCities(query).toList()

        // Then
        assertEquals(2, result.size) // Loading + Error
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)

        val errorResult = result[1] as Resource.Error
        assertEquals("List is empty", errorResult.message)
        assertEquals(-1, errorResult.errorCode)
    }

    @Test
    fun `searchCities with empty query returns error`() = runBlocking {
        val query = ""
        coEvery {
            searchNetworkDataSource.searchCities(query)
        } returns emptyList()

        // When
        val result = searchRepository.searchCities(query).toList()

        // Then
        assertEquals(2, result.size) // Loading + Error
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Error)

        val errorResult = result[1] as Resource.Error
        assertEquals("List is empty", errorResult.message)
        assertEquals(-1, errorResult.errorCode)
    }

    @Test
    fun `verify loading state is emitted first`() = runBlocking {
        val query = "London"
        coEvery {
            searchNetworkDataSource.searchCities(query)
        } returns emptyList()

        val result = searchRepository.searchCities(query).toList()
        assertTrue(result.first() is Resource.Loading)
    }

    @Test
    fun `searchCities with single result returns all items`() = runBlocking {
        // Given
        val query = "Yangon"
        val mockResponse = listOf(
            SearchDto(
                id = 1586549,
                name = "Yangon",
                region = "Yangon",
                country = "Myanmar",
                lat = 16.78,
                lon = 96.17,
                url = "yangon-yangon-myanmar"
            )
        )

        coEvery {
            searchNetworkDataSource.searchCities(query)
        } returns mockResponse

        val result = searchRepository.searchCities(query).toList()

        assertEquals(2, result.size) // Loading + Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)

        val successData = (result[1] as Resource.Success).data
        assertEquals(1, successData?.size)

        with(successData?.get(0)) {
            assertEquals("Yangon", this!!.name)
            assertEquals("Myanmar", country)
            assertEquals("Yangon", region)
        }
    }

    @Test
    fun `searchCities with multiple results returns all items`() = runBlocking {
        // Given
        val query = "London"
        val mockResponse = listOf(
            SearchDto(
                id = 2801268,
                name = "London",
                region = "City of London, Greater London",
                country = "United Kingdom",
                lat = 51.52,
                lon = -0.11,
                url = "london-city-of-london-greater-london-united-kingdom"
            ),
            SearchDto(
                id = 315398,
                name = "London",
                region = "Ontario",
                country = "Canada",
                lat = 42.98,
                lon = -81.25,
                url = "london-ontario-canada"
            ),
            SearchDto(
                id = 2610925,
                name = "Londonderry",
                region = "New Hampshire",
                country = "United States of America",
                lat = 42.87,
                lon = -71.37,
                url = "londonderry-new-hampshire-united-states-of-america"
            )
        )

        coEvery {
            searchNetworkDataSource.searchCities(query)
        } returns mockResponse

        val result = searchRepository.searchCities(query).toList()

        assertEquals(2, result.size) // Loading + Success
        assertTrue(result[0] is Resource.Loading)
        assertTrue(result[1] is Resource.Success)

        val successData = (result[1] as Resource.Success).data
        assertEquals(3, successData?.size)

        with(successData?.get(0)) {
            assertEquals("London", this!!.name)
            assertEquals("United Kingdom", country)
            assertEquals("City of London, Greater London", region)
        }

        with(successData?.get(1)) {
            assertEquals("London", this!!.name)
            assertEquals("Canada", country)
            assertEquals("Ontario", region)
        }

        with(successData?.get(2)) {
            assertEquals("Londonderry", this!!.name)
            assertEquals("United States of America", country)
            assertEquals("New Hampshire", region)
        }
    }
}
