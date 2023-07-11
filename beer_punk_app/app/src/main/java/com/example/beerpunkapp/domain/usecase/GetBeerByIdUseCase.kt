package com.example.beerpunkapp.domain.usecase

import com.example.beerpunkapp.domain.entity.Beer
import com.example.beerpunkapp.domain.repository.BeerRepository

class GetBeerByIdUseCase (
    private val repository: BeerRepository,
) {

    suspend operator fun invoke(id: Long): List<Beer> =
        repository.getById(id)
}