package com.yjmocha.nationinfo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.nation_list_item.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    var mAdapter: NationAdpater? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvNationList.layoutManager = LinearLayoutManager(this)
        mAdapter = NationAdpater(this, listOf(
                NationData(R.drawable.l_flag_belgium, "벨기에", "브뤼셀"),
                NationData(R.drawable.l_flag_argentina, "아르헨티나", "부에노스아이레스"),
                NationData(R.drawable.l_flag_brazil, "브라질","브라질리아"),
                NationData(R.drawable.l_flag_canada, "캐나다", "오타와"),
                NationData(R.drawable.l_flag_china,"중국", "베이징")))
        mAdapter!!.setOnItemClickListener(this)
        rvNationList.adapter = mAdapter
    }

    override fun onClick(v: View?) {
        //findviewid 하여 온클릭할시 텍스트 업데이트 하고 가져오기
        val textView = v?.findViewById(R.id.tvNation) as TextView
        val name = textView.text ?: "None"
        val intent = Intent(this, NationDetailActivity::class.java)
        intent.putExtra(NationDetailActivity.EXTRA_NATION_NAME, name)
        startActivity(intent)
    }
}
