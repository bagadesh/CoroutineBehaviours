package com.baga.coroutinebehaviours.async

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baga.coroutinebehaviours.production.appendNewLine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch


@Composable
fun AsyncUI() {
    Column {
        AsyncFirstOne()
    }
}

@Composable
fun AsyncFirstOne() {
    val scope = rememberCoroutineScope()
    var valueText by remember {
        mutableStateOf("Async await with Cancel?\n")
    }
    Button(onClick = {
        scope.launch {
            val j1 = launch(Dispatchers.IO) {
                Thread.sleep(5000)
            }
            val async1 = async (Dispatchers.IO) {
                valueText = valueText.appendNewLine("Async1 start")
                Thread.sleep(5000)
                return@async 50
            }
            val async2 = async (Dispatchers.IO) {
                valueText = valueText.appendNewLine("Async2 start")
                Thread.sleep(7000)
                return@async 50
            }
            valueText = valueText.appendNewLine("before async1.await()")
            val res1 = async1.await()
            valueText = valueText.appendNewLine("after async1.await()")
            val res2 = async2.await()
            valueText = valueText.appendNewLine("after async2.await()")
            // valueText = valueText.appendNewLine("using res1 $res1")
            valueText = valueText.appendNewLine("not-using res1 ")
            j1.cancelAndJoin()
            valueText = valueText.appendNewLine("after cancelAndJoin")
        }

    }, modifier = Modifier.padding(10.dp)) {
        Text(text = valueText)
    }
}