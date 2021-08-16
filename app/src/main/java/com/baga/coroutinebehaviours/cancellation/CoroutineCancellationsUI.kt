package com.baga.coroutinebehaviours.cancellation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baga.coroutinebehaviours.production.appendNewLine
import kotlinx.coroutines.*
import kotlin.time.ExperimentalTime


@Composable
fun CoroutineCancellationsUI() {
    Column {
        SuspendToSuspendCancellation()
        CancellationAndCompletion()
    }
}


@OptIn(ExperimentalTime::class)
@Composable
fun SuspendToSuspendCancellation() {
    val scope = rememberCoroutineScope()
    var valueText by remember {
        mutableStateOf("Normal Suspend to suspend method cancellation working fine?")
    }
    Button(onClick = {
        scope.launch {
            val test = CoroutineCancellationTest()
            val job = test.suspendToSuspendTest {
                valueText += "\n$it \$END"
            }
            delay(10)
            valueText += "\ncancel triggered"
            job.cancelAndJoin()
            valueText += "\ncancel finished"
        }

    }, modifier = Modifier.padding(10.dp)) {
        Text(text = valueText)
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun CancellationAndCompletion() {
    val scope = rememberCoroutineScope()
    var valueText by remember {
        mutableStateOf("CancellationAndCompletion?")
    }
    Button(onClick = {
        scope.launch {
            try {
                val job1 = launch (Dispatchers.IO) {
//                    try {
                        valueText = valueText.appendNewLine("job1 launched")
                        delay(1000)
                        valueText = valueText.appendNewLine("job1 after delay")
//                    }catch (e1: CancellationException) {
//                        valueText = valueText.appendNewLine("2nd TryCatch CancellationException message = ${e1.message}")
//                        throw e1
//                    }

                }
                delay(500)
                valueText = valueText.appendNewLine("item cancelled")
                try {
                    job1.cancel(CancellationException("My custom message"))
                    job1.join()
                }catch (e1: CancellationException) {
                    valueText = valueText.appendNewLine("\n3nd TryCatch CancellationException message = ${e1.message}")
                    throw e1
                }
                valueText = valueText.appendNewLine("isCancelled = ${job1.isCancelled}")
                valueText = valueText.appendNewLine("isCompleted = ${job1.isCompleted}")
                valueText = valueText.appendNewLine("isActive = ${job1.isActive}")
            }catch (e1: CancellationException) {
                valueText = valueText.appendNewLine("1st TryCatch CancellationException")
            }
        }

    }, modifier = Modifier.padding(10.dp)) {
        Text(text = valueText)
    }
}
