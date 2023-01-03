package com.example.miammaim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miammaim.adapter.CategoriesAdapter
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

    private var categoriesResponse: CategoriesResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.categories_recycler_view)
        categoriesProgressIndicator = findViewById(R.id.categories_circular_progress)

        categoriesProgressIndicator.visibility = View.VISIBLE;

        getCategories()
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
                            categoriesProgressIndicator.visibility = View.GONE
                            categoriesAdapter = CategoriesAdapter(it1)
                            Log.d("OKHTTP Categories", "TEST")
                            recyclerView.adapter = categoriesAdapter
                            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        }

                    }
                    Log.d("OKHTTP Categories", "Got " + categoriesResponse?.categories?.count() + " results")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.localizedMessage?.let { Log.e("OKHTTP Categories", it) }
                categoriesProgressIndicator.visibility = View.GONE
                Snackbar.make(recyclerView, "Unable to load the categories, check your internet connection", Snackbar.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun parseCategoriesResponse(json: String): CategoriesResponse? {
        val gson = Gson()
        return gson.fromJson(json, CategoriesResponse::class.java)
    }

}