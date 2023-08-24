package com.example.beerpunkapp.screen

import android.os.Bundle
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
import com.example.beerpunkapp.R
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
    private val adapterLinear by lazy { SearchResultAdapter(::handleBeerClick) }
    private val TAG = "fragment" //todo

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
        setRecyclerView()
        super.onViewCreated(view, savedInstanceState)
        loadData()
        mainActivity.setSupportActionBar(binding.mainToolbar)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Log.v(TAG,"onViewCreated") //todo
    }

    private fun setRecyclerView() {
        binding.searchRecyclerView.adapter = adapterLinear
        binding.searchRecyclerView.layoutManager = LinearLayoutManager(mainActivity)
        binding.searchRecyclerView.setHasFixedSize(true)
        val scrollListener = RecyclerViewLoadMoreScroll(LinearLayoutManager(mainActivity))
        scrollListener.setOnLoadMoreListener(object :
            OnLoadMoreListener {
            override fun onLoadMore() {
                /*if (viewModel.state){

                }*/
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
            SearchResultState.Initial    -> Log.v(TAG,"initial state") //todo
            SearchResultState.Loading    -> showProgress()
            is SearchResultState.Content -> showContent(state.items, state.expandAvailable)
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

    private fun showContent(items: List<Beer>, expandAvailable : Boolean) {
        with(binding) {
            progressBar.isVisible = false
            errorContent.isVisible = false
            recyclerViewContent.isVisible = true
            if (items.isEmpty()){
                Snackbar.make(binding.searchRecyclerView, getString(R.string.search_result_attention_snackbar_label), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.search_result_attention_snackbar_action)){  }
                    .show()
            }
            (searchRecyclerView.adapter as? SearchResultAdapter)?.rebuildData(items + null)
            if (!expandAvailable){
                searchRecyclerView.clearOnScrollListeners()
            }
            Log.v(TAG,"addData") //todo
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