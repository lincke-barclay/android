package com.alth.events.database.sources

import com.alth.events.database.dao.LastUpdateDao
import com.alth.events.database.models.LastUpdate
import com.alth.events.database.models.UpdateType
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import javax.inject.Inject

class LastUpdateLocalDataSource @Inject constructor(
    private val lastUpdateDao: LastUpdateDao,
) {
    suspend fun <T> doWithFeedUpdate(block: suspend () -> T): T {
        val t = block()
        val update = LastUpdate(
            updateType = UpdateType.FeedRefresh,
            lastUpdate = Clock.System.now()
        )
        lastUpdateDao.insert(update)
        return t
    }

    suspend fun getLastUpdatedFeedRefresh(): Instant {
        val result = lastUpdateDao.getLastUpdatedFeedRefresh()
        return if (result.isEmpty()) {
            Clock.System.now() // maybe pull this case into a sealed class - but not worth it right now
        } else if (result.size > 1) {
            throw Exception("Multiple refresh feeds - this is a bug")
        } else {
            result.first().lastUpdate
        }
    }
}