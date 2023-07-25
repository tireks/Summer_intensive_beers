package com.example.beerpunkapp.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.navArgs
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.FragmentDetailsBinding
import com.example.beerpunkapp.databinding.FragmentSearchResultBinding
import com.example.beerpunkapp.domain.entity.Beer
import com.example.beerpunkapp.domain.usecase.GetAllBeersUseCase
import com.example.beerpunkapp.domain.usecase.GetBeersBySearchUseCase
import com.example.beerpunkapp.presentation.SearchResultState
import com.example.beerpunkapp.presentation.SearchResultViewModel
import com.example.beerpunkapp.presentation.StartListState
import com.example.beerpunkapp.presentation.StartViewModel
import com.example.beerpunkapp.utilits.mainActivity
import com.example.beerpunkapp.utilits.showToast
import kotlin.math.abs

class SearchResultFragment : BaseFragment<FragmentSearchResultBinding>(){

    private val args: SearchResultFragmentArgs by navArgs()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchResultBinding {
        return FragmentSearchResultBinding.inflate(inflater, container, false)
    }

    private val viewModel: SearchResultViewModel by viewModels {
        viewModelFactory {
            initializer {
                SearchResultViewModel(GetBeersBySearchUseCase(mainActivity.repository))
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.setSupportActionBar(binding.mainToolbar)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        binding.searchRecyclerView.adapter = SearchResultAdapter(::handleBeerClick)
        loadData()
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun handleBeerClick(beer: Beer) {
        try {
            beer.id?.let { mainActivity.openSearchDetails(abs(it)) }
        } catch (e: Exception){
            showError(e.message.toString())
        }
    }

    private fun loadData() {
        args.paramList?.let { viewModel.loadData(it) }
    }

    private fun handleState(state: SearchResultState) {
        when (state) {
            SearchResultState.Initial    -> Unit
            SearchResultState.Loading    -> showProgress()
            is SearchResultState.Content -> showContent(state.items)
            is SearchResultState.Error   -> showError(state.msg)
            SearchResultState.EmptyContent -> showEmpty()
        }
    }

    private fun showEmpty() {
        with(binding){
            progressBar.isVisible = false
            errorContent.isVisible = false
            recyclerViewContent.isVisible = false
            emptyContainer.isVisible = true
        }
    }

    private fun showError(msg: String) {
        with(binding) {
            progressBar.isVisible = false
            recyclerViewContent.isVisible = false
            errorContent.isVisible = true
            errorText.text = msg
            errorButton.setOnClickListener {
                loadData()
            }
        }
    }

    private fun showContent(items: List<Beer>) {
        with(binding) {
            progressBar.isVisible = false
            errorContent.isVisible = false
            recyclerViewContent.isVisible = true
            (searchRecyclerView.adapter as? SearchResultAdapter)?.beers = items
        }
    }

    private fun showProgress() {
        with(binding) {
            errorContent.isVisible = false
            recyclerViewContent.isVisible = false
            progressBar.isVisible = true
        }
    }


}