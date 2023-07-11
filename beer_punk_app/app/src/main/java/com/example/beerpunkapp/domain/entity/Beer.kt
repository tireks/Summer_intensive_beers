package com.example.beerpunkapp.domain.entity

import com.example.beerpunkapp.data.BeerModel

data class Beer (
    val id: Long?,
    val name: String?,
    val tags: String?,
    val description: String?,
    val abv: String?,
    val ibu: String?,
    val ebc: String?,
    val srm: String?,
    val brew_date: String?,
    val food: Array<String?>,
    val photo: String?
        ){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BeerModel

        if (!food.contentEquals(other.food_pairing)) return false

        return true
    }

    override fun hashCode(): Int {
        return food.contentHashCode()
    }
}