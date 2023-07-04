package com.example.beerpunkapp.ui.fragments

import android.app.Application
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.FragmentStartBinding
import com.example.beerpunkapp.model.App
import com.example.beerpunkapp.model.BeerAdapter
import com.example.beerpunkapp.model.BeerService
import com.example.beerpunkapp.model.BeerTestModel
import com.example.beerpunkapp.utilits.showToast
import com.github.javafaker.Faker


class StartFragment : BaseFragment<FragmentStartBinding>(){
   // private val adapter by lazy { BeerAdapter() }

    /*private val beerService: BeerService
        get() = (activity?.applicationContext as App).beerService*/
    val faker = Faker.instance()
    val beerList = (1..20).map { BeerTestModel(
        id = it.toInt(),
        naming = faker.beer().name(),
        tags = faker.harryPotter().quote(),
        description = R.string.recycler_view_description_placeholder.toString(),
        photo = "https://images.punkapi.com/v2/86.png"
    ) }.toMutableList()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStartBinding {
        return FragmentStartBinding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()
        setHasOptionsMenu(true)
        binding.startRecyclerView.adapter = BeerAdapter(::handleCl)
        (binding.startRecyclerView.adapter as? BeerAdapter)?.beers = beerList
    }

    private fun handleCl(beerTestModel: BeerTestModel) {

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.start_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.start_menu_btn_search -> {
                showToast("searchPlaceHolder")
                true
            }
            R.id.start_menu_btn_random -> {
                showToast("randomizePlaceHolder")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}