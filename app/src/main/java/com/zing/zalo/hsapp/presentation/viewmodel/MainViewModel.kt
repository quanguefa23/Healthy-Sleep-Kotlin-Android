package com.zing.zalo.hsapp.presentation.viewmodel

import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zing.zalo.data.repository.Repository
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.framework.util.AppConstants.ALARM_FRAGMENT
import com.zing.zalo.hsapp.presentation.view.fragment.PersonalFragment
import com.zing.zalo.hsapp.presentation.view.fragment.SleepAlarmFragment
import com.zing.zalo.hsapp.presentation.view.fragment.TipsFragment
import com.zing.zalo.hsapp.presentation.view.fragment.SleepStatusFragment

class MainViewModel @ViewModelInject constructor(val repository: Repository): ViewModel() {

    private val _fragmentSelectedId = MutableLiveData(R.id.sleep_item)
    val fragmentSelectedId: LiveData<Int> = _fragmentSelectedId

    // if the alarm was over, always start SleepAlarmFragment
    private var typeOfFirstFragment =
        if (System.currentTimeMillis() > repository.getWakeUpTime()) ALARM_FRAGMENT
        else repository.getTypeOfFirstFragment()

    fun setDefaultFragment() {
        _fragmentSelectedId.value = R.id.sleep_item
    }

    fun createFragmentByMenuId(id: Int): Fragment = when (id) {
        R.id.sleep_item -> {
            if (typeOfFirstFragment == ALARM_FRAGMENT)
                SleepAlarmFragment.newInstance()
            else
                SleepStatusFragment.newInstance()
        }
        R.id.calculator_item -> TipsFragment.newInstance()
        R.id.personal_item -> PersonalFragment.newInstance()
        else -> SleepAlarmFragment.newInstance()
    }

    fun createNavListener() = lit@ {
        item: MenuItem ->
        if (item.itemId != _fragmentSelectedId.value) {
            _fragmentSelectedId.value = item.itemId
            return@lit true
        }
        false
    }

    // change and save the type of first fragment. Also trigger fragment transition via liveData
    fun setTypeOfFirstFragment(type: Int) {
        typeOfFirstFragment = type
        repository.saveTypeOfFirstFragment(type)
        _fragmentSelectedId.value = R.id.sleep_item
    }
}