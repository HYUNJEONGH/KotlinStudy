package com.yjmocha.registerusers

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.yjmocha.registerusers.DB.DBHandler_Anko

/**
 * Created by yunjeonghwang on 2018. 1. 24..
 */
class SaveUserActivity():AppCompatActivity() {
    val mDBHandler = DBHandler_Anko(this)
    val REQ_PICK_IMAGE = 1010
    val REQ_PERMISSION = 1011

    var mSelectedImgId:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_user)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun onClickImage(view: View?)
    {
        val check = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (check != PackageManager.PERMISSION_GRANTED) {
//            JCF, arrayof 읽기전용 모드 객체
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQ_PERMISSION)
        } else {
//            암시적 인텐트
//            External의 의미는 외부 저장소가 아니라 안드로이드에서 어떤 앱이든지 사용할 수 있도록 설정해 둔 저장소
            val i = Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(i, REQ_PICK_IMAGE)
        }
    }

}