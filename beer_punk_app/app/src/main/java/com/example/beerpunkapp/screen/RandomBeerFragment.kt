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
import com.bumptech.glide.Glide
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.FragmentDetailsBinding
import com.example.beerpunkapp.databinding.FragmentRandomBeerBinding
import com.example.beerpunkapp.domain.entity.Beer
import com.example.beerpunkapp.domain.usecase.GetBeerByIdUseCase
import com.example.beerpunkapp.domain.usecase.GetRandomBeerUseCase
import com.example.beerpunkapp.presentation.DetailsState
import com.example.beerpunkapp.presentation.DetailsViewModel
import com.example.beerpunkapp.presentation.RandomBeerState
import com.example.beerpunkapp.presentation.RandomBeerViewModel
import com.example.beerpunkapp.utilits.mainActivity


class RandomBeerFragment : BaseFragment<FragmentRandomBeerBinding>() {
    private val viewModel: RandomBeerViewModel by viewModels {
        viewModelFactory {
            initializer {
                RandomBeerViewModel(GetRandomBeerUseCase(mainActivity.repository))
            }
        }
    }

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentRandomBeerBinding {
        return FragmentRandomBeerBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        beerData()
    }

    override fun onResume() {
        super.onResume()
        //mainActivity.supportActionBar?.hide()
        mainActivity.setSupportActionBar(binding.randomBeerToolbar)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun beerData() {
        viewModel.loadData()
    }

    private fun handleState(state: RandomBeerState) {
        when (state) {
            is RandomBeerState.Initial -> Unit
            is RandomBeerState.Loading -> showProgress()
            is RandomBeerState.Content -> showContent(state.beers)
            is RandomBeerState.Error -> showError(state.msg)
        }

    }

    private fun showError(msg: String) {
        with(binding) {
            progressBar.isVisible = false
            contentContainer.isVisible = false

            errorContent.isVisible = true
            errorText.text = msg
            errorButton.setOnClickListener { beerData() }
        }
    }

    private fun showContent(beers: List<Beer>) {
        with(binding) {
            progressBar.isVisible = false
            errorContent.isVisible = false
            contentContainer.isVisible = true

            randomBeerFirstBrewedContent.text = beers[0].brew_date ?: "--date is missing--"
            randomBeerDescriptionContent.text = beers[0].description ?: "--description is missing--"
            randomBeerTagContent.text = beers[0].tags ?: "--tag is missing--"
            //detailsTagContent.text = beers[0].ingredients?.yeast ?: "--tag is missing--"
            randomBeerAbvContent.text = beers[0].abv ?: "--?--"
            randomBeerEbcContent.text = beers[0].ebc ?: "--?--"
            randomBeerIbuContent.text = beers[0].ibu ?: "--?--"
            randomBeerSrmContent.text = beers[0].srm ?: "--?--"
            randomBeerToolbar.title = beers[0].name ?: "--name is missing--"
            randomBeerYeastContent.text = beers[0].ingredients?.yeast ?: "--yeast is missing--"
            for (i in beers[0].food.indices){
                randomBeerFoodContent.append(beers[0].food[i])
                if (i < beers[0].food.size - 1){
                    randomBeerFoodContent.append("\n")
                }
            }
            val tempBuff = beers[0].ingredients?.malt
            if (tempBuff != null){
                for (i in tempBuff.indices){
                    randomBeerMaltContent.append(tempBuff[i]?.name ?: "--malt is missing--")
                    if (i < tempBuff.size - 1){
                        randomBeerMaltContent.append("\n")
                    }
                }
            }
            val tempBuff1 = beers[0].ingredients?.hops?.distinct()
            if (tempBuff1 != null){
                for (i in tempBuff1.indices){
                    randomBeerHopsContent.append(tempBuff1[i]?.name ?: "--malt is missing--")
                    if (i < tempBuff1.size - 1){
                        randomBeerHopsContent.append("\n")
                    }
                }
            }

            Glide.with(toolbarPic.context)
                .load(beers[0].photo)
                .placeholder(R.drawable.recycler_view_placeholder)
                .error(R.drawable.recycler_view_placeholder)
                .into(toolbarPic)

        }
    }

    private fun showProgress() {
        with(binding) {
            errorContent.isVisible = false
            contentContainer.isVisible = false

            progressBar.isVisible = true
        }
    }

}







