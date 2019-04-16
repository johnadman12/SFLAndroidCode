package stock.com.ui.createTeam.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.activity_graph.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_all_contest.*
import kotlinx.android.synthetic.main.content_choose_c_vc.*
import kotlinx.android.synthetic.main.fragment_chart.*
import kotlinx.android.synthetic.main.stock_details_screen.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.comment.activity.CommentActivity
import stock.com.ui.contest.adapter.AllContestAdapter
import stock.com.ui.createTeam.adapter.StockListAdapter
import stock.com.ui.news.activity.NewsActivity

class StockDetailActivity : BaseActivity(){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.stock_details_screen)
        initViews()
        setListener()
    }

    private fun setListener() {
        view_comment.setOnClickListener {
            startActivity(Intent(this, CommentActivity::class.java))
        }

        view_news.setOnClickListener {
            startActivity(Intent(this, NewsActivity::class.java))
        }
    }

    private fun initViews() {
        setAdapter()


    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_stock_details!!.layoutManager = llm
        rv_stock_details!!.adapter = StockListAdapter(this)
    }
}