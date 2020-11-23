package com.zing.zalo.hsapp.framework.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import com.zing.zalo.data.repository.LocalRepository
import com.zing.zalo.hsapp.presentation.MyApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * not yet done
 */
@Singleton
class LocalRepositoryMigrationImpl @Inject constructor(): LocalRepository {

    companion object {
        private const val SLEEP_TIME_OPTION_PREFERENCES_KEY = "SleepTimeOption"
        private const val FIRST_FRAGMENT_TYPE_PREFERENCES_KEY = "FirstFragmentType"
        private const val WAKE_UP_TIME_PREFERENCES_KEY = "WakeUpTimeType"
        private const val ALARM_CLOCK_OPT_PREFERENCES_KEY = "AlarmClockOption"
        private const val PREFERENCES_NAME = "AppDataAndSetting"
    }

    private val dataStore: DataStore<Preferences> = MyApplication.getInstance().createDataStore(
        name = PREFERENCES_NAME
    )

    override fun saveSleepTimeOption(pos: Int) {
        TODO("Not yet implemented")
    }

    override fun getSleepTimeOption(): Int {
        val key = preferencesKey<Int>(SLEEP_TIME_OPTION_PREFERENCES_KEY)
        val flow: Flow<Int> = dataStore.data.map { preferences ->
                // No type safety.
                preferences[key] ?: 0
            }
        return 0
    }

    override fun saveTypeOfFirstFragment(type: Int) {
        TODO("Not yet implemented")
    }

    override fun getTypeOfFirstFragment(): Int {
        TODO("Not yet implemented")
    }

    override fun saveWakeUpTime(wakeupTime: Long) {
        TODO("Not yet implemented")
    }

    override fun getWakeUpTime(): Long {
        TODO("Not yet implemented")
    }

    override fun saveAlarmClockOption(opt: Int) {
        TODO("Not yet implemented")
    }

    override fun getAlarmClockOption(): Int {
        TODO("Not yet implemented")
    }

}