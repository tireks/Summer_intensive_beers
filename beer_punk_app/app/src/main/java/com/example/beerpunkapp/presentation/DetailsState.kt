package com.example.beerpunkapp.presentation

import com.example.beerpunkapp.domain.entity.Beer

sealed interface DetailsState{
    object Initial : DetailsState

    object Loading : DetailsState

    data class Content(val beers: List<Beer>) : DetailsState

    data class Error(val msg: String) : DetailsState
}