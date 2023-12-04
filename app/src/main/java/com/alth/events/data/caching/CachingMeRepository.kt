package com.alth.events.data.caching

import com.alth.events.coroutines.ApplicationScope
import com.alth.events.networking.models.users.ingress.PrivateUserResponseDto
import com.alth.events.networking.sources.NetworkUserDataSource
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CachingMeRepository @Inject constructor(
    private val networkUserDataSource: NetworkUserDataSource,
    @ApplicationScope private val appScope: CoroutineScope,
) {
    private var me: PrivateUserResponseDto = PrivateUserResponseDto.empty()

    suspend fun getMe() = object : GenericCachingOperation<PrivateUserResponseDto>(
        appScope,
        me,
    ) {
        override suspend fun getFromSourceOfTruth() =
            networkUserDataSource
                .getMe()
                .toInternalCacheResult()

        override suspend fun getFromCache() = InternalCacheResult.Success(me)

        override suspend fun saveToCache(t: PrivateUserResponseDto): InternalCacheResult<Unit> {
            me = t
            return InternalCacheResult.Success(Unit)
        }

    }
}