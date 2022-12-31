package com.example.androidstudy_1_todolist.Data

import android.util.Log
import com.example.androidstudy_1_todolist.Data.DTO.Form
import com.example.androidstudy_1_todolist.Data.DTO.ToDoList
import retrofit2.*

class Repository {
    private var service = RetrofitManager.retrofit.create(
        RetrofitService::class.java)

    /** get 통신 */
    fun getList(param: GetDataCallback<ToDoList>) {
        /**비동기 통신: enqueue*/
            service.getList().enqueue(object : Callback<ToDoList>{
            override fun onResponse(call: Call<ToDoList>, response: Response<ToDoList>) {
                /**응답 성공*/
                Log.i("response", response.body().toString()) //데이터 확인

                /**Viewmodel의 인터페이스 구현부에 데이터 넘겨주기*/
                param.onSuccess(response.body())
            }

            override fun onFailure(call: Call<ToDoList>, t: Throwable) {
                /**응답 실패*/
                param.onFailure(t)
            }
        })
    }

    /** post 통신*/
    fun postForm(form: Form) {
        service.postForm(form).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: Response<String>) {
                /**응답 성공*/
                Log.i("post_Success", response.body().toString()) //데이터 확인
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
                /**요청 실패*/
                Log.i("post_Failure", t.toString()) //데이터 확인
            }
        })
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