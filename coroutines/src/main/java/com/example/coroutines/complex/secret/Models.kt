package com.example.coroutines.complex.secret

internal data class State(
    val result: Int
)

internal sealed class Events {
    object Init : Events()
    data class NewValue(val value: Int) : Events()
    object NoAddress: Events()
    data class Error(val throwable: Throwable) : Events()
}

internal sealed class Effects {

}

internal sealed class Commands {
    data class DoSomethingOnInit(val throwError: Boolean) : Commands()
}

sealed class Either<out Error, out Data> {
    data class Failure<Error>(val value: Error) : Either<Error, Nothing>()
    data class Success<Data>(val value: Data) : Either<Nothing, Data>()

    val isSuccess get() = this is Success<Data>
    val isFailure get() = this is Failure<Error>

    companion object {
        fun <Error> failure(error: Error) = Failure(error)
        fun <Data> success(data: Data) = Success(data)
    }
}