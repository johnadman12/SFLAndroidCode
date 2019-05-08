package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_edit_team.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.dashboard.Team.Stock.ActivityStockDetail
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.StockTeamPojo
import stock.com.ui.watch_list.WatchListActivity
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.util.ArrayList

class ActivityEditTeam : BaseActivity(), View.OnClickListener {
    private var stockSelectedItems: ArrayList<StockTeamPojo.Stock>? = null
    private var stockTeamAdapter: EditTeamAdapter? = null;
    private var list: ArrayList<StockTeamPojo.Stock>? = null;
    var flag: Boolean = false
    var array: JsonArray = JsonArray()
    var teamId: Int = 0
    var jsonparams: JsonObject = JsonObject()
    var exchangeId: Int = 0
    var contestId: Int = 0

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.tvViewteam -> {
                if (stockSelectedItems!!.size > 0) {
                    for (i in 0 until stockSelectedItems!!.size) {
                        var postData1 = JsonObject();
                        try {
                            postData1.addProperty("stock_id", stockSelectedItems!!.get(i).stockid.toString());
                            postData1.addProperty("price", stockSelectedItems!!.get(i).latestPrice.toString());
                            postData1.addProperty("stock_status", stockSelectedItems!!.get(i).stock_type);
                            Log.e("savedlist", postData1.toString())
                        } catch (e: Exception) {

                        }
                        Log.d("finaldata", array.toString())
                        array.add(postData1)
                    }
                    saveTeamList()
                } else {
                    displayToast("please select Stock first")
                }
            }
            R.id.relFieldView -> {
                startActivity(
                    Intent(this@ActivityEditTeam, TeamPreviewActivity::class.java)
                        .putExtra(StockConstant.STOCKLIST, list)
                )
            }
            R.id.ll_watchlist -> {
                startActivity(
                    Intent(this@ActivityEditTeam, WatchListActivity::class.java)
                )
            }
            R.id.ll_sort -> {
                startActivityForResult(
                    Intent(this@ActivityEditTeam, ActivitySortTeam::class.java),
                    StockConstant.RESULT_CODE_SORT_EDIT_TEAM
                )
            }

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_team)
        StockConstant.ACTIVITIES.add(this)
        list = ArrayList();
        array = JsonArray()
        jsonparams = JsonObject()
        initView()
    }


    @SuppressLint("WrongConstant")
    private fun initView() {
        stockSelectedItems = ArrayList();
        img_btn_back.setOnClickListener(this)
        tvViewteam.setOnClickListener(this)
        ll_watchlist.setOnClickListener(this)
        relFieldView.setOnClickListener(this)
        ll_sort.setOnClickListener(this)
        tvViewteam.isEnabled = true
        if (intent != null) {
            stockSelectedItems = intent.getParcelableArrayListExtra(StockConstant.STOCKLIST)
            teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
            contestId = intent.getIntExtra(StockConstant.CONTESTID, 0)
            exchangeId = intent.getIntExtra(StockConstant.EXCHANGEID, 0)
        }

        setTeamText(stockSelectedItems!!.size.toString())
        stockTeamAdapter = EditTeamAdapter(
            this, list as ArrayList,
            object : EditTeamAdapter.OnItemCheckListener {
                override fun onItemClick(item: StockTeamPojo.Stock) {
                    startActivityForResult(
                        Intent(
                            this@ActivityEditTeam,
                            ActivityStockDetail::class.java
                        )
                            .putExtra("Stockid", item.stockid)
                            .putExtra(StockConstant.STOCKLIST, list)
                        , StockConstant.RESULT_CODE_EDIT_TEAM
                    )
                }

                override fun onItemUncheck(item: StockTeamPojo.Stock) {
                    stockSelectedItems?.remove(item);
                    setTeamText(stockSelectedItems!!.size.toString())
                    Log.e("stocklistremove", stockSelectedItems.toString())
                }

                override fun onItemCheck(item: StockTeamPojo.Stock) {
                    stockSelectedItems?.add(item);
                    setTeamText(stockSelectedItems!!.size.toString())
                    Log.e("stocklist", stockSelectedItems.toString())
                }
            });

        et_search_stock.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                stockTeamAdapter!!.getFilter().filter(s);
            }
        })

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        rv_Players.visibility = View.VISIBLE
        rv_Players!!.adapter = stockTeamAdapter;
        getTeamlist()

    }

    fun getTeamlist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockTeamPojo> =
            apiService.getStockList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), exchangeId/*.toString()*/,
                getFromPrefsString(StockConstant.USERID)!!.toInt()/*.toString()*/, ""
            )
        call.enqueue(object : Callback<StockTeamPojo> {
            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        list!!.clear()
                        rv_Players!!.adapter!!.notifyDataSetChanged();
                        list!!.addAll(response.body()!!.stock!!);
                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
                        }
//                        rv_Players.adapter!!.notifyDataSetChanged();
                        for (i in 0 until list!!.size) {
                            for (j in 0 until stockSelectedItems!!.size) {
                                if (list!!.get(i).stockid == stockSelectedItems!!.get(j).stockid) {
                                    list!!.get(i).addedToList = 1
                                }
                            }
                        }
                        rv_Players!!.adapter = stockTeamAdapter;
                        rv_Players!!.adapter!!.notifyDataSetChanged();
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    Toast.makeText(
                        this@ActivityEditTeam,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<StockTeamPojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivityEditTeam,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    fun setTeamText(add: String) {
        tvteamplayer.setText(add + "/12")
        if (add.toInt() == 12) {
            tvViewteam.isEnabled = true
            tvViewteam.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.white_fill_button))
        } else {
            tvViewteam.isEnabled = false
            tvViewteam.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.grey_fill_button))
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.RESULT_CODE_SORT_EDIT_TEAM) {
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                if (data.getStringExtra("flag").equals("Volume")) {

                    var sortedList = list!!.sortedBy { it.latestVolume.toDouble() }

                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()
                        /* rv_Players!!.adapter = LobbyContestAdapter(context!!, sortedList)
                         rv_Players!!.adapter!!.notifyDataSetChanged()*/
                    }
                } else if (data.getStringExtra("flag").equals("price")) {
                    var sortedList = list!!.sortedWith(compareBy { it.latestPrice })
                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()
                        /*rv_Players!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()*/
                    }
                } else if (data.getStringExtra("flag").equals("Alpha")) {
                    var sortedList = list!!.sortedBy { it.symbol?.toString() }
                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()
                        /*rv_Players!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()*/
                    }
                }
            }
        }
        if (requestCode == StockConstant.RESULT_CODE_EDIT_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                list!!.clear()
                list!!.addAll(data.getParcelableArrayListExtra("list"))
                rv_Players!!.adapter!!.notifyDataSetChanged()
                setTeamText(list!!.size.toString())
            }
        }
    }

    fun saveTeamList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        jsonparams.addProperty("contest_id", contestId.toString())
        jsonparams.addProperty("team_id", teamId)
        jsonparams.addProperty("join_var", 0)
        jsonparams.addProperty("user_id", getFromPrefsString(StockConstant.USERID).toString())
        jsonparams.add("stocks", array)

        Log.e("savedlist", array.toString())

        val call: Call<BasePojo> =
            apiService.editTeam(
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
                        AppDelegate.showAlert(this@ActivityEditTeam, response.body()!!.message)
                    } else if (response.body()!!.status == "0") {
                        AppDelegate.showAlert(this@ActivityEditTeam, response.body()!!.message)
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    Toast.makeText(
                        this@ActivityEditTeam,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivityEditTeam,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

}

