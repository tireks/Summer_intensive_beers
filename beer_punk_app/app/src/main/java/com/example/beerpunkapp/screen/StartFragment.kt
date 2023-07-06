package com.example.beerpunkapp.screen

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
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
    private var page : Long = 1

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

        // Метод для запроса данных из сети
        loadData()
    }

    private fun loadData() {
        viewModel.loadData(page)
    }

    private fun handleState(state: StartListState) {
        when (state) {
            StartListState.Initial    -> Unit
            StartListState.Loading    -> showProgress()
            is StartListState.Content -> showContent(state.items)
            is StartListState.Error   -> showError(state.msg)
        }
    }

    override fun onResume() {
        super.onResume()
        setupMenu()
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

    private fun showContent(beers: List<Beer>) {
        with(binding) {
            progressBar.isVisible = false
            errorContent.isVisible = false
            recuclerViewContent.isVisible = true
            (startRecyclerView.adapter as? StartAdapter)?.beers = beers
            buttonNextPage.setOnClickListener {
                page++
                loadData()
                buttonInfoPage.text = "Page $page"
            }
            buttonPrevPage.setOnClickListener {
                if (page > 1){
                    page--
                    loadData()
                    buttonInfoPage.text = "Page $page"
                }
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
                        showToast("randomizePlaceHolder")
                        true
                    }

                    else -> return false
                }
            }

        }, viewLifecycleOwner, androidx.lifecycle.Lifecycle.State.RESUMED)
    }

    private fun handleBeerClick(beerModel: BeerModel) {

    }

}