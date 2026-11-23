package com.antonio.minigames.domain.error

sealed class AppError(cause: Throwable? = null) : Exception(cause) {
    data object NoConnection : AppError()
    data object ServerError : AppError()
    data class Unexpected(val error: Throwable) : AppError(error)
}