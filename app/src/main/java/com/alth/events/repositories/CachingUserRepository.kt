package com.alth.events.repositories

import com.alth.events.models.network.users.ingress.PublicUserResponseDto

interface CachingUserRepository {
    fun getPublicUser(id: String): GenericCachingOperation<PublicUserResponseDto>
}
