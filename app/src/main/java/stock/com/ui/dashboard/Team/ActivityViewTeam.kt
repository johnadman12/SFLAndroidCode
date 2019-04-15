package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_team.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class ActivityViewTeam : BaseActivity(), View.OnClickListener {
    private var list: ArrayList<StockTeamPojo.Stock>? = null;
    private var ids: ArrayList<String>? = null;
    private var wizardStockTeamAdapter: WizardStockTeamAdapter? = null;
    private var stockSelectedItems: ArrayList<StockTeamPojo.Stock>? = null
    var exchangeId: String = ""
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.relFieldView -> {
                startActivity(Intent(this@ActivityViewTeam, TeamPreviewActivity::class.java))
            }
            R.id.ivedit -> {
                edtTeamName.isEnabled = true
                ivRight.visibility = VISIBLE
                ivedit.visibility = GONE
            }
            R.id.ivRight -> {
                edtTeamName.isEnabled = false
                ivedit.visibility = VISIBLE
                ivRight.visibility = GONE
            }
            R.id.ll_save -> {
                if (stockSelectedItems!!.size > 0) {
                    for (i in 0 until stockSelectedItems!!.size)
                        ids!!.add(stockSelectedItems!!.get(i).stockid.toString())
                    Log.e("savedlist", ids.toString())
                    saveTeamList()
                } else {
                    displayToast("please select Stock first")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_team)
        list = ArrayList()
        ids = ArrayList()
        if (intent != null)
            list = intent.getParcelableArrayListExtra(StockConstant.STOCKLIST)
        StockConstant.ACTIVITIES.add(this)
        initView()
    }

    private fun initView() {
        stockSelectedItems = ArrayList();
        ivedit.setOnClickListener(this)
        img_btn_back.setOnClickListener(this)
        ll_save.setOnClickListener(this)
        ivRight.setOnClickListener(this)
        relFieldView.setOnClickListener(this)
        if (intent != null) {
            exchangeId = intent.getStringExtra(StockConstant.EXCHANGEID)
        }
        stockSelectedItems = list
        wizardStockTeamAdapter = WizardStockTeamAdapter(
            this, list as ArrayList,
            object : WizardStockTeamAdapter.OnItemCheckListener {
                override fun onItemUncheck(item: StockTeamPojo.Stock) {
                    stockSelectedItems?.remove(item);
                }

                override fun onItemCheck(item: StockTeamPojo.Stock) {
                    stockSelectedItems?.add(item);
                }
            });
        setStockAdapter()
    }

    @SuppressLint("WrongConstant")
    fun setStockAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_team!!.layoutManager = llm
        rv_team.visibility = View.VISIBLE
        rv_team!!.adapter = wizardStockTeamAdapter;
    }

    fun saveTeamList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> =
            apiService.saveTeam(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                exchangeId,
                "",
                ids.toString(),
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        AppDelegate.showAlert(this@ActivityViewTeam, response.message())
                    }
                } else {
                    Toast.makeText(
                        this@ActivityViewTeam,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivityViewTeam,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }
}