package com.example.assignment3_202011780

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.assignment3_202011780.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val textArr = arrayListOf<String>("영상 검색","즐겨찾기")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.viewPager.adapter =MyViewPagerAdapter(this)
        TabLayoutMediator(binding.tapLayout,binding.viewPager){
            tab,pos->
            tab.text=textArr[pos]
        }.attach()
    }
}