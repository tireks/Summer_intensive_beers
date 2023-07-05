package com.example.beerpunkapp.ui.fragments

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.FragmentStartBinding
import com.example.beerpunkapp.utilits.showToast


class StartFragment : BaseFragment<FragmentStartBinding>(){
    override fun inflateViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentStartBinding {
        return FragmentStartBinding.inflate(inflater, container, false)
    }

    override fun onResume() {
        super.onResume()
        setupMenu()
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.start_action_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.start_menu_btn_search -> {
                        showToast("searchPlaceHolder")
                        true
                    }
                    R.id.start_menu_btn_random -> {
                        showToast("randomizePlaceHolder")
                        true
                    }

                    else -> return false
                }
            }

        }, viewLifecycleOwner, androidx.lifecycle.Lifecycle.State.RESUMED)
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        activity?.menuInflater?.inflate(R.menu.start_action_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.start_menu_btn_search -> {
                showToast("searchPlaceHolder")
                true
            }
            R.id.start_menu_btn_random -> {
                showToast("randomizePlaceHolder")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/
}