package stock.com.ui.dashboard.ContestNewBottom

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
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
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.MyTeamsPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class ActivityMyTeam : BaseActivity() {
    var exchangeId:Int =0
    var jsonparams: JsonObject = JsonObject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_team)
        StockConstant.ACTIVITIES.add(this)
        if (intent!=null)
            exchangeId = intent.getIntExtra(StockConstant.EXCHANGEID, 0)
        initView()
    }

    private fun initView() {
        jsonparams = JsonObject()
        img_btn_back.setOnClickListener {
            onBackPressed();
        }

        refreshData.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                //TODO make api call here
                Handler().postDelayed({
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
                getFromPrefsString(StockConstant.USERID)!!,
                exchangeId.toString()
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

                    } else if (response.body()!!.status == "2") {
                        appLogout()
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
                if (refreshData != null)
                    refreshData.finishRefreshing()
                println(t.toString())
                Toast.makeText(
                    this@ActivityMyTeam,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong),"error")
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
        rvMYTeam!!.adapter = MyTeamAdapter(this, myteam, this@ActivityMyTeam)
    }

    fun makeClone(teamId: Int, contestId: Int) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        jsonparams.addProperty("contest_id", contestId.toString())
        jsonparams.addProperty("team_id", teamId)
        jsonparams.addProperty("join_var", 0)
        jsonparams.addProperty("user_id", getFromPrefsString(StockConstant.USERID).toString())

        val call: Call<BasePojo> =
            apiService.saveTeam(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                jsonparams
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        AppDelegate.showAlert(this@ActivityMyTeam, response.body()!!.message)
                        getTeamlist()
                        rvMYTeam!!.adapter!!.notifyDataSetChanged()
                    } else if (response.body()!!.status == "0") {
                        AppDelegate.showAlert(this@ActivityMyTeam, response.body()!!.message)
                    } else if (response.body()!!.status == "2") {
                        appLogout()
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

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivityMyTeam,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong),"error")
                d.dismiss()
            }
        })
    }

}