package com.example.beerpunkapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

class SearchFormViewModel () : ViewModel(){
    private val _state: MutableLiveData<SearchFormState> = MutableLiveData(SearchFormState.UnlockedSearch)
    val state: LiveData<SearchFormState> = _state

    suspend fun uncorrectDates(stringDateAfter: String, stringDateBefore: String): Boolean{
        val blockStatus = viewModelScope.async {
            if (stringDateAfter.isEmpty() || stringDateBefore.isEmpty()){
                return@async false
            } else {
                val dateAfter = convertToDate(stringDateAfter)
                val dateBefore = convertToDate(stringDateBefore)
                if (dateAfter.isBefore(dateBefore) || dateAfter.isEqual(dateBefore)){
                    return@async false
                } else {
                    _state.value = SearchFormState.LockedSearch
                    return@async true
                }
            }
        }
        return blockStatus.await()
    }

    private fun convertToDate(string: String): LocalDate {
        return LocalDate.parse(
            "01-"+string.replace(".", "-"), //костыль "01-" дурацкая локалдата не хочет работать без дней
            DateTimeFormatter
                .ofPattern("dd-MM-uuuu")
                .withResolverStyle(ResolverStyle.STRICT)
        )
    }
}