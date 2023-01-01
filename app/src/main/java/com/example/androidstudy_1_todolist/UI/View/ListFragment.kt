package com.example.androidstudy_1_todolist.UI.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidstudy_1_todolist.Data.DTO.Todos
import com.example.androidstudy_1_todolist.Data.Repository
import com.example.androidstudy_1_todolist.Event
import com.example.androidstudy_1_todolist.MainActivity
import com.example.androidstudy_1_todolist.R
import com.example.androidstudy_1_todolist.UI.ViewModel.ListModel
import com.example.androidstudy_1_todolist.databinding.ListFragmentBinding
import kotlinx.android.synthetic.main.item_recycler.*
import kotlinx.android.synthetic.main.list_fragment.*

class ListFragment : Fragment() {
    private lateinit var binding:ListFragmentBinding    // ViewBinding
    private lateinit var adapter: Adapter               // Adapter


    private var _form = MutableLiveData<Event<Boolean>>()
    val form: LiveData<Event<Boolean>> = _form

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        /** 어뎁터를 RecyclerView에 등록 */
        val adapter = Adapter()
        var viewmodel = ListModel()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)

        /** 삭제 버튼에 대한 이벤트 처리 */
        adapter.setOnItemclickListner(object: Adapter.OnItemClickListner{
            override fun onItemClick(todo: Todos) {
                viewmodel.deleteList(todo)
            }
        })
        /** viewmodel의 옵저버 */
        viewmodel.delete.observe(viewLifecycleOwner, Observer {
            Toast.makeText(getActivity(), "삭제 완료!", Toast.LENGTH_SHORT).show()
        })

        /** putForm(id) 테스트 하기 */
        //val testForm = Form("이현승 다녀감", "20000417")
        //repo.putForm(3, testForm)

        /** View모델의 목록을 출력하는 getList()함수 호출 */
        viewmodel.getList()

        /** 추가하기 버튼을 이용하여 이동 */
        binding.btnAdd.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.layout, AddFragment())
                .commit()
        }

        /** 관찰하고 있는 ViewModel의 Observer */
        viewmodel.setList.observe(viewLifecycleOwner, Observer {
            adapter.setList(it.peekContent().todos)
            adapter.notifyItemRangeChanged(0,it.peekContent().total_post)
        })
    }
}