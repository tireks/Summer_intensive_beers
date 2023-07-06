package com.example.beerpunkapp.data

data class BeerModel (
    val id: Int,
    val name: String,
    val tagline: String,
    val description: String,
    val abv: String,
    //val ibu: String,
    //val ebc: String,
    val first_brewed: String,
    val image_url: String
        )