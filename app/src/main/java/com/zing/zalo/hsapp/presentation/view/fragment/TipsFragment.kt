package com.zing.zalo.hsapp.presentation.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zing.zalo.hsapp.R
import java.util.*

class TipsFragment private constructor(): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        requireActivity().title = getString(R.string.tips).toUpperCase(Locale.ROOT)
        return inflater.inflate(R.layout.fragment_tips, container, false)
    }

    companion object {
        fun newInstance() = TipsFragment()
    }
}