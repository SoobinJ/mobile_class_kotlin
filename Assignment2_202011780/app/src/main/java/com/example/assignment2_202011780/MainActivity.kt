package com.example.assignment2_202011780

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment2_202011780.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val data:ArrayList<MyData> = ArrayList()
    lateinit var adapter: MyDataAdapter
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSpinner()
        initRecyclerVeiw()
    }

    fun initSpinner(){
        binding.addSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val name = binding.date.text.toString()
                if(name!="") {
                    when(position){
                        1 ->{
                            data.add(MyData("감량",name))
                        }
                        2 ->{
                            data.add(MyData("유지",name))
                        }
                        3 ->{
                            data.add(MyData("증가",name))
                        }
                    }
                    adapter.notifyItemChanged(position)
                    binding.date.setText("")
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }
    }

    fun initRecyclerVeiw(){
        binding.recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        adapter = MyDataAdapter(data) //adapter 객체 명시적으로 생성
        adapter.itemClickListner = object :MyDataAdapter.OnItemClickListner{
            override fun OnItemClick(pos:Int) {
                adapter.deleteItem(pos)
            }
        }
        binding.recyclerview.adapter = adapter
    }
}