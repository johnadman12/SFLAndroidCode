package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_view_team.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.dashboard.Team.Stock.ActivityStockDetail
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.StockConstant

class ActivityMyTeamView : BaseActivity(), View.OnClickListener {
    private var list: ArrayList<StockTeamPojo.Stock>? = null;
    private var ids: ArrayList<String>? = null;
    var activity: DashBoardActivity = DashBoardActivity()
    private var viewTeamAdapter: MyTeamViewAdapter? = null;
    var array: JsonArray = JsonArray()
    var postData: JsonObject = JsonObject()
    var jsonparams: JsonObject = JsonObject()
    private var stockSelectedItems: ArrayList<StockTeamPojo.Stock>? = null
    var teamId: Int = 0
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.btn_Join -> {
            }
            R.id.relFieldView -> {
                startActivity(
                    Intent(this@ActivityMyTeamView, TeamPreviewActivity::class.java)
                        .putExtra(StockConstant.STOCKLIST, list)
                )
            }
            R.id.ivedit -> {
                edtTeamName.isEnabled = true
                ivRight.visibility = View.VISIBLE
                ivedit.visibility = View.GONE
            }
            R.id.ivRight -> {
                edtTeamName.isEnabled = false
                ivedit.visibility = View.VISIBLE
                ivRight.visibility = View.GONE
            }
            R.id.ll_save -> {
                if (stockSelectedItems!!.size > 0) {
                    for (i in 0 until stockSelectedItems!!.size) {
                        try {
                            postData.addProperty("stock_id", stockSelectedItems!!.get(i).stockid.toString());
                            postData.addProperty("price", stockSelectedItems!!.get(i).latestPrice.toString());
                            postData.addProperty("stock_status", stockSelectedItems!!.get(i).addedStock);
                            Log.e("savedlist", stockSelectedItems!!.get(i).addedStock)

                        } catch (e: Exception) {

                        }
                        array.add(postData)
                    }
                } else {
                    displayToast("please select Stock first")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_my_team)
        activity = DashBoardActivity()
        list = ArrayList()
        ids = ArrayList()
        if (intent != null) {
            list = intent.getParcelableArrayListExtra(StockConstant.STOCKLIST)
            teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
        }
        StockConstant.ACTIVITIES.add(this)
        initView()
        Log.e("updatedafterlist", list!!.get(0).addedToList.toString())
        viewTeamAdapter!!.notifyDataSetChanged()

    }

    private fun initView() {
        stockSelectedItems = ArrayList();
        array = JsonArray()
        postData = JsonObject()
        jsonparams = JsonObject()
        ivedit.setOnClickListener(this)
        img_btn_back.setOnClickListener(this)
        relFieldView.setOnClickListener(this)
        stockSelectedItems = list
        viewTeamAdapter = MyTeamViewAdapter(
            this, list as ArrayList, object : MyTeamViewAdapter.OnItemCheckListener {
                override fun onItemClick(item: StockTeamPojo.Stock) {
                    startActivity(
                        Intent(
                            this@ActivityMyTeamView,
                            ActivityStockDetail::class.java
                        )
                            .putExtra("Stockid", item.stockid)
                            .putExtra(StockConstant.STOCKLIST, list)
                    )
                }

                override fun onItemCheck(item: Int, itemcontest: StockTeamPojo.Stock) {
                    startActivity(
                        Intent(this@ActivityMyTeamView, ActivityEditTeam::class.java)
                            .putExtra(StockConstant.STOCKLIST, list)
                            .putExtra(StockConstant.CONTESTID, 0)
                            .putExtra(StockConstant.TEAMID, teamId)
                    )
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
        rv_team!!.adapter = viewTeamAdapter;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.RESULT_CODE_VIEW_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                list!!.clear()
                list!!.addAll(data.getParcelableArrayListExtra("list"))
                rv_team!!.adapter!!.notifyDataSetChanged()

            }
        }
    }

}