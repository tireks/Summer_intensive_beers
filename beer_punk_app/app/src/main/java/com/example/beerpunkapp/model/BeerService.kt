package com.example.beerpunkapp.model

import com.example.beerpunkapp.R
import com.github.javafaker.Faker

class BeerService {
    private var beer = mutableListOf<BeerTestModel>()

    init {
        val faker = Faker.instance()
        IMAGES.shuffle()
        beer = (1..20).map { BeerTestModel(
            id = it.toInt(),
            naming = faker.beer().name(),
            tags = faker.harryPotter().quote(),
            description = R.string.recycler_view_description_placeholder.toString(),
            photo = IMAGES[it % IMAGES.size]
        ) }.toMutableList()
    }



    companion object{
        private val IMAGES = mutableListOf(
            "https://images.punkapi.com/v2/keg.png",
            "https://images.punkapi.com/v2/82.png",
            "https://images.punkapi.com/v2/83.png",
            "https://images.punkapi.com/v2/85.png",
            "https://images.punkapi.com/v2/86.png",
            "https://images.punkapi.com/v2/87.png",
            "https://images.punkapi.com/v2/88.png"
        )
    }
}