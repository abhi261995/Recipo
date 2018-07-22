package com.example.abhi.recipefinderapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.abhi.recipefinderapp.data.RecipeListAdapter
import com.example.abhi.recipefinderapp.model.LEFT_LINK
import com.example.abhi.recipefinderapp.model.QUERY
import com.example.abhi.recipefinderapp.model.Recipe
import kotlinx.android.synthetic.main.activity_recipe_list.*
import org.json.JSONException
import org.json.JSONObject
import java.net.URL

class RecipeList : AppCompatActivity() {

    var volleyRequest:RequestQueue?=null
    var  recipeList:ArrayList<Recipe>?=null
    var   recipeListAdapter:RecipeListAdapter?=null
    var layoutManager: RecyclerView.LayoutManager?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)

        volleyRequest=Volley.newRequestQueue(this)
        recipeList=ArrayList<Recipe>()
        var urlString="http://www.recipepuppy.com/api/?i=onions,garlic&q=omelet&p=3"
        var url:String?
        var extras=intent.extras
        var ingredients=extras.get("ingredients")
        var search=extras.get("search")
        if(extras !=null && !ingredients.equals("")&&
                !search.equals(""))
        {
            //construct url
            var tempUrl= LEFT_LINK+ingredients+ QUERY+search

            url=tempUrl


        }
        else{
            url=urlString
        }

        getRecipe(url)
    }


    fun getRecipe(url:String)
    {
         val recipeRequest =JsonObjectRequest(Request.Method.GET,url, 
           Response.Listener {
                       response :JSONObject ->


                   try {
                        val resultArray=response!!.getJSONArray("results")
                        for(i in 0..resultArray.length()-1)
                        {
                            var recipeObj=resultArray.getJSONObject(i)

                            var title=recipeObj.getString("title")
                            var ingredients=recipeObj.getString("ingredients")
                            var thumbnail=recipeObj.getString("thumbnail")
                            var link=recipeObj.getString("href")
                           // Log.d("Response ====>>>",recipeObj.getString("title"))
                            var recipe=Recipe()
                            recipe.title=title
                            recipe.ingredients="Ingredients : ${ingredients}"
                            recipe.thumbnail=thumbnail
                            recipe.link=link

                            recipeList!!.add(recipe)
                            recipeListAdapter= RecipeListAdapter(recipeList!!,this)
                            layoutManager=LinearLayoutManager(this)



                            //setup recycler view
                            recyclerView.layoutManager=layoutManager
                            recyclerView.adapter=recipeListAdapter





                        }
                       recipeListAdapter!!.notifyDataSetChanged()



                   }
                   catch(e:JSONException) {
                           e.printStackTrace()
                   }
                     
             
         },Response.ErrorListener {
              error :VolleyError ->
                    try {
                            Log.d("Error:",error.toString())
                    }
                    catch (e:JSONException){
                        e.printStackTrace()
                    }
         })

        volleyRequest!!.add(recipeRequest)
    }
}
