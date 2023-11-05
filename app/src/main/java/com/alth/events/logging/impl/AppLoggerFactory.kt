package com.alth.events.logging.impl

import com.alth.events.logging.AppLogger

interface AppLoggerFactory {
    fun getLogger(tag: String): AppLogger

    fun getLogger(instance: Any): AppLogger {
        return getLogger(instance::class.java.simpleName)
    }
}