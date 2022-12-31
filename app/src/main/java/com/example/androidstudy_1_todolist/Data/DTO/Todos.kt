package com.example.androidstudy_1_todolist.Data.DTO

import com.google.gson.annotations.SerializedName

data class Todos(
    @SerializedName("_id")
    var _id:String,
    @SerializedName("id")
    var id:Int,
    @SerializedName("content")
    var content:String,
    @SerializedName("deadline")
    var deadline:String
)
