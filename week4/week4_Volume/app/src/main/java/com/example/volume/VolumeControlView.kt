package com.example.volume

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.PI
import kotlin.math.atan2

class VolumeControlView(context: Context, attrs: AttributeSet?) :
    AppCompatImageView(context, attrs) {

    var mx = 0.0f
    var my = 0.0f
    var tx = 0.0f
    var ty = 0.0f
    var angle = 180.0f

    var listener: VolumeListener?=null
    // 어느 클래스에서 가져다 쓰든지 상관없이 독립적으로 사용하기 위해서 interface로 정의
    interface VolumeListener{
        fun onChanged(angle:Float):Unit  //가져다 쓰는 클래스에서 구현해야 하는 함수
    }
    fun setVolumeListener(listener: VolumeListener){ //멤버 초기화
        this.listener = listener
    }

    fun getAngle(x1:Float, y1: Float):Float{
        mx = x1-(width/2.0f)
        my = (height/2.0f)-y1
        return(atan2(mx,my)*180.0f/ PI).toFloat()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(event!=null){
            tx=event.getX(0) //터치한 첫번째 값 가져옴
            ty=event.getY(0)
            angle = getAngle(tx,ty)
            invalidate() //onDraw 다시 호출됨
            if(listener!=null){
                listener?.onChanged(angle)
            }
            return true
        }
        return return false
    }

    // 화면을 다시 그려줌
    override fun onDraw(canvas: Canvas?) {
        canvas?.rotate(angle, width/2.0f, height/2.0f) //이미지가 앵글만큼 회전
        super.onDraw(canvas)
    }
}