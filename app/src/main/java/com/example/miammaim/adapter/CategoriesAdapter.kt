package com.example.miammaim.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miammaim.MainActivity
import com.example.miammaim.R
import com.example.miammaim.RecipiesActivity
import com.example.miammaim.model.Categorie
import com.squareup.picasso.Picasso

class CategorieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    public var nameTextView: TextView = itemView.findViewById(R.id.categorie_item_name)
    public var descTextView: TextView = itemView.findViewById(R.id.categorie_item_description)
    public var imgView: ImageView = itemView.findViewById(R.id.categorie_item_img)
}

class CategoriesAdapter(val categories: List<Categorie>) : RecyclerView.Adapter<CategorieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorieViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.categorie_item, parent, false)
        return CategorieViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategorieViewHolder, position: Int) {
        holder.nameTextView.setText(categories[position].name)
        holder.descTextView.setText(categories[position].description?.split(".")?.get(0))
        Picasso.get().load(categories[position].imgLink).into(holder.imgView)
        /* setOnClickListener on the item */
        holder.itemView.setOnClickListener(View.OnClickListener {
            // click effect on the item (zoom in)
            holder.itemView.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).withEndAction {
                holder.itemView.animate().scaleX(1f).scaleY(1f).setDuration(200)
            }
            // open a new activity with the list of recipies of the category
            val intent = Intent(holder.itemView.context, RecipiesActivity::class.java)
            intent.putExtra("categoryName", categories[position].name)
            holder.itemView.context.startActivity(intent)
        })

    }

    override fun getItemCount(): Int {
        return categories.count()
    }
}