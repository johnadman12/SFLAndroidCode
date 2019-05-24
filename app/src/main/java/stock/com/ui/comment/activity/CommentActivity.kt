package stock.com.ui.comment.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.stock_details_screen.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.comment.adapter.CommentAdapter

class CommentActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment)
        initViews()
        setListener()
    }


    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText("Comment")
        setAdapter()
        setMenu(false, false, false, false, false, false, false)
    }

    private fun setListener() {
        sent_comment.setOnClickListener {
            if (et_comment.text.toString().trim().isEmpty()) {
                displayToast("Please enter comment", "warning")
            } else {

            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_comment_list!!.layoutManager = llm
        rv_comment_list!!.adapter = CommentAdapter(this)
    }


}