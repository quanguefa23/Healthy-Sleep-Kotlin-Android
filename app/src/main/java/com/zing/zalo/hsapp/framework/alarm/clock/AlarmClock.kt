package com.zing.zalo.hsapp.framework.alarm.clock

import android.content.Context

/**
 * Interface for AlarmClock (to set alarm)
 */
interface AlarmClock {
    fun setAlarmAt(timeInMillis: Long)
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