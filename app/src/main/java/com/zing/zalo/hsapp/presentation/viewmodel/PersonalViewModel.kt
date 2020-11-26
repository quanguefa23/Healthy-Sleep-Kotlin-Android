package com.zing.zalo.hsapp.presentation.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zing.zalo.data.DataConstants.APP_CLOCK_OPTION
import com.zing.zalo.data.DataConstants.DEVICE_CLOCK_OPTION
import com.zing.zalo.data.repository.Repository
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.framework.alarm.MediaFactory
import com.zing.zalo.hsapp.framework.util.AppConstants.ALARM_FRAGMENT
import com.zing.zalo.hsapp.presentation.MyApplication
import java.util.*

class PersonalViewModel @ViewModelInject constructor(val repository: Repository): ViewModel() {

    private val _isDeviceClockOpt = MutableLiveData(repository.getAlarmClockOption() == DEVICE_CLOCK_OPTION)
    val isDeviceClockOpt: LiveData<Boolean> = this._isDeviceClockOpt

    private val _isAlarmFragmentEnable = MutableLiveData(repository.getTypeOfFirstFragment() == ALARM_FRAGMENT)
    val isAlarmFragmentEnable: LiveData<Boolean> = this._isAlarmFragmentEnable

    private val _mediaName = MutableLiveData<String>()
    val mediaName: LiveData<String> = this._mediaName

    fun getTitle() =
        MyApplication.getInstance().getString(R.string.personal).toUpperCase(Locale.ROOT)

    fun saveOption(checked: Boolean) {
        _isDeviceClockOpt.value = checked
        repository.saveAlarmClockOption(if (checked) DEVICE_CLOCK_OPTION else APP_CLOCK_OPTION)
    }

    fun updateValueMediaName() {
        _mediaName.value = MediaFactory.getMediaNameById(repository.getMediaOption())
    }

}