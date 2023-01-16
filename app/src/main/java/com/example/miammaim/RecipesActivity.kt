package com.example.miammaim

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.miammaim.adapter.RecipesAdapter
import com.example.miammaim.model.Recipe
import com.example.miammaim.model.RecipesResponse
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

class RecipesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recipesAdapter: RecipesAdapter
    private lateinit var recipesProgressIndicator: CircularProgressIndicator
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    private var recipesResponse: RecipesResponse? = null
    private var categoryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        recipesProgressIndicator = findViewById(R.id.recipes_circular_progress)
        recipesProgressIndicator.visibility = View.VISIBLE

        val bundle = intent.extras
        categoryName = bundle!!.getString("categoryName")

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView = findViewById(R.id.recipes_recycler_view)
        recyclerView.layoutManager = layoutManager

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            getRecipes()
            Log.d("Recipes", "Recipes refreshed")
        }

        getRecipes()
        Log.d("Recipes", "Recipes view loaded")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    public fun getRecipes() {
        var url = URL("https://www.themealdb.com/api/json/v1/1/filter.php?c=" + categoryName)
        var request = Request.Builder()
            .url(url)
            .build()

        var client = OkHttpClient()

        var response = client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    recipesResponse = parseRecipesResponse(it)
                    recipesResponse?.recipes?.let { it1 ->
                        runOnUiThread {
                            setViewContentOnUi(it1)
                        }
                    }
                    Log.d("OKHTTP Recipes", "Found " + recipesResponse?.recipes?.count() + " recipes for this category")
                    if(recipesResponse?.recipes?.isEmpty() == true) {
                        Snackbar.make(recyclerView,
                            "No recipes found for the $categoryName category", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.localizedMessage?.let { Log.e("OKHTTP Recipes Error", it) }
                recipesProgressIndicator.visibility = View.GONE
                Snackbar.make(recyclerView,
                    "Unable to load the recipes of $categoryName category, check your internet connection", Snackbar.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun setViewContentOnUi(recipes: List<Recipe>) {
        recipesProgressIndicator.visibility = View.GONE
        recipesAdapter = RecipesAdapter(recipes)
        recyclerView.adapter = recipesAdapter
    }

    private fun parseRecipesResponse(json: String): RecipesResponse? {
        val gson = Gson()
        return gson.fromJson(json, RecipesResponse::class.java)
    }
}