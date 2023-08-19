package com.example.beerpunkapp.presentation

import android.util.Log
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
    private var page = 1
    private var parametersMap : MutableMap<String, String> = mutableMapOf()
    private val TAG = "viewModel" //todo

    fun loadData (parameters: Array<String>){
        viewModelScope.launch {
            prepareParametersMap(parameters)
            try {
                Log.v(TAG,"trying usecase on load") //todo
                val beers = useCase(parametersMap)
                if (beers.isEmpty()){
                    _state.value = SearchResultState.EmptyContent
                    Log.v(TAG,"empty answer on load") //todo
                }
                _state.value = SearchResultState.Content(beers)
                Log.v(TAG,"contenting on load") //todo
            } catch (e: Exception) {
                // Тут изменяет состояние для отображения ошибки: передаем полученное сообщение об ошибки
                _state.value = SearchResultState.Error(e.localizedMessage.orEmpty())
            }
        }
    }

    fun expandData(){
        viewModelScope.launch {
            val localState = _state.value
            if (localState is SearchResultState.Content){
                page++
                var tempList = localState.items
                try {
                    parametersMap["page"] = page.toString()
                    Log.v(TAG,"trying usecase on expand") //todo
                    val beers = useCase(parametersMap)
                    if (beers.isEmpty()){
                        Log.v(TAG,"empty answer on expand") //todo
                    }
                    // Тут изменяет состояние для отображения списка займов: передаем полученный список займов из сети
                    Log.v(TAG,"contenting on expand") //todo
                    _state.value = SearchResultState.Content(beers)
                } catch (e: Exception) {
                    // Тут изменяет состояние для отображения ошибки: передаем полученное сообщение об ошибки
                    _state.value = SearchResultState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    private fun prepareParametersMap(paramArray: Array<String>){
        for (i in paramArray.indices){
            val separatorIndex = paramArray[i].indexOf("|")
            parametersMap[paramArray[i].substring(0, separatorIndex)] = paramArray[i].substring(separatorIndex + 1)
        }
    }

}