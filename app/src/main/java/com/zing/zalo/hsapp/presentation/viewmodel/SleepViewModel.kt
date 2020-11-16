package com.zing.zalo.hsapp.presentation.viewmodel

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.provider.AlarmClock
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zing.zalo.data.repository.Repository
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.framework.util.AppConstants.APP_NAME
import com.zing.zalo.hsapp.framework.util.executor.TaskExecutorRepeater
import com.zing.zalo.hsapp.framework.util.executor.TaskExecutorRepeaterFactory
import com.zing.zalo.hsapp.presentation.MyApplication
import com.zing.zalo.usecase.UseCaseConstants.GO_BED_MODE
import com.zing.zalo.usecase.UseCaseConstants.WAKE_UP_MODE
import com.zing.zalo.usecase.sleep.DateTimeConvert
import com.zing.zalo.usecase.sleep.SleepUseCase
import java.util.*

class SleepViewModel @ViewModelInject constructor(val repository: Repository): ViewModel() {

    val sleepTimeOpt = MutableLiveData(repository.getSleepTimeOption())

    val sleepTimeString: LiveData<String> = Transformations.map(sleepTimeOpt) {
        repository.getSleepTimeStringByCycles(it + 3)
    }

    private val _alarmSettingMode = MutableLiveData(GO_BED_MODE)
    val alarmSettingMode: MutableLiveData<Int> = _alarmSettingMode

    var timePickerData = Pair(0, 0)
        private set
    private var timeReflectPickerData = Pair(0, 0)

    private val _dropBoxText = MutableLiveData("")
    val dropBoxText: LiveData<String> = _dropBoxText

    val wakeupOrGoBed: LiveData<String> = Transformations.map(_alarmSettingMode) {
        if (it == WAKE_UP_MODE) "đi ngủ"
        else "thức dậy"
    }

    private val _reflectInfoText = MutableLiveData("")
    val reflectInfoText: LiveData<String> = _reflectInfoText

    private val _wakeupTimeStr = MutableLiveData(DateTimeConvert.convertDateHMDMY(repository.getWakeUpTime()))
    val wakeupTimeStr: LiveData<String> = _wakeupTimeStr

    private val _rotation: MutableLiveData<Float> = MutableLiveData(0f)
    val rotation: LiveData<Float> = _rotation

    private var taskExecutorRepeater: TaskExecutorRepeater? = null

    fun startRotateImage() {
        taskExecutorRepeater = TaskExecutorRepeaterFactory.getTaskExecutorRepeater()
        taskExecutorRepeater?.start(10) {
            var newRot = (_rotation.value ?: 0f) + 0.1f
            if (newRot >= 360)
                newRot -= 360
            _rotation.value = newRot
        }
    }

    fun stopRotateImage() {
        taskExecutorRepeater?.stop()
        taskExecutorRepeater = null
    }

    private val handler = Handler(Looper.getMainLooper())

    fun getAlarmClockOpt() = repository.getAlarmClockOption()

    fun prepareForSleep(): Long {
        repository.saveSleepTimeOption(sleepTimeOpt.value ?: 0)

        val wakeupTime = SleepUseCase.getExactTimeInMillis(
            if (alarmSettingMode.value == GO_BED_MODE)
                timeReflectPickerData
            else timePickerData
        )

        repository.saveWakeUpTime(wakeupTime)
        _wakeupTimeStr.value = DateTimeConvert.convertDateHMDMY(wakeupTime)

        return wakeupTime
    }

    fun setAlarmSettingMode(mode: Int) {
        _alarmSettingMode.value = mode
    }

    fun getAlarmTitle() =
        MyApplication.getInstance().getString(R.string.sleep_alarm).toUpperCase(Locale.ROOT)

    fun getStatusTitle() =
        MyApplication.getInstance().getString(R.string.sleep_status).toUpperCase(Locale.ROOT)

    fun launchAutoDestroyFragment(callback: Runnable) {
        handler.postDelayed(callback,
            repository.getWakeUpTime() - System.currentTimeMillis() + 3000)
    }

    fun cancelAutoDestroyFragment(callback: Runnable) {
        handler.removeCallbacks(callback)
    }

    fun saveTimePickerData(h: Int, m: Int) {
        timePickerData = Pair(h, m)
        updateDropBoxText()
    }

    fun updateTimePickerData() {
        timePickerData = DateTimeConvert.getTimeHmInPair()
        updateDropBoxText()
    }

    private fun updateDropBoxText() {
        val hStr = DateTimeConvert.convertIntTimeToStr(timePickerData.first)
        val mStr = DateTimeConvert.convertIntTimeToStr(timePickerData.second)
        val str = if (alarmSettingMode.value == GO_BED_MODE) "Đi ngủ" else "Thức dậy"
        _dropBoxText.value = "$str lúc $hStr:$mStr"
    }

    fun saveReflectTimeData() {
        timeReflectPickerData = SleepUseCase.getTimeReflectPickerData(
            timePickerData,
            (sleepTimeOpt.value ?: 0) + 3,
            alarmSettingMode.value ?: GO_BED_MODE)
        updateInfoTextView()
    }

    private fun updateInfoTextView() {
        val hStr = DateTimeConvert.convertIntTimeToStr(timeReflectPickerData.first)
        val mStr = DateTimeConvert.convertIntTimeToStr(timeReflectPickerData.second)
        _reflectInfoText.value = "$hStr:$mStr"
    }

    fun createSetAlarmIntent(): Intent {
        val time =
            if (alarmSettingMode.value == GO_BED_MODE)
                timeReflectPickerData
            else
                timePickerData

        return Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
            putExtra(AlarmClock.EXTRA_MESSAGE, APP_NAME)
            putExtra(AlarmClock.EXTRA_HOUR, time.first)
            putExtra(AlarmClock.EXTRA_MINUTES, time.second)
        }
    }

    fun createDismissAlarmIntent(): Intent {
        val time =
            if (alarmSettingMode.value == GO_BED_MODE)
                timeReflectPickerData
            else
                timePickerData

        return Intent(AlarmClock.ACTION_DISMISS_ALARM).apply {
            putExtra(AlarmClock.EXTRA_ALARM_SEARCH_MODE, AlarmClock.ALARM_SEARCH_MODE_ALL)
            putExtra(AlarmClock.EXTRA_MESSAGE, APP_NAME)
            putExtra(AlarmClock.EXTRA_HOUR, time.first)
            putExtra(AlarmClock.EXTRA_MINUTES, time.second)
        }
    }
}