package com.example.togglebuttonex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.togglebuttonex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding=ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        init()
    }
    fun init(){
        binding.toggleButton.setOnCheckedChangeListener{ _, isChecked->
            if(isChecked)
                Toast.makeText(this,"Toggle On",Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this,"Toggle Off",Toast.LENGTH_SHORT).show()
        }
    }

}