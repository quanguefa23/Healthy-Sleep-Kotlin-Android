package com.zing.zalo.hsapp.framework.alarm.clock

import android.content.Context

/**
 * const val for app Alarm
 */
const val AWAKE_TIME_KEY = "AWAKE_TIME"
const val MEDIA_OPTION_KEY = "MEDIA_OPTION"

/**
 * Interface for AlarmClock (to set alarm)
 */
interface AlarmClock {
    fun setAlarmAt(timeInMillis: Long, mediaOpt: Int)
    fun cancelAlarm()
}

/**
 * Factory pattern to switch between implementation of AlarmClock without break the view/viewModel
 */
object AlarmClockFactory {

    private lateinit var alarmClock: AlarmClock

    fun getAlarmClock(appContext: Context): AlarmClock {
        if (!::alarmClock.isInitialized) {
            alarmClock = AlarmClockByWorkerManager(appContext)
        }
        return alarmClock
    }
}