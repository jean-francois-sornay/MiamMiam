package com.example.miammaim

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.miammaim.model.CategoriesResponse
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var categoriesResponse: CategoriesResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                    Log.d("OKHTTP", it)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.localizedMessage?.let { Log.e("OKHTTP", it) }
            }
        })
    }

    private fun parseCategoriesResponse(json: String): CategoriesResponse? {
        val gson = Gson()
        return gson.fromJson(json, CategoriesResponse::class.java)
    }

}