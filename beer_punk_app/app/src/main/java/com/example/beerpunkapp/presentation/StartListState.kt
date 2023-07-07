package com.example.beerpunkapp.presentation

import com.example.beerpunkapp.domain.entity.Beer

sealed interface StartListState{
    object Initial : StartListState

    object Loading : StartListState

    data class Content(val items: List<Beer>, val page: Long) : StartListState

    data class Error(val msg: String) : StartListState
}