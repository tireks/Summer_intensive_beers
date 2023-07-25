package com.example.beerpunkapp.screen

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import by.dzmitry_lakisau.month_year_picker_dialog.MonthYearPickerDialog
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.FragmentSearchFormBinding
import com.example.beerpunkapp.presentation.SearchFormState
import com.example.beerpunkapp.presentation.SearchFormViewModel
import com.example.beerpunkapp.utilits.*
import kotlinx.coroutines.launch


class SearchFormFragment : BaseFragment<FragmentSearchFormBinding>() {
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSearchFormBinding {
        return FragmentSearchFormBinding.inflate(inflater, container, false)
    }

    private val viewModel: SearchFormViewModel by viewModels {
        viewModelFactory {
            initializer {
                SearchFormViewModel()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner, ::handleState)
        showForm()
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setSupportActionBar(binding.mainToolbar)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun handleState(state: SearchFormState) {
        when (state){
            is SearchFormState.UnlockedSearch -> unlockSearch()
            is SearchFormState.LockedSearch -> lockSearch()
        }
    }

    private fun lockSearch() {
        binding.searchButton.isEnabled = false
    }

    private fun unlockSearch() {
        binding.searchButton.isEnabled = true
    }

    private fun showForm() {
        with(binding){
            formContainer.isVisible = true
            binding.searchButton.isEnabled = true
            searchButton.setOnClickListener { handleSearchButtonClick() }
            dateBeforeEditText.setOnClickListener{handleDatePickClick(it as EditText)}
            dateAfterEditText.setOnClickListener{handleDatePickClick(it as EditText)}
            dateBeforeEditText.addTextChangedListener(AppTextWatcher{
                handleClearButtonsActivator(dateBeforeEditText, it)
                validateData(AppBlockablePalettes.Date)
            })
            dateAfterEditText.addTextChangedListener(AppTextWatcher{
                handleClearButtonsActivator(dateAfterEditText, it)
                validateData(AppBlockablePalettes.Date)
            })
            abvGreaterEditText.addTextChangedListener(AppTextWatcher { validateData(AppBlockablePalettes.Abv) })
            abvLessEditText.addTextChangedListener(AppTextWatcher { validateData(AppBlockablePalettes.Abv) })
            ibuGreaterEditText.addTextChangedListener(AppTextWatcher { validateData(AppBlockablePalettes.Ibu) })
            ibuLessEditText.addTextChangedListener(AppTextWatcher { validateData(AppBlockablePalettes.Ibu) })
            ebcGreaterEditText.addTextChangedListener(AppTextWatcher { validateData(AppBlockablePalettes.Ebc) })
            ebcLessEditText.addTextChangedListener(AppTextWatcher { validateData(AppBlockablePalettes.Ebc) })
            dateBeforeClearButton.setOnClickListener { dateBeforeEditText.text.clear() }
            dateAfterClearButton.setOnClickListener { dateAfterEditText.text.clear() }
        }
    }

    private fun validateData(palette: AppBlockablePalettes){
        var editTextLeft: EditText
        var editTextRight: EditText
        lifecycleScope.launch {
            when(palette){
                AppBlockablePalettes.Abv -> {
                    editTextLeft = binding.abvGreaterEditText
                    editTextRight = binding.abvLessEditText
                }
                AppBlockablePalettes.Ibu -> {
                    editTextLeft = binding.ibuGreaterEditText
                    editTextRight = binding.ibuLessEditText
                }
                AppBlockablePalettes.Ebc -> {
                    editTextLeft = binding.ebcGreaterEditText
                    editTextRight = binding.ebcLessEditText
                }
                AppBlockablePalettes.Date -> {
                    validateDates()
                    return@launch
                }
            }
            if (viewModel.incorrectNumerics(
                    editTextLeft.text.toString(),
                    editTextRight.text.toString(),
                    palette
                )
            ) {
                turnRed(editTextLeft,editTextRight)
            } else {
                turnNormal(editTextLeft,editTextRight)
            }
        }
    }

    private fun validateDates() {
        lifecycleScope.launch {
            if (viewModel.incorrectDates(
                    binding.dateAfterEditText.text.toString(),
                    binding.dateBeforeEditText.text.toString()
                )
            ) {
                turnRed(binding.dateAfterEditText,binding.dateBeforeEditText)
            } else {
                turnNormal(binding.dateAfterEditText,binding.dateBeforeEditText)
            }
        }

    }

    private fun handleClearButtonsActivator(view: View, text: Editable) {
        lifecycleScope.launch{
            with (binding){
                if (view == dateBeforeEditText){
                    dateBeforeClearButton.isVisible = text.isNotEmpty()
                } else {
                    dateAfterClearButton.isVisible = text.isNotEmpty()
                }
            }
        }

    }

    private fun handleDatePickClick(view: EditText) {
        val dialog = context?.let {
            MonthYearPickerDialog.Builder(context = it,
                themeResId = R.style.Style_MonthYearPickerDialog_Purple,
                onDateSetListener = { year, month ->
                   dateAdapter(view, year, month)
                }
            ).build()
        }
        dialog?.show()
    }

    private fun dateAdapter(view: EditText, year: Int, month: Int){
        val template: String = if(month < 10){
            resources.getString(R.string.search_form_date_text_variable_zero)
        } else {
            resources.getString(R.string.search_form_date_text_variable)
        }
        view.setText(
            String.format(
                template,
                month + 1,
                year
            )
        )
    }

    private fun handleSearchButtonClick() {
        val paramList = arrayListOf<String>()
        getEditTextMap().forEach { (appEditTextEnums, editText) ->
            if (editText.text.isNotEmpty()){
                paramList.add(appEditTextEnums.fieldTag + "|" + editText.text)
            }
        }
       mainActivity.openSearchResult(paramList.toTypedArray())
    }

    private fun turnRed(editText: EditText, editText1: EditText){
        editText.setBackgroundColor(resources.getColor(R.color.search_form_editText_red, mainActivity.theme))
        editText1.setBackgroundColor(resources.getColor(R.color.search_form_editText_red, mainActivity.theme))
    }

    private fun turnNormal(editText: EditText, editText1: EditText){
        editText.setBackgroundColor(resources.getColor(R.color.search_form_editText_purple, mainActivity.theme))
        editText1.setBackgroundColor(resources.getColor(R.color.search_form_editText_purple, mainActivity.theme))
    }

    private fun getEditTextMap(): Map<AppEditText, EditText>{
        return mapOf(
                AppEditText.AbvGt to binding.abvGreaterEditText,
                AppEditText.AbvLt to binding.abvLessEditText,
                AppEditText.IbuGt to binding.ibuGreaterEditText,
                AppEditText.IbuLt to binding.ibuLessEditText,
                AppEditText.EbcGt to binding.ebcGreaterEditText,
                AppEditText.EbcLt to binding.ebcLessEditText,
                AppEditText.BeerName to binding.nameEditText,
                AppEditText.Yeast to binding.yeastEditText,
                AppEditText.BrewedBefore to binding.dateBeforeEditText,
                AppEditText.BrewedAfter to binding.dateAfterEditText,
                AppEditText.Hops to binding.hopsEditText,
                AppEditText.Malt to binding.maltEditText,
                AppEditText.Food to binding.foodEditText
        )
    }
}