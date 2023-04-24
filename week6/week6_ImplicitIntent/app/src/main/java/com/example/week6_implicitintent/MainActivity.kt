package com.example.week6_implicitintent

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.example.week6_implicitintent.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
        //다중권한 처리, 한번에 전화, 카메라 권한 체, 모두 허용된 경우만 실행
        checkPermissions()
    }

    val permissions = arrayOf(android.Manifest.permission.CALL_PHONE,
    android.Manifest.permission.CAMERA)

    val multiplePermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            val resultPermission = it.all{ map->
                map.value
            }
            // 모든 권한 정보가 true인 경우에만 true로 반환됨
            if(!resultPermission){
                // 모든 권한이 true가 아닌 경우 종료함
                //finish()

                // 종료되지 않게 처리
                Toast.makeText(this,"모든 권한이 승인되어야 함",Toast.LENGTH_SHORT).show()
            }
        }

    fun checkPermissions(){
        when{
            (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE)
                    // 허용이된 경우
                    == PackageManager.PERMISSION_GRANTED)&&(ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) ->{
                Toast.makeText(this,"모든 권한 승인됨", Toast.LENGTH_SHORT).show()
            }
            (ActivityCompat.shouldShowRequestPermissionRationale(this,
                // 명시적으로 거절된 경우
                android.Manifest.permission.CALL_PHONE))||ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.CAMERA)
            ->{
                //alertDialog 보여줌
                permissionCheckAlertDlg()
            }
            // 처음 시작, 권한에 대한 정보가 없을 때
            else ->{
                multiplePermissionLauncher.launch(permissions)
            }
        }
    }

    fun permissionCheckAlertDlg(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 CAAL_PHONE과 CAMERA 권한이 모두 허용되어야 합니다.")
            .setTitle("권한체크")
            .setPositiveButton("OK"){
                //ok 버튼 누른 경우 다시 권한요청
                    _,_->
                multiplePermissionLauncher.launch(permissions)
            }.setNegativeButton("Cancel"){
                //dialog 종료
                    dlg,_-> dlg.dismiss()
            }
        //만들어 준 후 보여주기
        builder.create().show()
    }

    fun multiCallAction(){
        val number= Uri.parse("tel:010-1234-1234")
        val callIntent = Intent(Intent.ACTION_CALL, number)
        //둘중 하나를 거부한 경우 종료되지 않기 때문에 버튼을 클릭 시 검사
//        if(callphonePermissionGranted())
//            startActivity(callIntent)
//        else
//            checkPermissions()
        // -----------------
        //동시에 모든 권한 정보가 승인된 경우, 한개만 승인된 경우 실행되지 않음
        if(allPermissionGranted())
            startActivity(callIntent)
        else
            checkPermissions()
    }

    //모든 권한 정보 승인 여부 체크
    fun allPermissionGranted()=permissions.all{
        ActivityCompat.checkSelfPermission(this,it)==PackageManager.PERMISSION_GRANTED
    }
    //전화걸기에 대한 권한 정보 승인 여부
    fun callphonePermissionGranted()=ActivityCompat.checkSelfPermission(this,
        android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
    //카메라에 대한 권한 정보 승인 여부
    fun cameraPermissionGranted()=ActivityCompat.checkSelfPermission(this,
        android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED

//    --- 위에는 다중권한, 아래는 권한 한개

    val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
            if(it){
                callAction()
            } else{
                Toast.makeText(this, "권한 승인이 거부되었습니다", Toast.LENGTH_SHORT).show()
            }
    }

    fun callAlertDlg(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("반드시 CAAL_PHONE 권한이 허용되어야 합니다.")
            .setTitle("권한체크")
            .setPositiveButton("OK"){
                //ok 버튼 누른 경우 다시 권한요청
                _,_->
                permissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
            }.setNegativeButton("Cancel"){
                //dialog 종료
                dlg,_-> dlg.dismiss()
            }
        //만들어 준 후 보여주기
        builder.create().show()
    }

    fun callAction(){
        val number= Uri.parse("tel:010-1234-1234")
        val callIntent = Intent(Intent.ACTION_CALL, number)
        when{
            ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.CALL_PHONE)
                    // 허용이된 경우
                    == PackageManager.PERMISSION_GRANTED ->{
                        startActivity(callIntent)
                    }
            ActivityCompat.shouldShowRequestPermissionRationale(this,
                // 명시적으로 거절된 경우
                android.Manifest.permission.CALL_PHONE)
                    ->{
                        //alertDialog 보여줌
                        callAlertDlg()
                    }
            // 처음 시작, 권한에 대한 정보가 없을 때
            else ->{
                permissionLauncher.launch(android.Manifest.permission.CALL_PHONE)
            }
        }
    }

    fun initLayout(){
        binding.apply {

            //permission 적용 안함
//            callBtn.setOnClickListener {
//                val number= Uri.parse("tel:010-1234-1234")
//                val callIntent = Intent(Intent.ACTION_DIAL, number)
//                startActivity(callIntent)
//            }

            //permission
            callBtn.setOnClickListener{
                multiCallAction()
            }

            msgBtn.setOnClickListener{
                val message = Uri.parse("sms:010-1234-1234")
                val messageIntent = Intent(Intent.ACTION_SENDTO, message)
                messageIntent.putExtra("sms_body","빨리 다음꺼 하자./.")
                startActivity(messageIntent)
            }

            webBtn.setOnClickListener {
                val webPage = Uri.parse("http://www.naver.com")
                val webIntent = Intent(Intent.ACTION_VIEW, webPage)
                startActivity(webIntent)
            }

            mapBtn.setOnClickListener {
                val location=Uri.parse("geo:37.543684,127.077130?z=16")
                val mapIntent = Intent(Intent.ACTION_VIEW, location)
                startActivity(mapIntent)
            }

            cameraBtn.setOnClickListener {
                cameraAction()
            }
        }
    }

    fun cameraAction(){
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        //둘중 하나를 거부한 경우, 종료되지 않기 때문에 버튼을 클릭 시 검사
        if(cameraPermissionGranted())
            startActivity(intent)
        else
            checkPermissions()
    }
}