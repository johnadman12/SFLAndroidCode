package stock.com.ui.dashboard.Lobby

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_code_join.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.contest.activity.ContestDetailActivity
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.ContestList
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog


class ActivityCodeJoin : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.btn_Join -> {
                codeJoinForContest()
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_join)
        StockConstant.ACTIVITIES.add(this)
        initViews()
    }

    private fun initViews() {
        img_btn_back.setOnClickListener(this)
        et_code_join.setOnClickListener(this)
        btn_Join.setOnClickListener(this)

    }


    fun codeJoinForContest() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> =
            apiService.codeJoinContest(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
//                getFromPrefsString(StockConstant.USERID).toString(),
                et_code_join.text.toString()

            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        var contestId = response.body()!!.contestid
                        Handler().postDelayed(Runnable {
                            displayToast("code Matched !!!", "sucess")
                            startActivity(
                                Intent(this@ActivityCodeJoin, ContestDetailActivity::class.java).putExtra(
                                    StockConstant.CONTESTID,
                                    contestId.toInt()
                                ).putExtra(
                                    StockConstant.EXCHANGEID,
                                    0
                                )
                            )
                            finish()

                        }, 100)

                    } else if (response.body()!!.status == "1") {
                        displayToast(response.body()!!.message, "error")
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
                d.dismiss()
            }
        })
    }

}



