package com.example.beerpunkapp.model

import android.app.Application

class App : Application() {
    val beerService = BeerService()
}