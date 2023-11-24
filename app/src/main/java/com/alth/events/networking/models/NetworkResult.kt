package com.alth.events.networking.models

import com.alth.events.logging.impl.loggerFactory
import java.io.IOException

sealed interface NetworkResult<T> {
    // I'm putting off implementing HTTPException until the last moment so I don't use
    // that as a cop out - Everything else __can__ and __should__ happen, an HTTPException
    // __shouldn't__ happen - e.g. it's a bug report that I need to track

    data class IOFailure<T>(val e: IOException) : NetworkResult<T> {
        init {
            loggerFactory.getLogger("NetworkResult")
                .warn("There was an io failure and the request couldn't be completed: ", e)
        }
    }

    data class Success<T>(val t: T) : NetworkResult<T>
}

