package com.example.androidstudy_1_todolist.UI.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy_1_todolist.Data.DTO.Todos
import com.example.androidstudy_1_todolist.UI.ViewModel.ListModel
import com.example.androidstudy_1_todolist.databinding.ItemRecyclerBinding

class Adapter : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    var datalist = mutableListOf<Todos>()
    var viewmodel = ListModel()

    /** RecyclerView에는 ListView와 다르게 클릭리스너가 내장되어 있지 않다.
     * 그래서 추가로 CLickListner의 역할을 하는 interface를 만들어줘야 한다. */

    /** 커스텀 리스너 인터페이스 정의 */
    interface OnItemClickListner{
        // 삭제 아이콘 클릭시 이벤트 처리
        fun onItemDeleteClick(pos: Int, todo: Todos)
        // 아이템리스트 클릭시 이벤트 처리
        fun onItemClick(pos:Int, todo: Todos)
    }

    /** 리스너 인터페이스 객체를 전달하는 메서드 선언 */
    fun setOnItemclickListner(onItemClickListner: OnItemClickListner){
        itemClickListner = onItemClickListner
    }

    /** 전달된 객체를 저장할 변수 정의 */
    private lateinit var itemClickListner: OnItemClickListner

    inner class MyViewHolder(
        private val binding: ItemRecyclerBinding):
        RecyclerView.ViewHolder(binding.root) {

        /** 클릭 이벤트 핸들러 메소드에 커스텀 리스너 메소드 호출 */
        init {
            /** Delete버튼에 대한 이벤트 처리 */
            binding.btnDelete.setOnClickListener {
                val pos = bindingAdapterPosition
                itemClickListner.onItemDeleteClick(pos, datalist[pos])
            }

            /** 아이템 리스트 자체를 누르는 경우에 대한 이벤트 처리 */
            binding.item.setOnClickListener{
                val pos = bindingAdapterPosition
                itemClickListner.onItemClick(datalist[pos].id, datalist[pos])
            }
        }

        fun bind(data: Todos) {
            binding.textToDo.text = data.content
            binding.textDueDate.text = data.deadline
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter.MyViewHolder {
        val binding = ItemRecyclerBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int = datalist.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(datalist[position])
    }

    fun setList(input_list:List<Todos>){
        datalist.addAll(input_list)
    }
}