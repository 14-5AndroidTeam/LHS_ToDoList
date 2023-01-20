package com.example.androidstudy_1_todolist.UI.View

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.androidstudy_1_todolist.R
import com.example.androidstudy_1_todolist.UI.View.navigation.ListFragment
import com.example.androidstudy_1_todolist.UI.ViewModel.AddModel
import com.example.androidstudy_1_todolist.databinding.AddFragmentBinding

class AddFragment : Fragment() {
    /** ViewBinding*/
    private lateinit var binding:AddFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddFragmentBinding.inflate(inflater, container, false)
        var view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /** View에 대한 ViewModel 가져오기 */
        var viewmodel = AddModel()

        /** Cancel 버튼을 이용해 List화면으로 전환 */
        binding.btnCancel.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.layout, ListFragment())
                .commit()
        }

        /** Submit 버튼을 이용한 ToDoList 추가하기 */
        binding.btnSubmit.setOnClickListener {
            /**공백데이터가 아닌 경우만 서버로 제출을 허용함*/
            if(binding.textContent.text.isEmpty() or binding.textDeadline.text.isEmpty()){
                /** 공백데이터인 경우 ToastMessage 출력 */
                Toast.makeText(getActivity(), "공백데이터 존재", Toast.LENGTH_SHORT).show()
            }
            else{
                viewmodel.postForm(
                    binding.textContent.text.toString(),
                    binding.textDeadline.text.toString())
            }
            /**뷰모델에게 할일,기한 데이터를 뽑아서 전달한다*/

            /**관찰하고 있는 뷰모델의 LiveData의 Observer*/
            viewmodel.submit.observe(viewLifecycleOwner, Observer {
                /**서버로 보낸 제출요청에 대한 응답이 성공인 것으로 확인됨.
                 * 따라서 메인화면으로 이동*/

                /** 추가 성공에 대한 토스트 메시지 띄우기 */
                Toast.makeText(getActivity(), "추가 성공!", Toast.LENGTH_SHORT).show()

                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.layout, ListFragment())
                    .commit()
            })
        }
    }
}