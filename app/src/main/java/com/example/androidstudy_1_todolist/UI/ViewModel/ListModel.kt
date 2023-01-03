package com.example.androidstudy_1_todolist.UI.ViewModel

import android.widget.Adapter
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidstudy_1_todolist.Data.DTO.Form
import com.example.androidstudy_1_todolist.Data.DTO.ToDoList
import com.example.androidstudy_1_todolist.Data.DTO.Todos
import com.example.androidstudy_1_todolist.Event
import com.example.androidstudy_1_todolist.Data.Repository
import com.example.androidstudy_1_todolist.MainActivity
import com.google.android.material.internal.ContextUtils.getActivity
import android.app.PendingIntent.getActivity as getActivity1

class ListModel:androidx.lifecycle.ViewModel() {

    val repo = Repository()
    private var _setList = MutableLiveData<Event<ToDoList>>()
    val setList: LiveData<Event<ToDoList>> = _setList

    private var _delete = MutableLiveData<Event<Boolean>>()
    val delete: LiveData<Event<Boolean>> = _delete

    private var _put = MutableLiveData<Event<Boolean>>()
    val put: LiveData<Event<Boolean>> = _put

    /** callback 데이터를 UI에 할당 */
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

    /** 데이터를 삭제 */
    fun deleteList(todos: Todos) {
        repo.deleteForm(todos.id)
        //repo.getList()
        _delete.postValue(Event(true))
    }

    // 수정하기 위해서는 id값과 form을 받아야 한다.
    // form을 받지 못하더라도 todos 를 받아서 추출해서 써야 한다.
    /** 데이터를 수정 */
    fun putList(id: Int, content: String, deadline: String) {
        var form = Form(
            content = content,
            deadline = deadline
        )
        repo.putForm(id, form)
        _put.postValue(Event(true))
    }
}