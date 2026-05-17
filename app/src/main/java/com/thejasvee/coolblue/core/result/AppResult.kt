package com.thejasvee.coolblue.core.result

// App-level result wrapper
sealed interface AppResult<out T> {

    data class Success<T>(
        val data: T
    ) : AppResult<T>

    data class Error(
        val message: String,
        val type: ErrorType,
        val cause: Throwable? = null
    ) : AppResult<Nothing>
}

enum class ErrorType {
    NETWORK,
    SERVER,
    SERIALIZATION,
    UNKNOWN
}