package com.example.coroutines.complex.simple

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip

class ComplexCoroutinesRepository {

    // operationsRepository
    fun observeOperations(): Flow<Int> = flowOf(1)

    // localContactsRepository
    fun observeContactsWithNumbersHashes(): Flow<Map<String, Long>> = flowOf(mapOf("test" to 1L))

    // cryptoDictionaryRepository
    fun observeCoins(): Flow<Map<String, String>> = flowOf(mapOf("test" to "coin"))

    // cardIssueAddressDetailsRepository
    suspend fun getAddressDetailsOptions(int: Int): Int = int

    object AnyException : Exception("Any exception")
}