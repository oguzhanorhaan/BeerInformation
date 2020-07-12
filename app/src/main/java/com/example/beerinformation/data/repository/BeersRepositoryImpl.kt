package com.example.beerinformation.data.repository

import com.example.beerinformation.data.datasource.BeersRemoteDataSource
import com.example.beerinformation.datasource.model.BeerItemDTO
import com.example.beerinformation.domain.repository.BeersRepository
class BeersRepositoryImpl constructor(
    private val remoteDataSource: BeersRemoteDataSource
) : BeersRepository {

    override suspend fun getBeerList(): List<BeerItemDTO> {
        return remoteDataSource.getBeerList()
    }
}