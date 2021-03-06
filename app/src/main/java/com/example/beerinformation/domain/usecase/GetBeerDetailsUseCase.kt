package com.example.beerinformation.domain.usecase

import com.example.beerinformation.data.model.Resource
import com.example.beerinformation.datasource.model.BeerItemDTO
import com.example.beerinformation.domain.repository.BeersRepository

class GetBeerDetailsUseCase constructor(private val beersRepository: BeersRepository) {

    suspend fun get(id : String): Resource<List<BeerItemDTO>> =
        beersRepository.getBeerItemDetails(id)
}