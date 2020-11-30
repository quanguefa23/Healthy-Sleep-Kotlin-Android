package com.zing.zalo.hsapp.presentation.view.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.zing.zalo.hsapp.R
import com.zing.zalo.hsapp.databinding.FragmentTipsBinding
import com.zing.zalo.hsapp.framework.util.networking.NetworkingChecker
import java.util.*

class TipsFragment private constructor(): Fragment() {

    private lateinit var binding: FragmentTipsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTipsBinding.inflate(layoutInflater)

        requireActivity().title = getString(R.string.tips).toUpperCase(Locale.ROOT)
        setListenerForCardView()

        return binding.root
    }

    private fun setListenerForCardView() {
        binding.cardView1.setOnClickListener {
            onClickCardView("http://tapchitaichinh.vn/tai-chinh-gia-dinh/thoi-gian-ngu-tot-nhat-cho-suc-khoe-la-bao-nhieu-gio-322560.html")
        }
        binding.cardView2.setOnClickListener {
            onClickCardView("https://www.vinmec.com/vi/tin-tuc/thong-tin-suc-khoe/4-giai-doan-cua-mot-giac-ngu/")
        }
    }

    private fun onClickCardView(url: String) {
        if (NetworkingChecker.isNetworkConnected(requireContext())) {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
        else {
            Toast.makeText(requireContext(), "Không có kết nối mạng", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        fun newInstance() = TipsFragment()
    }
}