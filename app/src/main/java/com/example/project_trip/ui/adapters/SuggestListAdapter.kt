package com.example.project_trip.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.project_trip.R
import com.example.project_trip.data.InterestType
import com.example.project_trip.data.Places

class SuggestListAdapter : RecyclerView.Adapter<SuggestListAdapter.SuggestListViewHolder>() {

    companion object{
        public var places : ArrayList<Places> = ArrayList<Places>()
    }


    class SuggestListViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val my_card = view.findViewById<CardView>(R.id.suggest_list_card)
        val tv_text = view.findViewById<TextView>(R.id.my_text)
        val tv_image = view.findViewById<ImageView>(R.id.place_image)
        val state = view.findViewById<TextView>(R.id.state_name)
        val country = view.findViewById<TextView>(R.id.country_name)
        fun bindText(place : Places){
            tv_text.text = place.name
            state.text = place.state
            country.text = place.country
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : SuggestListViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_suggest_list, parent, false)

        return SuggestListViewHolder(layout)
    }

    override fun onBindViewHolder(holder: SuggestListViewHolder, position: Int) {
        val currentplace = places[position]
        holder.bindText(currentplace)
        Glide.with(holder.itemView.context).load(currentplace.image_url).into(holder.tv_image)
    }

    override fun getItemCount(): Int {
        return places.size
    }

}