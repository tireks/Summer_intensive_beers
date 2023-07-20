package com.example.beerpunkapp.data

import com.example.beerpunkapp.domain.entity.Beer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import com.example.beerpunkapp.domain.repository.BeerRepository

fun OkHttpClient.Builder.ignoreAllSslErrors(): OkHttpClient.Builder {
    val naiveTrustManager = object : X509TrustManager {
        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
        override fun checkClientTrusted(certs: Array<X509Certificate>, authType: String) = Unit
        override fun checkServerTrusted(certs: Array<X509Certificate>, authType: String) = Unit
    }

    val insecureSocketFactory = SSLContext.getInstance("TLSv1.2").apply {
        val trustAllCerts = arrayOf<TrustManager>(naiveTrustManager)
        init(null, trustAllCerts, SecureRandom())
    }.socketFactory

    sslSocketFactory(insecureSocketFactory, naiveTrustManager)
    hostnameVerifier { _, _ -> true }
    return this
}

class BeerRepositoryImpl : BeerRepository{
    private companion object {

        const val BASE_URL = "https://api.punkapi.com/v2/"
        const val CONNECT_TIMEOUT = 10L
        const val WRITE_TIMEOUT = 10L
        const val READ_TIMEOUT = 10L
    }
    private val retrofit = Retrofit.Builder()
        .client(provideOkHttpClientWithProgress())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun provideOkHttpClientWithProgress(): OkHttpClient =
        OkHttpClient().newBuilder()
            .ignoreAllSslErrors()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .build()

    private val beerApi by lazy {
        retrofit.create(BeersApi::class.java)
    }

    private val converter = BeerConverter()

    override suspend fun getAll(page: Long): List<Beer> =
        beerApi.getAll(page).map { converter.convert(it) }

    override suspend fun getById(id: Long): List<Beer> =
        beerApi.getBeerById(id).map { converter.convert(it) }

    override suspend fun getRandomBeer(): List<Beer> =
        beerApi.getRandomBeer().map { converter.convert(it) }

    override suspend fun getBeersBySearch(parameters : Map<String, String>): List<Beer> =
        beerApi.getBeersBySearch(parameters).map { converter.convert(it) }

}