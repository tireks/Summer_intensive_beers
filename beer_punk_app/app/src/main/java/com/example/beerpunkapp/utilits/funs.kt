package com.example.beerpunkapp.utilits

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment, dataContainerViewId: Int) {
    supportFragmentManager.beginTransaction()
        .addToBackStack(null)
        .replace(dataContainerViewId, fragment).commit()
}