package com.example.beerpunkapp.presentation

import com.example.beerpunkapp.domain.entity.Beer

sealed interface SearchResultState{
    object Initial : SearchResultState

    object Loading : SearchResultState

    data class Content(val items: List<Beer>) : SearchResultState

    data class Error(val msg: String) : SearchResultState

}