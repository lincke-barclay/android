package com.alth.events.models.network

import retrofit2.HttpException
import java.io.IOException

sealed interface NetworkResult<T> {
    data class APIFailure<T>(val e: HttpException) : NetworkResult<T>
    data class IOFailure<T>(val e: IOException) : NetworkResult<T>
    data class Success<T>(val t: T) : NetworkResult<T>
}

