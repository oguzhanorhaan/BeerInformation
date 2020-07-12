package com.example.beerinformation.domain.usecase

import com.example.beerinformation.datasource.model.BeerItemDTO
import com.example.beerinformation.domain.repository.BeersRepository


class GetBeerListUseCase constructor(private val beersRepository: BeersRepository) {

    suspend fun getBeerList(): List<BeerItemDTO> =
        beersRepository.getBeerList()
}