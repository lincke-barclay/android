package com.alth.events.exceptions

class IllegalAuthenticationStateException(
    message: String? = null,
    cause: Throwable? = null,
) : EventsException(message, cause)