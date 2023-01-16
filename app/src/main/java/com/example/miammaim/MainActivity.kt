package com.example.miammaim

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.miammaim.adapter.CategoriesAdapter
import com.example.miammaim.model.Categorie
import com.example.miammaim.model.CategoriesResponse
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var categoriesAdapter: CategoriesAdapter
    private lateinit var categoriesProgressIndicator: CircularProgressIndicator
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var randomRecipeButton: Button

    private var categoriesResponse: CategoriesResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.categories_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        categoriesProgressIndicator = findViewById(R.id.categories_circular_progress)
        categoriesProgressIndicator.visibility = View.VISIBLE

        randomRecipeButton = findViewById(R.id.random_recipe_button)
        randomRecipeButton.setOnClickListener {
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra("isRandom", true)
            this.startActivity(intent)
        }

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            getCategories()
            Log.d("Categories", "Categories refreshed")
        }

        getCategories()
        Log.d("Categories", "Categories view loaded")
    }

    public fun getCategories() {
        var url = URL("https://www.themealdb.com/api/json/v1/1/categories.php")
        var request = Request.Builder()
            .url(url)
            .build()

        var client = OkHttpClient()

        var response = client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    categoriesResponse = parseCategoriesResponse(it)
                    categoriesResponse?.categories?.let { it1 ->
                        runOnUiThread {
                            setViewContentOnUi(it1)
                        }

                    }
                    Log.d("OKHTTP Categories", "Found " + categoriesResponse?.categories?.count() + " existing categories")
                    if(categoriesResponse?.categories?.isEmpty() == true) {
                        Snackbar.make(recyclerView,
                            "No category found, check the app later", Snackbar.LENGTH_LONG)
                            .show()
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.localizedMessage?.let { Log.e("OKHTTP Categories Error", it) }
                categoriesProgressIndicator.visibility = View.GONE
                Snackbar.make(recyclerView, "Unable to load the categories, check your internet connection", Snackbar.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun setViewContentOnUi(categories: List<Categorie>) {
        categoriesProgressIndicator.visibility = View.GONE
        categoriesAdapter = CategoriesAdapter(categories)
        recyclerView.adapter = categoriesAdapter
    }

    private fun parseCategoriesResponse(json: String): CategoriesResponse? {
        val gson = Gson()
        return gson.fromJson(json, CategoriesResponse::class.java)
    }

}