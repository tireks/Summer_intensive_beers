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

    fun loadData (parameters: Array<String>){
        viewModelScope.launch {
            val localState = _state.value
            if (localState is SearchResultState.Content && localState.items.isNotEmpty()){
                _state.value = SearchResultState.Content(localState.items, true)
            } else {
                prepareParametersMap(parameters)
                try {
                    val beers = useCase(parametersMap)
                    if (beers.isEmpty()){
                        _state.value = SearchResultState.EmptyContent
                    }
                    _state.value = SearchResultState.Content(beers, true)
                } catch (e: Exception) {
                    // Тут изменяет состояние для отображения ошибки: передаем полученное сообщение об ошибки
                    _state.value = SearchResultState.Error(e.localizedMessage.orEmpty())
                }
            }
        }
    }

    fun expandData(){
        viewModelScope.launch {
            var expandAvailabilityFlag = true
            val localState = _state.value
            if (localState is SearchResultState.Content){
                page++
                var tempList = localState.items
                try {
                    parametersMap["page"] = page.toString()
                    val beers = useCase(parametersMap)
                    if (beers.isEmpty()){
                        //todo сюда надо бахнуть какое-нибудь изменение какого-нибудь флага,
                        // чтобы больше не дергал экспанд
                        expandAvailabilityFlag = false
                    }
                    // Тут изменяет состояние для отображения списка займов: передаем полученный список займов из сети
                    _state.value = SearchResultState.Content(tempList + beers, expandAvailabilityFlag)
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

//todo нужно сделать хранение всего списка в стейте контента(сейчас просто новая часть хранится),
// следовательно никикой конкатенации листа в адаптере, просто отображение нового списка
// (это вроде должно работать, ресайклер вроде неплохо работает со своим положением в списке),
// и еще важно посмотреть ту залупу с апдейтом лоадинг бара, чтобы при показе списка заново,
// а не конкатенации, все работало корректно (по идее и так должно работать, но нужно проверить)