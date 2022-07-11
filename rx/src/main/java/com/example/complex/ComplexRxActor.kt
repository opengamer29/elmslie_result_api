package com.example.complex

import com.example.complex.Events.NewValue
import io.reactivex.rxjava3.core.Observable
import vivid.money.elmslie.core.Actor

internal class ComplexRxActor(private val repository: ComplexRxRepository) : Actor<Commands, Events> {
    override fun execute(command: Commands): Observable<Events> =
        when (command) {
            is Commands.DoSomethingOnInit -> {
                Observable.combineLatest(
                    repository.observeOperations(),
                    repository.observeContactsWithNumbersHashes()
                        .onErrorReturn { emptyMap() },
                    repository.observeCoins().onErrorReturn { emptyMap() },
                    ::Triple,
                ).mapEvents(
                    eventMapper = { (response, contacts, cryptoCoins) ->
                        NewValue(response + contacts.size + cryptoCoins.size)
                    },
                    errorMapper = {
                        NewValue(0)
                    },
                )
            }
        }
}