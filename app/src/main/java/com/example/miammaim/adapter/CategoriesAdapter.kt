package com.example.miammaim.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miammaim.R
import com.example.miammaim.model.Categorie
import com.squareup.picasso.Picasso

class CategorieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    public var nameTextView: TextView = itemView.findViewById(R.id.categorie_item_name)
    public var imgView: ImageView = itemView.findViewById(R.id.categorie_item_img)
}

class CategoriesAdapter(val categories: List<Categorie>) : RecyclerView.Adapter<CategorieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.categorie_item, parent, false)
        return CategorieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategorieViewHolder, position: Int) {
        holder.nameTextView.setText(categories[position].name)
        Picasso.get().load(categories[position].imgLink).into(holder.imgView)
    }

    override fun getItemCount(): Int {
        return categories.count()
    }
}