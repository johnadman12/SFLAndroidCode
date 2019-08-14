package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.*
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_create_team.*
import kotlinx.android.synthetic.main.dialog_join_contest.*
import kotlinx.android.synthetic.main.dialog_join_wizard.*
import kotlinx.android.synthetic.main.dialogue_join_contest.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.StockTeamPojo
import stock.com.ui.watch_list.WatchListActivity
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import android.text.style.ForegroundColorSpan
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONArray
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.dashboard.ContestNewBottom.ActivityMyTeam
import stock.com.ui.dashboard.Market.StockAdapter
import stock.com.ui.dashboard.Team.Stock.ActivityStockDetail
import stock.com.ui.pojo.BasePojo
import stock.com.utils.AppDelegate
import java.net.URISyntaxException
import java.util.*


class ActivityCreateTeam : BaseActivity(), View.OnClickListener {
    private var stockSelectedItems: ArrayList<StockTeamPojo.Stock>? = null
    //    private var stockSelectedWizardItems: ArrayList<StockTeamPojo.Stock>? = null
    private var stockRemovedItems: ArrayList<StockTeamPojo.Stock>? = null
    private var stockTeamAdapter: StockTeamAdapter? = null;
    private var listOld: ArrayList<StockTeamPojo.Stock>? = null;
    private var list: ArrayList<StockTeamPojo.Stock>? = null;
    private var parseList: ArrayList<StockTeamPojo.Stock>? = null;
    val RESULT_DATA = 1003
    var flag: Boolean = false
    var flagCloning: Int = 0
    var sector: String = ""
    var teamName: String = ""
    lateinit var mainHandler: Handler;
    var flagFilter: Boolean = false
    var flagSort: String = ""
    var flagAlphaSort: Boolean = false
    var flagPriceHTL: Boolean = false
    var flagDayLTH: Boolean = false
    var flagPriceLTH: Boolean = false
    var flagVolume: Boolean = false
    var page: Int = 0
    var limit: Int = 50
    var flagDayHTL: Boolean = false
    var array: JsonArray = JsonArray()
    var jsonparams: JsonObject = JsonObject()
    private var flagSearch: Boolean = true;
    var flagRefresh: Boolean = false
    private var socket: Socket? = null;
    var searchText: String = ""
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.llMyTeam -> {
                startActivity(
                    Intent(this@ActivityCreateTeam, ActivityMyTeam::class.java)
                        .putExtra(
                            StockConstant.EXCHANGEID,
                            exchangeId
                        ).putExtra(
                            StockConstant.CONTESTID,
                            contestId
                        ).putExtra(
                            "flagMarket",
                            "exchange"
                        )
                )
            }
            R.id.imgButtonWizard -> {
                showJoinContestDialogue()
            }


            R.id.imgcross -> {
                if (!TextUtils.isEmpty(et_search_stock.text.toString())) {
                    callSearch("")
                    et_search_stock.setText("")
                    page = 0
                    getTeamlist("1");
                } else
                    displayToast("no words in search", "warning")
            }


