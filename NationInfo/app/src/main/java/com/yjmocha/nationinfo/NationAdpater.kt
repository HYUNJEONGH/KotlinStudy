package com.yjmocha.nationinfo

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.nation_list_item.view.*

/**
 * Created by yunjeonghwang on 2018. 2. 16..
 */
data class NationData (
        var resId: Int,
        var nation: String,
        var capital: String
)

class NationAdpater(context: Context, nationList: List<NationData>): RecyclerView.Adapter<NationAdpater.ViewHolder>() {
    var mInflater: LayoutInflater? = null
    val mContext = context
    val mNationList = nationList
    var itemClick: View.OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        mInflater = LayoutInflater.from(mContext)
        val view: View = mInflater!!.inflate(R.layout.nation_list_item, parent, false)
        view.setOnClickListener(itemClick)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = mNationList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: NationData = mNationList.get(position)
        var typedArray = mContext.resources.obtainTypedArray(R.array.nation_flag)
        holder.imgFlag.setImageResource(typedArray.getResourceId(position, -1))
        holder.nation.text = item.nation
        holder.capital.text = item.capital
        holder.nation.tag = position
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val imgFlag = view.imgFlag
        val nation = view.tvNation
        val capital = view.tvCaptital
    }

    fun setOnItemClickListener(listener: View.OnClickListener) {
        itemClick = listener
    }
}