package stock.com.ui.news.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_comment.*
import kotlinx.android.synthetic.main.activity_news_view.*
import kotlinx.android.synthetic.main.app_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.news.adapter.NewsAdapter

class NewsActivity : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_view)
        initViews()
        setListener()
    }


    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText("News")
        setAdapter()
        setMenu(false, false, false, false, false, false,false)
    }

    private fun setListener() {

    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_news_view!!.layoutManager = llm
        rv_news_view!!.adapter = NewsAdapter(this)
    }
}