package com.verbatoria.infrastructure.extensions

fun String.capitalizeFirstLetter(): String =
    when {
        length > 1 -> substring(0, 1).toUpperCase() + substring(1)
        length == 1 -> substring(0, 1).toUpperCase()
        else -> ""
    }