package com.alth.events.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alth.events.database.dao.LastUpdateDao
import com.alth.events.database.dao.events.EventDao
import com.alth.events.database.dao.events.EventForUserDao
import com.alth.events.database.dao.events.FeedDao
import com.alth.events.database.dao.events.SearchEventDao
import com.alth.events.database.dao.users.FriendshipDao
import com.alth.events.database.dao.users.PublicUserDao
import com.alth.events.database.models.LastUpdateEntity
import com.alth.events.database.models.events.EventEntity
import com.alth.events.database.models.events.FeedEventEntity
import com.alth.events.database.models.events.QueriedEventEntity
import com.alth.events.database.models.users.FriendshipEntity
import com.alth.events.database.models.users.PublicUserEntity

@Database(
    entities = [
        PublicUserEntity::class,
        EventEntity::class,
        LastUpdateEntity::class,
        FeedEventEntity::class,
        QueriedEventEntity::class,
        FriendshipEntity::class,
    ],
    version = 1,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun publicUserDao(): PublicUserDao
    abstract fun friendshipDao(): FriendshipDao

    /**
     * Events
     */
    abstract fun eventDao(): EventDao
    abstract fun eventForUserDao(): EventForUserDao
    abstract fun feedDao(): FeedDao
    abstract fun searchEventDao(): SearchEventDao


    abstract fun lastUpdatedDao(): LastUpdateDao
}