            R.id.relFieldView -> {
                startActivity(
                    Intent(this@ActivityCreateTeam, TeamPreviewActivity::class.java)
                        .putParcelableArrayListExtra(StockConstant.STOCKLIST, stockSelectedItems)
                        .putExtra(StockConstant.TEAMNAME, teamName)
                        .putExtra(StockConstant.TOTALCHANGE, "0.0%")
                )
            }
            R.id.ll_watchlist -> {
                startActivity(
                    Intent(this@ActivityCreateTeam, WatchListActivity::class.java)
                )
            }
            R.id.ll_filter -> {
                startActivityForResult(
                    Intent(this@ActivityCreateTeam, ActivitySectorFilter::class.java),
                    1009
                )

            }
            R.id.ll_sort -> {
                startActivityForResult(
                    Intent(this@ActivityCreateTeam, ActivitySortTeam::class.java)
                        .putExtra("flagStatus", flagSort),
                    StockConstant.RESULT_CODE_SORT_CREATE_TEAM
                )
            }
            R.id.tvViewteam -> {
                if (flagCloning == 2) {
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
                            array.add(postData1)
                        }
                        saveTeamList()
                    } else {
                        displayToast("please select Crypto first", "warning")
                    }
                } else {
                    if (flag) {
                        startActivityForResult(
                            Intent(this@ActivityCreateTeam, ActivityViewTeam::class.java)
                                .putExtra(StockConstant.STOCKLIST, stockSelectedItems)
                                .putExtra(
                                    StockConstant.EXCHANGEID,
                                    exchangeId
                                ).putExtra(
                                    StockConstant.CONTESTID,
                                    contestId
                                ).putExtra(
                                    StockConstant.TEAMID,
                                    teamId
                                ).putExtra(
                                    "isCloning",
                                    flagCloning
                                ), StockConstant.RESULT_CODE_VIEW_REMOVE_TEAM
                        )
                    } else {
                        startActivityForResult(
                            Intent(this@ActivityCreateTeam, ActivityViewTeam::class.java)
                                .putExtra(StockConstant.STOCKLIST, stockSelectedItems)
                                .putExtra(
                                    StockConstant.EXCHANGEID,
                                    exchangeId
                                ).putExtra(
                                    StockConstant.CONTESTID,
                                    contestId
                                ).putExtra(
                                    StockConstant.TEAMID,
                                    teamId
                                ).putExtra(
                                    "isCloning",
                                    flagCloning
                                ), StockConstant.RESULT_CODE_VIEW_REMOVE_TEAM
                        )
                    }
                }
            }
        }
    }

    var exchangeId: Int = 0
    var contestId: Int = 0
    var teamId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)
        StockConstant.ACTIVITIES.add(this)
        list = ArrayList();
        array = JsonArray()
        jsonparams = JsonObject()
        listOld = ArrayList();
        initView()
    }

    override fun onResume() {
        super.onResume()
        try {
            val opts = IO.Options()
            opts.forceNew = true
            opts.reconnection = true
            socket = IO.socket("https://www.dfxchange.com:4000", opts)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        socket!!.on(Socket.EVENT_CONNECT) {
        }.on("new_stock_message") {
        }.on(Socket.EVENT_DISCONNECT) {
            socket!!.connect()
        }

        socket!!.connect()
        socket!!.on("new_stock_message") { args ->
            val jsonArray = args[0] as JSONArray
            Log.d("socket_data_stock", "---" + jsonArray);
            Thread(Task(stockTeamAdapter!!, jsonArray)).start()
        }
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        stockSelectedItems = ArrayList();
//        stockSelectedWizardItems = ArrayList();
        stockRemovedItems = ArrayList();
        parseList = ArrayList();
        img_btn_back.setOnClickListener(this)
        tvViewteam.setOnClickListener(this)
        ll_watchlist.setOnClickListener(this)
        ll_filter.setOnClickListener(this)
        ll_sort.setOnClickListener(this)
        llMyTeam.setOnClickListener(this)
        relFieldView.setOnClickListener(this)
        tvViewteam.isEnabled = false
        imgButtonWizard.setOnClickListener(this)
        if (intent != null) {
            exchangeId = intent.getIntExtra(StockConstant.EXCHANGEID, 0)
            contestId = intent.getIntExtra(StockConstant.CONTESTID, 0)
            flagCloning = intent.getIntExtra("isCloning", 0)
            if (flagCloning == 1) {
                stockSelectedItems = intent.getParcelableArrayListExtra(StockConstant.STOCKLIST)
                teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
                teamName = intent.getStringExtra(StockConstant.TEAMNAME)
            } else if (flagCloning == 2) {
                stockSelectedItems = intent.getParcelableArrayListExtra(StockConstant.STOCKLIST)
                teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
                teamName = intent.getStringExtra(StockConstant.TEAMNAME)
                ll_filter.visibility = GONE
                tvViewteam.setText("  Save Team  ")
                textTeam.setText("Edit Team")
                relFieldView.visibility = VISIBLE
            } else {
                teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
            }
        }
        setTeamText(stockSelectedItems!!.size.toString())

        setAdapter()
        getTeamlist("0")

        et_search_stock.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                callSearch(s.toString())
            }
        })


        srl_layout.setOnRefreshListener {
            flagRefresh = true
            if (!flagSearch) {
                page++;
                callApiSearch(searchText)
            } else {
                page++;
                getTeamlist("2");
            }
        }

    }

    fun callSearch(c: CharSequence) {
        Log.d("dsadada", "sdada--" + c);
        if (c.toString().length >= 3) {
            flagSearch = false;
            flagRefresh = false;
            searchText = c.toString()
            page = 0
            Log.d("dsadada", "111111--");
            callApiSearch(c);
        } else {
            flagSearch = true;
            page = 0
            getTeamlist("1")
            Log.d("dsadada", "sdada--");
        }
    }


    fun getTeamlist(flag: String) {
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockTeamPojo> =
            apiService.getStockList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                exchangeId/*.toString()*/,
                getFromPrefsString(StockConstant.USERID)!!.toInt()/*.toString()*/,
                sector,
                page.toString(),
                limit.toString()
            )
        call.enqueue(object : Callback<StockTeamPojo> {
            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
                if (response.body() != null) {
                    if (srl_layout != null)
                        srl_layout.isRefreshing = false
                    if (response.body()!!.status == "1") {
                        if (flagCloning == 2)
                            llMyTeam.visibility = View.GONE
                        else {
                            if (response.body()!!.myteam.equals("1"))
                                llMyTeam.visibility = View.VISIBLE
                            else if (response.body()!!.myteam.equals("0"))
                                llMyTeam.visibility = View.GONE
                        }

                        if (flag.equals("1")) {
                            list!!.clear();
                            listOld!!.clear();
                        }
                        list!!.addAll(response.body()!!.stock!!)
                        listOld!!.addAll(response.body()!!.stock!!)

                        //sortingConcept
                        if (flagAlphaSort) {
                            val sortedList = list!!.sortedBy { it.symbol?.toString() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)

                        } else if (flagPriceLTH) {
                            val sortedList = list!!.sortedBy { it.latestPrice?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)

                        } else if (flagDayLTH) {
                            val sortedList = list!!.sortedBy { it.changePercent?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                        } else if (flagPriceHTL) {
                            val sortedList = list!!.sortedByDescending { it.latestPrice?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                        } else if (flagDayHTL) {
                            val sortedList = list!!.sortedByDescending { it.changePercent?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                        } else if (flagVolume) {
                            val sortedList = list!!.sortedByDescending { it.latestVolume?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                        }


                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
                        }

                        for (i in 0 until list!!.size) {
                            for (j in 0 until stockSelectedItems!!.size) {
                                if (list!!.get(i).stockid == stockSelectedItems!!.get(j).stockid) {
                                    list!!.get(i).addedToList = 1
                                }
                            }
                        }
                        for (i in 0 until list!!.size) {
                            for (j in 0 until stockSelectedItems!!.size) {
                                if (list!!.get(i).stockid == stockSelectedItems!!.get(j).stockid) {
                                    list!!.get(i).stock_type = stockSelectedItems!!.get(j).stock_type
                                }
                            }
                        }
                        if (stockTeamAdapter != null)
                            stockTeamAdapter!!.notifyDataSetChanged();

                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                }
            }

            override fun onFailure(call: Call<StockTeamPojo>, t: Throwable) {
                println(t.toString())
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                displayToast(resources.getString(R.string.something_went_wrong), "error")
//                d.dismiss()
            }
        })
    }


    fun showJoinContestDialogue() {
        var dialogue = Dialog(this)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialog_join_wizard)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val builder = SpannableStringBuilder()

        val next: String = "<fonts color='#46AFE6'>NOTE :</fonts>";
        dialogue.tvNote.setText(Html.fromHtml(next + getString(R.string.note)))
        dialogue.setCancelable(true)
        dialogue.setCanceledOnTouchOutside(false)
        dialogue.setTitle(null)
        dialogue.tvMAgic.setOnClickListener {
            getWizardStocklist()
            dialogue.dismiss()
        }
        dialogue.tv_cancel.setOnClickListener {
            dialogue.dismiss()
        }

        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }


    fun getWizardStocklist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockTeamPojo> =
            apiService.getWizardStockList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), exchangeId.toString(),
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<StockTeamPojo> {

            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        flag = true
                        stockSelectedItems!!.clear()
                        stockSelectedItems!!.addAll(response.body()!!.stock!!);
                        setTeamText(stockSelectedItems!!.size.toString())

                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
                        }
                        rv_Players!!.adapter!!.notifyDataSetChanged();

                        for (i in 0 until list!!.size) {
                            for (j in 0 until stockSelectedItems!!.size) {
                                if (list!!.get(i).stockid == stockSelectedItems!!.get(j).stockid) {
                                    list!!.get(i).addedToList = 1
                                }
                            }
                        }
                        for (i in 0 until list!!.size) {
                            for (j in 0 until stockSelectedItems!!.size) {
                                if (list!!.get(i).stockid == stockSelectedItems!!.get(j).stockid) {
                                    list!!.get(i).stockid = stockSelectedItems!!.get(j).stockid
                                }
                            }
                        }
                        rv_Players!!.adapter = stockTeamAdapter;
                        rv_Players!!.adapter!!.notifyDataSetChanged();
