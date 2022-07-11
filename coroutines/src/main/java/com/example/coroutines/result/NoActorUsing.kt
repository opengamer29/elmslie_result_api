package com.example.coroutines.result

import java.util.concurrent.CancellationException

internal class NoActorUsing(private val repository: CorResultRepository) {
    suspend fun doSomethingCorrect() {
        val result: Int = repository.getSomething(false).fold(
            onSuccess = { it },
            onFailure = { 0 }
        )
    }

    suspend fun doSomethingCorrect2() {
        val result1: Int = repository.getSomething(false).getOrDefault(0)
    }
}