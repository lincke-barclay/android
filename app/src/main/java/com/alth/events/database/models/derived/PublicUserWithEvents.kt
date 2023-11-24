package com.alth.events.database.models.derived

import androidx.room.Embedded
import androidx.room.Relation
import com.alth.events.database.models.Event
import com.alth.events.database.models.PublicUser

data class PublicUserWithEvents(
    @Embedded val owner: PublicUser,
    @Relation(
        parentColumn = "id",
        entityColumn = "owner_id"
    )
    val events: List<Event>,
)
