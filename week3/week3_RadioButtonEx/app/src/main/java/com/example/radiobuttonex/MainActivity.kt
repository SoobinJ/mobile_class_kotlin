package com.example.radiobuttonex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import com.example.radiobuttonex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    fun init(){
        val radioGroup = binding.radioGroup
        radioGroup.setOnCheckedChangeListener{
            radioGroup,checkedId->
            when(checkedId){
                R.id.radioButton1->binding.imageView.setImageResource(R.drawable.img1)
                R.id.radioButton2->binding.imageView.setImageResource(R.drawable.img2)
                R.id.radioButton3->binding.imageView.setImageResource(R.drawable.img3)
            }
        }
    }

    var posX:Float=0.0f
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN->{
                posX=event.rawX
            }
            MotionEvent.ACTION_UP->{
                val distX=posX-event.rawX
                if(distX>0){
                    if(binding.radioButton1.isChecked){
                        binding.radioButton2.isChecked=true
                    } else if(binding.radioButton2.isChecked){
                        binding.radioButton3.isChecked=true
                    } else if(binding.radioButton3.isChecked){
                        binding.radioButton1.isChecked=true
                    }

                }else if(distX<0){
                    if(binding.radioButton1.isChecked){
                        binding.radioButton3.isChecked=true
                    } else if(binding.radioButton2.isChecked){
                        binding.radioButton1.isChecked=true
                    } else if(binding.radioButton3.isChecked){
                        binding.radioButton2.isChecked=true
                    }
                }
            }
        }
        return  true
    }
}