package com.example.beerpunkapp.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BeersApi {

    @GET("beers")
    suspend fun getAll(@Query("page") page: Long): List<BeerModel>

    /*@GET("/loans/{id}")
    suspend fun getBeerById(@Path("id") loanId: Long): BeerModel*/
}
