package com.yjmocha.registerusers

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageView
import android.widget.TextView

/**
 * Created by yunjeonghwang on 2018. 1. 22..
 */
data class ViewHolder(val pic:ImageView,
                      val name:TextView,
                      val tel:TextView,
                      val del:ImageView)

enum class UserData(val index:Int){
    _id(0),
    Name(1),
    Age(2),
    TelNum(3),
    PicPath(4)
}

class UserListAdapter: CursorAdapter
{
    constructor(context: Context, cursor: Cursor?):super(context, cursor, FLAG_REGISTER_CONTENT_OBSERVER)

    val mContext = context

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View
    {
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val mainView = inflater.inflate(R.layout.layout_user_list_item, parent, false);
        var holder:ViewHolder = ViewHolder(mainView.findViewById(R.id.profile),
                mainView.findViewById(R.id.name),
                mainView.findViewById(R.id.tel_num),
                mainView.findViewById(R.id.del_item))
        mainView.tag = holder
        return mainView
    }

    override fun bindView(convertView: View?, context: Context?, cursor: Cursor?) {
        val holder = convertView?.tag as ViewHolder

        holder.name.text = String.format("%s (%d)", cursor?.getString(UserData.Name.index), cursor?.getInt(UserData.Age.index))
        holder.tel.text = cursor?.getString(UserData.TelNum.index)
        //todo
        val picture:Drawable = getPicture(cursor?.getString(UserData.PicPath.index))?:context.getDrawable(android.R.drawable.ic_menu_gallery)
        holder.pic.background = picture
        //save cursor id
        holder.del.tag = cursor?.getLong(UserData._id.index)
    }

    private fun getPicture(path:String?): Drawable?
    {
        val img_id = path?.toLong()
        if(img_id == 0L) return null

        val bitmap:Bitmap = MediaStore.Images.Thumbnails.getThumbnail(mContext.contentResolver, img_id, MediaStore.Images.Thumbnails.MICRO_KIND,null)
        bitmap?:return null
        return BitmapDrawable(mContext.resources, bitmap)
    }

}
