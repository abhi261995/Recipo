package com.example.abhi.recipefinderapp.data

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.abhi.recipefinderapp.R
import com.example.abhi.recipefinderapp.activities.ShowLinkActivity
import com.example.abhi.recipefinderapp.model.Recipe
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_row.view.*

class RecipeListAdapter(private var list:ArrayList<Recipe>,private var context: Context) :RecyclerView.Adapter<RecipeListAdapter.ViewHolder>(){

    override fun getItemCount(): Int {
        return list.size

    }


    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
                val view=LayoutInflater.from(context).inflate(R.layout.list_row,parent,false)
                return ViewHolder(view)

          }



    override fun onBindViewHolder(holder: ViewHolder, posiiton: Int) {
        //binding everything to present to recycler view
        holder!!.bindView(list[posiiton])


    }


    inner class ViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        var title  =itemView.recipeTitle
        var thumbnail=itemView.thumbnail
        var ingredients=itemView.recipeIngredients
        var linkButton=itemView.linkbutton



        fun bindView(recipe: Recipe){
            title.text=recipe.title
            ingredients.text=recipe.ingredients


            if(!TextUtils.isEmpty(recipe.thumbnail)){
                Picasso.get().load(recipe.thumbnail).placeholder(android.R.drawable.ic_menu_report_image)
                        .error(android.R.drawable.ic_menu_report_image)
                        .into(thumbnail)


            }
            else{
                Picasso.get().load(R.mipmap.ic_launcher_round).into(thumbnail)
            }

            linkButton.setOnClickListener {

                var intent=Intent(context, ShowLinkActivity::class.java)
                intent.putExtra("link",recipe.link.toString())
                context.startActivity(intent)


            }


        }



    }
}