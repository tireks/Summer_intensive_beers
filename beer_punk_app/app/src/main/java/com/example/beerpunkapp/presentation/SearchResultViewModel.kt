package com.example.beerpunkapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerpunkapp.domain.usecase.GetBeersBySearchUseCase
import kotlinx.coroutines.launch

class SearchResultViewModel (
    private var useCase: GetBeersBySearchUseCase
) : ViewModel() {

    private val _state = MutableLiveData<SearchResultState>(SearchResultState.Initial)
    val state: LiveData<SearchResultState> = _state


    fun loadData (paramArray: Array<String>){
        viewModelScope.launch {
            var yeast : String? = ""
            var ebcGt : String? = ""
            var food : String? = ""

            _state.value = SearchResultState.Loading

            yeast = paramArray[0].ifEmpty {
                null
            }
            ebcGt = paramArray[1].ifEmpty {
                null
            }
            food = paramArray[2].ifEmpty {
                null
            }
            try {
                val beers = useCase(yeast, ebcGt, food)
                // Тут изменяет состояние для отображения списка займов: передаем полученный список займов из сети
                _state.value = SearchResultState.Content(beers)
            } catch (e: Exception) {
                // Тут изменяет состояние для отображения ошибки: передаем полученное сообщение об ошибки
                _state.value = SearchResultState.Error(e.localizedMessage.orEmpty())
            }
        }


    }
}