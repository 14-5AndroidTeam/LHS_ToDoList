package com.example.androidstudy_1_todolist.UI.ViewModel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidstudy_1_todolist.Data.DTO.ToDoList
import com.example.androidstudy_1_todolist.Event
import com.example.androidstudy_1_todolist.Data.Repository
import com.google.android.material.internal.ContextUtils.getActivity
import android.app.PendingIntent.getActivity as getActivity1

class ListModel:androidx.lifecycle.ViewModel() {

    val repo = Repository()
    private var _setList = MutableLiveData<Event<ToDoList>>()
    val setList: LiveData<Event<ToDoList>> = _setList

    fun getList(){
        repo.getList(object : Repository.GetDataCallback<ToDoList> {
            override fun onSuccess(data: ToDoList?) {
                /**응답받은 데이터 내용을 LiveData로 할당한다*/
                if(data != null) _setList.postValue(Event(data))
            }

            override fun onFailure(throwable: Throwable) {
                /**throwable 매개변수를 가공을 해서 UI단에 에러메세지로
                 * 넘겨줄 수 있겠다....*/
            }
        })
    }
}