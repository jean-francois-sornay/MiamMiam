package com.example.miammaim

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.miammaim.adapter.*
import com.example.miammaim.model.*
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import okhttp3.*
import java.io.IOException
import java.net.URL
import kotlin.properties.Delegates

class MealActivity  : AppCompatActivity() {
    private lateinit var mealTitleCardView: CardView
    private lateinit var ingredientsCardView: CardView
    private lateinit var instructionsCardView: CardView
    private lateinit var tutorialCardView: CardView
    private lateinit var imageCardView: CardView
    private lateinit var ingredientAdapter: IngredientAdapter
    private lateinit var instructionAdapter: InstructionAdapter
    private lateinit var ingredientRecyclerView: RecyclerView
    private lateinit var instructionRecyclerView: RecyclerView
    private lateinit var mealProgressIndicator: CircularProgressIndicator

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

        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)

        mealProgressIndicator = findViewById(R.id.meal_circular_progress)
        imageCardView = findViewById(R.id.meal_image_card_view)
        mealTitleCardView = findViewById(R.id.meal_title_card_view)
        ingredientsCardView = findViewById(R.id.meal_ingredients_card_view)
        instructionsCardView = findViewById(R.id.meal_instructions_card_view)
        tutorialCardView = findViewById(R.id.meal_tutorial_card_view)
        setInitialVisibility()

        ingredientRecyclerView = findViewById(R.id.ingredients_recycler_view)
        ingredientRecyclerView.layoutManager = GridLayoutManager(this, 3)

        instructionRecyclerView = findViewById(R.id.instructions_recycler_view)
        instructionRecyclerView.layoutManager = LinearLayoutManager(this)

        getMeal()
        Log.d("Meal", "Meal view loaded")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    public fun setInitialVisibility() {
        mealProgressIndicator.visibility = View.VISIBLE
        imageCardView.visibility = View.GONE
        mealTitleCardView.visibility = View.GONE
        ingredientsCardView.visibility = View.GONE
        instructionsCardView.visibility = View.GONE
        tutorialCardView.visibility = View.GONE
    }

    public fun setSuccessLoadingVisibility() {
        mealProgressIndicator.visibility = View.GONE
        imageCardView.visibility = View.VISIBLE
        mealTitleCardView.visibility = View.VISIBLE
        ingredientsCardView.visibility = View.VISIBLE
        instructionsCardView.visibility = View.VISIBLE
        tutorialCardView.visibility = View.VISIBLE
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
                            setViewContentOnUi(meal!!)
                        }
                    }
                    Log.d("OKHTTP Meal", "Successfully retrieved " + (mealsResponse?.meals?.get(0)?.name ?: "") + " meal")
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                e.localizedMessage?.let { Log.e("OKHTTP Meal Error", it) }
                mealProgressIndicator.visibility = View.GONE
                Snackbar.make(mealTitleCardView,
                    "Unable to load the selected recipe, check your internet connection", Snackbar.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun setViewContentOnUi(meal: Meal) {
        setSuccessLoadingVisibility()
        headerTextView.text = meal.name
        Picasso.get().load(meal.imgLink).into(imgView)
        linkTextView.text = meal.tutorial

        ingredientAdapter = IngredientAdapter(meal.ingredients)
        ingredientRecyclerView.adapter = ingredientAdapter

        instructionAdapter = InstructionAdapter(meal.instructions)
        instructionRecyclerView.adapter = instructionAdapter
    }

    private fun parseMealsResponse(json: String): MealsResponse? {
        val gson = Gson()
        return gson.fromJson(json, MealsResponse::class.java)
    }
}