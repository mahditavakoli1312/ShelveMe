package ir.mahditavakoli.shelveme.util


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getPersianCurrentDateYMD(): String {
    // Get the current date and time
    val currentDateTime = LocalDateTime.now()

    // Define the formatter for the desired format
    val formatter = DateTimeFormatter.ofPattern("'d-'yyyy-MM-dd-'t-'HH:mm")

    // Format the current date and time using the formatter
    return currentDateTime.format(formatter)
}
