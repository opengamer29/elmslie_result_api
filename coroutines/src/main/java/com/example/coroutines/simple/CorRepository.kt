package com.example.coroutines.simple

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CorRepository {

    suspend fun getSomething(throwError: Boolean): Int {
        return if (throwError) {
            throw AnyException
        } else {
            1
        }
    }

    fun getStream(throwError: Boolean): Flow<Int> = flow {
        listOf(1, 2, 3, 4, 5).forEach {
            if (it == 1) throw AnyException
            emit(it)
            delay(it * 100L)
        }
    }

    object AnyException : Exception("Any exception")
}