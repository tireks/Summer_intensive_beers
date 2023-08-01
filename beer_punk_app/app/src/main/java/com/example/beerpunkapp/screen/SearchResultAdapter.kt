package com.example.beerpunkapp.screen

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM){
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemBeerBinding.inflate(inflater, parent, false)
            ItemReadyViewHolder(binding)
        } else {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ProgressLoadingBinding.inflate(inflater, parent, false)
            LoadingViewHolder(binding)
        }
        /*val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerBinding.inflate(inflater, parent, false)
        return ItemReadyViewHolder(binding)*/
    }

    override fun getItemCount(): Int = beers.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.itemViewType == VIEW_TYPE_ITEM){
            (holder as ItemReadyViewHolder).bind(beers[position], beerClickListener)
        }


    }

    override fun getItemViewType(position: Int): Int {
        return if (beers[position] == null) {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_ITEM
        }
    }

    fun addData(newItems: List<Beer?>){
        beers.addAll(newItems)
        notifyDataSetChanged()
    }

    fun getItemAtPosition(position: Int): Beer? {
        return beers[position]
    }

    fun addLoadingView(){
        Handler(Looper.getMainLooper()).post {
            beers.add(null)
            notifyItemInserted(beers.size - 1)
        }
    }

    fun removeLoadingView() {
        if (beers.size != 0) {
            beers.removeAt(beers.size - 1)
            notifyItemRemoved(beers.size)
        }
    }

    companion object {
        const val VIEW_TYPE_LOADING = 1
        const val VIEW_TYPE_ITEM = 0
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

class LoadingViewHolder(
    private val binding: ProgressLoadingBinding
): RecyclerView.ViewHolder(binding.root) {

}
