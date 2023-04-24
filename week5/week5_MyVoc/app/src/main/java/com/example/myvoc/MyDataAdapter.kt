package com.example.myvoc

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoc.databinding.RowBinding
class MyDataAdapter(val items:ArrayList<MyData>)
    :RecyclerView.Adapter<MyDataAdapter.ViewHolder>() {

    interface OnItemClickListner{
        //내가 호출하고 싶은 메서드
        fun OnItemClick(data: MyData)
    }

    //멤버로 선언
    var itemClickListner:OnItemClickListner?=null

    inner  class ViewHolder(val binding: com.example.myvoc.databinding.RowBinding): RecyclerView.ViewHolder(binding.root){
        //초기화
        init {
            binding.textView.setOnClickListener{
                //현재 클릭한 정보를 넘겨줌, 메서드 호출
                itemClickListner?.OnItemClick(items[adapterPosition])
            }
        }
    }

    //드래그 앤 드롭 이동
    fun moveItem(oldPos:Int, newPos: Int){
        val tmp = items[newPos]
        items[newPos]=items[oldPos]
        items[oldPos] = tmp
        notifyItemMoved(oldPos,newPos)
    }

    //스와이프할 때 데이터 삭제
    fun removeItem(pos:Int){
        items.removeAt(pos)
        notifyItemRemoved(pos) //갱신
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
    }
}