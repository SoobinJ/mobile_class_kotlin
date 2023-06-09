package com.example.week9_dynamicfragment2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.week9_dynamicfragment2.databinding.FragmentImageBinding

class ImageFragment : Fragment() {
    var binding: FragmentImageBinding?=null //null값을 가질 수 있는 타입으로 선언해줘야 함!
    val model:MyViewModel by activityViewModels()
    val imglist = arrayListOf<Int>(R.drawable.img1,R.drawable.img2,R.drawable.img3)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.apply {
            imageView.setOnClickListener{
                val fragment = requireActivity().supportFragmentManager.beginTransaction()
                fragment.addToBackStack(null)
                val textFragment = TextFragment()
                fragment.replace(R.id.frameLayout, textFragment)
                fragment.commit()
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