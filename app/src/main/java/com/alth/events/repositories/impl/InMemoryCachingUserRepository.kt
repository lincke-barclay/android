package com.alth.events.repositories.impl

import com.alth.events.di.ApplicationScope
import com.alth.events.models.network.users.ingress.PublicUserResponseDto
import com.alth.events.networking.sources.NetworkUserDataSource
import com.alth.events.repositories.CachingUserRepository
import com.alth.events.repositories.GenericCachingOperation
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class InMemoryCachingUserRepository @Inject constructor(
    @ApplicationScope private val appScope: CoroutineScope,
    private val networkUserDataSource: NetworkUserDataSource,
) : CachingUserRepository {

    var lastUser: PublicUserResponseDto? = null

    override fun getPublicUser(id: String) =
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