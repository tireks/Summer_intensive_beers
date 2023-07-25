package com.example.beerpunkapp.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beerpunkapp.utilits.AppBlockablePalettes
import kotlinx.coroutines.async
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.ResolverStyle

class SearchFormViewModel () : ViewModel(){
    private val _state: MutableLiveData<SearchFormState> = MutableLiveData(SearchFormState.UnlockedSearch)
    val state: LiveData<SearchFormState> = _state
    private val blockedPalettes = mutableListOf<AppBlockablePalettes>()

    suspend fun incorrectDates(stringDateLeft: String, stringDateRight: String): Boolean {
        val blockStatus = viewModelScope.async {
            if (stringDateLeft.isEmpty() || stringDateRight.isEmpty()) {
                checkPaletteRemove(AppBlockablePalettes.Date)
                return@async false
            } else {
                val dateLeft = convertToDate(stringDateLeft)
                val dateRight = convertToDate(stringDateRight)
                if (dateLeft.isBefore(dateRight) || dateLeft.isEqual(dateRight)) {
                    checkPaletteRemove(AppBlockablePalettes.Date)
                    return@async false
                } else {
                    checkPaletteAdd(AppBlockablePalettes.Date)
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

    suspend fun incorrectNumerics(stringNumLeft: String, stringNumRight: String, palette: AppBlockablePalettes): Boolean{
        val blockStatus = viewModelScope.async {
            if (stringNumLeft.isEmpty() || stringNumRight.isEmpty()){
                checkPaletteRemove(palette)
                return@async false
            } else {
                if (stringNumRight.toDouble() >= stringNumLeft.toDouble()){
                    checkPaletteRemove(palette)
                    return@async false
                } else {
                    checkPaletteAdd(palette)
                    return@async true
                }
            }
        }
        return blockStatus.await()
    }

    private fun checkPaletteAdd(palette: AppBlockablePalettes) {
        if (!blockedPalettes.contains(palette)){
            blockedPalettes.add(palette)
            updateState()
        }
    }

    private fun checkPaletteRemove(palette: AppBlockablePalettes) {
        if (blockedPalettes.contains(palette)){
            blockedPalettes.remove(palette)
            updateState()
        }
    }

    private fun updateState() {
        if (blockedPalettes.isEmpty()){
            _state.value = SearchFormState.UnlockedSearch
        } else {
            _state.value = SearchFormState.LockedSearch
        }
    }
}