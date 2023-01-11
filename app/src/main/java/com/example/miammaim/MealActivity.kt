package com.example.miammaim

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miammaim.adapter.*
import com.example.miammaim.model.*
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException
import java.net.URL
import kotlin.properties.Delegates

class MealActivity  : AppCompatActivity() {
    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var instructionAdapter: InstructionAdapter
    private lateinit var ingredientRecyclerView: RecyclerView
    private lateinit var instructionRecyclerView: RecyclerView

    public lateinit var headerTextView: TextView
    public lateinit var imgView: ImageView
    public lateinit var linkTextView: TextView

    private var mealId by Delegates.notNull<Int>()
    private var meal: Meal? = null
    private var isRandom: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)
        headerTextView = findViewById(R.id.meal_heading)
        imgView = findViewById(R.id.meal_image)
        linkTextView = findViewById(R.id.meal_tutorial_link)

        val bundle = intent.extras
        mealId = bundle!!.getInt("mealId")
        isRandom = bundle.getBoolean("isRandom")

        ingredientRecyclerView = findViewById(R.id.ingredients_recycler_view)
        ingredientRecyclerView.layoutManager = GridLayoutManager(this, 3)

        instructionRecyclerView = findViewById(R.id.instructions_recycler_view)
        instructionRecyclerView.layoutManager = LinearLayoutManager(this)
        getMeal()
    }

    public fun getMeal() {
        var url = URL("https://www.themealdb.com/api/json/v1/1/lookup.php?i=" + mealId)

        if (isRandom) {
            url = URL("https://www.themealdb.com/api/json/v1/1/random.php")
        }

        Log.d("OKHTTP Meal", "Got URL " + url)
        val request = Request.Builder()
            .url(url)
            .build()
        val client = OkHttpClient()

        var response = client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                response.body?.string()?.let { m ->
                    val mealsResponse: MealsResponse? = parseMealsResponse(m)
                    if (mealsResponse != null) {
                        meal = Meal(mealsResponse.meals!![0])

                        runOnUiThread {
                            headerTextView.text = meal!!.name
                            Picasso.get().load(meal!!.imgLink).into(imgView)
                             //meal!!.instructions?.joinToString { it + "\r\n" } ?: "No instructions"
                            linkTextView.text = meal!!.tutorial
                            Log.d("OKHTTP Meal", "Got " + meal!!.instructions?.joinToString { it + "\r\n" })

                            ingredientAdapter = IngredientAdapter(meal!!.ingredients)
                            ingredientRecyclerView.adapter = ingredientAdapter

                            instructionAdapter = InstructionAdapter(meal!!.instructions!!)
                            instructionRecyclerView.adapter = instructionAdapter
                        }
                    }
                    Log.d("OKHTTP Meal", "Got " + mealsResponse?.meals?.count() + " meals")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.localizedMessage?.let { Log.e("OKHTTP Meal", it) }
            }
        })
    }

    private fun parseMealsResponse(json: String): MealsResponse? {
        val gson = Gson()
        return gson.fromJson(json, MealsResponse::class.java)
    }
}