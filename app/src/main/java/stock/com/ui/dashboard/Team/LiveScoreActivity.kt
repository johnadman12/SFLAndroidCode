package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import com.madapps.liquid.LiquidRefreshLayout
import kotlinx.android.synthetic.main.include_back.*
import kotlinx.android.synthetic.main.live_score_activity.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.ContestDetail
import stock.com.ui.pojo.Scores
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class LiveScoreActivity : BaseActivity() {
    var contestid: Int = 0
    var mid: Int = 0
    var page: Int = 0
    var limit: Int = 50
    var flagRefresh: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.live_score_activity)
        StockConstant.ACTIVITIES.add(this)

        img_btn_back.setOnClickListener {
            onBackPressed();
        }
        if (intent != null) {
            contestid = intent.getIntExtra(StockConstant.CONTESTID, 0)
            mid = intent.getIntExtra(StockConstant.MARKETID, 0)
        }


        getScores()
        srl_layout.setOnRefreshListener {
            flagRefresh = true
            getScores()
        }


    }

    fun getScores() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<Scores> =
            apiService.getContestScore(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), contestid.toString()
                , getFromPrefsString(StockConstant.USERID).toString(), mid.toString(),
                page.toString(), limit.toString()
            )
        call.enqueue(object : Callback<Scores> {

            override fun onResponse(call: Call<Scores>, response: Response<Scores>) {
                srl_layout.isRefreshing = false
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        setScoreAdapter(response.body()!!.scores)
                        if (flagRefresh)
                            limit = limit + 50
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<Scores>, t: Throwable) {
                srl_layout.isRefreshing = false
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun setScoreAdapter(scores: ArrayList<Scores.Score>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycler_score!!.layoutManager = llm
        recycler_score!!.adapter = LiveScoreAdapter(
            this,
            getFromPrefsString(StockConstant.USERID)!!.toInt(), scores, 0
        )
    }

}