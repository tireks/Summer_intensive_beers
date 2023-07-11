package com.example.beerpunkapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerpunkapp.domain.usecase.GetBeerByIdUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class DetailsViewModel(
    private val useCase: GetBeerByIdUseCase,
) : ViewModel() {

    private val _state: MutableLiveData<DetailsState> = MutableLiveData(DetailsState.Initial)
    val state: LiveData<DetailsState> = _state

    fun loadData(id: Long) {
        viewModelScope.launch {
            // Тут изменяет состояние для отображения лоадера
            _state.value = DetailsState.Loading

            try {
                val loan = useCase(id)
                // Тут изменяет состояние для отображения займа: передаем полученный из сети займ
                _state.value = DetailsState.Content(loan)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // Тут изменяет состояние для отображения ошибки: передаем полученное сообщение об ошибки
                _state.value = DetailsState.Error(e.message.toString())
            }
        }
    }
}