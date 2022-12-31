package com.example.androidstudy_1_todolist.Data.DTO

import com.google.gson.annotations.SerializedName

/** 전체적인 data class */

data class ToDoList(
    @SerializedName("todos")
    var todos: MutableList<Todos>,
    @SerializedName("total_post")
    var total_post:Int
)