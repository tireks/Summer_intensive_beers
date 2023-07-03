package com.example.beerpunkapp.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.FragmentStartBinding


class StartFragment : BaseFragment<FragmentStartBinding>(){
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStartBinding {
        return FragmentStartBinding.inflate(inflater, container, false)
    }


}