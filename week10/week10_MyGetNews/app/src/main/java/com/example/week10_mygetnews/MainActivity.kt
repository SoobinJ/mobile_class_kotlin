package com.example.week10_mygetnews

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week10_mygetnews.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.parser.Parser

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: MyAdapter
    val url ="https://news.daum.net"
    val rssUrl = "http://fs.jtbc.joins.com//RSS//culture.xml"
    val melonUrl = "https://www.melon.com/chart/index.htm"

    val scope = CoroutineScope(Dispatchers.IO)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    fun getnews(){
        scope.launch {
            adapter.items.clear() //배열 비움
            val doc = Jsoup.connect(url).get()
            //Log.i("check",doc.toString())
            val headLine = doc.select("ul.list_newsissue>li>div.item_issue>div>strong.tit_g>a")
            for(news in headLine){
                adapter.items.add(MyData(news.text(),news.absUrl("href")))
            }
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing=false
            }
        }
    }

    fun getRssNews(){
        scope.launch {
            adapter.items.clear() //배열 비움
            val doc = Jsoup.connect(rssUrl).parser(Parser.xmlParser()).get()
            val headLine = doc.select("item")
            for(news in headLine){
                adapter.items.add(MyData(news.select("title").text(),news.select("link").text()))
            }
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing=false
            }
        }
    }

    fun getMelon(){
        scope.launch {
            adapter.items.clear()
            val doc = Jsoup.connect(melonUrl).get()
            val headLine = doc.select("tr.lst50>td>div.wrap>div.wrap_song_info")
            val lst100 = doc.select("tr.lst100>td>div.wrap>div.wrap_song_info")

            for(news in headLine){
                if(news.select("div.rank01>span>a").text()!=""){
                    adapter.items.add(MyData(news.select("div.rank01>span>a").text(),news.select("div.rank02>span>a").text()))
                    }
                }
            for(news in lst100){
                if(news.select("div.rank01>span>a").text()!=""){
                    adapter.items.add(MyData(news.select("div.rank01>span>a").text(),news.select("div.rank02>span>a").text()))
                }
            }
            withContext(Dispatchers.Main){
                adapter.notifyDataSetChanged()
                binding.swipe.isRefreshing=false
            }
        }
    }

    private fun initLayout() {
        binding.swipe.setOnRefreshListener {
            binding.swipe.isRefreshing=true
            //getnews()
            //getRssNews()
            getMelon()
        }
        binding.recyclerView.layoutManager=
            LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        adapter = MyAdapter(ArrayList<MyData>())
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this,LinearLayoutManager.VERTICAL))
        adapter.itemClickListener = object :MyAdapter.OnItemClickListener{
            override fun OnItemclick(position: Int,data: MyData) {
//                val intent = Intent(ACTION_VIEW, Uri.parse(adapter.items[position].url))
//                startActivity(intent)
                Toast.makeText(this@MainActivity,data.url,Toast.LENGTH_SHORT).show()
            }
        }
        binding.recyclerView.adapter=adapter
        //getnews()
        //getRssNews()
        getMelon()
    }
}