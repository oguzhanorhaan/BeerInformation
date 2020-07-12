package com.example.beerinformation.data.datasource

import com.example.beerinformation.datasource.model.BeerItemDTO

interface BeersRemoteDataSource {

     suspend fun getBeerList(): List<BeerItemDTO>

     suspend fun getBeerItemDetails(id: String): List<BeerItemDTO>
}