package com.baga.coroutinebehaviours.production


fun String.appendNewLine(message: String): String {
    return "$this $message \$END\n"
}