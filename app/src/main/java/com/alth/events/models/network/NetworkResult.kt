package com.alth.events.models.network

import com.alth.events.logging.impl.loggerFactory
import java.io.IOException

sealed interface NetworkResult<T> {
    data class IOFailure<T>(val e: IOException) : NetworkResult<T> {
        init {
            loggerFactory.getLogger("NetworkResult")
                .warn("There was an io failure and the request couldn't be completed: ", e)
        }
    }

    data class Success<T>(val t: T) : NetworkResult<T>
}

