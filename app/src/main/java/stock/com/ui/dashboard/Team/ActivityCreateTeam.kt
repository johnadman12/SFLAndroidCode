package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_create_team.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.FilterPojo
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class ActivityCreateTeam : BaseActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.imgButtonWizard -> {

            }
            R.id.tvViewteam -> {
                startActivity(Intent(this@ActivityCreateTeam, ActivityViewTeam::class.java))
            }
        }
    }

    var exchangeId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)
        StockConstant.ACTIVITIES.add(this)
        initView()
    }

    private fun initView() {
        img_btn_back.setOnClickListener(this)
        tvViewteam.setOnClickListener(this)
        imgButtonWizard.setOnClickListener(this)
        if (intent != null) {
            exchangeId = intent.getStringExtra(StockConstant.EXCHANGEID)
        }
        getTeamlist()
    }

    fun getTeamlist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockTeamPojo> =
            apiService.getStockList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), exchangeId,
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<StockTeamPojo> {

            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        setStockTeamAdapter(response.body()!!.stock!!)

                    }
                } else {
                    Toast.makeText(
                        this@ActivityCreateTeam,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<StockTeamPojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivityCreateTeam,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun setStockTeamAdapter(stock: List<StockTeamPojo.Stock>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        rv_Players.visibility = View.VISIBLE
        rv_Players!!.adapter = StockTeamAdapter(this, stock);
    }


}