package com.verbatoria.infrastructure.date

import java.text.SimpleDateFormat
import java.util.*

/***
 * @author n.remnev
 */

const val FORMAT_WITH_MILLISECONDS_AND_ZERO_OFFSET = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

fun String.parseWithMillisecondsAndZeroOffset(): Date {
    val formatter = SimpleDateFormat(FORMAT_WITH_MILLISECONDS_AND_ZERO_OFFSET, Locale.getDefault())
    return formatter.parse(this)
}

fun Date.formatWithMillisecondsAndZeroOffset(): String {
    val formatter = SimpleDateFormat(FORMAT_WITH_MILLISECONDS_AND_ZERO_OFFSET, Locale.getDefault())
    return formatter.format(this)
}
