package com.example.beerpunkapp.domain.repository

import com.example.beerpunkapp.domain.entity.Beer

interface BeerRepository {
    suspend fun getAll(): List<Beer>

    /*suspend fun getById(id: Long): Loan*/
}