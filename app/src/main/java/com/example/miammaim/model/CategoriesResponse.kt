package com.example.miammaim.model

import com.google.gson.annotations.SerializedName

class CategoriesResponse {
    var categories: List<Categorie>? = null
}


class Categorie {
    @SerializedName("idCategory")
    var id: Int? = null
    @SerializedName("strCategory")
    var name: String? = null
    @SerializedName("strCategoryThumb")
    var imgLink: String? = null
    @SerializedName("strCategoryDescription")
    var description: String? = null
}