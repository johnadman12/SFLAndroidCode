package stock.com.ui.dashboard.ContestNewBottom

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
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
    var exchangeId: Int = 0
    var marketId: Int = 0
    var contestId: Int = 0
    var page: Int = 0
    var limit: Int = 50
    var jsonparams: JsonObject = JsonObject()
    var flagMarket: Boolean = false
    var flagRefresh: Boolean = false
    var myTeamAdapter: MyTeamAdapter? = null
    var myTeams: ArrayList<MyTeamsPojo.Myteamss>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_team)
        StockConstant.ACTIVITIES.add(this)
        if (intent != null) {
            contestId = intent.getIntExtra(StockConstant.CONTESTID, 0)
            flagMarket = intent.getBooleanExtra("flagMarket", false)
        }
        if (flagMarket)
            marketId = intent.getIntExtra(StockConstant.MARKETID, 0)
        else
            exchangeId = intent.getIntExtra(StockConstant.EXCHANGEID, 0)
//        initView()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    private fun initView() {
        myTeams = ArrayList()
        jsonparams = JsonObject()
        setMyAdapter()
        img_btn_back.setOnClickListener {
            onBackPressed();
        }
        sr2_layout.setOnRefreshListener {
            flagRefresh = true
            if (flagMarket) {
                getMarketTeamlist()
            } else {
                getTeamlist()
            }

        }

        if (flagMarket)
            getMarketTeamlist()
        else
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
                exchangeId.toString(), page.toString(), limit.toString()
            )
        call.enqueue(object : Callback<MyTeamsPojo> {

            override fun onResponse(call: Call<MyTeamsPojo>, response: Response<MyTeamsPojo>) {
                d.dismiss()
                sr2_layout.isRefreshing = false
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        myTeams!!.clear()
                        myTeams!!.addAll(response.body()!!.myteams)
                        if (myTeamAdapter != null)
                            myTeamAdapter!!.notifyDataSetChanged()
                        if (flagRefresh)
                            limit = limit + 50
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<MyTeamsPojo>, t: Throwable) {
                sr2_layout.isRefreshing = false
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    fun getMarketTeamlist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MyTeamsPojo> =
            apiService.getMyMarketTeams(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID)!!,
                marketId.toString(), page.toString(), limit.toString()
            )
        call.enqueue(object : Callback<MyTeamsPojo> {
            override fun onResponse(call: Call<MyTeamsPojo>, response: Response<MyTeamsPojo>) {
                d.dismiss()
                sr2_layout.isRefreshing = false
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        myTeams!!.clear()
                        myTeams!!.addAll(response.body()!!.myteams)
                        if (myTeamAdapter != null)
                            myTeamAdapter!!.notifyDataSetChanged()

                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<MyTeamsPojo>, t: Throwable) {
                sr2_layout.isRefreshing = false
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    fun setMyAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rvMYTeam!!.layoutManager = llm
        rvMYTeam?.itemAnimator = DefaultItemAnimator()
        myTeamAdapter = MyTeamAdapter(this, myTeams!!, this@ActivityMyTeam, contestId)
        rvMYTeam!!.adapter = myTeamAdapter
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
                            AppDelegate.showAlert(this@ActivityMyTeam, response.body()!!.message)
                            getTeamlist()
                            myTeamAdapter!!.notifyDataSetChanged()
                        }, 100)

                    } else if (response.body()!!.status == "0") {
                        AppDelegate.showAlert(this@ActivityMyTeam, response.body()!!.message)
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }


    fun makeCloneMarket(teamId: Int, contestId: Int) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        jsonparams.addProperty("contest_id", contestId.toString())
        jsonparams.addProperty("team_id", teamId)
        jsonparams.addProperty("market_id", marketId)
        jsonparams.addProperty("join_var", 0)
        jsonparams.addProperty("user_id", getFromPrefsString(StockConstant.USERID).toString())

        val call: Call<BasePojo> =
            apiService.saveMarketTeam(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                jsonparams
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityMyTeam, response.body()!!.message)
                            getMarketTeamlist()
                            myTeamAdapter!!.notifyDataSetChanged()
                        }, 100)

                    } else if (response.body()!!.status == "0") {
                        AppDelegate.showAlert(this@ActivityMyTeam, response.body()!!.message)
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

}