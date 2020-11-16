package com.zing.zalo.hsapp.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/**
 * BaseFragment for all Fragments
 */
abstract class BaseFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setUpBinding(inflater, container)
        return null
    }
    protected abstract fun setUpBinding(inflater: LayoutInflater, container: ViewGroup?)
}