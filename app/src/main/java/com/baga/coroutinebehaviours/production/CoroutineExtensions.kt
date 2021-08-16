package com.baga.coroutinebehaviours.production

import kotlinx.coroutines.ensureActive
import kotlin.coroutines.intrinsics.suspendCoroutineUninterceptedOrReturn

/**
 * It can be used inside any suspend function to check for whether its current Job is active or not.
 * ex:  Heavy operation
 *      checkJobActive()
 *      Heavy operation
 * Easiest way to check isActive for a suspend function.
 */
suspend fun checkJobActive() {
    suspendCoroutineUninterceptedOrReturn<Unit> sc@{
        it.context.ensureActive()
    }
}


