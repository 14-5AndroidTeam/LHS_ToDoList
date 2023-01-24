package com.example.androidstudy_1_todolist.Data

import android.util.Log
import com.example.androidstudy_1_todolist.Data.DTO.Form
import com.example.androidstudy_1_todolist.Data.DTO.ToDoList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.*

class Repository {
    private var service = RetrofitManager.retrofit.create(
        RetrofitService::class.java)

    fun getList(param: GetDataCallback<ToDoList>) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getList()

            if (response.isSuccessful) {
                param.onSuccess(response.body())
            }
            else {
                param.onFailure(Throwable())
            }
        }
    }

    /** Retrofit2를 이용한 post 통신
    fun postForm(form: Form) {
        service.postForm(form).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                /**응답 성공*/
                Log.i("post_Success", response.body().toString())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                /**요청 실패*/
                Log.i("post_Failure", t.toString())
            }
        })
    }
    */

    /** Coroutine을 이용한 post 통신 */
    fun postForm(form: Form) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.postForm(form)

            if (response.isSuccessful) {
                Log.d("Coroutine", "Coroutine Success")
            }
            else {
                Log.e("Coroutine", "onResponse, status :" +
                        "${response.code()}, message : ${response.message()}")
            }
        }
    }

    /** put 통신 */
    fun putForm(id: Int, form: Form) {
        service.putForm(id, form).enqueue(object: Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                /**응답 성공*/
                Log.i("put_Success", response.body().toString())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                /** 응답 실패 */
                Log.i("put_Failure", t.toString())
            }
        })
    }

    /** Delete 통신*/
     fun deleteForm(id: Int) {
        service.deleteForm(id).enqueue(object: Callback<String> {
            override fun onResponse(Call: Call<String>, response: Response<String>) {
                /**응답 성공*/
                Log.i("Delete_Success", response.body().toString())
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                /** 응답 실패 */
                Log.i("Delete_Failure", t.toString())
            }
        })
    }

    /** ViewModel의 UI부분에 데이터를 넘겨주기 위한 함수 */
    interface GetDataCallback<T> {
        fun onSuccess(data: T?)
        fun onFailure(throwable: Throwable)
    }
}