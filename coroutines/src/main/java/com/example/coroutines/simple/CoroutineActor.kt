package com.example.coroutines.simple

import com.example.coroutines.Commands
import com.example.coroutines.Events
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import vivid.money.elmslie.core.Actor

internal class CoroutineActor(private val repository: CorRepository) : Actor<Commands, Events> {
    override fun execute(command: Commands): Flow<Events> =
        when (command) {
            is Commands.GetData ->
                flow {
                    emit(repository.getSomething(command.throwError))
                }.mapEvents(
                    eventMapper = Events::NewValue,
                    errorMapper = { Events.NewValue(0) }
                )

            is Commands.ObserveData -> repository.getStream(command.throwError).mapEvents(
                eventMapper = Events::NewValue,
                errorMapper = { Events.NewValue(0) }
            )
        }
}