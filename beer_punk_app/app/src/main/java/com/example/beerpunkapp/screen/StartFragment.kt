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
import com.example.beerpunkapp.databinding.FragmentStartBinding
import com.example.beerpunkapp.domain.entity.Beer
import com.example.beerpunkapp.domain.usecase.GetAllBeersUseCase
import com.example.beerpunkapp.presentation.StartListState
import com.example.beerpunkapp.presentation.StartViewModel
import com.example.beerpunkapp.utilits.mainActivity
import kotlin.math.abs


class StartFragment : BaseFragment<FragmentStartBinding>(){

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
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setSupportActionBar(binding.mainToolbar)
        setupMenu()  //возможно не стоит это делать здесь, просто по другому тулбар не поднимается после возврата на этот фрагмент
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
                startRecyclerView.scrollToPosition(0)
                viewModel.nextButtonHandler()
            }
            buttonPrevPage.setOnClickListener {
                startRecyclerView.scrollToPosition(0)
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
                        handleSearchClick()
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

    private fun handleSearchClick() {
        mainActivity.openSearchForm()
    }

    private fun handleRandomClick() {
        mainActivity.openDetails(-1) // все эти пляски с положительным\отрицательным id возможно не очень корректно,
                                                    // но мне для рандома хотелось использовать инфраструктуру details,
                                                    // ибо там по сути все то же самое
    }

    private fun handleBeerClick(beer: Beer) {
        try {
            beer.id?.let { mainActivity.openDetails(abs(it)) }
        } catch (e: Exception){
            showError(e.message.toString())
        }
    }

}