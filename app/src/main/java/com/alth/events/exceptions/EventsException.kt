package com.alth.events.exceptions

open class EventsException(
    message: String?,
    cause: Throwable?
) : Exception(message, cause)

class IllegalOperationException(
    message: String? = null,
    cause: Throwable? = null
) : EventsException(message, cause)