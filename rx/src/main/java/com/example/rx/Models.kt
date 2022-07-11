package com.example.rx

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
    data class GetData(val throwError: Boolean) : Commands()
    data class ObserveData(val throwError: Boolean) : Commands()
}