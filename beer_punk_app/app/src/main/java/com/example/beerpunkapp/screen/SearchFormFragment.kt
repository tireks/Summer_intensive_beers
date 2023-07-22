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
import com.example.beerpunkapp.utilits.AppEditText
import com.example.beerpunkapp.utilits.AppTextWatcher
import com.example.beerpunkapp.utilits.mainActivity
import com.example.beerpunkapp.utilits.showToast
import kotlinx.coroutines.launch
import java.time.Month


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
            is SearchFormState.UnlockedSearch -> binding.searchButton.isEnabled = true
            is SearchFormState.LockedSearch -> binding.searchButton.isEnabled = false
        }
    }

    private fun showForm() {
        with(binding){
            formContainer.isVisible = true
            binding.searchButton.isEnabled = true
            searchButton.setOnClickListener { handleSearchButtonClick() }
            searchFormDateBeforeEditText.setOnClickListener{handleDatePickClick(it)}
            searchFormDateAfterEditText.setOnClickListener{handleDatePickClick(it)}
            searchFormDateBeforeEditText.addTextChangedListener(AppTextWatcher{
                handleClearButtonsActivator(searchFormDateBeforeEditText, it)
                validateDates()
            })
            searchFormDateAfterEditText.addTextChangedListener(AppTextWatcher{
                handleClearButtonsActivator(searchFormDateAfterEditText, it)
                validateDates()
            })
            searchFormDateBeforeClearButton.setOnClickListener { searchFormDateBeforeEditText.text.clear() }
            searchFormDateAfterClearButton.setOnClickListener { searchFormDateAfterEditText.text.clear() }
        }
    }

    private fun validateDates() {
        lifecycleScope.launch {
            if (viewModel.uncorrectDates(
                    binding.searchFormDateAfterEditText.text.toString(),
                    binding.searchFormDateBeforeEditText.text.toString()
                )
            ) {
                showToast("alaaaaaarm")
            }
        }

    }

    private fun handleClearButtonsActivator(view: View, text: Editable) {
        lifecycleScope.launch{
            with (binding){
                if (view == searchFormDateBeforeEditText){
                    searchFormDateBeforeClearButton.isVisible = text.isNotEmpty()
                } else {
                    searchFormDateAfterClearButton.isVisible = text.isNotEmpty()
                }
            }
        }

    }

    private fun handleDatePickClick(view: View) {
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

    private fun dateAdapter(view: View, year: Int, month: Int){
        val template: String = if(month < 10){
            resources.getString(R.string.search_form_date_text_variable_zero)
        } else {
            resources.getString(R.string.search_form_date_text_variable)
        }
        when (view) {
            binding.searchFormDateBeforeEditText -> {
                binding.searchFormDateBeforeEditText.setText(
                    String.format(
                        template,
                        month + 1,
                        year
                    )
                )
            }
            binding.searchFormDateAfterEditText -> {
                binding.searchFormDateAfterEditText.setText(
                    String.format(
                        template,
                        month + 1,
                        year
                    )
                )
            }
        }
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