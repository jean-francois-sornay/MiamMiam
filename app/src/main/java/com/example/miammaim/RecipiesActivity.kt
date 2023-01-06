package com.example.miammaim

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miammaim.adapter.RecipiesAdapter
import com.example.miammaim.model.RecipiesResponse
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

class RecipiesActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var recipiesAdapter: RecipiesAdapter
    private lateinit var recipiesProgressIndicator: CircularProgressIndicator

    private var recipiesResponse: RecipiesResponse? = null
    private var categoryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipies)
        recipiesProgressIndicator = findViewById(R.id.recipies_circular_progress)

        recipiesProgressIndicator.visibility = View.VISIBLE
        val bundle = intent.extras
        categoryName = bundle!!.getString("categoryName")

        val layoutManager = GridLayoutManager(this, 2)
        recyclerView = findViewById(R.id.recipies_recycler_view)
        recyclerView.layoutManager = layoutManager
        getRecipies()
    }

    public fun getRecipies() {
        var url = URL("https://www.themealdb.com/api/json/v1/1/filter.php?c=" + categoryName)
        var request = Request.Builder()
            .url(url)
            .build()

        var client = OkHttpClient()

        var response = client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let {
                    recipiesResponse = parseRecipiesResponse(it)
                    recipiesResponse?.recipies?.let { it1 ->
                        runOnUiThread {
                            recipiesProgressIndicator.visibility = View.GONE
                            recipiesAdapter = RecipiesAdapter(it1)
                            recyclerView.adapter = recipiesAdapter
                        }
                    }
                    Log.d("OKHTTP Recipies", "Got " + recipiesResponse?.recipies?.count() + " results")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.localizedMessage?.let { Log.e("OKHTTP Recipies", it) }
                recipiesProgressIndicator.visibility = View.VISIBLE
                Snackbar.make(recyclerView,
                    "Unable to load the recipies of $categoryName category, check your internet connection", Snackbar.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun parseRecipiesResponse(json: String): RecipiesResponse? {
        val gson = Gson()
        return gson.fromJson(json, RecipiesResponse::class.java)
    }
}