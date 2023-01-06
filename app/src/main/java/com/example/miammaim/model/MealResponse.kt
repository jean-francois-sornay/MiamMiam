package com.example.miammaim.model

import com.google.gson.annotations.SerializedName

class MealsResponse {
    @SerializedName("meals")
    var meals: List<MealResponse>? = null
}

class MealResponse {
    @SerializedName("idMeal")
    var id: Int? = null
    @SerializedName("strMeal")
    var name: String? = null
    @SerializedName("strMealThumb")
    var imgLink: String? = null
    @SerializedName("strDrinkAlternate")
    var alternative: String? = null
    @SerializedName("strCategory")
    var category: String? = null
    @SerializedName("strArea")
    var origin: String? = null
    @SerializedName("strInstructions")
    var instructions: String? = null
    @SerializedName("strTags")
    var tags: String? = null
    @SerializedName("strYoutube")
    var tutorial: String? = null

    @SerializedName("strIngredient1")
    var ingredient1: String? = null
    @SerializedName("strIngredient2")
    var ingredient2: String? = null
    @SerializedName("strIngredient3")
    var ingredient3: String? = null
    @SerializedName("strIngredient4")
    var ingredient4: String? = null
    @SerializedName("strIngredient5")
    var ingredient5: String? = null
    @SerializedName("strIngredient6")
    var ingredient6: String? = null
    @SerializedName("strIngredient7")
    var ingredient7: String? = null
    @SerializedName("strIngredient8")
    var ingredient8: String? = null
    @SerializedName("strIngredient9")
    var ingredient9: String? = null
    @SerializedName("strIngredient10")
    var ingredient10: String? = null
    @SerializedName("strIngredient11")
    var ingredient11: String? = null
    @SerializedName("strIngredient12")
    var ingredient12: String? = null
    @SerializedName("strIngredient13")
    var ingredient13: String? = null
    @SerializedName("strIngredient14")
    var ingredient14: String? = null
    @SerializedName("strIngredient15")
    var ingredient15: String? = null
    @SerializedName("strIngredient16")
    var ingredient16: String? = null
    @SerializedName("strIngredient17")
    var ingredient17: String? = null
    @SerializedName("strIngredient18")
    var ingredient18: String? = null
    @SerializedName("strIngredient19")
    var ingredient19: String? = null
    @SerializedName("strIngredient20")
    var ingredient20: String? = null

    @SerializedName("strMeasure1")
    var measure1: String? = null
    @SerializedName("strMeasure2")
    var measure2: String? = null
    @SerializedName("strMeasure3")
    var measure3: String? = null
    @SerializedName("strMeasure4")
    var measure4: String? = null
    @SerializedName("strMeasure5")
    var measure5: String? = null
    @SerializedName("strMeasure6")
    var measure6: String? = null
    @SerializedName("strMeasure7")
    var measure7: String? = null
    @SerializedName("strMeasure8")
    var measure8: String? = null
    @SerializedName("strMeasure9")
    var measure9: String? = null
    @SerializedName("strMeasure10")
    var measure10: String? = null
    @SerializedName("strMeasure11")
    var measure11: String? = null
    @SerializedName("strMeasure12")
    var measure12: String? = null
    @SerializedName("strMeasure13")
    var measure13: String? = null
    @SerializedName("strMeasure14")
    var measure14: String? = null
    @SerializedName("strMeasure15")
    var measure15: String? = null
    @SerializedName("strMeasure16")
    var measure16: String? = null
    @SerializedName("strMeasure17")
    var measure17: String? = null
    @SerializedName("strMeasure18")
    var measure18: String? = null
    @SerializedName("strMeasure19")
    var measure19: String? = null
    @SerializedName("strMeasure20")
    var measure20: String? = null
}

class Meal(mealResponse: MealResponse) {
    var id: Int? = null
    var name: String? = null
    var imgLink: String? = null
    var alternative: String? = null
    var tutorial: String? = null

    var tags: List<String>? = null
    var instructions: List<String>
    var ingredients: List<Ingredient>

    init {
        id = mealResponse.id
        name = mealResponse.name
        imgLink = mealResponse.imgLink
        alternative = mealResponse.alternative
        tutorial = mealResponse.tutorial
        val temp_instruction = mealResponse.instructions?.split("\r\n")?.filter { it.isNotEmpty() && it != "\r\n" }
        if (temp_instruction != null) {
            instructions = temp_instruction
        } else {
            instructions = listOf("No instructions available")
        }

        tags = mealResponse.tags?.split(",")
        ingredients = listOf(
            Ingredient(1, mealResponse.ingredient1, mealResponse.measure1),
            Ingredient(2, mealResponse.ingredient2, mealResponse.measure2),
            Ingredient(3, mealResponse.ingredient3, mealResponse.measure3),
            Ingredient(4, mealResponse.ingredient4, mealResponse.measure4),
            Ingredient(5, mealResponse.ingredient5, mealResponse.measure5),
            Ingredient(6, mealResponse.ingredient6, mealResponse.measure6),
            Ingredient(7, mealResponse.ingredient7, mealResponse.measure7),
            Ingredient(8, mealResponse.ingredient8, mealResponse.measure8),
            Ingredient(9, mealResponse.ingredient9, mealResponse.measure9),
            Ingredient(10, mealResponse.ingredient10, mealResponse.measure10),
            Ingredient(11, mealResponse.ingredient11, mealResponse.measure11),
            Ingredient(12, mealResponse.ingredient12, mealResponse.measure12),
            Ingredient(13, mealResponse.ingredient13, mealResponse.measure13),
            Ingredient(14, mealResponse.ingredient14, mealResponse.measure14),
            Ingredient(15, mealResponse.ingredient15, mealResponse.measure15),
            Ingredient(16, mealResponse.ingredient16, mealResponse.measure16),
            Ingredient(17, mealResponse.ingredient17, mealResponse.measure17),
            Ingredient(18, mealResponse.ingredient18, mealResponse.measure18),
            Ingredient(19, mealResponse.ingredient19, mealResponse.measure19),
            Ingredient(20, mealResponse.ingredient20, mealResponse.measure20)
        ).filter { it.name != null && it.name != "" }.sortedBy { it.id }
    }
}

data class Ingredient(
    val id: Int,
    val name: String? = null,
    val measure: String? = null,
)