package com.alth.events.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alth.events.database.dao.LastUpdateDao
import com.alth.events.database.dao.PublicUserDao
import com.alth.events.database.dao.events.EventDao
import com.alth.events.database.dao.events.EventForUserDao
import com.alth.events.database.dao.events.FeedDao
import com.alth.events.database.dao.events.SearchEventDao
import com.alth.events.database.models.Event
import com.alth.events.database.models.LastUpdate
import com.alth.events.database.models.PublicUser

@Database(
    entities = [
        PublicUser::class,
        Event::class,
        LastUpdate::class
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun publicUserDao(): PublicUserDao

    /**
     * Events
     */
    abstract fun eventDao(): EventDao
    abstract fun eventForUserDao(): EventForUserDao
    abstract fun feedDao(): FeedDao
    abstract fun searchEventDao(): SearchEventDao


    abstract fun lastUpdatedDao(): LastUpdateDao
}
