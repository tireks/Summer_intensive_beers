package com.example.beerpunkapp.domain.entity

data class Beer (
    val id: Int,
    val name: String,
    val tags: String,
    val description: String,
    val abv: String,
    //val ibu: String,
    //val ebc: String,
    val brew_date: String,
    val photo: String
        )