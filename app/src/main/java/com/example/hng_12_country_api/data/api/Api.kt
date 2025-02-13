package com.example.hng_12_country_api.data.api

import com.example.hng_12_country_api.data.model.Country
import retrofit2.http.GET

interface Api {
    @GET("all")
    suspend fun getCountryList():Country

    companion object {
        const val  BASE_URL = "https://restcountries.com/v3.1/"
    }
}