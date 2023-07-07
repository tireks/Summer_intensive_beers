package com.example.beerpunkapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerpunkapp.domain.usecase.GetAllBeersUseCase
import kotlinx.coroutines.launch

class StartViewModel(
    private val getAllUseCase: GetAllBeersUseCase
) : ViewModel(){
    private val _state = MutableLiveData<StartListState>(StartListState.Initial)
    val state: LiveData<StartListState> = _state
    private var page: Long = 1

    fun loadData(){
        viewModelScope.launch {
            // Тут изменяет состояние для отображения лоадера
            _state.value = StartListState.Loading
            try {
                val loans = getAllUseCase(page)
                // Тут изменяет состояние для отображения списка займов: передаем полученный список займов из сети
                _state.value = StartListState.Content(loans, page)
            } catch (e: Exception) {
                // Тут изменяет состояние для отображения ошибки: передаем полученное сообщение об ошибки
                _state.value = StartListState.Error(e.localizedMessage.orEmpty())
            }
        }
    }

    fun nextButtonHandler(){
        page++
        loadData()
    }

    fun prevButtonHandler(){
        if (page > 1){
            page--
            loadData()
        }
    }
}