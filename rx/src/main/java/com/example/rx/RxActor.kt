package com.example.rx

import com.example.rx.Events.NewValue
import io.reactivex.rxjava3.core.Observable
import vivid.money.elmslie.core.Actor

internal class RxActor(private val repository: RxRepository) : Actor<Commands, Events> {
    override fun execute(command: Commands): Observable<Events> =
        when (command) {
            is Commands.GetData ->
                repository.getSomething(command.throwError).mapEvents(
                    eventMapper = ::NewValue,
                    errorMapper = { NewValue(0) }
                )
            is Commands.ObserveData -> repository.getStream(command.throwError).mapEvents(
                eventMapper = ::NewValue,
                errorMapper = { NewValue(0) }
            )
        }
}