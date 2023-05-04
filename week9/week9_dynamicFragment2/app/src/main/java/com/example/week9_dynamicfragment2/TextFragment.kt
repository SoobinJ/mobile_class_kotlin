package com.example.week9_dynamicfragment2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.week9_dynamicfragment2.databinding.FragmentTextBinding

class TextFragment : Fragment() {

    var binding: FragmentTextBinding?=null
    val data = arrayListOf<String>("ImageData1","ImageData2","ImageData3")
    val model:MyViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTextBinding.inflate(layoutInflater,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model.selectedNum.observe(viewLifecycleOwner, Observer {
            binding!!.textView.text = data[it]})
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding=null
    }

}