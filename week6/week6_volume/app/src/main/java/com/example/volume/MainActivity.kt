package com.example.volume

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.volume.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var mediaPlayer:MediaPlayer?=null
    var vol = 0.5f
    var flag = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    override fun onResume() {
        super.onResume()
        if(flag)
            mediaPlayer?.start()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayer?.pause()
    }

    fun initLayout(){
        //binding 객체 통하지 않고 사용 가능, apply 내에서
        binding.apply {
            imageView.setVolumeListener(object :VolumeControlView.VolumeListener{
                override fun onChanged(angle: Float) {
                    //angle값에 맞게 볼륨값 처리
                    vol = if(angle>0){
                        angle/360
                    }else{
                        (360+angle)/360
                    }
                    //볼륨 적용
                    mediaPlayer?.setVolume(vol,vol)
                }

            })
            playBtn.setOnClickListener{
                if(mediaPlayer==null){
                    //mediaPlayer 객체 생성
                    mediaPlayer=MediaPlayer.create(this@MainActivity, R.raw.song)
                    mediaPlayer?.setVolume(vol,vol)
                }
                mediaPlayer?.start()
                flag = true
            }
            pauseBtn.setOnClickListener {
                mediaPlayer?.pause()
                flag = false
            }
            stopBtn.setOnClickListener {
                mediaPlayer?.stop()
                mediaPlayer?.release() //메모리에서 사용하던 리소스들 사라짐
                mediaPlayer = null //null 값으로 초기화, 이후에 playBtn 누르면 새롭게 객체 생성됨
                flag = false
            }
        }
    }
}