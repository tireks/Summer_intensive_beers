package com.example.beerpunkapp.screen

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.ItemBeerBinding
import com.example.beerpunkapp.databinding.ProgressLoadingBinding
import com.example.beerpunkapp.domain.entity.Beer

class SearchResultAdapter (
    private val beerClickListener: (Beer) -> Unit)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var beers: ArrayList<Beer?> = arrayListOf()
    private var loadingStatus = false
    private val TAG = "adapter" //todo

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM){
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemBeerBinding.inflate(inflater, parent, false)
            ItemReadyViewHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProgressLoadingBinding.inflate(inflater, parent, false)
            ItemLastViewHolder(binding)
        }
    }

    override fun getItemCount(): Int = beers.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder.itemViewType){
            VIEW_TYPE_ITEM -> {
                (holder as ItemReadyViewHolder).bind(beers[position], beerClickListener)
            }
            VIEW_TYPE_LAST -> {
                (holder as ItemLastViewHolder).bindEmpty()
            }
            VIEW_TYPE_LOADER -> {
                (holder as ItemLastViewHolder).bindLoader()
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (beers[position] == null) {
            if (loadingStatus){
                VIEW_TYPE_LOADER
            } else {
                VIEW_TYPE_LAST
            }
        } else {
            VIEW_TYPE_ITEM
        }
    }

    fun addData(newItems: List<Beer?>){
        if (beers.isEmpty()){
            beers.addAll(newItems)
            Log.v(TAG,"beers: " + beers.size) //todo
            notifyDataSetChanged()
        } else {
            beers.addAll(newItems)
            removeLoadingView()
            loadingStatus = false
        }
        beers.add(null)
    }

    fun getItemAtPosition(position: Int): Beer? {
        return beers[position]
    }

    fun addLoadingView(){
        // todo
        loadingStatus = true
        notifyItemChanged(beers.indexOf(null))
    }

    fun removeLoadingView() {
        if (beers.size != 0) {
            val tempIndex = beers.indexOf(null)
            beers[tempIndex] = beers[tempIndex + 1]
            beers.removeAt(tempIndex + 1)
           // beers.rep
            //notifyItemRemoved(tempIndex + 1)
            notifyItemChanged(tempIndex)
            /*beers.removeAt(tempIndex)
            notifyItemRemoved(tempIndex)*/
        }
    }

    companion object {
        const val VIEW_TYPE_LAST = 1
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADER = -1
    }
}

class ItemReadyViewHolder (
    private val binding: ItemBeerBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(beer: Beer?, beerClickListener: (Beer) -> Unit){
        if (beer != null){
            var res = itemView.context.resources
            with(binding){
                itemId.text = beer.id.toString()
                itemName.text = beer.name ?: res.getString(R.string.start_recycler_view_name_placeholder_nulldata)
                itemTag.text = beer.tags ?: res.getString(R.string.start_recycler_view_tag_placeholder_nulldata)
                itemDescription.text = beer.description ?: res.getString(R.string.start_recycler_view_description_placeholder_nulldata)
                Glide.with(itemPhoto.context)
                    .load(beer.photo)
                    .placeholder(R.drawable.recycler_view_placeholder)
                    .error(R.drawable.recycler_view_placeholder)
                    .into(itemPhoto)
            }
            itemView.setOnClickListener { beerClickListener(beer) }
        }
    }
}

class ItemLastViewHolder(
    private val binding: ProgressLoadingBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindEmpty() {
        binding.progressbar.isVisible = false
    }

    fun bindLoader() {
        binding.progressbar.isVisible = true
    }

}
