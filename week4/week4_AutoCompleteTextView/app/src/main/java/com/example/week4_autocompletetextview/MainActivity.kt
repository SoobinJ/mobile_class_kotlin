package com.example.week4_autocompletetextview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.MultiAutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.week4_autocompletetextview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

//    val countries = arrayOf(
//        "Afghanistan","Albania","Algeria","American Samoa","Andorra","Angola","Auguilla",
//        "Antarctica","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan",
//        "Bahrain","Bangladesh","Barbados","Belarus","Belgium"
//    )

//    array는 수정할 수 없는 객체이므로 저장구조 수정, TextWatcher 실습할 때 변경됨
    val countries = mutableListOf(
        "Afghanistan","Albania","Algeria","American Samoa","Andorra","Angola","Auguilla",
        "Antarctica","Argentina","Armenia","Aruba","Australia","Austria","Azerbaijan",
        "Bahrain","Bangladesh","Barbados","Belarus","Belgium"
    )
    lateinit var countries2:Array<String>

    lateinit var adapter1: ArrayAdapter<String>
    lateinit var adapter2: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout(){
        countries2 = resources.getStringArray(R.array.countries_array)
        adapter1 = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,countries)
        adapter2 = ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,countries2)

        binding.autoCompleteTextView.setAdapter(adapter1)
        binding.autoCompleteTextView.setOnItemClickListener{adapterView,view,i,l->
            val item = adapterView.getItemAtPosition(i).toString()
            Toast.makeText(this,"선택된 나라: $item",Toast.LENGTH_SHORT).show()
        }

        binding.multiAutoCompleteTextView.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())
        binding.multiAutoCompleteTextView.setAdapter(adapter2)

        // 3번째 방식
        binding.editText.addTextChangedListener {
            val str = it.toString()
            binding.button.isEnabled = str.isNotEmpty()
        }

        // 2번째 방식, 필요한 부분만 오버라이딩해서 사용
//        binding.editText.addTextChangedListener(
//            afterTextChanged = {
//                val str = it.toString()
//                binding.button.isEnabled = str.isNotEmpty()
//            }
//        )

        // 1번째 방식
//        binding.editText.addTextChangedListener(object:TextWatcher{
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
////                TODO("Not yet implemented")
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
////                TODO("Not yet implemented")
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                val str = s.toString()
//                binding.button.isEnabled = str.isNotEmpty()
//            }
//
//        })

        binding.button.setOnClickListener{
            adapter1.add(binding.editText.text.toString()) //adapter에 add 해줘야함
            adapter1.notifyDataSetChanged() //화면에 보여주는 내용들이 갱신됨
            binding.editText.text.clear() //text도 지워줌
        }
    }
}