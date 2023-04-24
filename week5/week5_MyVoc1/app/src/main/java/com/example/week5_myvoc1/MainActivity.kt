package com.example.week5_myvoc1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.week5_myvoc1.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val data:ArrayList<MyData> = ArrayList()
    lateinit var adapter: MyDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initRecyclerView()
    }

    fun initData(){
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while (scan.hasNextLine()){
            val word=scan.nextLine()
            val meaning = scan.nextLine()
            data.add(MyData(word,meaning))
        }
    }

    fun initRecyclerView(){
        binding.recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        adapter = MyDataAdapter(data) //adapter 객체 명시적으로 생성
        adapter.itemClickListner = object :MyDataAdapter.OnItemClickListner{ //adapter에 객체 생성
            override fun OnItemClick(data: MyData, position:Int) {
                data.isOpen=!data.isOpen
                adapter.notifyItemChanged(position)
//                Toast.makeText(this@MainActivity,data.meaning,Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerview.adapter = adapter //객체 초기화
    }

}