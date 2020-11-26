package com.zing.zalo.hsapp.framework.repository

import com.zing.zalo.data.repository.InMemoryRepository
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.presentation.MyApplication
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of InMemoryRepository
 */
@Singleton
class InMemoryRepositoryImpl @Inject constructor(): InMemoryRepository {

    private lateinit var _sleepTimeMap: Map<Int, String>
    private var _sleepTimeOption: Int? = null
    private var _typeOfFirstFragment: Int? = null
    private var _wakeupTime: Long? = null
    private var _alarmClockOpt: Int? = null
    private var _mediaOption: Int? = null

    override fun getSleepTimeStringByCycles(option: Int): String {
        if (!::_sleepTimeMap.isInitialized) {
            _sleepTimeMap = (3..6).associateBy({it}) {
                val h = (it * 90 + 15) / 60
                val m = (it * 90 + 15) % 60
                MyApplication.getInstance().getString(R.string.sleep_time_format, h, m)
            }
        }

        return _sleepTimeMap[option] ?: ""
    }

    override fun saveSleepTimeOption(pos: Int) { _sleepTimeOption = pos }
    override fun getSleepTimeOption(): Int? = _sleepTimeOption

    override fun saveTypeOfFirstFragment(type: Int) { _typeOfFirstFragment = type }
    override fun getTypeOfFirstFragment(): Int? = _typeOfFirstFragment

    override fun saveWakeUpTime(wakeupTime: Long) { _wakeupTime = wakeupTime }
    override fun getWakeUpTime(): Long? = _wakeupTime

    override fun saveAlarmClockOption(opt: Int) { _alarmClockOpt = opt }
    override fun getAlarmClockOption(): Int? = _alarmClockOpt

    override fun saveMediaOption(opt: Int) { _mediaOption = opt }
    override fun getMediaOption(): Int? = _mediaOption
}