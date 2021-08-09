package com.baga.coroutinebehaviours

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

@OptIn(ExperimentalTime::class)
@Composable
fun DispatchersTesting() {
    val scope = rememberCoroutineScope()
    Column {
        var valueText by remember {
            mutableStateOf("measure Main thread to Main Dispatcher time?")
        }
        Button(onClick = {
            scope.launch {
                val time = TimeSource.Monotonic.markNow()
                launch(Dispatchers.Main) {
                    val duration = time.elapsedNow()
                    valueText+="\n duration = $duration"
                }
            }

        }, modifier = Modifier.padding(10.dp)) {
            Text(text = valueText)
        }

        var item2Text by remember {
            mutableStateOf("measure Main thread to Main Dispatcher.immediate time?")
        }
        Button(onClick = {
            scope.launch {
                val time = TimeSource.Monotonic.markNow()
                launch(Dispatchers.Main.immediate) {
                    val duration = time.elapsedNow()
                    item2Text+="\n duration = $duration"
                }
            }

        }, modifier = Modifier.padding(10.dp)) {
            Text(text = item2Text)
        }
        IoScopeToImmediateFunction(scope)
        DefaultScopeToImmediateFunction(scope)
    }
}

@OptIn(ExperimentalTime::class)
@Composable
fun IoScopeToImmediateFunction(scope: CoroutineScope) {
    var item3Text by remember {
        mutableStateOf("ioScope to Main.immediate execution working fine and duration?")
    }
    Button(onClick = {
        scope.launch (Dispatchers.IO) {
            val time = TimeSource.Monotonic.markNow()
            launch(Dispatchers.Main.immediate) {
                val duration = time.elapsedNow()
                item3Text+="\n duration = $duration"
            }
        }

    }, modifier = Modifier.padding(10.dp)) {
        Text(text = item3Text)
    }
}
@OptIn(ExperimentalTime::class)
@Composable
fun DefaultScopeToImmediateFunction(scope: CoroutineScope) {
    var item3Text by remember {
        mutableStateOf("defaultScope to Main.immediate execution working fine and duration?")
    }
    Button(onClick = {
        scope.launch (Dispatchers.Default) {
            val time = TimeSource.Monotonic.markNow()
            launch(Dispatchers.Main.immediate) {
                val duration = time.elapsedNow()
                item3Text+="\n duration = $duration"
            }
        }

    }, modifier = Modifier.padding(10.dp)) {
        Text(text = item3Text)
    }
}
