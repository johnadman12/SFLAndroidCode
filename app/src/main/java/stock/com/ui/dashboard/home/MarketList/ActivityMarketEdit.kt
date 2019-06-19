package stock.com.ui.dashboard.home.MarketList

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
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
import stock.com.ui.dashboard.Team.ActivitySortTeam
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.MarketList
import stock.com.ui.watch_list.WatchListActivity
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.util.ArrayList

class ActivityMarketEdit : BaseActivity(), View.OnClickListener {
    private var marketSelectedItems: ArrayList<MarketList.Crypto>? = null
    private var teamAdapter: MarketEditAdapter? = null;
    private var list: ArrayList<MarketList.Crypto>? = null;
    var flag: Boolean = false
    var array: JsonArray = JsonArray()
    var teamId: Int = 0
    var jsonparams: JsonObject = JsonObject()
    var marketId: Int = 0
    var contestId: Int = 0

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.tvViewteam -> {
                if (marketSelectedItems!!.size > 0) {
                    for (i in 0 until marketSelectedItems!!.size) {
                        var postData1 = JsonObject();
                        try {
                            postData1.addProperty(
                                "crypto_id",
                                marketSelectedItems!!.get(i).cryptocurrencyid.toString()
                            );
                            postData1.addProperty("price", marketSelectedItems!!.get(i).latestPrice.toString());
                            postData1.addProperty("change_percent", marketSelectedItems!!.get(i).changeper.toString());
                            postData1.addProperty("stock_type", marketSelectedItems!!.get(i).cryptoType);
                            Log.e("savedlist", postData1.toString())
                        } catch (e: Exception) {

                        }
                        Log.d("finaldata", array.toString())
                        array.add(postData1)
                    }
                    saveTeamList()
                } else {
                    displayToast("please select Crypto first", "warning")
                }
            }
            R.id.relFieldView -> {
                startActivity(
                    Intent(this@ActivityMarketEdit, MarketTeamPreviewActivity::class.java)
                        .putExtra(StockConstant.MARKETLIST, marketSelectedItems)
                        .putExtra(StockConstant.TOTALCHANGE, "0.0%")
                )
            }
            R.id.ll_watchlist -> {
                startActivity(
                    Intent(this@ActivityMarketEdit, WatchListActivity::class.java)
                )
            }
            R.id.ll_sort -> {
                startActivityForResult(
                    Intent(this@ActivityMarketEdit, ActivitySortTeam::class.java),
                    StockConstant.RESULT_CODE_SORT_MARKET_EDIT_TEAM
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
        marketSelectedItems = ArrayList();
        img_btn_back.setOnClickListener(this)
        tvViewteam.setOnClickListener(this)
        ll_watchlist.setOnClickListener(this)
        relFieldView.setOnClickListener(this)
        ll_sort.setOnClickListener(this)
        tvViewteam.isEnabled = true
        if (intent != null) {
            marketSelectedItems = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)
            teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
            contestId = intent.getIntExtra(StockConstant.CONTESTID, 0)
            marketId = intent.getIntExtra(StockConstant.MARKETID, 0)
        }

        teamAdapter = MarketEditAdapter(
            this, this@ActivityMarketEdit, list as ArrayList,
            object : MarketEditAdapter.OnItemCheckListener {
                override fun onItemClick(item: MarketList.Crypto) {
                    /*startActivityForResult(
                        Intent(
                            this@ActivityEditTeam,
                            ActivityStockDetail::class.java
                        )
                            .putExtra("Stockid", item.stockid)
                            .putExtra(StockConstant.STOCKLIST, list)
                            .putExtra(StockConstant.SELECTEDSTOCK, marketSelectedItems!!.size)
                        , StockConstant.RESULT_CODE_EDIT_MARKET
                    )*/
                }

                override fun onItemUncheck(item: MarketList.Crypto) {
                    for (j in 0 until marketSelectedItems!!.size) {
                        if (item.cryptocurrencyid.equals(marketSelectedItems!!.get(j).cryptocurrencyid)) {
                            marketSelectedItems!!.removeAt(j);
                            break;
                        }
                    }
                    setTeamText(marketSelectedItems!!.size.toString())
                }

                override fun onItemCheck(item: MarketList.Crypto) {
                    marketSelectedItems!!.add(item);
                    setTeamText(marketSelectedItems!!.size.toString())
                    Log.e("stocklist0000", marketSelectedItems!!.get(0).cryptocurrencyid.toString())
                }
            });

        et_search_stock.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                teamAdapter!!.getFilter().filter(s);
            }
        })

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        rv_Players.visibility = View.VISIBLE
        rv_Players!!.adapter = teamAdapter;
        getTeamlist()

    }

    fun getTeamText(): Int {
        return marketSelectedItems!!.size.toInt()
    }

    fun getTeamlist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketList> =
            apiService.getMarketList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), marketId.toString(),
                getFromPrefsString(StockConstant.USERID)!!
            )
        call.enqueue(object : Callback<MarketList> {
            override fun onResponse(call: Call<MarketList>, response: Response<MarketList>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        list!!.clear()
                        rv_Players!!.adapter!!.notifyDataSetChanged();
                        list!!.addAll(response.body()!!.crypto!!);
                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
                        }
//                        rv_Players.adapter!!.notifyDataSetChanged();
                        for (i in 0 until list!!.size) {
                            for (j in 0 until marketSelectedItems!!.size) {
                                if (list!!.get(i).cryptocurrencyid == marketSelectedItems!!.get(j).cryptocurrencyid) {
                                    list!!.get(i).addedToList = 1
                                }
                            }
                        }
                        for (i in 0 until list!!.size) {
                            for (j in 0 until marketSelectedItems!!.size) {
                                if (list!!.get(i).cryptocurrencyid == marketSelectedItems!!.get(j).cryptocurrencyid) {
                                    list!!.get(i).cryptocurrencyid = marketSelectedItems!!.get(j).cryptocurrencyid
                                }
                            }
                        }
                        rv_Players!!.adapter = teamAdapter;
                        rv_Players!!.adapter!!.notifyDataSetChanged();
                        setTeamText(marketSelectedItems!!.size.toString())

                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<MarketList>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
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
        if (requestCode == StockConstant.RESULT_CODE_SORT_MARKET_EDIT_TEAM) {
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
                    }
                }
            }
        }
        if (requestCode == StockConstant.RESULT_CODE_EDIT_MARKET) {
            if (resultCode == RESULT_OK && data != null) {
                list!!.clear()
                list!!.addAll(data.getParcelableArrayListExtra("list"))
                rv_Players!!.adapter!!.notifyDataSetChanged()
                marketSelectedItems!!.clear();
                for (i in 0 until list!!.size) {
                    if (list!!.get(i).addedToList == 1) {
                        marketSelectedItems!!.add(list!!.get(i))
                    }
                }
                setTeamText(marketSelectedItems!!.size.toString())
            }
        }
    }

    fun saveTeamList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        jsonparams.addProperty("contest_id", contestId.toString())
        jsonparams.addProperty("team_id", teamId)
        jsonparams.addProperty("market_id", marketId)
        jsonparams.addProperty("join_var", 0)
        jsonparams.addProperty("user_id", getFromPrefsString(StockConstant.USERID).toString())
        jsonparams.add("marketdatas", array)

        Log.e("savedlist", array.toString())

        val call: Call<BasePojo> =
            apiService.editMarketTeam(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                jsonparams
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        AppDelegate.showAlert(this@ActivityMarketEdit, response.body()!!.message)
                        finish()
                    } else if (response.body()!!.status == "0") {
                        AppDelegate.showAlert(this@ActivityMarketEdit, response.body()!!.message)
//                        finish()
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
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
