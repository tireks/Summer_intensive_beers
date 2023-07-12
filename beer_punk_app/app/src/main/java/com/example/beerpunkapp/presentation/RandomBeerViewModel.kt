package com.example.beerpunkapp.presentation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerpunkapp.domain.usecase.GetAllBeersUseCase
import com.example.beerpunkapp.domain.usecase.GetRandomBeerUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch

class RandomBeerViewModel(
    private val getRandomBeerUseCase: GetRandomBeerUseCase
) : ViewModel() {

    private val _state: MutableLiveData<RandomBeerState> = MutableLiveData(RandomBeerState.Initial)
    val state: LiveData<RandomBeerState> = _state

    fun loadData() {
        viewModelScope.launch {
            // Тут изменяет состояние для отображения лоадера
            _state.value = RandomBeerState.Loading

            try {
                val beer = getRandomBeerUseCase()
                // Тут изменяет состояние для отображения займа: передаем полученный из сети займ
                _state.value = RandomBeerState.Content(beer)
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                // Тут изменяет состояние для отображения ошибки: передаем полученное сообщение об ошибки
                _state.value = RandomBeerState.Error(e.message.toString())
            }
        }
    }
}