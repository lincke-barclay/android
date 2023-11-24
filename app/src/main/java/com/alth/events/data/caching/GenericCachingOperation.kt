package com.alth.events.data.caching

import com.alth.events.logging.impl.loggerFactory
import com.alth.events.models.domain.CacheGetResult
import com.alth.events.networking.models.NetworkResult
import kotlinx.coroutines.CoroutineScope

abstract class GenericCachingOperation<T>(
    private val scope: CoroutineScope,
    private val allFailedValue: T,
) {
    private val logger = loggerFactory.getLogger("Caching Operation")

    suspend operator fun invoke(
        shouldRefresh: Boolean,
    ): CacheGetResult<T> =
        if (shouldRefresh) {
            when (val sourceOfTruthResult = getFromSourceOfTruth()) {
                is InternalCacheResult.Success -> {
                    CacheGetResult.SourceOfTruth(sourceOfTruthResult.t)
                }

                is InternalCacheResult.Failure -> {
                    logger.warn("Source of truth failed... trying backup source")
                    when (val cacheResult = getFromCache()) {
                        is InternalCacheResult.Success -> {
                            CacheGetResult.SourceOfTruthFailedButCacheWorked(cacheResult.t)
                        }

                        is InternalCacheResult.Failure -> {
                            logger.warn("Source of truth failed and cache failed")
                            CacheGetResult.EverythingFailed(allFailedValue)
                        }
                    }
                }
            }
        } else {
            when (val cacheResult = getFromCache()) {
                is InternalCacheResult.Success -> {
                    CacheGetResult.CacheSuccess(cacheResult.t)
                }

                is InternalCacheResult.Failure -> {
                    logger.warn("Cache Failed")
                    CacheGetResult.EverythingFailed(allFailedValue)
                }
            }
        }

    protected abstract suspend fun getFromSourceOfTruth(): InternalCacheResult<T>
    protected abstract suspend fun getFromCache(): InternalCacheResult<T>
    protected abstract suspend fun saveToCache(t: T): InternalCacheResult<Unit>

    protected sealed interface InternalCacheResult<T> {
        data class Success<T>(val t: T) : InternalCacheResult<T>
        class Failure<T> : InternalCacheResult<T>
    }

    protected fun <T> NetworkResult<T>.toInternalCacheResult(): InternalCacheResult<T> {
        return when (this) {
            is NetworkResult.Success -> {
                InternalCacheResult.Success(t)
            }

            else -> {
                InternalCacheResult.Failure()
            }
        }
    }
}
