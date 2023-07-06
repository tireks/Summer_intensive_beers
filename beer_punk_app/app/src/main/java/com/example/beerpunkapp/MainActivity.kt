package com.example.beerpunkapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.example.beerpunkapp.data.BeerRepositoryImpl
import com.example.beerpunkapp.databinding.ActivityMainBinding
import com.example.beerpunkapp.domain.repository.BeerRepository

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navController get() = findNavController(R.id.mainDataContainer)
    val repository: BeerRepository = BeerRepositoryImpl()

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
        //
    }

    private fun initFields() {
        //TODO("Not yet implemented")
    }

}