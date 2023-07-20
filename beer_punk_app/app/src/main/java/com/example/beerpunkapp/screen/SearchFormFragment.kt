package com.example.beerpunkapp.screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import by.dzmitry_lakisau.month_year_picker_dialog.MonthYearPickerDialog
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.FragmentSearchFormBinding
import com.example.beerpunkapp.presentation.SearchFormState
import com.example.beerpunkapp.presentation.SearchFormViewModel
import com.example.beerpunkapp.utilits.AppEditText
import com.example.beerpunkapp.utilits.mainActivity
import com.example.beerpunkapp.utilits.showToast


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
    }

    override fun onResume() {
        super.onResume()
        mainActivity.setSupportActionBar(binding.mainToolbar)
        mainActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun handleState(state: SearchFormState) {
        when (state){
            is SearchFormState.Initial -> showForm()
        }
    }

    private fun showForm() {
        with(binding){
            formContainer.isVisible = true
            searchButton.setOnClickListener { handleSearchButtonClick() }
            searchFormDateBeforeEditText.setOnClickListener{handleDatePickClick(searchFormDateBeforeEditText)}
            searchFormDateAfterEditText.setOnClickListener {handleDatePickClick(searchFormDateAfterEditText)}
        }
    }

    private fun handleDatePickClick(view: View?) {

        val dialog = context?.let {
            MonthYearPickerDialog.Builder(
                context = it,
                themeResId = R.style.Style_MonthYearPickerDialog_Purple,
                onDateSetListener = { year, month ->
                    when (view){
                        binding.searchFormDateBeforeEditText -> {
                            binding.searchFormDateBeforeEditText.setText(String.format(resources.getString(R.string.search_form_date_text_variable), month+1, year))
                        }
                        binding.searchFormDateAfterEditText -> {
                            binding.searchFormDateAfterEditText.setText(String.format(resources.getString(R.string.search_form_date_text_variable), month+1, year))

                        }
                    }
                }
            )
                .build()
        }
        dialog?.show()
    }

    private fun handleSearchButtonClick() {
        val paramList = arrayListOf<String>()
        getEditTextMap().forEach { (appEditTextEnums, editText) ->
            if (editText.text.isNotEmpty()){
                paramList.add(appEditTextEnums.fieldTag + "|" + editText.text)
            }
        }
        showToast("asd")
       mainActivity.openSearchResult(paramList.toTypedArray())
    }

    private fun getEditTextMap(): Map<AppEditText, EditText>{
// todo поля реально должно быть два, на каждое больше-меньше, и сканить надо оба поля,
//  но нужно проверять, не нарушен ли ввод, функции проверки должны быть реализованы в вьюмодели
//  и при ошибке дергать новый стейт ошибки(пусть какоенибудь диалоговое окно дергается)
        return mapOf(
                AppEditText.AbvGt to binding.searchFormAbvGreaterEditText,
                AppEditText.AbvLt to binding.searchFormAbvLessEditText,
                AppEditText.IbuGt to binding.searchFormIbuGreaterEditText,
                AppEditText.IbuLt to binding.searchFormIbuLessEditText,
                AppEditText.EbcGt to binding.searchFormEbcGreaterEditText,
                AppEditText.EbcLt to binding.searchFormEbcLessEditText,
                AppEditText.BeerName to binding.searchFormNameEditText,
                AppEditText.Yeast to binding.searchFormYeastEditText,
                AppEditText.BrewedBefore to binding.searchFormDateBeforeEditText,
                AppEditText.BrewedAfter to binding.searchFormDateAfterEditText,
                AppEditText.Hops to binding.searchFormHopsEditText,
                AppEditText.Malt to binding.searchFormMaltEditText,
                AppEditText.Food to binding.searchFormFoodEditText
        )
    }



}