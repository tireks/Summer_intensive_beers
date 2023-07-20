package com.example.beerpunkapp.domain.repository

import com.example.beerpunkapp.domain.entity.Beer

interface BeerRepository {
    suspend fun getAll(page: Long): List<Beer>

    suspend fun getById(id: Long): List<Beer>

    suspend fun getRandomBeer() : List<Beer>

    suspend fun getBeersBySearch(parameters: Map<String, String>) : List<Beer>
}