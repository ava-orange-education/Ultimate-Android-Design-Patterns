package com.example.core

import androidx.compose.ui.graphics.Color
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

fun Long.format(): String {
    return this.formatDate() + " " + this.formatTime()
}

fun Long.formatDate(): String {
    val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
    return sdf.format(Date(this))
}

fun Long.formatTime(): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(this))
}

/**
 * Maps a [String] to a [Color] by converting its hash code to a hue value.
 *
 * @param input The input string.
 * @return A Compose Color based on the hue generated from the input string.
 */
fun stringToColor(input: String): Color {
    // Create a consistent hash from the input string
    val hash = input.hashCode().absoluteValue

    // Map the hash to a hue in the range 0..360
    val hue = (hash % 360).toFloat()

    // Define a fixed saturation and brightness (feel free to tweak these values)
    val saturation = 0.5f
    val brightness = 0.8f

    // Build the HSV array
    val hsv = floatArrayOf(hue, saturation, brightness)

    // Convert HSV to an ARGB color int using Android's HSVToColor, then wrap it as a Compose Color.
    return Color(android.graphics.Color.HSVToColor(hsv))
}

/**
 * Formats a phone number by inserting spaces.
 *
 * The function assumes that the input phone number is in a format like:
 *   +[CountryCode][LocalNumber]
 * where the local number is exactly 10 digits long. The country code may be one or two digits.
 *
 * For example:
 *   +11234567890  ->  +1 123 456 7890
 *   +441234567890 ->  +44 123 456 7890
 *
 * If the input doesn't meet the expected criteria, the original string is returned.
 *
 * @param phone The raw phone number string.
 * @return The formatted phone number.
 */
fun formatPhoneNumber(phone: String): String {
    // Remove any existing whitespace.
    val cleaned = phone.filter { !it.isWhitespace() }

    // We expect a '+' and at least 1 digit for country code plus 10 digits for the local part.
    if (!cleaned.startsWith("+") || cleaned.length < 12) return phone

    // The local part is always the last 10 characters.
    val localPart = cleaned.takeLast(10)

    // The country code is whatever lies between the '+' and the local part.
    val countryCode = cleaned.substring(1, cleaned.length - 10)

    // Group the local part into: first 3, next 3, and last 4 digits.
    val areaCode = localPart.substring(0, 3)
    val prefix = localPart.substring(3, 6)
    val lineNumber = localPart.substring(6)

    // Return the formatted string.
    return "+$countryCode $areaCode $prefix $lineNumber"
}