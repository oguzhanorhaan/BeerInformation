package com.example.beerinformation.domain.repository

import com.example.beerinformation.data.model.Resource
import com.example.beerinformation.datasource.model.BeerItemDTO
import com.example.beerinformation.domain.model.BeerItem
import kotlinx.coroutines.Deferred

interface BeersRepository {

    suspend fun getBeerList(): Resource<List<BeerItemDTO>>

    suspend fun getBeerItemDetails(id: String): Resource<List<BeerItemDTO>>
}