package com.example.week5_myvoc1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week5_myvoc1.databinding.RowBinding

class MyDataAdapter(val items:ArrayList<MyData>)
    :RecyclerView.Adapter<MyDataAdapter.ViewHolder>() {

    interface OnItemClickListner{
        //내가 호출하고 싶은 메서드
        fun OnItemClick(data: MyData, position: Int)
    }

    //멤버로 선언
    var itemClickListner:OnItemClickListner?=null

    inner  class ViewHolder(val binding: com.example.week5_myvoc1.databinding.RowBinding): RecyclerView.ViewHolder(binding.root){
        //초기화
        init {
            binding.textView.setOnClickListener{
                //현재 클릭한 정보를 넘겨줌, 메서드 호출
                itemClickListner?.OnItemClick(items[adapterPosition], adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.textView.text = items[position].word
        holder.binding.meaningView.text=items[position].meaning
        if(!items[position].isOpen)
            holder.binding.meaningView.visibility = View.GONE
        else
            holder.binding.meaningView.visibility = View.VISIBLE
    }
}