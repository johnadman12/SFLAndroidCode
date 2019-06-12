package stock.com.ui.statistics

import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_statistics.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.StatisticsPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class StatisticsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistics)
        getStatstics()

        img_btn_back.setOnClickListener {
            onBackPressed();
        }

    }

    fun getStatstics() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StatisticsPojo> =
            apiService.getStatistics(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString()
                , getFromPrefsString(StockConstant.USERID).toString()

            )
        call.enqueue(object : Callback<StatisticsPojo> {
            override fun onResponse(call: Call<StatisticsPojo>, response: Response<StatisticsPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (response.body()!!.staticprofile!!.size > 0)
                            setData(response.body()!!.staticprofile!!.get(0))


                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<StatisticsPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }

        })
    }

    private fun setData(staticprofile: StatisticsPojo.Staticprofile) {
        tv_location.setText(staticprofile.address)
        tv_pro.setText(staticprofile.levelType)
        tv_user_name.setText(staticprofile.username)
        tv_total_winners.setText(staticprofile.username)
        tv_joined_contest.setText(staticprofile.contestJoined)
        tv_won_contest.setText(staticprofile.contestWon)
        tv_winning_percentage.setText(staticprofile.winningPer + "%")
        tv_contest_size.setText(staticprofile.contestLost)
        tv_total_spent.setText(staticprofile.totalSpent)
        Glide.with(this).load(staticprofile.profileImage).into(profile_image)

    }

}
