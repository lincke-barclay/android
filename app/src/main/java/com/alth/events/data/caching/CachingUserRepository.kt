package com.alth.events.data.caching

import com.alth.events.coroutines.ApplicationScope
import com.alth.events.networking.models.users.ingress.PublicUserResponseDto
import com.alth.events.networking.sources.NetworkUserDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class CachingUserRepository @Inject constructor(
    @ApplicationScope private val appScope: CoroutineScope,
    private val networkUserDataSource: NetworkUserDataSource,
) {

    var lastUser: PublicUserResponseDto? = null

    fun getPublicUser(id: String) =
        object : GenericCachingOperation<PublicUserResponseDto>(
            appScope,
            PublicUserResponseDto.empty()
        ) {
            override suspend fun getFromSourceOfTruth(): InternalCacheResult<PublicUserResponseDto> {
                return networkUserDataSource.getNotMe(id)
                    .toInternalCacheResult()
            }

            override suspend fun getFromCache(): InternalCacheResult<PublicUserResponseDto> {
                return InternalCacheResult.Failure()
            }

            override suspend fun saveToCache(t: PublicUserResponseDto): InternalCacheResult<Unit> {
                lastUser = t
                return InternalCacheResult.Success(Unit)
            }
        }
}