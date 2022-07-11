package com.example.rx

import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class RxRepository {

    fun getSomething(throwError: Boolean): Single<Int> {
        return if (throwError) {
            Single.error(AnyException)
        } else {
            Single.just(1)
        }
    }

    fun getStream(throwError: Boolean): Observable<Int> = Observable.fromIterable(listOf(1, 2, 3, 4)).concatMap {
        if (it == 3 && throwError) throw AnyException
        Observable.just(it).delay(100, TimeUnit.MILLISECONDS)
    }

    object AnyException : Exception("Any exception")
}