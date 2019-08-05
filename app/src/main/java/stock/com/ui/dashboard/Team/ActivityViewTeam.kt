package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_team.*
import kotlinx.android.synthetic.main.dialog_join_contest.*
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
import com.google.gson.JsonObject
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.dialogue_wallet_new.*
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.dashboard.Lobby.ActivityAddCash
import stock.com.ui.dashboard.Team.Stock.ActivityStockDetail
import stock.com.ui.pojo.ContestDetail


class ActivityViewTeam : BaseActivity(), View.OnClickListener {
    private var list: ArrayList<StockTeamPojo.Stock>? = null;
    private var ids: ArrayList<String>? = null;
    var activity: DashBoardActivity = DashBoardActivity()
    private var viewTeamAdapter: ViewTeamAdapter? = null;
    var array: JsonArray = JsonArray()
    var jsonparams: JsonObject = JsonObject()
    private var stockSelectedItems: ArrayList<StockTeamPojo.Stock>? = null
    var exchangeId: Int = 0
    var contestId: Int = 0
    var teamId: Int = 0
    var flagCloning: Int = 0
    var contestFee: String = ""
    var blank: String = ""
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.ll_sort -> {
                startActivityForResult(
                    Intent(this@ActivityViewTeam, ActivitySortTeam::class.java),
                    StockConstant.RESULT_CODE_SORT_VIEW_TEAM
                )
            }
            R.id.btn_Join -> {
                showJoinContestDialogue()
//                startActivity(Intent(this@ActivityViewTeam, ActivityJoiningConfirmation::class.java))
            }
            R.id.relFieldView -> {
                startActivity(
                    Intent(this@ActivityViewTeam, TeamPreviewActivity::class.java)
                        .putExtra(StockConstant.STOCKLIST, list)
                        .putExtra(StockConstant.TEAMNAME, edtTeamName.text.toString())
                        .putExtra(StockConstant.TOTALCHANGE, "0.0%")
                )
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
                    for (i in 0 until stockSelectedItems!!.size) {
                        var postData1 = JsonObject();
                        try {
                            postData1.addProperty("stock_id", stockSelectedItems!!.get(i).stockid.toString());
                            postData1.addProperty("price", stockSelectedItems!!.get(i).latestPrice.toString());
                            postData1.addProperty(
                                "change_percent",
                                stockSelectedItems!!.get(i).changePercent.toString()
                            );
                            postData1.addProperty("stock_type", stockSelectedItems!!.get(i).stock_type);
                            Log.e("savedlist", postData1.toString())
                        } catch (e: Exception) {

                        }
                        Log.d("finaldata", array.toString())
                        array.add(postData1)
                    }
                    saveTeamList()
                } else {
                    displayToast("please select Stock first", "warning")
                }
            }
        }
    }


    fun getContestDetail() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<ContestDetail> =
            apiService.getContestDetail(
                contestId.toString()
                , getFromPrefsString(StockConstant.USERID).toString(), ""
            )
        call.enqueue(object : Callback<ContestDetail> {

            override fun onResponse(call: Call<ContestDetail>, response: Response<ContestDetail>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        contestFee = response.body()!!.contest.get(0).entryFees
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<ContestDetail>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_team)
        activity = DashBoardActivity()
        list = ArrayList()
        ids = ArrayList()
        list!!.clear()
        if (intent != null) {
            list = intent.getParcelableArrayListExtra(StockConstant.STOCKLIST)
            contestId = intent.getIntExtra(StockConstant.CONTESTID, 0)
            exchangeId = intent.getIntExtra(StockConstant.EXCHANGEID, 0)
            teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
            flagCloning = intent.getIntExtra("isCloning", 0)

        }
        StockConstant.ACTIVITIES.add(this)
        initView()
        Log.e("updatedafterlist", list!!.get(0).addedToList.toString())
        viewTeamAdapter!!.notifyDataSetChanged()
        getContestDetail()
    }

    private fun initView() {
        stockSelectedItems = ArrayList();
        array = JsonArray()
        jsonparams = JsonObject()
        ivedit.setOnClickListener(this)
        img_btn_back.setOnClickListener(this)
        btn_Join.setOnClickListener(this)
        ll_save.setOnClickListener(this)
        ivRight.setOnClickListener(this)
        ll_sort.setOnClickListener(this)
        ll_sort.visibility = GONE

        relFieldView.setOnClickListener(this)

        stockSelectedItems = list
        viewTeamAdapter = ViewTeamAdapter(
            this, list as ArrayList, object : ViewTeamAdapter.OnItemCheckListener {

                override fun onRemoveIteam(item: StockTeamPojo.Stock) {
                    if (stockSelectedItems!!.size > 0) {
                        stockSelectedItems!!.remove(item)
                        setTeamText(stockSelectedItems!!.size)
                        val intent = Intent();
                        intent.putExtra("removedlist", list)
                        intent.putExtra("flag", "1")
                        setResult(Activity.RESULT_OK, intent);
                    }
                }

                override fun onToggleBuy(item: StockTeamPojo.Stock) {
                    for (i in 0 until stockSelectedItems!!.size) {
                        if (stockSelectedItems!!.get(i).stockid == item.stockid) {
                            stockSelectedItems!!.get(i).stock_type = item.stock_type
                        }
                    }
                }

                override fun onToggleSell(item: StockTeamPojo.Stock) {
                    for (i in 0 until stockSelectedItems!!.size) {
                        if (stockSelectedItems!!.get(i).stockid == item.stockid) {
                            stockSelectedItems!!.get(i).stock_type = item.stock_type
                        }
                    }
                }

                override fun onItemClick(item: StockTeamPojo.Stock) {

                    startActivityForResult(
                        Intent(
                            this@ActivityViewTeam,
                            ActivityStockDetail::class.java
                        )
                            .putExtra(StockConstant.STOCKID, item.stockid)
                            .putExtra("flag", 1)
                            .putExtra(StockConstant.STOCKLIST, list)
                            .putExtra(StockConstant.SELECTEDSTOCK, stockSelectedItems!!.size)
                        , StockConstant.RESULT_CODE_VIEW_TEAM
                    )
                }

                override fun onItemCheck(item: Int, itemcontest: StockTeamPojo.Stock) {
                    setTeamText(item)
                    val intent = Intent();
                    intent.putExtra("removedlist", list)
                    intent.putExtra("flag", "1")
                    setResult(Activity.RESULT_OK, intent);
                    Log.e("stocklist", stockSelectedItems!!.get(0).stockid.toString())
                }
            });
        setStockAdapter()
    }

    fun showJoinContestDialogue() {
        var dialogue = Dialog(this)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialog_join_contest)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.setCancelable(true)
        dialogue.setCanceledOnTouchOutside(true)
        dialogue.setTitle(null)
        dialogue.entreefee.setText(contestFee)
        dialogue.tvEntryFee.setText(contestFee)
        dialogue.tv_yes1.setOnClickListener {
            dialogue.dismiss()
            showDialogue()
        }

        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }


    fun joinContest() {
        if (stockSelectedItems!!.size > 0) {
            for (i in 0 until stockSelectedItems!!.size) {
                /*if (stockSelectedItems!!.get(i).addedStock.equals("yes")) {
                    stockSelectedItems!!.get(i).addedStock="0"
                } else if (stockSelectedItems!!.get(i).addedStock.equals("no")) {
                    stockSelectedItems!!.get(i).addedStock="1"
                }*/
                var postData: JsonObject = JsonObject()
                try {
                    postData.addProperty("stock_id", stockSelectedItems!!.get(i).stockid.toString());
                    postData.addProperty("price", stockSelectedItems!!.get(i).latestPrice.toString());
                    postData.addProperty("change_percent", stockSelectedItems!!.get(i).changePercent.toString());
                    postData.addProperty("stock_type", stockSelectedItems!!.get(i).stock_type);

                } catch (e: Exception) {

                }
                array.add(postData)
            }
//                    Log.e("savedlist", array.toString())

            if (flagCloning == 1)
                joinWithThisTeamID()
            else
                joinWithThisTeam()
        } else {
            displayToast("please select Stock first", "warning")
        }
    }

    public fun showDialogue() {
        var dialogue = Dialog(this)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialogue_wallet_new)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.window.setGravity(Gravity.BOTTOM)
        dialogue.setCancelable(true)
        dialogue.setCanceledOnTouchOutside(false)
        dialogue.setTitle(null)
        dialogue.imgbtnCancle.setOnClickListener {
            dialogue.dismiss()
        }
        dialogue.tv_yes.setOnClickListener {
            dialogue.dismiss()
            joinContest()
        }
        dialogue.txt_Withdraw.setOnClickListener {
            dialogue.dismiss()
            startActivity(Intent(this@ActivityViewTeam, ActivityAddCash::class.java))
        }

        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }

    @SuppressLint("WrongConstant")
    fun setStockAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_team!!.layoutManager = llm
        rv_team.visibility = View.VISIBLE
        rv_team!!.adapter = viewTeamAdapter;
    }

    fun saveTeamList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        jsonparams.addProperty("contest_id", contestId.toString())
        jsonparams.addProperty("team_id", "")
        jsonparams.addProperty("join_var", 0)
        jsonparams.addProperty("user_id", getFromPrefsString(StockConstant.USERID).toString())
        jsonparams.add("stocks", array)

        Log.e("savedlist", array.toString())

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
                            AppDelegate.showAlert(this@ActivityViewTeam, response.body()!!.message)
                        }, 500)
                        teamId = response.body()!!.team_id
                        finish()
                    } else if (response.body()!!.status == "0") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityViewTeam, response.body()!!.message)
                        }, 1000)

                    } else if (response.body()!!.status == "2") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityViewTeam, response.body()!!.message)
                        }, 1000)
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

    fun joinWithThisTeam() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        if (teamId == 0) {
            jsonparams.addProperty("team_id", "")
        } else {
            jsonparams.addProperty("team_id", teamId)
        }
        jsonparams.addProperty("contest_id", contestId.toString())
        jsonparams.addProperty("join_var", 1)
        jsonparams.addProperty("user_id", getFromPrefsString(StockConstant.USERID).toString())
        jsonparams.add("stocks", array)
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
                            AppDelegate.showAlert(this@ActivityViewTeam, response.body()!!.message)
                            var intent = Intent();
                            intent.putExtra("flag", "2")
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }, 1500)

                    } else if (response.body()!!.status == "0") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityViewTeam, response.body()!!.message)
                        }, 1500)
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


    fun joinWithThisTeamID() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        jsonparams.addProperty("contest_id", contestId.toString())
        jsonparams.addProperty("team_id", teamId)
        jsonparams.addProperty("join_var", 1)
        jsonparams.addProperty("user_id", getFromPrefsString(StockConstant.USERID).toString())
        jsonparams.add("stocks", array)
        val call: Call<BasePojo> =
            apiService.joinWithTeamId(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                jsonparams
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityViewTeam, response.body()!!.message)
                        }, 1000)
                        var intent = Intent();
                        intent.putExtra("flag", "2")
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else if (response.body()!!.status == "0") {
                        AppDelegate.showAlert(this@ActivityViewTeam, response.body()!!.message)
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

    fun setTeamText(total: Int) {
        if (total >= 12) {
            ll_save.isEnabled = true
            btn_Join.isEnabled = true
            save.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.white_fill_button))
            btn_Join.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.green_fill_button))
        } else {
            ll_save.isEnabled = false
            btn_Join.isEnabled = false
            save.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.grey_fill_button))
            btn_Join.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.grey_fill_button))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.RESULT_CODE_VIEW_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                list!!.clear()
                list!!.addAll(data.getParcelableArrayListExtra("list"))
                rv_team!!.adapter!!.notifyDataSetChanged()

            }
        } else if (requestCode == StockConstant.RESULT_CODE_SORT_VIEW_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                if (data.getStringExtra("flag").equals("Volume")) {

                    var sortedList = list!!.sortedBy { it.latestVolume.toDouble() }

                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_team!!.adapter!!.notifyDataSetChanged()
                        /* rv_Players!!.adapter = LobbyContestAdapter(context!!, sortedList)
                         rv_Players!!.adapter!!.notifyDataSetChanged()*/
                    }
                } else if (data.getStringExtra("flag").equals("price")) {
                    var sortedList = list!!.sortedWith(compareBy { it.latestPrice })
                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_team!!.adapter!!.notifyDataSetChanged()
                        /*rv_Players!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()*/
                    }
                } else if (data.getStringExtra("flag").equals("Alpha")) {
                    var sortedList = list!!.sortedBy { it.symbol?.toString() }
                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_team!!.adapter!!.notifyDataSetChanged()
                        /*rv_Players!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()*/
                    }
                }
            }
        }
    }

}