package com.alth.events.database

import androidx.room.withTransaction
import javax.inject.Inject

class DatabaseTransactionUseCase @Inject constructor(
    private val appDatabase: AppDatabase
) {
    suspend operator fun <T> invoke(block: suspend () -> T): T {
        return appDatabase.withTransaction {
            block()
        }
    }
}
