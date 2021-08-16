package com.baga.coroutinebehaviours

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.baga.coroutinebehaviours.async.AsyncUI
import com.baga.coroutinebehaviours.cancellation.CoroutineCancellationsUI
import com.baga.coroutinebehaviours.javabasic.JavaBasic
import com.baga.coroutinebehaviours.yield.YieldUI
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@Composable
fun CoroutineBehaviourUI(navController: NavController) {

    val count = 10
    val listOfStates = remember {
        mutableStateListOf(false).apply {
            repeat(count) {
                add(false)
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
        contentPadding = PaddingValues(10.dp),
    ) {
        items(count) {
            when (it) {
                0 -> {
                    ExpandableButton(
                        "Dispatcher.Main.Immediate",
                        listOfStates[it], {
                            listOfStates[it] = !listOfStates[it]
                        }, {}, {
                            DispatchersTesting()
                        }
                    )
                }
                1 -> {
                    ExpandableButton(
                        "Coroutine Cancellation",
                        listOfStates[it], {
                            listOfStates[it] = !listOfStates[it]
                        }, {
                            navController.navigate("CancellableAbout")
                        }, {
                            CoroutineCancellationsUI()
                        }
                    )
                }
                2 -> {
                    ExpandableButton(
                        "Yield Coroutine",
                        listOfStates[it], {
                            listOfStates[it] = !listOfStates[it]
                        }, {
                            //navController.navigate("CancellableAbout")
                        }, {
                            YieldUI()
                        }
                    )
                }
                3 -> {
                    ExpandableButton(
                        "JavaBasic",
                        listOfStates[it], {
                            listOfStates[it] = !listOfStates[it]
                        }, {
                            //navController.navigate("CancellableAbout")
                        }, {
                            JavaBasic()
                        }
                    )
                }
                4 -> {
                    ExpandableButton(
                        "AsyncUI",
                        listOfStates[it], {
                            listOfStates[it] = !listOfStates[it]
                        }, {
                            //navController.navigate("CancellableAbout")
                        }, {
                            AsyncUI()
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
    helpIconClick: () -> Unit,
    content: @Composable () -> Unit
) {

    Column(
        modifier = Modifier.padding(5.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                itemClick()
            }) {
                Text(text = title)
            }
            IconButton(onClick = {
                helpIconClick()
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_help_24),
                    contentDescription = "Help Docs",
                    tint = Color.Black
                )
            }
        }

        if (viewContent) {
            content()
        }
    }

}