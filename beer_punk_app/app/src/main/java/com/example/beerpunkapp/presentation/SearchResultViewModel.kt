package com.example.beerpunkapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerpunkapp.domain.usecase.GetBeersBySearchUseCase
import com.example.beerpunkapp.utilits.showToast
import kotlinx.coroutines.launch

class SearchResultViewModel (
    private var useCase: GetBeersBySearchUseCase
) : ViewModel() {

    private val _state = MutableLiveData<SearchResultState>(SearchResultState.Initial)
    val state: LiveData<SearchResultState> = _state


    fun loadData (paramArray: Array<String>){
        viewModelScope.launch {
            val parametersMap = mutableMapOf<String, String>()
            for (i in paramArray.indices){
                val separatorIndex = paramArray[i].indexOf("|")
                parametersMap[paramArray[i].substring(0, separatorIndex)] = paramArray[i].substring(separatorIndex + 1)
            }
            try {
                val beers = useCase(parametersMap)
                if (beers.isEmpty()){
                    _state.value = SearchResultState.EmptyContent
                }
                // Тут изменяет состояние для отображения списка займов: передаем полученный список займов из сети
                _state.value = SearchResultState.Content(beers)
            } catch (e: Exception) {
                // Тут изменяет состояние для отображения ошибки: передаем полученное сообщение об ошибки
                _state.value = SearchResultState.Error(e.localizedMessage.orEmpty())
            }
        }


    }
}