package com.zing.zalo.hsapp.framework.repository

import android.content.Context
import com.zing.zalo.data.DataConstants.APP_CLOCK_OPTION
import com.zing.zalo.data.repository.LocalRepository
import com.zing.zalo.hsapp.framework.util.AppConstants.ALARM_FRAGMENT
import com.zing.zalo.hsapp.presentation.MyApplication
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of LocalRepository, use room db and shared preferences
 */
@Singleton
class LocalRepositoryImpl @Inject constructor(): LocalRepository {

    companion object {
        private const val SLEEP_TIME_OPTION_PREFERENCES_KEY = "SleepTimeOption"
        private const val FIRST_FRAGMENT_TYPE_PREFERENCES_KEY = "FirstFragmentType"
        private const val WAKE_UP_TIME_PREFERENCES_KEY = "WakeUpTimeType"
        private const val ALARM_CLOCK_OPT_PREFERENCES_KEY = "AlarmClockOption"
        private const val MEDIA_OPT_PREFERENCES_KEY = "MediaOption"
        private const val PREFERENCES_STATE_NAME = "AppDataState"
    }

    private val sharedPreferences = MyApplication.getInstance()
        .getSharedPreferences(PREFERENCES_STATE_NAME, Context.MODE_PRIVATE)

    override fun saveSleepTimeOption(pos: Int) {
        sharedPreferences.edit().apply {
            putInt(SLEEP_TIME_OPTION_PREFERENCES_KEY, pos)
            apply()
        }
    }
    override fun getSleepTimeOption() =
        sharedPreferences.getInt(SLEEP_TIME_OPTION_PREFERENCES_KEY, 0)


    override fun saveTypeOfFirstFragment(type: Int) {
        sharedPreferences.edit().apply {
            putInt(FIRST_FRAGMENT_TYPE_PREFERENCES_KEY, type)
            apply()
        }
    }
    override fun getTypeOfFirstFragment() =
        sharedPreferences.getInt(FIRST_FRAGMENT_TYPE_PREFERENCES_KEY, ALARM_FRAGMENT)

    override fun saveWakeUpTime(wakeupTime: Long) {
        sharedPreferences.edit().apply {
            putLong(WAKE_UP_TIME_PREFERENCES_KEY, wakeupTime)
            apply()
        }
    }
    override fun getWakeUpTime() =
        sharedPreferences.getLong(WAKE_UP_TIME_PREFERENCES_KEY, 0L)

    override fun saveAlarmClockOption(opt: Int) {
        sharedPreferences.edit().apply {
            putInt(ALARM_CLOCK_OPT_PREFERENCES_KEY, opt)
            apply()
        }
    }
    override fun getAlarmClockOption() =
        sharedPreferences.getInt(ALARM_CLOCK_OPT_PREFERENCES_KEY, APP_CLOCK_OPTION)

    override fun saveMediaOption(opt: Int) {
        sharedPreferences.edit().apply {
            putInt(MEDIA_OPT_PREFERENCES_KEY, opt)
            apply()
        }
    }
    override fun getMediaOption() =
        sharedPreferences.getInt(MEDIA_OPT_PREFERENCES_KEY, 1)
}