package com.example.beerpunkapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerpunkapp.domain.usecase.GetBeerByIdUseCase
import com.example.beerpunkapp.domain.usecase.GetRandomBeerUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val idUseCase: GetBeerByIdUseCase,
    private val randomUseCase: GetRandomBeerUseCase
) : ViewModel() {

    private val _state: MutableLiveData<DetailsState> = MutableLiveData(DetailsState.Initial)
    val state: LiveData<DetailsState> = _state

    fun loadData(id: Long) {
        viewModelScope.launch {
            // Тут изменяет состояние для отображения лоадера
            _state.value = DetailsState.Loading

            try {
                if (id > 0){
                    _state.value = DetailsState.Content(idUseCase(id))
                }
                else {
                    _state.value = DetailsState.Content(randomUseCase())
                }
                // Тут изменяет состояние для отображения займа: передаем полученный из сети займ

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // Тут изменяет состояние для отображения ошибки: передаем полученное сообщение об ошибки
                _state.value = DetailsState.Error(e.message.toString())
            }
        }
    }
}