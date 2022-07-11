package com.example.coroutines.complex.secret

import kotlinx.coroutines.flow.*
import vivid.money.elmslie.core.Actor

private const val FALLBACK_ISO_CODE = 0

internal class ComplexCoroutinesResultActor(private val repository: ComplexCoroutinesResultRepository) : Actor<Commands, Events> {
    override fun execute(command: Commands): Flow<Events> =
        when (command) {
            is Commands.DoSomethingOnInit -> doSomethingOnInit()
        }

    // OperationsFeedCommand.ObserveData
    private fun doSomethingOnInit(): Flow<Events> = makeAAddressRequest {
        makeAAddressRequest {
            flow { Events.NoAddress } // Error!
        }
    }

    private fun makeAAddressRequest(onNoAddress: () -> Flow<Events>): Flow<Events> = flow {
        val result = when (val response = repository.getAddressDetailsOptions(1)) {
            is Either.Success -> {
                Events.NewValue(response.value)
            }
            is Either.Failure -> {
                when (response.value) {
                    is ComplexCoroutinesResultRepository.PossibleErrors.NetworkError -> {
                        Events.Error(response.value.throwable)
                    }
                    ComplexCoroutinesResultRepository.PossibleErrors.NoAddressError -> {
                        onNoAddress.invoke().collect(this)
                        return@flow
                    }
                }
            }
        }
        emit(result)
    }
}