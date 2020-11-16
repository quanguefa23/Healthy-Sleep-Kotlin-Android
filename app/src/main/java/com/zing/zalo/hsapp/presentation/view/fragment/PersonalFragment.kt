package com.zing.zalo.hsapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.zing.zalo.hsapp.databinding.FragmentPersonalBinding
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

//    override fun onDestroy() {
//        super.onDestroy()
//        Log.d("QUANG", "destroy personal")
//    }

    override fun setUpBinding(inflater: LayoutInflater, container: ViewGroup?) {
        binding = FragmentPersonalBinding.inflate(inflater, container, false)
        binding.myController = this
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
    }

    companion object {
        fun newInstance() = PersonalFragment()
    }
}