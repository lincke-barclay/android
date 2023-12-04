package com.alth.events.models

sealed class CacheGetResult<T>(val t: T) {
    class SourceOfTruth<T>(t: T) : CacheGetResult<T>(t)
    class SourceOfTruthFailedButCacheWorked<T>(t: T) : CacheGetResult<T>(t)
    class CacheSuccess<T>(t: T) : CacheGetResult<T>(t)
    class EverythingFailed<T>(t: T) : CacheGetResult<T>(t)
}
