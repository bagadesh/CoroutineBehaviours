package com.baga.coroutinebehaviours.yield

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime

@Composable
fun YieldUI() {
    Column {
        YieldWhileDoingHeavyFunction()
        SuspendToSuspendToSuspendTest()
        CancelationCom()
    }
    //test
    println()
}

@OptIn(ExperimentalTime::class)
@Composable
fun YieldWhileDoingHeavyFunction() {
    val scope = rememberCoroutineScope()
    var valueText by remember {
        mutableStateOf("YieldWhileDoingHeavyFunction?")
    }
    Button(onClick = {
        scope.launch {
            val test = YieldTesting()
            var job: Job? = null
            job = test.runTest {
                valueText += "\n$it \$END"
                if (it == "Finished Heavy") {
                    job!!.cancel()
                }
            }
        }

    }, modifier = Modifier.padding(10.dp)) {
        Text(text = valueText)
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun SuspendToSuspendToSuspendTest() {
    val scope = rememberCoroutineScope()
    var valueText by remember {
        mutableStateOf("suspend to suspend to suspend?")
    }
    Button(onClick = {
        scope.launch {
            val test = YieldTesting()
            var job: Job? = null
            job = test.suspendTest {
                valueText += "\n$it \$END"
                if (it == "before t1") {
                    scope.launch {
                        delay(1)
                        job?.cancel()
                        valueText += "\nJob Cancelled \$END"
                    }

                }
            }
        }

    }, modifier = Modifier.padding(10.dp)) {
        Text(text = valueText)
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun CancelationCom() {
    val scope = rememberCoroutineScope()
    var valueText by remember {
        mutableStateOf("Cancel and Completion check?")
    }
    Button(onClick = {
        scope.launch {
            val test = YieldTesting()
            var job: Job? = null
            job = test.cancelAndCompletionTest {
                valueText += "\n$it \$END"
            }
            delay(100)
            valueText += "\n cancelAndJoin \$END"
            job.cancelAndJoin()
            valueText += "\n cancelAndJoin Finished ${job!!.isCompleted},${job!!.isCancelled
            } \$END"
        }

    }, modifier = Modifier.padding(10.dp)) {
        Text(text = valueText)
    }
}
