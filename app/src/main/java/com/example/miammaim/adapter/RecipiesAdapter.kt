package com.example.miammaim.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.miammaim.MealActivity
import com.example.miammaim.R
import com.example.miammaim.model.Recipe
import com.squareup.picasso.Picasso


class RecipeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    public var nameTextView: TextView = itemView.findViewById(R.id.recipe_item_name)
    public var imgView: ImageView = itemView.findViewById(R.id.recipe_item_img)
}

class RecipiesAdapter(val recipies: List<Recipe>) : RecyclerView.Adapter<RecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item, parent, false)
        var randomRecipeButton: Button = (parent.context as Activity).findViewById(R.id.random_recipe_button)
        randomRecipeButton.setOnClickListener { view ->
            val intent = Intent(parent.context, MealActivity::class.java)
            val randomRecipe = recipies.random()
            intent.putExtra("mealId", randomRecipe.id)
            parent.context.startActivity(intent)
        }
        return RecipeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.nameTextView.setText(recipies[position].name)
        Picasso.get().load(recipies[position].imgLink).into(holder.imgView)
        holder.itemView.setOnClickListener(View.OnClickListener {
            // click effect on the item (zoom in)
            holder.itemView.animate().scaleX(1.1f).scaleY(1.1f).setDuration(200).withEndAction {
                holder.itemView.animate().scaleX(1f).scaleY(1f).setDuration(200)
            }
            // open a new activity with the list of recipies of the category
            val intent = Intent(holder.itemView.context, MealActivity::class.java)
            intent.putExtra("mealId", recipies[position].id)
            holder.itemView.context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return recipies.count()
    }
}