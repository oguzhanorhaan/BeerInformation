package com.example.beerinformation.data.repository

import com.example.beerinformation.data.datasource.BeersRemoteDataSource
import com.example.beerinformation.data.model.Resource
import com.example.beerinformation.datasource.model.BeerItemDTO
import com.example.beerinformation.datasource.remote.BeerApiService
import com.example.beerinformation.datasource.remote.BeersRemoteDataSourceImpl
import com.example.beerinformation.datasource.remote.ResponseHandler
import com.example.beerinformation.domain.repository.BeersRepository
import com.example.beerinformation.presentation.beerlist.BeersApiStatus
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

class BeerRepositoryTest {

    private val responseHandler = ResponseHandler()
    private lateinit var beerApiService: BeerApiService
    private lateinit var beersRepository: BeersRepository
    private lateinit var remoteDataSource: BeersRemoteDataSource
    private var invalidId = "-9999"
    private var validId= "1"
    private val errorResponse = Resource.error("Bad Request", null)
    private val mockItem : List<BeerItemDTO> = listOf(BeerItemDTO(
        Integer(1),"Buzz","A Real Bitter Experience.",
        "A light, crisp and bitter IPA brewed with English and American hops. A small batch brewed only once."
    , "The earthy and floral aromas from the hops can be overpowering. Drop a little Cascade in at the end of the boil to lift the profile with a bit of citrus."
    ,"https://images.punkapi.com/v2/keg.png"))

    private val mockResponse = Resource(BeersApiStatus.DONE, mockItem, null)

    @Before
    fun setUp() {
        beerApiService = mock()
        val mockException: HttpException = mock()
        beersRepository = BeersRepositoryImpl(
            BeersRemoteDataSourceImpl(beerApiService),
            responseHandler
        )
        whenever(mockException.code()).thenReturn(400)
        runBlocking {
            whenever(beerApiService.getBeerItemDetails(invalidId)).thenThrow(mockException)
           whenever(beerApiService.getBeerItemDetails(validId)).then { responseHandler.handleSuccess(mockItem) } .thenReturn(CompletableDeferred(mockItem))
        }
    }

    @Test
    fun `test getBeerItemDetails when valid id is requested, then item returned`() =
        runBlocking {
            assertEquals(mockItem[0].id, beersRepository.getBeerItemDetails(validId))
        }

    @Test
    fun `test getBeerItemDetails when invalid id is requested, then error is returned`() =
        runBlocking {
            assertEquals(errorResponse.status, beersRepository.getBeerItemDetails(invalidId).status)
        }
}