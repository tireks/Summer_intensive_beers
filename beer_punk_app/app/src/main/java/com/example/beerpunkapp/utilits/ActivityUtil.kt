package com.example.beerpunkapp.utilits

import androidx.fragment.app.Fragment
import com.example.beerpunkapp.MainActivity

val Fragment.mainActivity: MainActivity
    get() = requireActivity() as MainActivity