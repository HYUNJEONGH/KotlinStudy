package com.yjmocha.registerusers

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.View
import android.widget.ImageView
import com.yjmocha.registerusers.DB.DBHandler_Anko
import com.yjmocha.registerusers.DB.UserInfo
import kotlinx.android.synthetic.main.activity_save_user.*
import org.jetbrains.anko.image

/**
 * Created by yunjeonghwang on 2018. 1. 24..
 */
class SaveUserActivity():AppCompatActivity() {
    val mDBHandler = DBHandler_Anko(this)
    val REQ_PICK_IMAGE = 1010
    val REQ_PERMISSION = 1011
    var mSelectedImgId:Long = 0
    var id:Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_user)
        id = intent.getLongExtra("id", 0)
        if(id != 0L) {
            var user:UserInfo = mDBHandler.getUserwithId(id)
            etName.text = Editable.Factory.getInstance().newEditable(user.name)
            etAge.text = Editable.Factory.getInstance().newEditable(user.age)
            etTel.text = Editable.Factory.getInstance().newEditable(user.TelNum)
            imageView.image = getPicture(user.pic_path)
            btnAdd.visibility = View.GONE
            btnEdit.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun getPicture(path:String): Drawable?
    {
        val img_id = path.toLong()
        if(img_id == 0L) return null
        val bitmap:Bitmap? = MediaStore.Images.Thumbnails.getThumbnail(this.contentResolver, img_id, MediaStore.Images.Thumbnails.MICRO_KIND,null)
        bitmap?:return null
        return BitmapDrawable(this.resources, bitmap)
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        var notGranted = kotlin.arrayOfNulls<String>(permissions.size)
        when(requestCode)
        {
            REQ_PERMISSION->{
                var index:Int = 0
                for(i in 0..permissions.size-1) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        val rationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i])
                        if (!rationale) {
                            val dialogBuild = AlertDialog.Builder(this).setTitle("권한 설정")
                                    .setMessage("이미지 쌈네일을 만들기 위해서 저장권한이 필요합니다. 승인하지 않으면" +
                                            "이미지를 설정할 수 없습니다.")
                                    .setCancelable(true)
                                    .setPositiveButton("설정하러 가기") { dialog, whichButton ->
                                        showSetting()
                                    }
                            dialogBuild.create().show();
                            return
                        } else {
                            notGranted[index++] = permissions[i]
                        }
                    }
                }

                if (notGranted.isNotEmpty()) {
                    ActivityCompat.requestPermissions(this, notGranted, REQ_PERMISSION)
                }
            }
        }
    }

    fun showSetting()
    {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.fromParts("package", packageName, null))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            REQ_PICK_IMAGE->{
                val uri = data?.getData()
                uri?:return

                mSelectedImgId = getImageID(uri)
                if(mSelectedImgId == -1L) return
                val bitmap:Bitmap = MediaStore.Images.Thumbnails.getThumbnail(contentResolver, mSelectedImgId,
                        MediaStore.Images.Thumbnails.MICRO_KIND, null)
                val selImage:ImageView = findViewById(R.id.imageView) as ImageView
                selImage.setImageBitmap(bitmap)
            }

        }
    }

    fun getImageID(uri: Uri):Long
    {
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val cursor = contentResolver.query(uri, projection, null, null, null);
        val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID)

        if(columnIndex == -1)
            return -1

        cursor.moveToFirst()
        val id = cursor.getLong(columnIndex)
        cursor.close()
        return id
    }

    fun onClickSaveBtn(view: View)
    {
        //Kotlin extensions
        val user:UserInfo = UserInfo(etName.text.toString(), etAge.text.toString(),
                etTel.text.toString(), mSelectedImgId.toString())
        mDBHandler.addUser(user)
        mDBHandler.close()
        finish()
    }

    fun onClickEditBtn(view: View)
    {
        //TODO image를 수정하지 않을경우
        val user: UserInfo = UserInfo(etName.text.toString(), etAge.text.toString(),
                    etTel.text.toString(), mSelectedImgId.toString())
        if(id != 0L)
            mDBHandler.editUser(user, id)
        mDBHandler.close()
        finish()
    }
}