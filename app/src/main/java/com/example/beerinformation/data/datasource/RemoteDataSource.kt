package com.example.beerinformation.data.datasource

import com.example.beerinformation.datasource.model.BeerItemDTO
import kotlinx.coroutines.Deferred

interface BeersRemoteDataSource {

     suspend fun getBeerList(): List<BeerItemDTO>
}