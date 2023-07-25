package com.example.beerpunkapp.domain.usecase

import com.example.beerpunkapp.domain.entity.Beer
import com.example.beerpunkapp.domain.repository.BeerRepository

class GetBeersBySearchUseCase (
    private val repository: BeerRepository
    ){
    suspend operator fun invoke(parameters: Map<String, String>): List<Beer> =
        repository.getBeersBySearch(parameters)
}