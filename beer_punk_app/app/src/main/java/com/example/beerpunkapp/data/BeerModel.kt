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
    val ingredients: Ingredients?,
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

data class Ingredients (
    val malt : Array<Malt?>,
    val hops : Array<Hops?>,
    val yeast : String?
        ) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ingredients

        if (!malt.contentEquals(other.malt)) return false
        if (!hops.contentEquals(other.hops)) return false
        if (yeast != other.yeast) return false

        return true
    }

    override fun hashCode(): Int {
        var result = malt.contentHashCode()
        result = 31 * result + hops.contentHashCode()
        result = 31 * result + (yeast?.hashCode() ?: 0)
        return result
    }
}

data class Hops (
    val name: String?
        )

data class Malt (
    val name: String?
        )
