package com.example.week10_mywebdata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.week10_mywebdata.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    val scope = CoroutineScope(Dispatchers.Main)
    lateinit var requestQueue:RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //initLayout()
        initLayout2()
    }

    private fun initLayout2() {
        requestQueue = Volley.newRequestQueue(this)
        binding.apply {
            button.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                val stringRequest = StringRequest(
                    Request.Method.GET,editText.text.toString(),Response.Listener {
                        textView.text = String(it.toByteArray(Charsets.ISO_8859_1),Charsets.UTF_8)
                        progressBar.visibility=View.GONE
                    },
                    Response.ErrorListener {
                        textView.text = it.message
                    }
                )
                requestQueue.add(stringRequest)
            }

        }
    }

    private fun initLayout() {
        binding.apply {
            button.setOnClickListener {
                Log.i("checkScope",Thread.currentThread().name)
                scope.launch {
                    Log.i("checkScope",Thread.currentThread().name)
                    progressBar.visibility=View.VISIBLE
                    var data=""
                    withContext(Dispatchers.IO){
                        data = loadNetwork(URL(editText.text.toString()))
                    }
//                    CoroutineScope(Dispatchers.IO).async {
//                        data = loadNetwork(URL(editText.text.toString()))
//                    }.await()
                    textView.text = data
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun loadNetwork(url: URL):String{
        var result=""
        val connect = url.openConnection() as HttpURLConnection
        connect.connectTimeout=4000
        connect.readTimeout=1000
        connect.requestMethod="GET"
        connect.connect()
        val responseCode=connect.responseCode
        if(responseCode==HttpURLConnection.HTTP_OK){
            result = streamToString(connect.inputStream)
        }
        return result
    }

    private fun streamToString(inputStream:InputStream):String{
        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line : String
        var result=""
        try {
            do{
                line = bufferReader.readLine()
                if(line!=null){
                    result+=line
                }
            }while (line!=null)
            Log.i("close","close")
            inputStream.close()
        }catch (ex:java.lang.Exception){
            Log.d("error","읽기 실패")
        }
        return result
    }
}