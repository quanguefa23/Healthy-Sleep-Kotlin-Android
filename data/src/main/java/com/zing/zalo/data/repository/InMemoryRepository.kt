package com.zing.zalo.data.repository

/**
 * This repo saves data in memory (will be deleted when your app destroyed)
 */
interface InMemoryRepository {

    fun getSleepTimeStringByCycles(option: Int): String

    fun saveSleepTimeOption(pos: Int)
    fun getSleepTimeOption(): Int?

    fun saveTypeOfFirstFragment(type: Int)
    fun getTypeOfFirstFragment(): Int?

    fun saveWakeUpTime(wakeupTime: Long)
    fun getWakeUpTime(): Long?

    fun saveAlarmClockOption(opt: Int)
    fun getAlarmClockOption(): Int?
}