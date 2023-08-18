package com.example.beerpunkapp.utilits

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLoadMoreScroll : RecyclerView.OnScrollListener {

    private lateinit var onLoadMoreListener: OnLoadMoreListener
    private var isLoading: Boolean = false
    private var layoutManager: RecyclerView.LayoutManager

    fun setLoaded() {
        isLoading = false
    }

    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        this.onLoadMoreListener = listener
    }

    constructor(layoutManager: LinearLayoutManager) {
        this.layoutManager = layoutManager
    }


    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (!recyclerView.canScrollVertically(1)) {
            if (!isLoading){
                isLoading = true
                onLoadMoreListener.onLoadMore()
            }
        }
    }
}

interface OnLoadMoreListener {
    fun onLoadMore()
}