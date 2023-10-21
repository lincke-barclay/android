package com.alth.events.data.feed

import com.alth.events.data.authentication.FirebaseAuthenticationRepository
import com.alth.events.data.authentication.models.PublicUser
import com.alth.events.data.feed.models.Event
import com.alth.events.data.feed.models.NewEventRequest
import com.alth.events.networking.apis.models.POSTEventRequestDTO
import com.alth.events.networking.apis.models.RemoteEventResponseDTO
import javax.inject.Inject

interface FeedElementRepository {
    suspend fun getFeedElements(): List<Event>
    suspend fun addNewEvent(newEventRequest: NewEventRequest): Event
}

class NetworkOnlyFeedElementRepository @Inject constructor(
    private val remoteFeedDataSource: RemoteFeedDataSource,
    private val firebaseAuthenticationRepository: FirebaseAuthenticationRepository,
) : FeedElementRepository {
    override suspend fun getFeedElements(): List<Event> {
        return remoteFeedDataSource.getFeed().map {
            transformEventResponseDTOToDomainEvent(it)
        }
    }

    override suspend fun addNewEvent(newEventRequest: NewEventRequest): Event {
        val firebaseOwnerId = firebaseAuthenticationRepository.getCurrentlySignedInUserIdOrThrow()
        val postEventRequestDTO = transformDomainNewEventRequestToPOSTEventRequest(
            newEventRequest,
            firebaseOwnerId,
        )

        return transformEventResponseDTOToDomainEvent(
            remoteFeedDataSource.postEvent(postEventRequestDTO)
        )
    }
}

fun transformDomainNewEventRequestToPOSTEventRequest(
    newEventRequest: NewEventRequest,
    firebaseOwnerId: String
): POSTEventRequestDTO {
    return with(newEventRequest) {
        POSTEventRequestDTO(
            title = title,
            shortDescription = shortDescription,
            longDescription = longDescription,
            firebaseOwnerId = firebaseOwnerId,
            startingDateTime = startDateTime,
            endingDateTime = endDateTime,
        )
    }
}

fun transformEventResponseDTOToDomainEvent(
    eventResponseDTO: RemoteEventResponseDTO,
): Event {
    return with(eventResponseDTO) {
        Event(
            title = title,
            shortDescription = shortDescription,
            longDescription = longDescription,
            createdDateTime = createdDateTime,
            startDateTime = startingDateTime,
            endDateTime = endingDateTime,
            organizer = PublicUser("Bob"),
            images = emptySet(),
        )
    }
}