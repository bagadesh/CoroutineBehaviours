package com.baga.coroutinebehaviours.cancellation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime


@Composable
fun CoroutineCancellationsUI() {
    Column {
        SuspendToSuspendCancellation()
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
                valueText+="\n$it \$END"
            }
            delay(10)
            valueText+="\ncancel triggered"
            job.cancelAndJoin()
            valueText+="\ncancel finished"
        }

    }, modifier = Modifier.padding(10.dp)) {
        Text(text = valueText)
    }
}
