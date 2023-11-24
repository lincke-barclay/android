package com.alth.events.database.models.events.derived

import androidx.room.Embedded
import androidx.room.Relation
import com.alth.events.database.models.events.EventEntity
import com.alth.events.database.models.users.PublicUserEntity

data class PublicUserWithEvents(
    @Embedded val owner: PublicUserEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "ownerId"
    )
    val eventEntities: List<EventEntity>,
)
