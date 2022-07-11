package com.example.coroutines.complex.simple

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.core.Actor
import java.util.concurrent.CancellationException

private const val FALLBACK_ISO_CODE = 0

internal class ComplexCoroutinesActor(private val repository: ComplexCoroutinesRepository) : Actor<Commands, Events> {
    override fun execute(command: Commands): Flow<Events> =
        when (command) {
            is Commands.DoSomethingOnInit -> doSomethingOnInit()
            is Commands.DoSomethingOnDeinit -> doSomethingOnDeInit()
        }

    // OperationsFeedCommand.ObserveData
    private fun doSomethingOnInit() = combine(
        repository.observeOperations(),
        repository.observeContactsWithNumbersHashes().catch { emit(emptyMap()) }, // stream stopped!
        repository.observeCoins().catch { emit(emptyMap()) },  // stream stopped!
    ) { operation: Int, contacts: Map<String, Long>, coins: Map<String, String> ->
        // on error all stream will be stopped
        Events.NewValue(operation + contacts.size + coins.size)
    }

    private fun doSomethingOnDeInit(): Flow<Events> = flow {
        val details: Int? = safeCall(
            block = { repository.getAddressDetailsOptions(1) },
            onException = {
                null
            }
        )

        if (details != null) {
            emit(Events.NewValue(details))
            return@flow
        }

        emit(Events.NewValue(repository.getAddressDetailsOptions(FALLBACK_ISO_CODE)))

    }

    // new extensions to be used
    private suspend fun <T> safeCall(block: suspend () -> T, onException: (Throwable) -> T): T {
        return try {
            block.invoke()
        } catch (e: Exception) {
            if (e is CancellationException) {
                throw e
            }
            onException.invoke(e)
        }
    }
}