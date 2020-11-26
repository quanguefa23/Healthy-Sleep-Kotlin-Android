package com.zing.zalo.hsapp.presentation.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.databinding.ActivitySelectRingtoneBinding
import com.zing.zalo.hsapp.presentation.adapter.MediaAdapter
import com.zing.zalo.hsapp.presentation.viewmodel.SelectRingtoneViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SelectRingtoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySelectRingtoneBinding
    private val viewModel by viewModels<SelectRingtoneViewModel>()
    private val adapter = MediaAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBinding()
        setContentView(binding.root)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        adapter.selPos = viewModel.getMediaOption()
        adapter.listMedia = viewModel.getListMedia()
    }

    override fun onStop() {
        super.onStop()
        adapter.releaseMedia()
    }

    private fun setupBinding() {
        binding = ActivitySelectRingtoneBinding.inflate(layoutInflater)
        binding.myController = this
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
    }

    fun onClickOkButton() {
        viewModel.saveMediaOption(adapter.selPos)
        Toast.makeText(this, "Đã đổi nhạc chuông", Toast.LENGTH_SHORT).show()
        onBackPressed()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.anim_in1, R.anim.anim_out1)
    }
}