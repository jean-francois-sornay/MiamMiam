package com.example.miammaim.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miammaim.R
import com.example.miammaim.model.Ingredient

class IngredientRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    public var ingredientNameTextView: TextView = itemView.findViewById(R.id.ingredient_name)
    public var ingredientMeasureTextView: TextView = itemView.findViewById(R.id.ingredient_amount)
}

class IngredientAdapter(val ingredients: List<Ingredient>) : RecyclerView.Adapter<IngredientRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientRecyclerViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.ingredient_item, parent, false)
        return IngredientRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: IngredientRecyclerViewHolder, position: Int) {
        holder.ingredientNameTextView.setText(ingredients[position].name)
        holder.ingredientMeasureTextView.setText(ingredients[position].measure)
    }

    override fun getItemCount(): Int {
        return ingredients.count()
    }
}