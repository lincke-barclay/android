package com.alth.events.logging.impl

import android.util.Log
import com.alth.events.logging.AppLogger

class AndroidLoggerFactory : AppLoggerFactory {
    override fun getLogger(tag: String): AppLogger {
        return object : AppLogger {
            override fun debug(msg: String, throwable: Throwable?) {
                Log.d(tag, msg, throwable)
            }

            override fun info(msg: String, throwable: Throwable?) {
                Log.i(tag, msg, throwable)
            }

            override fun warn(msg: String, throwable: Throwable?) {
                Log.w(tag, msg, throwable)
            }

            override fun error(msg: String, throwable: Throwable?) {
                Log.e(tag, msg, throwable)
            }
        }
    }
}