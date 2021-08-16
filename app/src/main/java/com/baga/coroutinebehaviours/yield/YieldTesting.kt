package com.baga.coroutinebehaviours.yield

import com.baga.coroutinebehaviours.production.checkJobActive
import kotlinx.coroutines.*
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

class YieldTesting {
    private val ioScope = CoroutineScope(Job() + Dispatchers.IO)

    fun cancelAndCompletionTest(action: suspend (((String) -> Unit))): Job {
        return ioScope.launch {
            action("launched")
            for (i in 0..100*1000) {
                println("test $i")
                checkJobActive()
            }
            action("ended")
        }
    }

    fun suspendTest(action: ((String) -> Unit)): Job {
        return ioScope.launch {
            action("before t1")
            t1(action)
            action("after t1")
        }
    }

    private suspend fun t1(action:  ((String) -> Unit)) {
        t12(action)
    }

    private suspend fun t12(action:  ((String) -> Unit)) {
        for (i in 0..100 * 1000) {
            println("ass")
            try {
                checkJobActive()
            }catch (e: CancellationException) {
                action("CancellationException thrown at $i")
                throw e
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun runTest(action: (String) -> Unit): Job {
        return ioScope.launch {
            action("Heavy Operation")
            for (i in 0..10 * 1000) {
                println("test")
            }
            action("Finished Heavy")
            val delayOfZeroTimeDelay = measureTime {
                delay(0)
            }
            val printlnTimeDelay = measureTime {
                println("Some Random Test")
            }
            val randomSuspendFuncTimeDelay = measureTime {
                action("before randomSuspendFunction")
                randomSuspendFunction()
                action("after randomSuspendFunction")
            }
            val timeDelay = measureTime {
                action("before yield")
                //yield()
            }
            val experimentalTime = measureTime {
                action("before suspendCancellableCoroutine.")
//                suspendCoroutineUninterceptedOrReturn<Unit> sc@{ uCont ->
//                    val context = uCont.context
//                    action("before ensureActive.")
//                    context.ensureActive()
//                }
                suspendCoroutine<Unit> {
                    it.resume(Unit)
                }
                action("after suspendCancellableCoroutine.")
            }
            action("\n")
            action("Experimental Duration = $experimentalTime")
            action("After Yield duration = $timeDelay")
            action("Delay of zero duration = $delayOfZeroTimeDelay")
            action("printlnTimeDelay = $printlnTimeDelay")
            action("randomSuspendFuncTimeDelay = $randomSuspendFuncTimeDelay")

        }
    }

    suspend fun randomSuspendFunction() {

    }
}