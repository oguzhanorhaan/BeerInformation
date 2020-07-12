package com.example.beerinformation.datasource.remote

import com.example.beerinformation.data.datasource.BeersRemoteDataSource
import com.example.beerinformation.datasource.model.BeerItemDTO
import kotlinx.coroutines.Deferred

class BeersRemoteDataSourceImpl constructor(
    private val api: BeerApiService
) : BeersRemoteDataSource {

    override suspend fun getBeerList(): List<BeerItemDTO>{
        return api.getBeerItems().await()
    }
}