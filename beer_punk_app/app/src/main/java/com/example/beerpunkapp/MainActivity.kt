package com.example.beerpunkapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.beerpunkapp.databinding.ActivityMainBinding
import com.example.beerpunkapp.ui.fragments.StartFragment
import com.example.beerpunkapp.utilits.replaceFragment

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunctionality()
    }

    private fun initFunctionality() {
        replaceFragment(StartFragment(), R.id.mainDataContainer)
    }

    private fun initFields() {
        //TODO("Not yet implemented")
    }

}