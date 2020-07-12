package com.example.beerinformation.data.repository

import com.example.beerinformation.data.datasource.BeersRemoteDataSource
import com.example.beerinformation.data.model.Resource
import com.example.beerinformation.datasource.model.BeerItemDTO
import com.example.beerinformation.datasource.remote.ResponseHandler
import com.example.beerinformation.domain.repository.BeersRepository

class BeersRepositoryImpl constructor(
    private val remoteDataSource: BeersRemoteDataSource,
    private val responseHandler: ResponseHandler
) : BeersRepository {

    override suspend fun getBeerList(): Resource<List<BeerItemDTO>> {
        return  return try {
            val response = remoteDataSource.getBeerList()
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }

    override suspend fun getBeerItemDetails(id: String): Resource<List<BeerItemDTO>> {
        return return try {
            val response = remoteDataSource.getBeerItemDetails(id)
            return responseHandler.handleSuccess(response)
        } catch (e: Exception) {
            responseHandler.handleException(e)
        }
    }
}