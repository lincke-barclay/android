package com.alth.events.database

import androidx.room.TypeConverter
import com.alth.events.database.models.UpdateType
import com.alth.events.models.domain.events.PublicEventQuery
import kotlinx.datetime.Instant

class Converters {
    @TypeConverter
    fun fromSerialized(value: String): UpdateType {
        return UpdateType.valueOf(value)
    }

    @TypeConverter
    fun fromEnum(value: UpdateType): String {
        return value.name
    }

    @TypeConverter
    fun toTimeStamp(value: Long): Instant {
        return Instant.fromEpochMilliseconds(value)
    }

    @TypeConverter
    fun fromTimeStamp(value: Instant): Long {
        return value.toEpochMilliseconds()
    }

    companion object {
        val instance = Converters() // For use outside
    }
}