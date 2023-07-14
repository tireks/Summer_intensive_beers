package com.example.beerpunkapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.beerpunkapp.data.BeerRepositoryImpl
import com.example.beerpunkapp.databinding.ActivityMainBinding
import com.example.beerpunkapp.domain.repository.BeerRepository
import com.example.beerpunkapp.screen.SearchFormFragmentDirections
import com.example.beerpunkapp.screen.StartFragmentDirections

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navController get() = findNavController(R.id.mainDataContainer)

    /*private val mToolbar by lazy { binding.mainToolbar }*/

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
    }

    private fun initFields() {
        //
    }

    fun openDetails(beerId: Long){
        val action = StartFragmentDirections.actionStartFragmentToDetailsFragment(beerId)
        navController.navigate(action)
    }

    fun openSearchForm(){
        val action = StartFragmentDirections.actionStartFragmentToSearchFormFragment()
        navController.navigate(action)
    }

    fun openSearchResult(paramList: Array<String>){
        val action = SearchFormFragmentDirections.actionSearchFormFragmentToSearchResultFragment(paramList)
        navController.navigate(action)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}