package com.antonio.minigames.data.error

import com.antonio.minigames.domain.error.AppError
import kotlinx.coroutines.CancellationException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Result<T> =
    try {
        val response = call()
        val body = response.body()
        when {
            !response.isSuccessful -> Result.failure(HttpException(response).toAppError())
            body == null -> Result.failure(AppError.Unexpected(IllegalStateException("Empty body")))
            else -> Result.success(body)
        }
    } catch (error: CancellationException) {
        throw error
    } catch (error: Exception) {
        Result.failure(error.toAppError())
    }

fun Throwable.toAppError(): AppError = when (this) {
    is AppError -> this
    is IOException -> AppError.NoConnection
    is HttpException -> if (code() >= 500) AppError.ServerError else AppError.Unexpected(this)
    else -> AppError.Unexpected(this)
}
