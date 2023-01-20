package com.example.androidstudy_1_todolist.UI.View.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidstudy_1_todolist.Data.DTO.Todos
import com.example.androidstudy_1_todolist.Event
import com.example.androidstudy_1_todolist.R
import com.example.androidstudy_1_todolist.UI.View.Adapter
import com.example.androidstudy_1_todolist.UI.View.AddFragment
import com.example.androidstudy_1_todolist.UI.View.ModifyFragment
import com.example.androidstudy_1_todolist.UI.View.TakePictureFragment
import com.example.androidstudy_1_todolist.UI.ViewModel.ListModel
import com.example.androidstudy_1_todolist.databinding.ListFragmentBinding

class ListFragment : Fragment() {
    private lateinit var binding:ListFragmentBinding    // ViewBinding
    private lateinit var adapter: Adapter               // Adapter
    lateinit var navController: NavController

    private var _form = MutableLiveData<Event<Boolean>>()
    val form: LiveData<Event<Boolean>> = _form

    var viewmodel = ListModel()

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

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(activity)
        var position = -1

        /** 삭제 버튼에 대한 이벤트 처리 */
        adapter.setOnItemclickListner(object: Adapter.OnItemClickListner {
            override fun onItemDeleteClick(pos: Int, todo: Todos) {
                viewmodel.deleteList(todo)
                position = pos
            }

            override fun onItemClick(pos: Int, todo:Todos) {
                val id = pos.toString()
                val content = todo.content
                val deadline = todo.deadline

                /** *각각의 키값에 매핑해서 데이터를 전달한다 */
                setFragmentResult("requestKey", bundleOf(
                    "id" to id,
                    "content" to content,
                    "deadline" to deadline))

                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.layout, ModifyFragment())
                    .commit()



                Log.i("test_click", "$pos 클릭완료!")
            }
        })

        /** viewmodel의 옵저버 */
        viewmodel.delete.observe(viewLifecycleOwner, Observer {
            // 삭제 완료 메시지를 출력하고 리스트를 다시 업데이트
            Toast.makeText(getActivity(), "삭제 완료!", Toast.LENGTH_SHORT).show()
            adapter.notifyItemRemoved(position)
        })

        /** 추가하기 버튼을 이용하여 이동 */
        /*binding.btnAdd.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.layout, AddFragment())
                .commit()
        }*/
        /** Navigation으로 Fragment 이동 */
        navController = Navigation.findNavController(view)
        binding.btnAdd.setOnClickListener {
            navController.navigate(R.id.action_navigation_todolist_to_addFragment)
        }

        binding.btnModify.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.layout, TakePictureFragment())
                .commit()
        }

        /** View모델의 목록을 출력하는 getList()함수 호출 */
        viewmodel.getList()
        /** 옵저버 */
        viewmodel.setList.observe(viewLifecycleOwner, Observer {
            adapter.setList(it.peekContent().todos)
            adapter.notifyItemRangeChanged(0,it.peekContent().total_post)
        })
    }
}