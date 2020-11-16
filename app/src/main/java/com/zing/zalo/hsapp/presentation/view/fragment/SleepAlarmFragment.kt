package com.zing.zalo.hsapp.presentation.view.fragment

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.zing.zalo.data.DataConstants.APP_CLOCK_OPTION
import com.zing.zalo.data.DataConstants.DEVICE_CLOCK_OPTION
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.databinding.DialogTimePickerBinding
import com.zing.zalo.hsapp.databinding.FragmentSleepAlarmBinding
import com.zing.zalo.hsapp.framework.alarm.clock.AlarmClockFactory
import com.zing.zalo.hsapp.presentation.MyApplication
import com.zing.zalo.hsapp.presentation.view.activity.MainActivity
import com.zing.zalo.hsapp.presentation.viewmodel.SleepViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SleepAlarmFragment private constructor(): BaseFragment() {

    private lateinit var binding: FragmentSleepAlarmBinding
    private val viewModel by activityViewModels<SleepViewModel>()
    private lateinit var dialog: Dialog
    private var dialogBinding: DialogTimePickerBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        requireActivity().title = viewModel.getAlarmTitle()
        setHasOptionsMenu(true)

        viewModel.updateTimePickerData()
        viewModel.saveReflectTimeData()
        viewModel.sleepTimeOpt.observe(viewLifecycleOwner) {
            viewModel.saveReflectTimeData()
        }

        return binding.root
    }

    override fun setUpBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentSleepAlarmBinding.inflate(inflater, container, false)
        binding.myController = this
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.option_menu, menu)
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("QUANG", "destroy sleep alarm")
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.set_tone -> startSetRingToneActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startSetRingToneActivity() {
    }

    fun showTimePickerDialog() {
        if (!::dialog.isInitialized) {
            // create view binding instance
            dialogBinding = DialogTimePickerBinding.inflate(layoutInflater)
            dialogBinding?.myController = this
            dialogBinding?.myViewModel = viewModel
            dialogBinding?.lifecycleOwner = this

            // create dialog
            dialog = Dialog(requireContext())
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogBinding?.root?.let { dialog.setContentView(it) }
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            dialogBinding?.timePicker?.hour = viewModel.timePickerData.first
            dialogBinding?.timePicker?.minute = viewModel.timePickerData.second
        }
        dialog.show()
    }

    fun onCancelButtonDialog() {
        dialog.cancel()
    }

    fun onOkButtonDialog() {
        val h = dialogBinding?.timePicker?.hour ?: 0
        val m = dialogBinding?.timePicker?.minute ?: 0
        viewModel.saveTimePickerData(h, m)
        viewModel.saveReflectTimeData()
        dialog.cancel()
    }

    fun startSleep() {
        val time = viewModel.prepareForSleep()

        when (viewModel.getAlarmClockOpt()) {
            DEVICE_CLOCK_OPTION -> {
                startActivity(viewModel.createSetAlarmIntent())
                Toast.makeText(requireContext(), getString(R.string.set_alarm_success_noti), Toast.LENGTH_SHORT).show()
            }
            APP_CLOCK_OPTION -> {
                AlarmClockFactory.getAlarmClock(MyApplication.getInstance()).setAlarmAt(time)
                Toast.makeText(requireContext(), getString(R.string.set_alarm_success_noti), Toast.LENGTH_SHORT).show()
            }
            else ->
                Toast.makeText(requireContext(), getString(R.string.error), Toast.LENGTH_SHORT).show()
        }

        (activity as MainActivity).startSleep()
    }

    companion object {
        fun newInstance() = SleepAlarmFragment()
    }
}