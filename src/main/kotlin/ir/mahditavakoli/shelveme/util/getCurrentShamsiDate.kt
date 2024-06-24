package ir.mahditavakoli.shelveme.util

import com.github.eloyzone.jalalicalendar.DateConverter
import com.github.eloyzone.jalalicalendar.JalaliDateFormatter
import java.text.SimpleDateFormat
import java.util.*

fun getPersianCurrentDateYMD(): String {
    val calendar: Calendar = Calendar.getInstance()

    val year: Int = calendar.get(Calendar.YEAR)
    val month: Int = calendar.get(Calendar.MONTH) + 1 // January is 0, so we add 1
    val day: Int = calendar.get(Calendar.DAY_OF_MONTH)
    val dateConverter = DateConverter()
    val time = SimpleDateFormat("HH-mm-ss").format(Date())


    return dateConverter.gregorianToJalali(year, month, day)
        .format(JalaliDateFormatter("yyyy/mm/dd", JalaliDateFormatter.FORMAT_IN_ENGLISH)).replace("/", "-")+"-"+time
}