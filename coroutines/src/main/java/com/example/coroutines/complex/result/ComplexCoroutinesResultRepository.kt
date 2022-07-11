package com.example.coroutines.complex.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ComplexCoroutinesResultRepository {

    // operationsRepository
    fun observeOperations(): Flow<Result<Int>> = flowOf(Result.success(1))

    // localContactsRepository
    fun observeContactsWithNumbersHashes(): Flow<Result<Map<String, Long>>> = flowOf(Result.success(mapOf("test" to 1L)))

    // cryptoDictionaryRepository
    fun observeCoins(): Flow<Result<Map<String, String>>> = flowOf(Result.success(mapOf("test" to "coin")))

    // cardIssueAddressDetailsRepository
    suspend fun getAddressDetailsOptions(int: Int): Result<Int> = Result.success(int)

    object AnyException : Exception("Any exception")
}