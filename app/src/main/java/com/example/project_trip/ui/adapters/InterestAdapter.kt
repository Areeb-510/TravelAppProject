package com.example.project_trip.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_trip.Interest
import com.example.project_trip.data.InterestType
import com.example.project_trip.R
import com.google.android.material.card.MaterialCardView

class InterestAdapter : RecyclerView.Adapter<InterestAdapter.InterestViewHolder>() {


    public var isSelected : Boolean = false

    companion object{
        public var selectedItems : ArrayList<InterestType> = arrayListOf()
        public var are_interest_Selected = true
    }


    class InterestViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val interest_card: MaterialCardView = view.findViewById<MaterialCardView>(R.id.interest_card)
        val img: ImageView = view.findViewById<ImageView>(R.id.interest_image)
        val imgtext: TextView = view.findViewById<TextView>(R.id.interest_text)
        fun bindImage(interest : InterestType){
            img.setImageResource(interest.ImageSrc)
            imgtext.text = interest.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InterestViewHolder {
        val layout = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_interest, parent, false)

        return InterestViewHolder(layout)
    }

    override fun onBindViewHolder(holder: InterestViewHolder, position: Int) {
        val item = Interest.get(position)
        holder.bindImage(item)
        holder.interest_card.setOnClickListener {
            isSelected = true
            if(selectedItems.contains(item)){
                holder.img.clearColorFilter()
                selectedItems.remove(item)
            }else{
                holder.img.setColorFilter(R.color.transparent)
                selectedItems.add(item)
            }
        }
    }

    override fun getItemCount(): Int {
        return Interest.size
    }

}