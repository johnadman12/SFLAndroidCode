package stock.com.ui.createTeam.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_all_contest.*
import kotlinx.android.synthetic.main.content_choose_c_vc.*
import kotlinx.android.synthetic.main.stock_details_screen.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.comment.activity.CommentActivity
import stock.com.ui.contest.adapter.AllContestAdapter
import stock.com.ui.createTeam.adapter.StockListAdapter
import stock.com.ui.news.activity.NewsActivity

class StockDetailActivity : BaseActivity(){

    val xVals_earnings = ArrayList<String>()
    val total_earnings = ArrayList<Entry>()

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
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText("Details")
        setMenu(false, false, false, false, true, false, false)
        setAdapter()
        ll_graph_show.setOnClickListener {
            startActivity(Intent(this, GraphActivity::class.java))
        }

        for(i in 0 until 10) {
            xVals_earnings.add(i.toString())
            val total_sale_value = i.toDouble()
            val v1e1 = Entry(total_sale_value.toFloat(), i)
            total_earnings.add(v1e1)
        }

        // Line Graph
        val barDataSet = LineDataSet(total_earnings, "")
        val data = LineData(xVals_earnings, barDataSet)
        chart.data = data
        chart.animateXY(500, 500)
        chart.legend.isEnabled = false
        chart.setTouchEnabled(true)
        chart.isDoubleTapToZoomEnabled = false
        chart.setMaxVisibleValueCount(0)
        chart.setDescription("")
        barDataSet.color = ContextCompat.getColor(this, R.color.colorPrimary)
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.axisLeft.setDrawGridLines(false)
        xAxis.textColor = ContextCompat.getColor(this, R.color.black)
        val yAxis = chart.axisLeft
        yAxis.setAxisMinValue(0f)
        yAxis.textColor = ContextCompat.getColor(this, R.color.black)
        val rightAxis = chart.axisRight
        rightAxis.setDrawLabels(false)


    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_stock_details!!.layoutManager = llm
        rv_stock_details!!.adapter = StockListAdapter(this)
    }
}