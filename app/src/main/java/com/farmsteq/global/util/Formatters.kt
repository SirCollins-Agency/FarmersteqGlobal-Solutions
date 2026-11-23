package com.farmsteq.global.util

import java.text.NumberFormat
import java.util.Locale

fun formatNaira(value: Double): String =
    "₦${NumberFormat.getNumberInstance(Locale.getDefault()).format(value.toLong())}"

fun formatDate(timestamp: Long): String =
    java.text.SimpleDateFormat("d MMM", Locale.getDefault()).format(java.util.Date(timestamp))

fun greetingForHour(hour: Int): String = when (hour) {
    in 0..11 -> "Good morning"
    in 12..16 -> "Good afternoon"
    in 17..20 -> "Good evening"
    else -> "Good night"
}

fun initials(name: String): String =
    name.trim().split(Regex("\\s+")).filter { it.isNotBlank() }
        .take(2).joinToString("") { it.first().uppercaseChar().toString() }
        .ifEmpty { "FG" }
