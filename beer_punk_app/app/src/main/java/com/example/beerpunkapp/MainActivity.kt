package com.example.beerpunkapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.beerpunkapp.data.BeerRepositoryImpl
import com.example.beerpunkapp.databinding.ActivityMainBinding
import com.example.beerpunkapp.domain.repository.BeerRepository
import com.example.beerpunkapp.screen.StartFragmentDirections

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val navController get() = findNavController(R.id.mainDataContainer)
    /*private val mToolbar by lazy { binding.mainToolbar }*/

    val repository: BeerRepository = BeerRepositoryImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /*val appBarConfiguration = AppBarConfiguration(navController.graph)
        findViewById<Toolbar>(R.id.main_toolbar)
            .setupWithNavController(navController, appBarConfiguration)*/
        /*val collapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_details_toolbar)
        val detailsToolbar = findViewById<Toolbar>(R.id.details_toolbar)
        val navHostFragment = supportFragmentManager.findFragmentById(androidx.navigation.fragment.R.id.nav_host_fragment_container) as NavHostFragment
        val navController1 = navHostFragment.navController
        val appBarConfiguration1 = AppBarConfiguration(navController1.graph)
        collapsingToolbarLayout.setupWithNavController(detailsToolbar, navController1, appBarConfiguration1)*/
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

    fun openRandomBeer(){
        val action = StartFragmentDirections.actionStartFragmentToRandomBeerFragment()
        navController.navigate(action)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp()
    }

}