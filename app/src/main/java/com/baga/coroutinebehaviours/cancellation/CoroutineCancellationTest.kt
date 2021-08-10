package com.baga.coroutinebehaviours.cancellation

import kotlinx.coroutines.*

class CoroutineCancellationTest {

    private val ioScope = CoroutineScope(Dispatchers.IO + Job())

    fun suspendToSuspendTest(action: (String) -> Unit): Job {
        return ioScope.launch {
            for (i in 0..(1 * 100 * 1000)) {
                println("test$i $isActive")
            }
            action("before coroutineScope")
            coroutineScope {
                action("inside coroutineScope isActive = $isActive")
            }
            action("before Delay")
            delay(200)
            action("after Delay")
            randomSuspendFunction()
            action("after randomFunc")

        }
    }

    suspend fun randomSuspendFunction() {
        delay(200)
    }
}