package com.example.beerinformation.datasource.remote

import com.example.beerinformation.data.datasource.BeersRemoteDataSource
import com.example.beerinformation.datasource.model.BeerItemDTO

class BeersRemoteDataSourceImpl constructor(
    private val api: BeerApiService
) : BeersRemoteDataSource {

    override suspend fun getBeerList(): List<BeerItemDTO>{
        return api.getBeerItems().await()
    }

    override suspend fun getBeerItemDetails(id: String): List<BeerItemDTO> {
        return api.getBeerItemDetails("beers/$id").await()
    }
}