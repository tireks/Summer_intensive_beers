package com.example.beerpunkapp.ui.fragments

import android.view.*
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.FragmentStartBinding
import com.example.beerpunkapp.model.BeerAdapter
import com.example.beerpunkapp.model.BeerTestModel
import com.example.beerpunkapp.utilits.mainActivity
import com.example.beerpunkapp.utilits.showToast
import com.github.javafaker.Faker


class StartFragment : BaseFragment<FragmentStartBinding>(){
   // private val adapter by lazy { BeerAdapter() }
    val faker = Faker.instance()
    val beerList by lazy { (1..20).map { BeerTestModel(
        id = it.toInt(),
        naming = faker.beer().name(),
        tags = faker.harryPotter().quote(),
        description = activity?.getString(R.string.recycler_view_description_placeholder) ?: "123",
        //description = "123321",
        photo = "https://images.punkapi.com/v2/86.png"
    ) }.toMutableList() }

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

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.start_action_menu, menu)
    }

    @Deprecated("Deprecated in Java")
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