//                        setWizardAdapter()

                        //  setStockTeamAdapter(response.body()!!.stock!!)

                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<StockTeamPojo>, t: Throwable) {
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

    fun getTeamText(): Int {
        return stockSelectedItems!!.size.toInt()
    }


    private fun callApiSearch(c: CharSequence) {
        Log.d("dsadada", "22222--");
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockTeamPojo> =
            apiService.searchExchange(
                exchangeId.toString(), c.toString(), getFromPrefsString(StockConstant.USERID).toString(),
                "Equity", "0", "50"
            )
        call.enqueue(object : Callback<StockTeamPojo> {
            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
                d.dismiss()
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (response.body()!!.myteam.equals("1"))
                            llMyTeam.visibility = View.VISIBLE
                        else if (response.body()!!.myteam.equals("0"))
                            llMyTeam.visibility = View.GONE

                        if (flagRefresh) {
                            list!!.addAll(response.body()!!.stock!!);
                            listOld!!.addAll(response.body()!!.stock!!);
                        } else {
                            list!!.clear()
                            listOld!!.clear()
                            list!!.addAll(response.body()!!.stock!!);
                            listOld!!.addAll(response.body()!!.stock!!);
                        }


                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0

                        }

                        for (i in 0 until list!!.size) {
                            for (j in 0 until stockSelectedItems!!.size) {
                                if (list!!.get(i).stockid == stockSelectedItems!!.get(j).stockid) {
                                    list!!.get(i).addedToList = 1
                                }
                            }
                        }
                        for (i in 0 until list!!.size) {
                            for (j in 0 until stockSelectedItems!!.size) {
                                if (list!!.get(i).stockid == stockSelectedItems!!.get(j).stockid) {
                                    list!!.get(i).stockid = stockSelectedItems!!.get(j).stockid
                                }
                            }
                        }
                        if (flagAlphaSort) {
                            val sortedList = list!!.sortedBy { it.symbol?.toString() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)

                        } else if (flagPriceLTH) {
                            val sortedList = list!!.sortedBy { it.latestPrice?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)

                        } else if (flagDayLTH) {
                            val sortedList = list!!.sortedBy { it.changePercent?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                        } else if (flagPriceHTL) {
                            val sortedList = list!!.sortedByDescending { it.latestPrice?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                        } else if (flagDayHTL) {
                            val sortedList = list!!.sortedByDescending { it.changePercent?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                        } else if (flagVolume) {
                            val sortedList = list!!.sortedByDescending { it.latestVolume?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                        }
                        /*if (flagFilter) {
                            for (i in 0 until list!!.size) {
                                if (list!!.get(i).changePercent != null)
                                    if (!list!!.get(i).changePercent.equals("0")) {
                                        listFiltered!!.add(list!!.get(i))
//                                        stockList!!.remove(stockList!!.get(i))
                                        Log.d("stocklist", listFiltered!!.size.toString())
                                    }
                            }
                        } else
                            if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.ACTIVE_CURRENCY_TYPE))) {
                                setActiveCurrencyType("")
                            }*/
                        if (stockTeamAdapter != null)
                            stockTeamAdapter!!.notifyDataSetChanged();
                        setTeamText(stockSelectedItems!!.size.toString())
                        d.dismiss()

                    } else if (response.body()!!.status == "2") {
                        d.dismiss()
                        appLogout()
                    }
                } else {
                    displayToast(response.body()!!.message, "warning")
                }

            }

            override fun onFailure(call: Call<StockTeamPojo>, t: Throwable) {
                Log.d("serach_error", "---" + t.localizedMessage);
                d.dismiss()
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                displayToast(resources.getString(R.string.something_went_wrong), "error")
            }
        })
    }

    /*fun setWizardAdapter() {
        rv_Players!!.adapter = WizardStockTeamAdapter(
            this,
            list as ArrayList,
            object : WizardStockTeamAdapter.OnItemCheckListener {
                override fun onItemClick(item: StockTeamPojo.Stock) {
                    startActivityForResult(
                        Intent(
                            this@ActivityCreateTeam,
                            ActivityStockDetail::class.java
                        )
                            .putExtra("Stockid", item.stockid)
                            .putExtra(StockConstant.STOCKLIST, list)
                            .putExtra(StockConstant.SELECTEDSTOCK, stockSelectedItems!!.size)
                        , StockConstant.RESULT_CODE_CREATE_TEAM
                    )
                }

                override fun onItemUncheck(item: StockTeamPojo.Stock) {
                    stockSelectedWizardItems?.remove(item);
                    setTeamText(stockSelectedWizardItems!!.size.toString())
                    Log.e("stocklistremove", stockSelectedWizardItems.toString())
                }

                override fun onItemCheck(item: StockTeamPojo.Stock) {
                    stockSelectedWizardItems?.add(item);
                    setTeamText(stockSelectedWizardItems!!.size.toString())
                    Log.e("stocklist", stockSelectedItems.toString())
                }
            });
    }*/

    @SuppressLint("WrongConstant")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.RESULT_CODE_SORT_CREATE_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                flagSort = (data.getStringExtra("flag"))
                if (flagSort.equals("Volume")) {
                    flagVolume = true
                    var sortedList = list!!.sortedByDescending { it.latestVolume!!.toDouble() }
                    try {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        listOld!!.clear()
                        listOld!!.addAll(sortedList)
                        setAdapter()

                    } catch (e: Exception) {

                    }
                } else if (flagSort.equals("price")) {
                    flagPriceLTH = true
                    var sortedList = list!!.sortedWith(compareBy { it.latestPrice })
                    try {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        listOld!!.clear()
                        listOld!!.addAll(sortedList)
                        setAdapter()

                    } catch (e: Exception) {

                    }
                } else if (flagSort.equals("priceHTL")) {
                    flagPriceHTL = true
                    var sortedList = list!!.sortedByDescending { it.latestPrice?.toDouble() }
                    try {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        listOld!!.clear()
                        listOld!!.addAll(sortedList)
                        setAdapter()

                    } catch (e: Exception) {

                    }
                } else if (flagSort.equals("dayLTH")) {
                    flagDayLTH = true
                    var sortedList = list!!.sortedBy { it.changePercent?.toDouble() }
                    try {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        listOld!!.clear()
                        listOld!!.addAll(sortedList)
                        setAdapter()

                    } catch (e: Exception) {

                    }
                } else if (flagSort.equals("dayHTL")) {
                    flagDayHTL = true
                    var sortedList = list!!.sortedByDescending { it.changePercent?.toDouble() }
                    try {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        listOld!!.clear()
                        listOld!!.addAll(sortedList)
                        setAdapter()

                    } catch (e: Exception) {

                    }
                } else if (flagSort.equals("Alpha")) {
                    flagAlphaSort = true
                    var sortedList = list!!.sortedBy { it.symbol?.toString() }
                    try {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        listOld!!.clear()
                        listOld!!.addAll(sortedList)
                        setAdapter()

                    } catch (e: Exception) {

                    }
                } else if (flagSort.equals("nodata")) {
                    flagPriceLTH = false
                    flagDayHTL = false
                    flagAlphaSort = false
                    flagDayLTH = false
                    flagPriceLTH = false
                    flagPriceHTL = false
                    flagVolume = false
                    getTeamlist("0")
                }

            }
        }

        if (requestCode == StockConstant.RESULT_CODE_CREATE_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                /* if (getTeamText() > 12) {
                     Toast.makeText(this, "You have selected maximum number of stocks for your team.", 10000).show()
                 } else {*/

                list!!.clear()
                list!!.addAll(data.getParcelableArrayListExtra("list"))
                rv_Players!!.adapter!!.notifyDataSetChanged()
                stockSelectedItems!!.clear();
                for (i in 0 until list!!.size) {
                    if (list!!.get(i).addedToList == 1) {
                        stockSelectedItems!!.add(list!!.get(i))
                    }
                }
                setTeamText(stockSelectedItems!!.size.toString())
            }
        }

        if (requestCode == StockConstant.RESULT_CODE_VIEW_REMOVE_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                if (data.getStringExtra("flag").equals("1")) {
                    if (stockSelectedItems != null)
                        stockSelectedItems!!.clear()
                    stockRemovedItems = data.getParcelableArrayListExtra("removedlist")
                    stockSelectedItems!!.addAll(stockRemovedItems!!)
                    page = 0;
                    getTeamlist("1")

                } else if (data.getStringExtra("flag").equals("2")) {
                    var intent = Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        }
        if (requestCode == 1009) {
            if (resultCode == RESULT_OK && data != null) {
                var flagreset = data.getStringExtra("resetfilter")
                if (flagreset.equals("0")) {
                    sector = data.getStringExtra("sectorlist")
                    getTeamlist("0")
                } else {
                    sector = ""
                    getTeamlist("0")
                }
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
                        AppDelegate.showAlert(this@ActivityCreateTeam, response.body()!!.message)
                        Handler().postDelayed({
                            finish()
                        }, 1000)
                    } else if (response.body()!!.status == "0") {
                        AppDelegate.showAlert(this@ActivityCreateTeam, response.body()!!.message)
                        Handler().postDelayed({
                        }, 1000)
                    } else if (response.body()!!.status == "2") {
                        AppDelegate.showAlert(this@ActivityCreateTeam, response.body()!!.message)
                        Handler().postDelayed({
                            finish()
                        }, 1000)
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


    /* internal inner class Task(var adapter: StockTeamAdapter, var jsonArray: JSONArray) : Runnable {
         override fun run() {
             try {
                 runOnUiThread(Runnable {
                     // Stuff that updates the UI
                     try {
                         listOld!!.clear();
                         listOld!!.addAll(list!!);
                         for (i in 0..jsonArray.length()) {
                             var jsonObject = jsonArray.getJSONObject(i);
                             var model = StockTeamPojo.Stock()
                             try {
                                 model.changePercent = jsonObject.getString("changePercent");
                                 model.latestPrice = jsonObject.getString("latestPrice");
                                 model.symbol = jsonObject.getString("symbol");
                                 model.slug = jsonObject.getString("slug");
 //                                list!!.add(model)
                                 for (i in 0..list!!.size) {
                                     if (model.slug.equals(list!!.get(i).slug)) {
                                         model.latestPrice = list!!.get(i).latestPrice;
                                         model.changePercent = list!!.get(i).changePercent;
                                         list!!.set(i, model)
                                     }
                                 }
                             } catch (e: Exception) {
                             }
                         }
                     } catch (ee: java.lang.Exception) {
                     }
                     if (adapter != null)
                         adapter!!.notifyDataSetChanged();

                 })
             } catch (e: InterruptedException) {
                 e.printStackTrace()

             }
         }
     }*/

    internal inner class Task(var adapter: StockTeamAdapter, var jsonArray: JSONArray) : Runnable {
        override fun run() {
            try {
               runOnUiThread(Runnable {
                    // Stuff that updates the UI
                    try {
                        Log.d("errroroororor", "--dadadadasddadasdda")
                        listOld!!.clear()
                        listOld!!.addAll(list!!);
                        for (i in 0..jsonArray.length()) {
                            var jsonObject = jsonArray.getJSONObject(i);
                            var model = StockTeamPojo.Stock()
                            try {
                                model.changePercent = jsonObject.getString("changePercent");
                                model.latestVolume = jsonObject.getString("latestVolume");
                                model.latestPrice = jsonObject.getString("latestPrice");
                                model.symbol = jsonObject.getString("symbol");
                                model.slug = jsonObject.getString("slug");
                                //stockListNew!!.add(model)
                                for (i in 0..list!!.size) {
                                    if (model.slug.equals(list!!.get(i).slug)) {
                                        model.companyName= list!!.get(i).companyName
                                        model.addedToList = listOld!!.get(i).addedToList;
                                        model.symbol= list!!.get(i).symbol
                                        list!!.set(i, model);
                                    }
                                }
                            } catch (e: Exception) {

                            }
                        }
                    } catch (ee: java.lang.Exception) {
                    }
                    if (adapter != null)
                        adapter!!.notifyDataSetChanged();

                })
            } catch (e: InterruptedException) {
                e.printStackTrace()

            }
        }
    }

    @SuppressLint("WrongConstant")
    fun setAdapter() {
        stockTeamAdapter = StockTeamAdapter(
            this, listOld as ArrayList,
            list as ArrayList,
            this@ActivityCreateTeam,
            object : StockTeamAdapter.OnItemCheckListener {
                override fun onToggleUncheck(item: StockTeamPojo.Stock) {
                    for (j in 0 until list!!.size) {
                        if (item.stockid.equals(list!!.get(j).stockid)) {
                            item.stock_type = "1";
                            if (stockSelectedItems!!.size > 0) {
                                for (i in 0 until stockSelectedItems!!.size)
                                    if (item.stockid.equals(stockSelectedItems!!.get(i).stockid))
                                        stockSelectedItems!!.get(i).stock_type = item.stock_type

                            } else
                                list!!.get(j).stock_type = item.stock_type
                            break;
                        }
                    }
                }

                override fun onToggleCheck(item: StockTeamPojo.Stock) {
                    for (j in 0 until list!!.size) {
                        if (item.stockid.equals(list!!.get(j).stockid)) {
                            item.stock_type = "0";
                            if (stockSelectedItems!!.size > 0) {
                                for (i in 0 until stockSelectedItems!!.size)
                                    if (item.stockid.equals(stockSelectedItems!!.get(i).stockid))
                                        stockSelectedItems!!.get(i).stock_type = item.stock_type
                            } else
                                list!!.get(j).stock_type = item.stock_type
                            break;
                        }
                    }
                }

                override fun onItemClick(item: StockTeamPojo.Stock) {
                    startActivityForResult(
                        Intent(
                            this@ActivityCreateTeam,
                            ActivityStockDetail::class.java
                        )
                            .putExtra(StockConstant.STOCKID, item.stockid)
                            .putExtra("flag", 1)
                            .putExtra(StockConstant.STOCKLIST, list)
                            .putExtra(StockConstant.SELECTEDSTOCK, stockSelectedItems!!.size)
                        , StockConstant.RESULT_CODE_CREATE_TEAM
                    )

                }

                override fun onItemUncheck(item: StockTeamPojo.Stock) {
                    for (j in 0 until stockSelectedItems!!.size) {
                        if (item.stockid.equals(stockSelectedItems!!.get(j).stockid)) {
                            stockSelectedItems!!.removeAt(j);
                            break;
                        }
                    }
                    setTeamText(stockSelectedItems!!.size.toString())
                }

                override fun onItemCheck(item: StockTeamPojo.Stock) {
                    stockSelectedItems?.add(item);
                    setTeamText(stockSelectedItems!!.size.toString())

                    Log.e("stocklist", stockSelectedItems.toString())
                }
            });

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        rv_Players.visibility = View.VISIBLE
        rv_Players!!.adapter = stockTeamAdapter;


    }


    override fun onDestroy() {
        super.onDestroy()
        try {
            socket!!.off()
            socket!!.disconnect()
            Log.e("Disss", "ok")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onPause() {
        super.onPause()
        try {
            socket!!.off()
            socket!!.disconnect()
            Log.e("Disss", "ok")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}






