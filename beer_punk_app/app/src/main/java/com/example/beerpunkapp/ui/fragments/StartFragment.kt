package com.example.beerpunkapp.ui.fragments

import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.FragmentStartBinding
import com.example.beerpunkapp.model.BeerAdapter
import com.example.beerpunkapp.model.BeerTestModel
import com.example.beerpunkapp.utilits.mainActivity
import com.example.beerpunkapp.utilits.showToast
import com.github.javafaker.Faker


class StartFragment : BaseFragment<FragmentStartBinding>() {
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
        setupMenu()
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.start_action_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.start_menu_btn_search -> {
                        showToast("searchPlaceHolder")
                        true
                    }
                    R.id.start_menu_btn_random -> {
                        showToast("randomizePlaceHolder")
                        true
                    }

                    else -> return false
                }
            }

        }, viewLifecycleOwner, androidx.lifecycle.Lifecycle.State.RESUMED)
    }
}