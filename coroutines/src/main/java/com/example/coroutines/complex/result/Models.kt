package com.example.coroutines.complex.result

internal data class State(
    val result: Int
)

internal sealed class Events {
    object Init : Events()
    data class NewValue(val value: Int) : Events()
}

internal sealed class Effects {

}

internal sealed class Commands {
    data class DoSomethingOnInit(val throwError: Boolean): Commands()
    data class DoSomethingOnDeinit(val throwError: Boolean): Commands()
}