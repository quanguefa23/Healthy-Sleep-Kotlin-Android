package com.zing.zalo.hsapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.zing.zalo.data.DataConstants.APP_CLOCK_OPTION
import com.zing.zalo.data.DataConstants.DEVICE_CLOCK_OPTION
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.databinding.FragmentSleepStatusBinding
import com.zing.zalo.hsapp.framework.alarm.clock.AlarmClockFactory
import com.zing.zalo.hsapp.presentation.MyApplication
import com.zing.zalo.hsapp.presentation.view.activity.MainActivity
import com.zing.zalo.hsapp.presentation.viewmodel.SleepViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SleepStatusFragment private constructor(): BaseFragment() {

    private lateinit var binding: FragmentSleepStatusBinding
    private val viewModel by activityViewModels<SleepViewModel>()
    private val stopSleepRunnable = Runnable { (activity as MainActivity).stopSleep() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        requireActivity().title = viewModel.getStatusTitle()
        viewModel.launchAutoDestroyFragment(stopSleepRunnable)

        return binding.root
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("QUANG", "destroy sleep status")
//    }

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentSleepStatusBinding.inflate(inflater, container, false)
        binding.myController = this
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.cancelAutoDestroyFragment(stopSleepRunnable)
    }

    override fun onStart() {
        super.onStart()
        viewModel.startRotateImage()
    }

    override fun onStop() {
        super.onStop()
        viewModel.stopRotateImage()

    }

    fun stopSleep() {
        when (viewModel.getAlarmClockOpt()) {
            DEVICE_CLOCK_OPTION -> {
                startActivity(viewModel.createDismissAlarmIntent())
                Toast.makeText(requireContext(), getString(R.string.cancel_alarm_success_noti), Toast.LENGTH_SHORT).show()
            }
            APP_CLOCK_OPTION -> {
                AlarmClockFactory.getAlarmClock(MyApplication.getInstance()).cancelAlarm()
                Toast.makeText(requireContext(),  getString(R.string.cancel_alarm_success_noti), Toast.LENGTH_SHORT).show()
            }
            else ->
                Toast.makeText(requireContext(),  getString(R.string.error), Toast.LENGTH_SHORT).show()
        }

        (activity as MainActivity).stopSleep()
    }

    companion object {
        fun newInstance() = SleepStatusFragment()
    }
}