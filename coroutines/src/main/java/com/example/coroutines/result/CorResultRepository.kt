package com.example.coroutines.result

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CorResultRepository {

    suspend fun getSomething(throwError: Boolean): Result<Int> {
        return if (throwError) {
            Result.failure(AnyException)
        } else {
            Result.success(1)
        }
    }

    fun getStream(throwError: Boolean): Flow<Result<Int>> = flow {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 3 && throwError) return@flow emit(Result.failure(AnyException))
            emit(Result.success(it))
            delay(it * 100L)
        }
    }

    object AnyException : Exception("Any exception")
}