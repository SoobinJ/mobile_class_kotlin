package com.example.week13_myservice

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {
    var thread:Thread?=null
    var num=0

    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(thread==null){
            thread=object :Thread("MyThread"){
                override fun run(){
                    try{
                        for(i in 0..10){
                            Log.i("MyThread $num","Count : $i")
                            Thread.sleep(1000)
                        }
                    }catch (e:InterruptedException){
                        Thread.currentThread().interrupt()
                    }
                    thread=null
                }
            }
            thread!!.start()
            num++
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        if(thread!=null){
            thread!!.interrupt()
        }
        thread=null
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}