package com.yjmocha.registerusers

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import org.jetbrains.anko.image

/**
 * Created by yunjeonghwang on 2018. 1. 22..
 */
data class ViewHolder(val pic:ImageView,
                      val name:TextView,
                      val tel:TextView,
                      val del:ImageButton)

enum class UserData(val index:Int){
    _id(0),
    Name(1),
    Age(2),
    TelNum(3),
    PicPath(4)
}

class UserListAdapter(context: Context, cursor: Cursor?): CursorAdapter(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER)
{
//    확장성, 사용성이 더 좋은 코드
//    constructor(context: Context, cursor: Cursor?):super(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER)
    val mContext = context
    private var onItemClick:View.OnClickListener? = null

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View
    {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mainView = inflater.inflate(R.layout.layout_user_list_item, parent, false);
        var holder:ViewHolder = ViewHolder(mainView.findViewById(R.id.profile),
                mainView.findViewById(R.id.name),
                mainView.findViewById(R.id.tel_num),
                mainView.findViewById(R.id.del_item))
        mainView.tag = holder
        mainView.setOnClickListener(onItemClick)
//              anonymous class를 받아주는 object 객체
//        mainView.setOnClickListener ( object: View.OnClickListener {
//            override fun onClick(p0: View?) {
//                val intent:Intent = Intent(mContext, SaveUserActivity::class.java)
//                intent.putExtra("id", holder.del.tag as Long)
//                mContext.startActivity(intent)
//            }
//        } )
        return mainView
    }

    override fun bindView(convertView: View, context: Context, cursor: Cursor) {
        val holder = convertView.tag as ViewHolder

        holder.name.text = String.format("%s (%d)", cursor.getString(UserData.Name.index), cursor.getInt(UserData.Age.index))
        holder.tel.text = cursor.getString(UserData.TelNum.index)
        val picture:Drawable = getPicture(cursor.getString(UserData.PicPath.index))?:context.getDrawable(android.R.drawable.ic_menu_gallery)
        holder.pic.image = picture
        //save cursor id
        holder.del.tag = cursor.getLong(UserData._id.index)
    }

    private fun getPicture(path:String): Drawable?
    {
        val img_id = path.toLong()
        if(img_id == 0L) return null

        val bitmap:Bitmap? = MediaStore.Images.Thumbnails.getThumbnail(mContext.contentResolver, img_id, MediaStore.Images.Thumbnails.MICRO_KIND,null)
        bitmap?:return null
        return BitmapDrawable(mContext.resources, bitmap)
    }

    fun setOnItemClickListener(l:View.OnClickListener)
    {
        onItemClick = l
    }

}
