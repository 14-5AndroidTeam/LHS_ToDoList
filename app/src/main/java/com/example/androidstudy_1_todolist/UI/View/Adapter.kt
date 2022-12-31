package com.example.androidstudy_1_todolist.UI.View

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidstudy_1_todolist.Data.DTO.Todos
import com.example.androidstudy_1_todolist.databinding.ItemRecyclerBinding

class Adapter : RecyclerView.Adapter<Adapter.MyViewHolder>() {

    var datalist = mutableListOf<Todos>()

    inner class MyViewHolder(private val binding: ItemRecyclerBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Todos) {
            binding.textToDo.text = data.content
            binding.textDueDate.text = data.deadline
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
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