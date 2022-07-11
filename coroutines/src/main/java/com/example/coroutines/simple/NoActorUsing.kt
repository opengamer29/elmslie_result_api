package com.example.coroutines.simple

import java.util.concurrent.CancellationException

internal class NoActorUsing(private val repository: CorRepository) {
    suspend fun doSomethingWrong1() {
        // easy to forget
        val result = repository.getSomething(false)
    }

    suspend fun doSomethingWrong2() {
        val result = try {
            repository.getSomething(false)
        } catch (e: Exception) {
            0
        }
    }

    suspend fun doSomethingCorrect() {
        val result = try {
            repository.getSomething(false)
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            0
        }
    }
}