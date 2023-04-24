package com.example.assignment2_202011780

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2_202011780.databinding.RawBinding

class MyDataAdapter(val items:ArrayList<MyData>)
    :RecyclerView.Adapter<MyDataAdapter.ViewHolder>() {

    interface OnItemClickListner{
        fun OnItemClick(pos:Int)
    }

    var itemClickListner: OnItemClickListner?=null

        inner class ViewHolder(val binding: com.example.assignment2_202011780.databinding.RawBinding): RecyclerView.ViewHolder(binding.root){
            var imageView:ImageView = itemView.findViewById(R.id.imageView)
            var date:TextView = itemView.findViewById(R.id.date)
            var state:TextView = itemView.findViewById(R.id.state)
            init{
                binding.delete.setOnClickListener{
                    itemClickListner?.OnItemClick(adapterPosition)
                }
            }
        }

    fun deleteItem(position: Int){
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RawBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.date.text = items[position].date
        holder.state.text = items[position].state
        when(holder.state.text){
            "감량"->{
                holder.imageView.setImageResource(R.drawable.baseline_sentiment_satisfied_alt_24)
                holder.imageView.setColorFilter(Color.parseColor("#c6dbda"))
            }
            "유지"->{
                holder.imageView.setImageResource(R.drawable.baseline_sentiment_neutral_24)
                holder.imageView.setColorFilter(Color.parseColor("#f6eac2"))
            }
            "증가"->{
                holder.imageView.setImageResource(R.drawable.baseline_sentiment_very_dissatisfied_24)
                holder.imageView.setColorFilter(Color.parseColor("#fcb9aa"))
            }
        }
    }

}