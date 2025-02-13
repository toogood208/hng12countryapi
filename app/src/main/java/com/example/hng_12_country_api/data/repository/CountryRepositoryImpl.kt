package com.example.hng_12_country_api.data.repository

import coil.network.HttpException
import com.example.hng_12_country_api.data.api.Api
import com.example.hng_12_country_api.data.api.ResponseResult
import com.example.hng_12_country_api.data.model.CountryItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException

class CountryRepositoryImpl(private val api: Api) : CountryRepository {
    override suspend fun getCountryList(): Flow<ResponseResult<List<CountryItem>>> {
        return flow {
            val countriesFromApi = try {
                api.getCountryList()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(ResponseResult.Error(message = "error fetching countries:$e"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(ResponseResult.Error(message = "error fetching countries: $e"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(ResponseResult.Error(message = "error fetching countries: $e"))
                return@flow
            }
            emit(ResponseResult.Success(countriesFromApi))

        }
    }
}