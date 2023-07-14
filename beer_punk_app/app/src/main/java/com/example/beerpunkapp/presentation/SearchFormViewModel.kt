package com.example.beerpunkapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SearchFormViewModel () : ViewModel(){
    private val _state: MutableLiveData<SearchFormState> = MutableLiveData(SearchFormState.Initial)
    val state: LiveData<SearchFormState> = _state
}