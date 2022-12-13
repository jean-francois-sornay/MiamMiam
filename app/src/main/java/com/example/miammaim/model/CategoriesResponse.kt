package com.example.miammaim.model

class CategoriesResponse {
    var categories: List<Categorie>? = null
}


class Categorie {
    var id: Int? = null
    var name: String? = null
    var imgLink: String? = null
    var description: String? = null
}