package com.example.beerpunkapp.screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.beerpunkapp.R
import com.example.beerpunkapp.data.BeerModel
import com.example.beerpunkapp.databinding.ItemBeerBinding
import com.example.beerpunkapp.domain.entity.Beer
import kotlin.reflect.KFunction1

class StartAdapter(
    private val loanClickListener: KFunction1<BeerModel, Unit>
) : RecyclerView.Adapter<StartViewHolder>() {

    var beers: List<Beer> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StartViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBeerBinding.inflate(inflater, parent, false)
        return StartViewHolder(binding)
    }

    override fun getItemCount(): Int = beers.size


    override fun onBindViewHolder(holder: StartViewHolder, position: Int) {
        holder.bind(beers[position]/*, loanClickListener*/)
    }
}

class StartViewHolder(
    private val binding: ItemBeerBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(beer: Beer){
        with(binding){
            itemId.text = beer.id.toString()
            itemName.text = beer.name ?: "--name is missing--"
            itemTag.text = beer.tags ?: "--tag is missing--"
            itemDescription.text = beer.description ?: "--description is missing--"
            Glide.with(itemPhoto.context)
                .load(beer.photo)
                .placeholder(R.drawable.recycler_view_placeholder)
                .error(R.drawable.recycler_view_placeholder)
                .into(itemPhoto)
        }
    }
}
