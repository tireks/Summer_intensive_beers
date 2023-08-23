package com.example.beerpunkapp.presentation

import com.example.beerpunkapp.domain.entity.Beer

sealed interface SearchResultState{
    object Initial : SearchResultState

    object Loading : SearchResultState

    object EmptyContent : SearchResultState

    data class Content(val items: List<Beer>, val expandAvailable : Boolean) : SearchResultState

    data class Error(val msg: String) : SearchResultState

}