package com.example.beerpunkapp.screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.beerpunkapp.databinding.FragmentSearchResultBinding
import com.example.beerpunkapp.domain.entity.Beer
import com.example.beerpunkapp.domain.usecase.GetBeersBySearchUseCase
import com.example.beerpunkapp.presentation.SearchResultState
import com.example.beerpunkapp.presentation.SearchResultViewModel
import com.example.beerpunkapp.utilits.OnLoadMoreListener
import com.example.beerpunkapp.utilits.RecyclerViewLoadMoreScroll
import com.example.beerpunkapp.utilits.mainActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        setRecyclerView()
        loadData()
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setRecyclerView() {
        var adapterLinear = SearchResultAdapter(::handleBeerClick)
        binding.searchRecyclerView.adapter = adapterLinear
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(mainActivity)
        binding.searchRecyclerView.setHasFixedSize(true)
        val scrollListener = RecyclerViewLoadMoreScroll(LinearLayoutManager(mainActivity))
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                loadMoreData()
            }
            private fun loadMoreData() {
                adapterLinear.addLoadingView()
                lifecycleScope.launch {
                    delay(3000)
                    expandData()
                    scrollListener.setLoaded()
                }
            }
        })
        binding.searchRecyclerView.addOnScrollListener(scrollListener)
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

    private fun expandData(){
        viewModel.expandData()
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
            if (items.isEmpty()){
                Snackbar.make(binding.searchRecyclerView, "Sorry! There no else beers left", Snackbar.LENGTH_LONG)
                    .setAction("Got It"){  }
                    .show()
            }
            (searchRecyclerView.adapter as? SearchResultAdapter)?.addData(items)
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