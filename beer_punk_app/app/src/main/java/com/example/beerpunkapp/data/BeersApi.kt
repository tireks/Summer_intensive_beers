package com.example.beerpunkapp.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface BeersApi {

    @GET("beers")
    suspend fun getAll(@Query("page") page: Long): List<BeerModel>

    @GET("beers")
    suspend fun getBeerById(@Query("ids") beerId: Long): List<BeerModel>

    @GET("beers/random")
    suspend fun getRandomBeer() : List<BeerModel>

    @GET("beers")
    suspend fun getBeersBySearch(@QueryMap parameters : Map<String, String> ): List<BeerModel>

}
