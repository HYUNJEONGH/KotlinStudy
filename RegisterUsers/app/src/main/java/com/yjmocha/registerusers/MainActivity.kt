package com.yjmocha.registerusers

import android.content.Intent
import android.database.Cursor
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ListView
import com.yjmocha.registerusers.DB.DBHandler_Anko
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_user_list_item.view.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var mAdapter:UserListAdapter? = null
    var mDBHandler:DBHandler_Anko = DBHandler_Anko(this)

    companion object {
        val REQUEST_ADD_USER = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var toolbar:Toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val newItems:Cursor = mDBHandler.getUserAllWithCursor()
        if (newItems?.count != 0) {
            mAdapter = UserListAdapter(this, newItems)
            mAdapter?.setOnItemClickListener(this)
            val userList:ListView = findViewById(R.id.userList) as ListView
            userList.adapter = mAdapter
        }
    }

    override fun onClick(p0: View?) {
        val intent:Intent = Intent(this, SaveUserActivity::class.java)
        intent.putExtra("id", userList.del_item.tag as Long)
        startActivityForResult(intent, REQUEST_ADD_USER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode)
        {
            REQUEST_ADD_USER -> {
                val newItems = mDBHandler.getUserAllWithCursor()
                if (mAdapter == null) {
                    mAdapter = UserListAdapter(this, newItems)
                    mAdapter?.setOnItemClickListener(this)
                    val userList:ListView = findViewById(R.id.userList) as ListView
                    userList.adapter = mAdapter
                }
                mAdapter?.changeCursor(newItems)
                mAdapter?.notifyDataSetInvalidated()
            }
        }
    }

    fun onClickDelete(view: View)
    {
        mDBHandler.deleteUser(view.tag as Long)
        val newItems = mDBHandler.getUserAllWithCursor()
        mAdapter?.changeCursor(newItems)

    }

    override fun onDestroy() {
        mAdapter?.cursor?.close()
        mDBHandler.close()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId)
        {
            R.id.add_user ->{
                val intent:Intent = Intent(this, SaveUserActivity::class.java)
                startActivityForResult(intent, REQUEST_ADD_USER)
            }
            R.id.anko ->{
                val intent:Intent = Intent(this, AnkoDSLActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
