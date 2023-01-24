package com.example.androidstudy_1_todolist.Data

import com.example.androidstudy_1_todolist.Data.DTO.Form
import com.example.androidstudy_1_todolist.Data.DTO.ToDoList
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

/** 사용할 Rest API의 타입과 정보를 인터페이스로 구축 */

interface RetrofitService {
    @PUT("/todos")
    fun putForm(
        @Query("id") id: Int,
        @Body form: Form
    ): Call<String>

    @DELETE("/todos")
    fun deleteForm(
        @Query("id") id: Int
    ): Call<String>

    @GET("/todos")
    suspend fun getList(
    ): Response<ToDoList>

    @POST("/todos")
    suspend fun postForm(
        @Body form: Form
    ): Response<String>
}
