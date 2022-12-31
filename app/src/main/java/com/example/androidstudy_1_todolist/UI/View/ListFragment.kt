package com.example.androidstudy_1_todolist.UI.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.androidstudy_1_todolist.Data.DTO.Form
import com.example.androidstudy_1_todolist.Data.DTO.Todos
import com.example.androidstudy_1_todolist.Data.Repository
import com.example.androidstudy_1_todolist.Event
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
        var adapter = Adapter()
        binding.recyclerView.adapter = adapter

        val repo = Repository()

        /** deleteForm(id) 테스트 하기 */
        //repo.deleteForm(2)

        /** putForm(id) 테스트 하기 */
        //val testForm = Form("이현승 다녀감", "20000417")
        //repo.putForm(3, testForm)

        /** View모델의 목록을 출력하는 getList()함수 호출 */
        var viewmodel = ListModel()
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

    /** RecyclerView 확인용 데이터 입력 */
    val mDatas = mutableListOf<Todos>()
    private fun initRecycler() {
        adapter = Adapter()
        recyclerView.adapter = adapter

        mDatas.apply{
            add(Todos(_id="123", id=1, content="play", deadline="10"))
            adapter.datalist = mDatas
            adapter.notifyDataSetChanged()
        }
    }
}