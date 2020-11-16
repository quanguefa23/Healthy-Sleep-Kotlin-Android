package com.zing.zalo.data.repository

/**
 * This repo saves data on disk (remain until your app uninstalled)
 */
interface LocalRepository {

    fun saveSleepTimeOption(pos: Int)
    fun getSleepTimeOption(): Int

    fun saveTypeOfFirstFragment(type: Int)
    fun getTypeOfFirstFragment(): Int

    fun saveWakeUpTime(wakeupTime: Long)
    fun getWakeUpTime(): Long

    fun saveAlarmClockOption(opt: Int)
    fun getAlarmClockOption(): Int
}