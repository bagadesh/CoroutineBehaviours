package com.baga.coroutinebehaviours.cancellation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun AboutUI() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        items(10) {
            when(it) {
                0 -> {
                    Column {
                        Text(text = buildAnnotatedString {
                            appendLine("\n\t Suspend function cancellation")
                        },color = Color.White,fontSize = 24.sp)
                        Text(text = buildAnnotatedString {
                            appendLine("\t1. When one suspend function calls another suspend function then internally isAlive is being checked")

                       //     appendBold("\t2. \bisAlive\b")
                       //     appendLine("is a kind of atomic boolean so when the this or its parent job is cancelled it is automatically becomes false")


                            appendLine("\t2. \bisAlive\b is a kind of atomic boolean so when the this or its parent job is cancelled it is automatically becomes false")
                        },color = Color.White,fontSize = 16.sp)
                    }

                }
            }
        }
    }
}

fun AnnotatedString.Builder.appendLine(text: String) {
    append("$text\n")
}
fun AnnotatedString.Builder.appendLineT(text: String) {
//    text.toRegex().matches()
//    append("$text\n")
}

fun AnnotatedString.Builder.appendBoldLine(text: String) {
    withStyle(style = SpanStyle(Color.Red)) {
        appendLine(text)
    }
}
fun AnnotatedString.Builder.appendBold(text: String) {
    withStyle(style = SpanStyle(Color.Red)) {
        append(text)
    }
}