package com.example.beerpunkapp.utilits

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewLoadMoreScroll : RecyclerView.OnScrollListener {

    /*private var visibleThreshold = 1*/
    private lateinit var onLoadMoreListener: OnLoadMoreListener
    private var isLoading: Boolean = false
    /*private var lastVisibleItem: Int = 0
    private var totalItemCount: Int = 0*/
    private var layoutManager: RecyclerView.LayoutManager
    private val TAG = "scrollListener" //todo

    fun setLoaded() {
        isLoading = false
    }

    fun setOnLoadMoreListener(listener: OnLoadMoreListener) {
        this.onLoadMoreListener = listener
    }

    constructor(layoutManager: LinearLayoutManager) {
        this.layoutManager = layoutManager
    }


    /*override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (dy <= 0) return

    }*/

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (!recyclerView.canScrollVertically(1)) {
            Log.v(TAG,"on bottom") //todo
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