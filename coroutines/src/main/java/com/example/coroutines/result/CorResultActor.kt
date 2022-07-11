package com.example.coroutines.result

import com.example.coroutines.Commands
import com.example.coroutines.Events
import com.example.coroutines.Events.NewValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapNotNull
import vivid.money.elmslie.core.Actor

internal class CorResultActor(private val repository: CorResultRepository) : Actor<Commands, Events> {
    override fun execute(command: Commands): Flow<Events> =
        when (command) {
            is Commands.GetData ->
                flow {
                    emit(repository.getSomething(command.throwError))
                }.mapResultEvents(
                    eventMapper = ::NewValue,
                    errorMapper = { NewValue(0) }
                )

            is Commands.ObserveData -> repository.getStream(command.throwError).mapResultEvents(
                eventMapper = ::NewValue,
                errorMapper = { NewValue(0) }
            )
        }

    // new our extension
    private fun <T : Any, Event : Any> Flow<Result<T>>.mapResultEvents(
        eventMapper: (T) -> Event? = { null },
        errorMapper: (error: Throwable) -> Event? = { null }
    ): Flow<Event> = this.mapNotNull { result ->
        result.fold(
            onSuccess = eventMapper,
            onFailure = errorMapper,
        )
    }
}