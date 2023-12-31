package com.example.beerpunkapp.data

import com.example.beerpunkapp.domain.entity.Beer

class BeerConverter {
    fun convert(from: BeerModel): Beer =
        with(from) {
            Beer(
                id = id,
                name = name,
                tags = tagline,
                description = description,
                abv = abv,
                ibu = ibu,
                ebc = ebc,
                srm = srm,
                brew_date = first_brewed,
                food = food_pairing,
                ingredients = ingredients,
                photo = image_url
            )
        }
}