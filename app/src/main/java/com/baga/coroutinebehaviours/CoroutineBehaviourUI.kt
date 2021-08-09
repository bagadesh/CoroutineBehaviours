package com.baga.coroutinebehaviours

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.time.ExperimentalTime
import kotlin.time.TimeSource

@OptIn(ExperimentalTime::class)
@Composable
fun CoroutineBehaviourUI() {

    val count = 10
    val listOfStates = remember {
        mutableStateListOf(false).apply {
            repeat(count) {
                add(false)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.padding(10.dp),
        contentPadding = PaddingValues(10.dp),
    ) {
        items(count) {
            when (it) {
                0 -> {
                    ExpandableButton(
                        "Dispatcher.Main.Immediate",
                        listOfStates[it], {
                            listOfStates[it] = !listOfStates[it]
                        }, {
                            DispatchersTesting()
                        }
                    )
                }
                else -> {
                    Button(onClick = {
                    }, modifier = Modifier.padding(10.dp)) {
                        Text(text = "Yet to Implement")
                    }
                }
            }
        }
    }
}



@Composable
fun ExpandableButton(
    title: String,
    viewContent: Boolean,
    itemClick: () -> Unit,
    content: @Composable () -> Unit
) {

    Column {
        Button(onClick = {
            itemClick()
        }) {
            Text(text = title)
        }
        if (viewContent) {
            content()
        }
    }

}