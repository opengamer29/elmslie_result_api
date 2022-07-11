package com.example.coroutines.complex.secret

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ComplexCoroutinesResultRepository {

    // cardIssueAddressDetailsRepository
    suspend fun getAddressDetailsOptions(int: Int): Either<PossibleErrors, Int> = Either.success(int)

    object AnyException : Exception("Any exception")

    sealed class PossibleErrors {
        data class NetworkError(val throwable: Throwable): PossibleErrors()
        object NoAddressError: PossibleErrors()
    }
}