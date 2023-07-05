package com.example.beerpunkapp.model

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ExpandableListView.OnChildClickListener
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerpunkapp.R
import com.example.beerpunkapp.databinding.ItemBeerBinding

class BeerAdapter(private  val beerClickListener: (BeerTestModel) -> Unit) : RecyclerView.Adapter<BeerAdapter.BeersViewHolder>() {

    var beers: List<BeerTestModel> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class BeersViewHolder( val binding: ItemBeerBinding ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerBinding.inflate(inflater, parent, false)
        return BeersViewHolder(binding)
    }

    override fun getItemCount(): Int = beers.size

    override fun onBindViewHolder(holder: BeersViewHolder, position: Int) {
        val beer = beers[position]
        with(holder.binding){
            itemName.text = beer.naming
            itemId.text = beer.id.toString()
            itemTag.text = beer.tags
            itemDescription.text = beer.description
            Glide.with(itemPhoto.context)
                .load(beer.photo)
                .placeholder(R.drawable.recycler_view_placeholder)
                .error(R.drawable.recycler_view_placeholder)
                .into(itemPhoto)
        }
    }
}