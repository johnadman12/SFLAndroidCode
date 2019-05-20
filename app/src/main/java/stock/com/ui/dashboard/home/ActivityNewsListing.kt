package stock.com.ui.dashboard.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.madapps.liquid.LiquidRefreshLayout
import kotlinx.android.synthetic.main.activity_news_list.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.home.adapter.LatestNewsAdapter
import stock.com.ui.pojo.CityfalconNewsPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class ActivityNewsListing : BaseActivity() {
    var identifires: String = ""
    var newsStories: ArrayList<CityfalconNewsPojo.Story>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        newsStories = ArrayList()
        img_btn_back.setOnClickListener {
            onBackPressed()
        }
        ll_search.setOnClickListener {
            startActivity(Intent(this, ActivityNewsSearch::class.java))
        }

        if (intent != null)
            identifires = intent.getStringExtra(StockConstant.IDENTIFIRE)
        newsStories = intent.getSerializableExtra(StockConstant.NEWSLIST) as ArrayList<CityfalconNewsPojo.Story>?

        refreshData.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                //TODO make api call here
                Handler().postDelayed({
                }, 5000)
                getNewslist()
            }
        })
        if (newsStories != null)
            setLatestNewAdapter(newsStories!!)
    }


    val categories: String = "mp,op"
    val topic: String = "stocks,indices,commodities,cryptocurrencies"
    fun getNewslist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClientNews()!!.create(ApiInterface::class.java)
        val call: Call<CityfalconNewsPojo> =
            apiService.getHomeNews(
                topic, categories, "0",
                "latest", "d1", false,
                StockConstant.NEWS_ACCESS_TOKEN
            )
        call.enqueue(object : Callback<CityfalconNewsPojo> {
            override fun onResponse(call: Call<CityfalconNewsPojo>, response: Response<CityfalconNewsPojo>) {
                d.dismiss()
                if (refreshData != null)
                    refreshData.finishRefreshing()
                if (response.body() != null) {
                    d.dismiss()
                    newsStories = response.body()!!.stories
//                    setLatestNewAdapter(response.body()!!.stories)
                }
            }

            override fun onFailure(call: Call<CityfalconNewsPojo>, t: Throwable) {
                if (refreshData != null)
                    refreshData.finishRefreshing()
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun setLatestNewAdapter(news: ArrayList<CityfalconNewsPojo.Story>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_news!!.layoutManager = llm
        recycle_news.visibility = View.VISIBLE
        recycle_news!!.adapter = NewsListdapter(this, news, identifires);
    }

}