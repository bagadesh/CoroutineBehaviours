package com.baga.coroutinebehaviours.javabasic

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.baga.coroutinebehaviours.cancellation.CoroutineCancellationTest
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun JavaBasic() {
    Column {
        BitWiseAnd()
    }
}

@Composable
fun BitWiseAnd() {
    val scope = rememberCoroutineScope()
    var item1 = false
    var item2 = false
    var valueText by remember {
        mutableStateOf("Bitwise And?")
    }
    Button(onClick = {
       // valueText += "\nitem1 and item2 ${item1 & item2}\n"
       // valueText += "\nitem1 and item2 ${item1 and item2}\n"
    }, modifier = Modifier.padding(10.dp)) {
        Text(text = valueText)
    }
}