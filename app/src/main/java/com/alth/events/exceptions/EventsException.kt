package com.alth.events.exceptions

open class EventsException(
    message: String?,
    cause: Throwable?
) : Exception(message, cause)

class IllegalOperationException(
    message: String? = null,
    cause: Throwable? = null
) : EventsException(message, cause)

// Somehow the app did an operation that required authentication
// without authentication
class IllegalAuthenticationStateException(
    message: String? = null,
    cause: Throwable? = null,
) : EventsException(message, cause)
