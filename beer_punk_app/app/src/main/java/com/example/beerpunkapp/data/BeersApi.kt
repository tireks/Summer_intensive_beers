package com.example.beerpunkapp.data

import retrofit2.http.GET
import retrofit2.http.Path

interface BeersApi {

    @GET("beers?page=2")
    suspend fun getAll(): List<BeerModel>

    /*@GET("/loans/{id}")
    suspend fun getBeerById(@Path("id") loanId: Long): BeerModel*/
}
