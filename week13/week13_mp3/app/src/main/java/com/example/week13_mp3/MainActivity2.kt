package com.example.week13_mp3

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.SystemClock
import com.example.week13_mp3.MainActivity2.ProgressThread
import com.example.week13_mp3.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    lateinit var binding: ActivityMain2Binding

    lateinit var songlist:Array<String>
    lateinit var song:String

    var runThread=false
    var thread: ProgressThread?=null

    lateinit var myBindingService:MyBindService
    var mBound=false

    var connection=object :ServiceConnection{
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as MyBindService.MyBinder
            myBindingService=binder.getService()
            mBound=true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mBound=false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        songlist= resources.getStringArray(R.array.songlist)
        song = songlist[0]

        binding.apply {
            listview.setOnItemClickListener { parent, view, position, id ->
                song = songlist[position]
                startPlay()
            }

            btnpaly.setOnClickListener {
                startPlay()
            }

            btnstop.setOnClickListener {
                stopPlay()
            }
        }

        val intent = Intent(this, MyBindService::class.java)
        bindService(intent,connection,Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(mBound){
            stopPlay()
            unbindService(connection)
        }
        mBound=false
    }

    private fun stopPlay() {
        if(mBound)
            myBindingService.stopPlay()
        runThread = false
        binding.progressBar.progress=0
    }

    private fun startPlay() {
        runThread=true
        if(thread==null || !thread!!.isAlive){
            myBindingService.startPlay(song)
            binding.progressBar.max=myBindingService.getMaxDuration()
            binding.progressBar.progress=0
            thread = ProgressThread()
            thread!!.start()
        }else{
            myBindingService.stopPlay()
            myBindingService.startPlay(song)
            binding.progressBar.max=myBindingService.getMaxDuration()
            binding.progressBar.progress=0
        }
    }

    inner class ProgressThread:Thread(){
        override fun run() {
            while(runThread){
                binding.progressBar.incrementProgressBy(1000)
                SystemClock.sleep(1000)
                if(binding.progressBar.progress == binding.progressBar.max) {
                    runThread = false
                    binding.progressBar.progress=0
                }
            }
        }
    }
}