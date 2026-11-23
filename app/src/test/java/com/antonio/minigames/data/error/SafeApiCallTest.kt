package com.antonio.minigames.data.error

import com.antonio.minigames.domain.error.AppError
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import retrofit2.Response
import java.io.IOException

class SafeApiCallTest {

    @Test
    fun `successful response returns its body`() = runTest {
        val result = safeApiCall { Response.success("pikachu") }

        assertEquals("pikachu", result.getOrNull())
    }

    @Test
    fun `io exception maps to no connection`() = runTest {
        val result = safeApiCall<String> { throw IOException("offline") }

        assertEquals(AppError.NoConnection, result.exceptionOrNull())
    }

    @Test
    fun `server error code maps to server error`() = runTest {
        val result = safeApiCall<String> { Response.error(503, "".toResponseBody()) }

        assertEquals(AppError.ServerError, result.exceptionOrNull())
    }

    @Test
    fun `client error code maps to unexpected`() = runTest {
        val result = safeApiCall<String> { Response.error(404, "".toResponseBody()) }

        assertTrue(result.exceptionOrNull() is AppError.Unexpected)
    }

    @Test
    fun `empty body maps to unexpected`() = runTest {
        val result = safeApiCall<String> { Response.success(null) }

        assertTrue(result.exceptionOrNull() is AppError.Unexpected)
    }

    @Test
    fun `unknown exception maps to unexpected keeping the cause`() = runTest {
        val cause = IllegalArgumentException("broken json")

        val result = safeApiCall<String> { throw cause }

        assertEquals(AppError.Unexpected(cause), result.exceptionOrNull())
    }
}
