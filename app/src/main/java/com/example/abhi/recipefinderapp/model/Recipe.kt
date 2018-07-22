package com.example.abhi.recipefinderapp.model

import android.provider.MediaStore

class Recipe() {

    var title:String?=null
    var link:String?=null
    var ingredients:String?=null
     var thumbnail:String?=null

    constructor(title:String,link:String,ingredients:String,thumbnail:String):this(){

        this.title=title
        this.ingredients=ingredients
        this.link=link
        this.thumbnail=thumbnail
    }

}