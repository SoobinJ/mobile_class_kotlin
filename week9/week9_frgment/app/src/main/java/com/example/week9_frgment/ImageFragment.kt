package com.example.week9_frgment

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.week9_frgment.databinding.FragmentImageBinding

class ImageFragment : Fragment() {
    var binding:FragmentImageBinding?=null //null값을 가질 수 있는 타입으로 선언해줘야 함!
    val model:MyViewModel by activityViewModels()
    val imglist = arrayListOf<Int>(R.drawable.img1,R.drawable.img2,R.drawable.img3)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    //view가 생성된 후 호출됨, 초기화시켜주기 적합
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply {
            imageView.setOnClickListener{
                if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                    val intent = Intent(activity,SecondActivity::class.java)
                    intent.putExtra("imgNum",model.selectedNum.value)
                    startActivity(intent)
                }
            }
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when(checkedId){
                    R.id.radioButton1->{
                        model.setLiveData(0)
                    }
                    R.id.radioButton2->{
                        model.setLiveData(1)
                    }
                    R.id.radioButton3->{
                        model.setLiveData(2)
                    }
                }
                imageView.setImageResource(imglist[model.selectedNum.value!!])
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }
}