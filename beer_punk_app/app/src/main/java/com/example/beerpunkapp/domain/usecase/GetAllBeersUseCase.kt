package com.example.beerpunkapp.domain.usecase

import com.example.beerpunkapp.domain.entity.Beer
import com.example.beerpunkapp.domain.repository.BeerRepository

class GetAllBeersUseCase(
    private val repository: BeerRepository,
) {
    suspend operator fun invoke(): List<Beer> =
        repository.getAll()
}