package com.zing.zalo.hsapp.presentation.view.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.databinding.ActivitySelectRingtoneBinding
import com.zing.zalo.hsapp.presentation.viewmodel.SelectRingtoneViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectRingtoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectRingtoneBinding
    private val viewModel by viewModels<SelectRingtoneViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setContentView(binding.root)
    }

    private fun setupBinding() {
        binding = ActivitySelectRingtoneBinding.inflate(layoutInflater)
        binding.myController = this
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_in1, R.anim.anim_out1)
    }
}