package com.zing.zalo.hsapp.presentation.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.zing.zalo.hsapp.databinding.ActivityMainBinding
import com.zing.zalo.hsapp.framework.util.AppConstants.ALARM_FRAGMENT
import com.zing.zalo.hsapp.framework.util.AppConstants.STATUS_FRAGMENT
import com.zing.zalo.hsapp.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigationListener()
        viewModel.setDefaultFragment()
    }

    // set up bottom navigation to respond user action
    private fun setupNavigationListener() {
        // replace fragment when fragmentSelectedId changed
        viewModel.fragmentSelectedId.observe(this) {
            replaceFragment(viewModel.createFragmentByMenuId(it))
        }
        // change fragmentSelectedId value when bottomNavigation touched
        binding.bottomNavigation.setOnNavigationItemSelectedListener(viewModel.createNavListener())
    }

    // remove current fragment in Layout and add the new necessary one
    private fun replaceFragment(fragment: Fragment) {
        with(supportFragmentManager.beginTransaction()) {
            replace(binding.frameContainer.id, fragment)
            commit()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("destroy activity")
    }

    fun startSleep() {
        // start service to set alarm
      //  AlarmClockFactory.createAlarmService(MyApplication.getInstance()).setAlarmAt(time)
        viewModel.setTypeOfFirstFragment(STATUS_FRAGMENT)
    }

    fun stopSleep() {
        viewModel.setTypeOfFirstFragment(ALARM_FRAGMENT)
    }
}