package com.example.beerpunkapp.presentation

import com.example.beerpunkapp.domain.entity.Beer

sealed interface RandomBeerState{
    object Initial : RandomBeerState

    object Loading : RandomBeerState

    data class Content(val beers: List<Beer>) : RandomBeerState

    data class Error(val msg: String) : RandomBeerState
}