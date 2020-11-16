package com.zing.zalo.usecase.sleep

import com.zing.zalo.usecase.UseCaseConstants.GO_BED_MODE
import kotlinx.datetime.*

/**
 * Implement use cases of sleep fragments
 */
object SleepUseCase {

    fun getExactTimeInMillis(timeInDay: Pair<Int, Int>): Long {
        val today: LocalDate = Clock.System.todayAt(TimeZone.currentSystemDefault())
        val instant = today.atStartOfDayIn(TimeZone.currentSystemDefault())
        var res = instant.toEpochMilliseconds() + (timeInDay.first * 60 + timeInDay.second) * 60 * 1000
        if (res < System.currentTimeMillis())
            res += 24 * 60 * 60 * 1000
        return res
    }

    fun getTimeReflectPickerData(src: Pair<Int, Int>, cycles: Int, mode: Int): Pair<Int, Int> {
        val minCycle = cycles * 90 + 15
        return if (mode == GO_BED_MODE) {
            val minSrc = src.first * 60 + src.second
            val minRes = (minSrc + minCycle) % (24 * 60)
            Pair(minRes / 60, minRes % 60)
        }
        else {
            val minSrc = src.first * 60 + src.second + 24 * 60
            val minRes = (minSrc - minCycle) % (24 * 60)
            Pair(minRes / 60, minRes % 60)
        }
    }
}