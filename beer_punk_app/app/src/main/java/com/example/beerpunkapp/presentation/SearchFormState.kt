package com.example.beerpunkapp.presentation

sealed interface SearchFormState{
    object UnlockedSearch : SearchFormState
    object LockedSearch : SearchFormState
}