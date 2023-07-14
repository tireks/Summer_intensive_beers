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
import com.bumptech.glide.Glide
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.FragmentDetailsBinding
import com.example.beerpunkapp.domain.entity.Beer
import com.example.beerpunkapp.domain.usecase.GetBeerByIdUseCase
import com.example.beerpunkapp.domain.usecase.GetRandomBeerUseCase
import com.example.beerpunkapp.presentation.DetailsState
import com.example.beerpunkapp.presentation.DetailsViewModel
import com.example.beerpunkapp.utilits.mainActivity
import java.sql.Types.NULL


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(){

    private val viewModel: DetailsViewModel by viewModels {
        viewModelFactory {
            initializer {
                DetailsViewModel(GetBeerByIdUseCase(mainActivity.repository),
                                GetRandomBeerUseCase(mainActivity.repository))
            }
        }
    }

    private val args: DetailsFragmentArgs by navArgs()

    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsBinding {
        return FragmentDetailsBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        beerData()
    }

    private fun beerData() {
        viewModel.loadData(args.beerId)
    }

    private fun handleState(state: DetailsState) {
        when (state) {
            is DetailsState.Initial    -> Unit
            is DetailsState.Loading    -> showProgress()
            is DetailsState.Content -> showContent(state.beers)
            is DetailsState.Error   -> showError(state.msg)
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

            detailsFirstBrewedContent.text = beers[0].brew_date ?: getString(R.string.details_id_placeholder_nulldata)
            detailsDescriptionContent.text = beers[0].description ?: getString(R.string.details_description_placeholder_nulldata)
            detailsTagContent.text = beers[0].tags ?: getString(R.string.details_tag_placeholder_nulldata)
            //detailsTagContent.text = beers[0].ingredients?.yeast ?: "--tag is missing--"
            detailsAbvContent.text = beers[0].abv ?: getString(R.string.details_nums_placeholder_nulldata)
            detailsEbcContent.text = beers[0].ebc ?: getString(R.string.details_nums_placeholder_nulldata)
            detailsIbuContent.text = beers[0].ibu ?: getString(R.string.details_nums_placeholder_nulldata)
            detailsSrmContent.text = beers[0].srm ?: getString(R.string.details_nums_placeholder_nulldata)
            detailsToolbar.title = beers[0].name ?: getString(R.string.details_name_placeholder_nulldata)
            detailsYeastContent.text = beers[0].ingredients?.yeast ?: getString(R.string.details_yeast_placeholder_nulldata)
            for (i in beers[0].food.indices){
                detailsFoodContent.append(beers[0].food[i])
                if (i < beers[0].food.size - 1){
                    detailsFoodContent.append("\n")
                }
            }
            val tempBuff = beers[0].ingredients?.malt
            if (tempBuff != null){
                for (i in tempBuff.indices){
                    detailsMaltContent.append(tempBuff[i]?.name ?: "--malt is missing--")
                    if (i < tempBuff.size - 1){
                        detailsMaltContent.append("\n")
                    }
                }
            }
            val tempBuff1 = beers[0].ingredients?.hops?.distinct()
            if (tempBuff1 != null){
                for (i in tempBuff1.indices){
                    detailsHopsContent.append(tempBuff1[i]?.name ?: "--malt is missing--")
                    if (i < tempBuff1.size - 1){
                        detailsHopsContent.append("\n")
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

    override fun onResume() {
        super.onResume()
        //mainActivity.supportActionBar?.hide()
        mainActivity.setSupportActionBar(binding.detailsToolbar)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}