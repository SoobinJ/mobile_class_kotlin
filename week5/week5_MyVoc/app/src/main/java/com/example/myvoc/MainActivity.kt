package com.example.myvoc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myvoc.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val data:ArrayList<MyData> = ArrayList()
    lateinit var adapter: MyDataAdapter
    lateinit var tts:TextToSpeech
    var isTTsReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initData()
        initRecyclerView()
        initTTS()
    }

    fun initData(){
        val scan = Scanner(resources.openRawResource(R.raw.words))
        while (scan.hasNextLine()){
            val word=scan.nextLine()
            val meaning = scan.nextLine()
            data.add(MyData(word,meaning))
        }
    }

    override fun onStop() {
        super.onStop()
        if(isTTsReady)
            tts.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(isTTsReady)
            tts.shutdown()
    }

    fun initRecyclerView(){
        binding.recyclerview.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        adapter = MyDataAdapter(data) //adapter 객체 명시적으로 생성
        adapter.itemClickListner = object :MyDataAdapter.OnItemClickListner{ //adapter에 객체 생성
            override fun OnItemClick(data: MyData) {
                if(isTTsReady){
                    tts.speak(data.word,TextToSpeech.QUEUE_ADD,null,null)
                }
                Toast.makeText(this@MainActivity,data.meaning,Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerview.adapter = adapter //객체 초기화

        val simpleItemTouchCallback = object :
        ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                adapter.moveItem(viewHolder.adapterPosition,target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                adapter.removeItem(viewHolder.adapterPosition)
            }

        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerview)
    }

    fun initTTS(){
        tts=TextToSpeech(this){
            isTTsReady=true
            tts.language = Locale.US
        }
    }

}