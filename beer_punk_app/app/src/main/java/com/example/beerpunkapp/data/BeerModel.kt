package com.example.beerpunkapp.data

data class BeerModel (
    val id: Long?,
    val name: String?,
    val tagline: String?,
    val description: String?,
    val abv: String?,
    val ibu: String?,
    val ebc: String?,
    val srm: String?,
    val first_brewed: String?,
    val food_pairing: Array<String?>,
    val image_url: String?
        ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BeerModel

        if (!food_pairing.contentEquals(other.food_pairing)) return false

        return true
    }

    override fun hashCode(): Int {
        return food_pairing.contentHashCode()
    }
}