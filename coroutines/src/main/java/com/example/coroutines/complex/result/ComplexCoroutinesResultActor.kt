package com.example.coroutines.complex.result

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.core.Actor

private const val FALLBACK_ISO_CODE = 0

internal class ComplexCoroutinesResultActor(private val repository: ComplexCoroutinesResultRepository) : Actor<Commands, Events> {
    override fun execute(command: Commands): Flow<Events> =
        when (command) {
            is Commands.DoSomethingOnInit -> doSomethingOnInit()
            is Commands.DoSomethingOnDeinit -> doSomethingOnDeInit()
        }

    // OperationsFeedCommand.ObserveData
    private fun doSomethingOnInit(): Flow<Events> = combine(
        repository.observeOperations(),
        repository.observeContactsWithNumbersHashes(),
        repository.observeCoins(),
    ) { operation: Result<Int>, contacts: Result<Map<String, Long>>, coins: Result<Map<String, String>> ->

        // a way to prevent stream from stopping
        val operationValue1 = operation.getOrElse {
            return@combine Events.NewValue(0)
        }

        // an easy way to getValue
        val operationValue2 = operation.getOrThrow()
        val contactsValue = contacts.getOrDefault(emptyMap())
        val coinsValue = coins.getOrDefault(emptyMap())

        Events.NewValue(operationValue2 + contactsValue.size + coinsValue.size)
    }

    // AddressDetailsOptionsCommand GetAddressDetailsOptions
    private fun doSomethingOnDeInit(): Flow<Events> = flow {
        val result = repository.getAddressDetailsOptions(1)

        result.getOrNull()?.let {
            emit(Events.NewValue(it))
            return@flow
        }

        val fallback = repository.getAddressDetailsOptions(FALLBACK_ISO_CODE).getOrThrow()
        emit(Events.NewValue(fallback))
    }
}