package com.example.week9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.week9.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val textarr = arrayListOf<String>("이미지","리스트","팀소개")
    //val imgarr= arrayListOf<Int>(R.drawable.baseline_brightness_3_24,R.drawable.baseline_brightness_2_24)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        binding.viewPager.adapter = MyViewPagerAdapter(this)
        TabLayoutMediator(binding.tapLayout,binding.viewPager){
            tab,pos->
            tab.text = textarr[pos]
            //tab.setIcon(imgarr[pos])
        }.attach()
    }
}