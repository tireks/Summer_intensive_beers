package com.example.beerpunkapp.screen

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.beerpunkapp.R
import com.example.beerpunkapp.data.BeerModel
import com.example.beerpunkapp.databinding.FragmentStartBinding
import com.example.beerpunkapp.domain.entity.Beer
import com.example.beerpunkapp.domain.usecase.GetAllBeersUseCase
import com.example.beerpunkapp.presentation.StartListState
import com.example.beerpunkapp.presentation.StartViewModel
import com.example.beerpunkapp.utilits.mainActivity
import com.example.beerpunkapp.utilits.showToast


class StartFragment : BaseFragment<FragmentStartBinding>(){

    /*private val mToolbar by lazy { binding.mainToolbar }*/

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStartBinding {
        return FragmentStartBinding.inflate(inflater, container, false)
    }

    private val viewModel: StartViewModel by viewModels {
        viewModelFactory {
            initializer {
                StartViewModel(GetAllBeersUseCase(mainActivity.repository))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.startRecyclerView.adapter = StartAdapter(::handleBeerClick)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        loadData()
        //setupMenu()
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setSupportActionBar(binding.mainToolbar)
        setupMenu()  //возможно не стоит это делать здесь, просто по другому тулбар не поднимается после возврата на этот фрагмент
        //mainActivity.supportActionBar?.show()
    }

    private fun loadData() {
        viewModel.loadData()
    }

    private fun handleState(state: StartListState) {
        when (state) {
            StartListState.Initial    -> Unit
            StartListState.Loading    -> showProgress()
            is StartListState.Content -> showContent(state.items,state.page)
            is StartListState.Error   -> showError(state.msg)
        }
    }

    private fun showError(msg: String) {
        with(binding) {
            progressBar.isVisible = false
            recuclerViewContent.isVisible = false
            errorContent.isVisible = true
            errorText.text = msg
            errorButton.setOnClickListener {
                loadData()
            }
        }
    }

    private fun showContent(beers: List<Beer>, page: Long) {
        with(binding) {
            progressBar.isVisible = false
            errorContent.isVisible = false
            recuclerViewContent.isVisible = true
            (startRecyclerView.adapter as? StartAdapter)?.beers = beers
            buttonInfoPage.text = String.format(resources.getString(R.string.start_button_info_variable), page)
            buttonNextPage.setOnClickListener {
                viewModel.nextButtonHandler()
            }
            buttonPrevPage.setOnClickListener {
                viewModel.prevButtonHandler()
            }
        }
}

    private fun showProgress() {
        with(binding) {
            errorContent.isVisible = false
            recuclerViewContent.isVisible = false
            progressBar.isVisible = true
        }
    }



    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
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
                        handleRandomClick()
                        true
                    }

                    else -> return false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun handleRandomClick() {
        mainActivity.openRandomBeer()
    }

    private fun handleBeerClick(beer: Beer) {
        beer.id?.let { mainActivity.openDetails(beerId = it) }
    }

}