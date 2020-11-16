package com.zing.zalo.usecase.sleep

import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.text.SimpleDateFormat
import java.util.*

/**
 * Util class to handle date-time
 */
object DateTimeConvert {

    fun convertDateHM(timeInMillis: Long): String  {
        val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return simpleDateFormat.format(Date(timeInMillis))
    }

    fun convertDateHMDMY(timeInMillis: Long): String {
        val simpleDateFormat = SimpleDateFormat("HH:mm (dd/MM/yyyy)", Locale.getDefault())
        return simpleDateFormat.format(Date(timeInMillis))
    }

    fun convertIntTimeToStr(value: Int) = if (value < 10) "0$value" else value.toString()

    fun getTimeHmInPair(): Pair<Int, Int> {
        val datetime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        return Pair(datetime.hour, datetime.minute)
    }
}