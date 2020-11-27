package com.zing.zalo.hsapp.presentation.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.databinding.FragmentPersonalBinding
import com.zing.zalo.hsapp.presentation.view.activity.SelectRingtoneActivity
import com.zing.zalo.hsapp.presentation.viewmodel.PersonalViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonalFragment private constructor(): BaseFragment() {

    private lateinit var binding: FragmentPersonalBinding
    private val viewModel by viewModels<PersonalViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        requireActivity().title = viewModel.getTitle()
        setListenerForSwitchButton()

        return binding.root
    }

    private fun setListenerForSwitchButton() {
        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveOption(isChecked)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateValueMediaName()
    }
//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("QUANG", "destroy personal")
//    }

    fun startSelectRingToneActivity() {
        startActivity(Intent(requireContext(), SelectRingtoneActivity::class.java))
        requireActivity().overridePendingTransition(R.anim.anim_in, R.anim.anim_out)
    }

    override fun setupBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentPersonalBinding.inflate(inflater, container, false)
        binding.myController = this
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
    }

    companion object {
        fun newInstance() = PersonalFragment()
    }
}