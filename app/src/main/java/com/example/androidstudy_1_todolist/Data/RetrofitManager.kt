package com.example.androidstudy_1_todolist.Data

import com.example.androidstudy_1_todolist.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** 레트로핏 객체 초기화 */

object RetrofitManager {
    private val BASE_URL = BuildConfig.SERVER

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}