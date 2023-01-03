package com.example.androidstudy_1_todolist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Observer
import com.example.androidstudy_1_todolist.UI.View.ListFragment
import com.example.androidstudy_1_todolist.UI.ViewModel.ListModel
import com.example.androidstudy_1_todolist.databinding.FragmentModifyBinding

class ModifyFragment : Fragment() {
    private lateinit var binding: FragmentModifyBinding
    var viewmodel = ListModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var itemId:String

        /** ListFragment에서 pos값과 해당하는 todo값을 전달받았다. */
        setFragmentResultListener("requestKey") { key, bundle ->
            itemId = bundle.getString("id").toString()
            var itemContent = bundle.getString("content").toString()
            var itemDeadline = bundle.getString("deadline").toString()

            /** 전달받은 값을 화면의 content와 deadline에 띄워준다. */
            binding.textContent.setText(itemContent)
            binding.textDeadline.setText(itemDeadline)
        }

        /** cancel 버튼 이벤트 처리*/
        binding.btnCancel.setOnClickListener {
            parentFragmentManager
                .beginTransaction()
                .replace(R.id.layout, ListFragment())
                .commit()
        }

        /** submit 이벤트 처리 */
        binding.btnSubmit.setOnClickListener {
            /**공백데이터가 아닌 경우만 서버로 제출을 허용함*/
            if(binding.textContent.text.isEmpty() or binding.textDeadline.text.isEmpty()){
                /** 공백데이터인 경우 ToastMessage 출력 */
                Toast.makeText(getActivity(), "공백데이터 존재", Toast.LENGTH_SHORT).show()
            }
            else{
                viewmodel.putList(
                    // 선언 에러 나는 이유,,?
                    // 일단 임시 아이디 값 지정
                    1,
                    binding.textContent.text.toString(),
                    binding.textDeadline.text.toString())
            }
            /** 뷰모델에게 할일,기한 데이터를 뽑아서 전달한다 */

            /** 관찰하고 있는 뷰모델의 LiveData의 Observer */
            viewmodel.put.observe(viewLifecycleOwner, Observer {
                /**서버로 보낸 제출요청에 대한 응답이 성공인 것으로 확인됨.
                 * 따라서 메인화면으로 이동*/

                /** 수정 성공에 대한 토스트 메시지 띄우기 */
                Toast.makeText(getActivity(), "수정 성공!", Toast.LENGTH_SHORT).show()

                parentFragmentManager
                    .beginTransaction()
                    .replace(R.id.layout, ListFragment())
                    .commit()
            })
        }
    }
}