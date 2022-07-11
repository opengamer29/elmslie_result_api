package com.example.complex

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class ComplexRxRepository {

    // operationsRepository
    fun observeOperations(): Observable<Int> = Observable.just(1)

    // localContactsRepository
    fun observeContactsWithNumbersHashes(): Observable<Map<String, Long>> = Observable.just(mapOf("test" to 1L))

    // cryptoDictionaryRepository
    fun observeCoins(): Observable<Map<String, String>> = Observable.just(mapOf("test" to "coin"))

    object AnyException : Exception("Any exception")
}