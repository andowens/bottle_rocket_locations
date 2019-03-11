package com.andrewowens.rocketstores.internal

import kotlinx.coroutines.*

// Lets the user use coroutines to defer the initialization of a variable
fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}