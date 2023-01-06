package com.example.miammaim.model

import com.google.gson.annotations.SerializedName

class RecipesResponse {
    @SerializedName("meals")
    var recipes: List<Recipe>? = null
}



class Recipe {
    @SerializedName("idMeal")
    var id: Int? = null
    @SerializedName("strMeal")
    var name: String? = null
    @SerializedName("strMealThumb")
    var imgLink: String? = null
}