package com.example.beerpunkapp.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.FragmentDetailsBinding
import com.example.beerpunkapp.databinding.FragmentSearchFormBinding
import com.example.beerpunkapp.domain.usecase.GetAllBeersUseCase
import com.example.beerpunkapp.presentation.SearchFormState
import com.example.beerpunkapp.presentation.SearchFormViewModel
import com.example.beerpunkapp.presentation.StartListState
import com.example.beerpunkapp.presentation.StartViewModel
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
        //binding.startRecyclerView.adapter = StartAdapter(::handleBeerClick)
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
        }
    }

    private fun handleSearchButtonClick() {
        val paramList = arrayListOf<String>()
        getEditTextMap().forEach { (appEditTextEnums, editText) ->
            paramList.add(appEditTextEnums.fieldTag + "|" + editText.text.ifEmpty { "" })
        }
        /*with(binding){
            if (!search1.text.isEmpty()){
                paramList.add(search1.text.toString())
            }
            else {
                paramList.add("")
            }
            if (!search2.text.isEmpty()){
                paramList.add(search2.text.toString())
            } else {
                paramList.add("")
            }
            if (!search3.text.isEmpty()){
                paramList.add(search3.text.toString())
            } else{
                paramList.add("")
            }
        }*/
        showToast("asd")
       // mainActivity.openSearchResult(paramList.toTypedArray())
    }

    private fun getEditTextMap(): Map<AppEditText, EditText>{
        fun abvControl() : Pair<AppEditText, EditText>{
            with(binding){
                if (searchFormAbvToggleGroup.checkedButtonId == searchFormAbvToggleButtonGreater.id){
                    return AppEditText.AbvGt to binding.searchFormAbvEditText
                } else{
                    return AppEditText.AbvLt to binding.searchFormAbvEditText
                }
            }
        }
// todo поля реально должно быть два, на каждое больше-меньше, и сканить надо оба поля,
//  но нужно проверять, не нарушен ли ввод, функции проверки должны быть реализованы в вьюмодели
//  и при ошибке дергать новый стейт ошибки(пусть какоенибудь диалоговое окно дергается)

        return mapOf(
                abvControl(),
                AppEditText.IbuGt to binding.searchFormIbuEditText,
                AppEditText.IbuLt to binding.searchFormIbuEditText,
                AppEditText.EbcGt to binding.searchFormEbcEditText,
                AppEditText.EbcLt to binding.searchFormEbcEditText,
                AppEditText.BeerName to binding.searchFormNameEditText,
                AppEditText.Yeast to binding.searchFormYeastEditText,
                AppEditText.BrewedBefore to binding.searchFormDateEditText,
                AppEditText.BrewedAfter to binding.searchFormDateEditText,
                AppEditText.Hops to binding.searchFormHopsEditText,
                AppEditText.Malt to binding.searchFormMaltEditText,
                AppEditText.Food to binding.searchFormFoodEditText
        )
    }



}