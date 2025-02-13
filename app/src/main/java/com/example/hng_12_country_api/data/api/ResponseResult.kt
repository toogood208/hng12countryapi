package com.example.hng_12_country_api.data.api


sealed class ResponseResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class  Success<T> (data: T?): ResponseResult<T>(data)
    class Error<T>(data: T? = null, message: String): ResponseResult<T>(data,message)
}