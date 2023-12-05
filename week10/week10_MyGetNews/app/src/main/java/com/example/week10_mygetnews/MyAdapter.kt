package com.example.week10_mygetnews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week10_mygetnews.databinding.RowBinding

class MyAdapter(val items:ArrayList<MyData>)
    :RecyclerView.Adapter<MyAdapter.MyViewHolder>()
{
        interface OnItemClickListener{
            fun OnItemclick(position: Int,data: MyData)
        }

    var itemClickListener:OnItemClickListener?=null

    inner class MyViewHolder(val binding:RowBinding):RecyclerView.ViewHolder(binding.root){
        init{
            binding.newstitle.setOnClickListener{
                itemClickListener?.OnItemclick(adapterPosition,items[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.newstitle.text = items[position].newstitle
    }
}