package com.example.hng_12_country_api.data.repository

import com.example.hng_12_country_api.data.api.ResponseResult
import com.example.hng_12_country_api.data.model.CountryItem
import kotlinx.coroutines.flow.Flow

interface CountryRepository {
    suspend fun getCountryList():Flow<ResponseResult<List<CountryItem>>>
}