package stock.com.ui.dashboard.ContestNewBottom

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.madapps.liquid.LiquidRefreshLayout
import kotlinx.android.synthetic.main.content_myteam.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.MyTeamsPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class ActivityMyTeam : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_team)
        StockConstant.ACTIVITIES.add(this)
        initView()
    }

    private fun initView() {
        img_btn_back.setOnClickListener {
            onBackPressed();
        }

        refreshData.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                //TODO make api call here
                Handler().postDelayed({
                    refreshData.finishRefreshing()
                }, 5000)
                getTeamlist()
            }
        })
        getTeamlist()
    }

    fun getTeamlist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MyTeamsPojo> =
            apiService.getMyTeams(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID)!!
            )
        call.enqueue(object : Callback<MyTeamsPojo> {

            override fun onResponse(call: Call<MyTeamsPojo>, response: Response<MyTeamsPojo>) {
                d.dismiss()
                if (refreshData != null)
                    refreshData.finishRefreshing()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
//                        displayToast(list!!.size.toString())
                        setMyAdapter(response.body()!!.myteam)
                        //  setStockTeamAdapter(response.body()!!.stock!!)

                    }
                } else {
                    Toast.makeText(
                        this@ActivityMyTeam,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<MyTeamsPojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivityMyTeam,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    fun setMyAdapter(myteam: MutableList<MyTeamsPojo.Myteam>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rvMYTeam!!.layoutManager = llm
        rvMYTeam?.itemAnimator = DefaultItemAnimator()
        rvMYTeam!!.adapter = MyTeamAdapter(this, myteam)
    }
}