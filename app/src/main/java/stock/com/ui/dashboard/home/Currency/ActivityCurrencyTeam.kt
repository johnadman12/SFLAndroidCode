package stock.com.ui.dashboard.home.Currency

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.android.synthetic.main.activity_create_team.*
import kotlinx.android.synthetic.main.include_back.*
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.ContestNewBottom.ActivityMyTeam
import stock.com.ui.dashboard.Team.ActivityMarketDetail
import stock.com.ui.dashboard.Team.ActivitySortTeam
import stock.com.ui.dashboard.home.ActivityMarketFilter
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.CurrencyPojo
import stock.com.ui.pojo.MarketList
import stock.com.ui.watch_list.WatchListActivity
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.net.URISyntaxException
import java.util.*

class ActivityCurrencyTeam : BaseActivity(), View.OnClickListener {
    private var currencySelected: ArrayList<CurrencyPojo.Currency>? = null
    private var currencyRemoved: ArrayList<CurrencyPojo.Currency>? = null
    var marketId: Int = 0
    var page: Int = 0
    var limit: Int = 50
    var currencyAdapter: CurrencyTeamAdapter? = null
    var flag: Boolean = false
    var flagCloning: Int = 0
    var teamId: Int = 0
    var contestFee: String = ""
    var flagSort: String = ""
    var contestId: Int = 0
    var flagFilter: Boolean = false
    var flagRefresh: Boolean = false
    lateinit var mainHandler: Handler;
    private var flagSearch: Boolean = true;
    var flagAlphaSort: Boolean = false
    var flagPriceHTL: Boolean = false
    var flagDayLTH: Boolean = false
    var flagPriceLTH: Boolean = false
    var flagVolume: Boolean = false
    var searchText: String = ""
    var teamName: String = ""
    var flagDayHTL: Boolean = false
    var array: JsonArray = JsonArray()
    var jsonparams: JsonObject = JsonObject()
    private var socket: Socket? = null;

    private var list: ArrayList<stock.com.ui.pojo.CurrencyPojo.Currency>? = null;
    private var listOld: ArrayList<CurrencyPojo.Currency>? = null;
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.imgcross -> {
                if (!TextUtils.isEmpty(et_search_stock.text.toString())) {
                    callSearch("")
                    et_search_stock.setText("")
                    page = 0
                    getCurrencyTeamlist("1");
                } else
                    displayToast("no words in search", "warning")
            }
            R.id.llMyTeam -> {
                startActivity(
                    Intent(this@ActivityCurrencyTeam, ActivityMyTeam::class.java)
                        .putExtra(
                            StockConstant.MARKETID,
                            marketId
                        ).putExtra(
                            StockConstant.CONTESTID,
                            contestId
                        ).putExtra(
                            "flagMarket",
                            "currency"
                        )
                )
            }
            R.id.relFieldView -> {
                startActivity(
                    Intent(this@ActivityCurrencyTeam, CurrencyPreviewTeamActivity::class.java)
                        .putExtra(StockConstant.MARKETLIST, currencySelected)
                        .putExtra(StockConstant.TEAMNAME, teamName)
                        .putExtra(StockConstant.TOTALCHANGE, "0.0%")
                )
            }
            R.id.ll_watchlist -> {
                startActivity(
                    Intent(this@ActivityCurrencyTeam, WatchListActivity::class.java)
                )
            }
            R.id.ll_sort -> {
                startActivityForResult(
                    Intent(this@ActivityCurrencyTeam, ActivitySortTeam::class.java).putExtra("flagStatus", flagSort),
                    StockConstant.RESULT_CODE_SORT_MARKET_TEAM
                )
            }
            R.id.tvViewteam -> {
                if (flagCloning == 2) {
                    if (currencySelected!!.size > 0) {
                        for (i in 0 until currencySelected!!.size) {
                            var postData1 = JsonObject();
                            try {
                                postData1.addProperty(
                                    "crypto_id",
                                    currencySelected!!.get(i).currencyid.toString()
                                );
                                postData1.addProperty("price", currencySelected!!.get(i).ask);
                                postData1.addProperty(
                                    "change_percent",
                                    currencySelected!!.get(i).changeper
                                );
                                postData1.addProperty("stock_type", currencySelected!!.get(i).cryptoType);
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
                } else {
                    if (flag) {
                        startActivityForResult(
                            Intent(this@ActivityCurrencyTeam, CurrencyViewTeamActivity::class.java)
                                .putExtra(StockConstant.MARKETLIST, currencySelected)
                                .putExtra(
                                    StockConstant.TEAMID,
                                    teamId
                                ).putExtra(
                                    StockConstant.CONTESTID,
                                    contestId
                                ).putExtra(
                                    StockConstant.MARKETID,
                                    marketId
                                ).putExtra(
                                    StockConstant.CONTESTFEE,
                                    contestFee
                                ).putExtra(
                                    "isCloning",
                                    flagCloning
                                ), StockConstant.RESULT_CODE_MARKET_REMOVE_TEAM
                        )
                    } else {
                        startActivityForResult(
                            Intent(this@ActivityCurrencyTeam, CurrencyViewTeamActivity::class.java)
                                .putExtra(StockConstant.MARKETLIST, currencySelected)
                                .putExtra(
                                    StockConstant.TEAMID,
                                    teamId
                                ).putExtra(
                                    StockConstant.CONTESTID,
                                    contestId
                                ).putExtra(
                                    StockConstant.MARKETID,
                                    marketId
                                ).putExtra(
                                    StockConstant.CONTESTFEE,
                                    contestFee
                                ).putExtra(
                                    "isCloning",
                                    flagCloning
                                ), StockConstant.RESULT_CODE_MARKET_REMOVE_TEAM
                        )
                    }
                }
            }
            R.id.ll_filter -> {
                startActivityForResult(
                    Intent(this@ActivityCurrencyTeam, ActivityMarketFilter::class.java), 10
                )
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)
        StockConstant.ACTIVITIES.add(this)
        list = ArrayList();
        listOld = ArrayList();
        currencySelected = ArrayList();
        currencyRemoved = ArrayList();
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
        }.on("new_message") {
        }.on(Socket.EVENT_DISCONNECT) {
            socket!!.connect()
        }

        socket!!.connect()
        socket!!.on("new_message") { args ->
            val jsonArray = args[0] as JSONArray
            Log.d("socket_data", "---" + jsonArray);
            Thread(Task(currencyAdapter!!, jsonArray)).start()
        }
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        img_btn_back.setOnClickListener(this)
        tvViewteam.setOnClickListener(this)
        ll_watchlist.setOnClickListener(this)
        ll_filter.setOnClickListener(this)
        ll_sort.setOnClickListener(this)
        llMyTeam.setOnClickListener(this)
        relFieldView.setOnClickListener(this)
        tvViewteam.isEnabled = false
        imgButtonWizard.setOnClickListener(this)
        imgcross.setOnClickListener(this)
        if (intent != null) {
            marketId = intent.getIntExtra(StockConstant.MARKETID, 0)
            contestId = intent.getIntExtra(StockConstant.CONTESTID, 0)
            flagCloning = intent.getIntExtra("isCloning", 0)

            if (flagCloning == 1) {
                currencySelected = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)
                teamName = intent.getStringExtra(StockConstant.TEAMNAME)

            } else if (flagCloning == 2) {
                currencySelected = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)
                teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
                teamName = intent.getStringExtra(StockConstant.TEAMNAME)
                ll_filter.visibility = View.GONE
                tvViewteam.setText("  Save Team  ")
                textTeam.setText("Edit Team")
                relFieldView.visibility = View.VISIBLE
            } else {
                contestFee = intent.getStringExtra(StockConstant.CONTESTFEE)
                teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
            }

        }
        et_search_stock.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                callSearch(s.toString())
            }
        })


        setAdapter()
        getCurrencyTeamlist("1")

        srl_layout.setOnRefreshListener {
            flagRefresh = true
            if (!flagSearch) {
                page++;
                callApiSearch(searchText)
            } else {
                page++;
                getCurrencyTeamlist("2");
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
            getCurrencyTeamlist("1")
            Log.d("dsadada", "sdada--");
        }
    }

    @Suppress("DEPRECATION")
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

    private fun callApiSearch(c: CharSequence) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<CurrencyPojo> =
            apiService.searchCurrency(
                "currency",
                c.toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                page.toString(),
                limit.toString()
            )
        call.enqueue(object : Callback<CurrencyPojo> {
            override fun onResponse(call: Call<CurrencyPojo>, response: Response<CurrencyPojo>) {
                d.dismiss()
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    // displayToast(response.body()!!.message, "sucess")
                    if (response.body()!!.status == "1") {

                        if (response.body()!!.currency!!.size == 0) {
                            displayToast("no Data Available", "error")
                        }
                        if (response.body()!!.myteam.equals("1"))
                            llMyTeam.visibility = View.VISIBLE
                        else if (response.body()!!.myteam.equals("0"))
                            llMyTeam.visibility = View.GONE

                        if (flagRefresh) {
                            list!!.addAll(response.body()!!.currency!!);
                            listOld!!.addAll(response.body()!!.currency!!);
                        } else {
                            list!!.clear()
                            listOld!!.clear()
                            list!!.addAll(response.body()!!.currency!!);
                            listOld!!.addAll(response.body()!!.currency!!);
                        }
                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
                        }
                        for (i in 0 until list!!.size) {
                            for (j in 0 until currencySelected!!.size) {
                                if (list!!.get(i).currencyid == currencySelected!!.get(j).currencyid) {
                                    list!!.get(i).addedToList = 1
                                }
                            }
                        }
                        for (i in 0 until list!!.size) {
                            for (j in 0 until currencySelected!!.size) {
                                if (list!!.get(i).currencyid == currencySelected!!.get(j).currencyid) {
                                    list!!.get(i).currencyid = currencySelected!!.get(j).currencyid
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
                            val sortedList = list!!.sortedBy { it.ask?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)

                        } else if (flagDayLTH) {
                            val sortedList = list!!.sortedBy { it.changeper?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                        } else if (flagPriceHTL) {
                            val sortedList = list!!.sortedByDescending { it.ask?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                        } else if (flagDayHTL) {
                            val sortedList = list!!.sortedByDescending { it.changeper?.toDouble() }
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
                        /* if (flagFilter) {
                             for (i in 0 until list!!.size) {
                                 if (list!!.get(i).changeper != null)
                                     if (!list!!.get(i).changeper.equals("0")) {
                                         listFiltered!!.add(list!!.get(i))
 //                                        stockList!!.remove(stockList!!.get(i))
                                         Log.d("stocklist", listFiltered!!.size.toString())
                                     }
                             }
                         } else
                             if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.ACTIVE_CURRENCY_TYPE))) {
                                 setActiveCurrencyType("")
                             }*/

                        if (currencyAdapter != null)
                            currencyAdapter!!.notifyDataSetChanged();
                        setTeamText(currencySelected!!.size.toString())
                        page++;
                        d.dismiss()

                    } else if (response.body()!!.status == "2") {
                        d.dismiss()
                        appLogout()
                    }
                } else if (response.body()!!.status == "2") {
                    appLogout()
                } else {
                    displayToast(response.body()!!.message, "warning")
                }

            }

            override fun onFailure(call: Call<CurrencyPojo>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                Log.d("serach_error", "---" + t.localizedMessage);
                d.dismiss()
                displayToast(resources.getString(R.string.something_went_wrong), "error")
            }
        })
    }


    fun getCurrencyTeamlist(flag: String) {
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        var call: Call<CurrencyPojo>? = null;
        call = apiService.getCurrencyList(
            getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
            marketId.toString()/*.toString()*/,
            getFromPrefsString(StockConstant.USERID)!!,
            page.toString(),
            limit.toString()
        )
        call.enqueue(object : Callback<CurrencyPojo> {
            override fun onResponse(call: Call<CurrencyPojo>, response: Response<CurrencyPojo>) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    //for show my team
                    if (response.body()!!.status == "1") {
                        if (response.body()!!.currency!!.size == 0) {
                            displayToast("no Data Available", "error")
                        }

                        if (flagCloning == 2) {
                            llMyTeam.visibility = View.GONE
                        } else {
                            if (response.body()!!.myteam.equals("1"))
                                llMyTeam.visibility = View.VISIBLE
                            else if (response.body()!!.myteam.equals("0"))
                                llMyTeam.visibility = View.GONE
                        }

                        if (flag.equals("1")) {
                            list!!.clear();
                            listOld!!.clear();
                        }
                        list!!.addAll(response.body()!!.currency!!)
                        listOld!!.addAll(response.body()!!.currency!!)


                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0

                        }
                        for (i in 0 until list!!.size) {
                            for (j in 0 until currencySelected!!.size) {
                                if (list!!.get(i).currencyid == currencySelected!!.get(j).currencyid) {
                                    list!!.get(i).addedToList = 1
                                }
                            }
                        }
                        for (i in 0 until list!!.size) {
                            for (j in 0 until currencySelected!!.size) {
                                if (list!!.get(i).currencyid == currencySelected!!.get(j).currencyid) {
                                    list!!.get(i).cryptoType = currencySelected!!.get(j).cryptoType
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
                            val sortedList = list!!.sortedBy { it.ask?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)

                        } else if (flagDayLTH) {
                            val sortedList = list!!.sortedBy { it.changeper?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                        } else if (flagPriceHTL) {
                            val sortedList = list!!.sortedByDescending { it.ask?.toDouble() }
                            list!!.clear()
                            list!!.addAll(sortedList)
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                        } else if (flagDayHTL) {
                            val sortedList = list!!.sortedByDescending { it.changeper?.toDouble() }
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

                        if (currencyAdapter != null)
                            currencyAdapter!!.notifyDataSetChanged();

                        setTeamText(currencySelected!!.size.toString())

                        page++;
//                        d.dismiss()


                    } else if (response.body()!!.status == "2") {
//                        d.dismiss()
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
//                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<CurrencyPojo>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
//                d.dismiss()
            }
        })
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
                        AppDelegate.showAlert(this@ActivityCurrencyTeam, response.body()!!.message)
                        Handler().postDelayed({
                            finish()
                        }, 1000)

                    } else if (response.body()!!.status == "0") {
                        AppDelegate.showAlert(this@ActivityCurrencyTeam, response.body()!!.message)
                        Handler().postDelayed({
                        }, 1000)
//                        finish()
                    } else if (response.body()!!.status == "2") {
                        AppDelegate.showAlert(this@ActivityCurrencyTeam, response.body()!!.message)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.RESULT_CODE_SORT_MARKET_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                flagSort = data.getStringExtra("flag")
                if (data.getStringExtra("flag").equals("Volume")) {
                    try {
                        setFlag(false, false, false, false, false, true)
                        var sortedList = list!!.sortedByDescending { it.latestVolume!!.toDouble() }
                        list!!.clear()
                        listOld!!.clear()
                        listOld!!.addAll(sortedList)
                        list!!.addAll(sortedList)
                        setAdapter()
                    } catch (e: Exception) {

                    }


                } else if (data.getStringExtra("flag").equals("price")) {
                    setFlag(false, false, false, false, true, false)
                    try {
                        var sortedList = list!!.sortedWith(compareBy { it.ask })
                        list!!.clear()
                        listOld!!.clear()
                        listOld!!.addAll(sortedList)
                        list!!.addAll(sortedList)
                        setAdapter()
                    } catch (e: Exception) {

                    }
                } else if (data.getStringExtra("flag").equals("priceHTL")) {
                    try {
                        setFlag(false, false, false, true, false, false)
                        var sortedList = list!!.sortedByDescending { it.ask?.toDouble() }
                        list!!.clear()
                        listOld!!.clear()
                        listOld!!.addAll(sortedList)
                        list!!.addAll(sortedList)
                        setAdapter()
                    } catch (e: Exception) {

                    }

                } else if (data.getStringExtra("flag").equals("dayLTH")) {
                    try {
                        setFlag(false, false, true, false, false, false)
                        var sortedList = list!!.sortedBy { it.changeper?.toDouble() }
                        list!!.clear()
                        listOld!!.clear()
                        listOld!!.addAll(sortedList)
                        list!!.addAll(sortedList)
                        setAdapter()
                    } catch (e: Exception) {

                    }

                } else if (data.getStringExtra("flag").equals("dayHTL")) {
                    try {
                        setFlag(false, true, false, false, false, false)
                        var sortedList = list!!.sortedByDescending { it.changeper?.toDouble() }
                        list!!.clear()
                        listOld!!.clear()
                        listOld!!.addAll(sortedList)
                        list!!.addAll(sortedList)
                        setAdapter()
                    } catch (e: Exception) {

                    }

                } else if (data.getStringExtra("flag").equals("Alpha")) {
                    try {
                        setFlag(true, false, false, false, false, false)
                        var sortedList = list!!.sortedBy { it.symbol?.toString() }
                        list!!.clear()
                        listOld!!.clear()
                        listOld!!.addAll(sortedList)
                        list!!.addAll(sortedList)
                        setAdapter()
                    } catch (e: Exception) {

                    }

                } else if (data.getStringExtra("flag").equals("nodata")) {
                    try {
                        setFlag(false, false, false, false, false, false)
                        page = 0
                        getCurrencyTeamlist("0")
                    } catch (e: Exception) {

                    }
                }
            }
        }
        if (requestCode == StockConstant.RESULT_CODE_CREATE_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                list!!.clear()
                list!!.addAll(data.getParcelableArrayListExtra("list"))
                rv_Players!!.adapter!!.notifyDataSetChanged()
                currencySelected!!.clear();
                for (i in 0 until list!!.size) {
                    if (list!!.get(i).addedToList == 1) {
                        currencySelected!!.add(list!!.get(i))
                    }
                }
                setTeamText(currencySelected!!.size.toString())
            }
        }


        if (requestCode == StockConstant.RESULT_CODE_CREATE_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                list!!.clear()
                list!!.addAll(data.getParcelableArrayListExtra("list"))
                rv_Players!!.adapter!!.notifyDataSetChanged()
                currencySelected!!.clear();
                for (i in 0 until list!!.size) {
                    if (list!!.get(i).addedToList == 1) {
                        currencySelected!!.add(list!!.get(i))
                    }
                }
                setTeamText(currencySelected!!.size.toString())
            }
        }


        if (requestCode == StockConstant.RESULT_CODE_MARKET_REMOVE_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                if (data.getStringExtra("flag").equals("1")) {
                    if (currencySelected != null)
                        currencySelected!!.clear()
                    currencyRemoved = data.getParcelableArrayListExtra("removedlist")
                    currencySelected!!.addAll(currencyRemoved!!)
                    page = 0;
                    getCurrencyTeamlist("1")
                } else if (data.getStringExtra("flag").equals("2")) {
                    var intent = Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else if (data.getStringExtra("flag").equals("3")) {
                    var intent = Intent();
                    setResult(Activity.RESULT_OK, intent);
                    teamId = data.getIntExtra(StockConstant.TEAMID, 0)
                }
            }
        } else if (requestCode == 10) {
            if (resultCode == RESULT_OK && data != null) {
                if (data.getStringExtra("resetfiltermarket").equals("0")) {
                    page = 0;
                    flagFilter = true
                    getCurrencyTeamlist("1")
                }
            }
        }
    }


    @SuppressLint("WrongConstant")
    fun setAdapter() {
        currencyAdapter = CurrencyTeamAdapter(
            this, list as ArrayList,
            listOld as ArrayList,
            this@ActivityCurrencyTeam,
            object : CurrencyTeamAdapter.OnItemCheckListener {
                override fun onToggleUncheck(item: CurrencyPojo.Currency) {
                    for (j in 0 until list!!.size) {
                        if (item.currencyid.equals(list!!.get(j).currencyid)) {
                            item.cryptoType = "1";
                            if (currencySelected!!.size > 0) {
                                for (i in 0 until currencySelected!!.size)
                                    if (item.currencyid.equals(currencySelected!!.get(i).currencyid))
                                        currencySelected!!.get(i).cryptoType = item.cryptoType

                            } else
                                list!!.get(j).cryptoType = item.cryptoType
                            break;
                        }
                    }

                }

                override fun onToggleCheck(item: CurrencyPojo.Currency) {
                    for (j in 0 until list!!.size) {
                        if (item.currencyid.equals(list!!.get(j).currencyid)) {
                            item.cryptoType = "0";
                            if (currencySelected!!.size > 0) {
                                for (i in 0 until currencySelected!!.size)
                                    if (item.currencyid.equals(currencySelected!!.get(i).currencyid))
                                        currencySelected!!.get(i).cryptoType = item.cryptoType
                            } else
                                list!!.get(j).cryptoType = item.cryptoType
                            break;
                        }
                    }
                }

                override fun onItemClick(item: CurrencyPojo.Currency) {
                    startActivityForResult(
                        Intent(
                            this@ActivityCurrencyTeam,
                            ActivityMarketDetail::class.java
                        )
                            .putExtra("cryptoId", item.currencyid)
                            .putExtra("flag", 1)
                            .putExtra(StockConstant.MARKETLIST, list)
                            .putExtra(StockConstant.SELECTEDSTOCK, currencySelected!!.size)
                        , StockConstant.RESULT_CODE_CREATE_TEAM
                    )

                }

                override fun onItemUncheck(item: CurrencyPojo.Currency) {
                    for (j in 0 until currencySelected!!.size) {
                        if (item.currencyid.equals(currencySelected!!.get(j).currencyid)) {
                            currencySelected!!.removeAt(j);
                            break;
                        }
                    }
                    setTeamText(currencySelected!!.size.toString())
                }

                override fun onItemCheck(item: CurrencyPojo.Currency) {
                    currencySelected?.add(item);
                    setTeamText(currencySelected!!.size.toString())
                    Log.e("stocklist", currencySelected.toString())
                }
            });

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        rv_Players.visibility = View.VISIBLE
        rv_Players!!.adapter = currencyAdapter;
    }

    fun setFlag(fAl: Boolean, fDHTL: Boolean, fDLTH: Boolean, fPHTL: Boolean, fPLTH: Boolean, fV: Boolean) {
        flagAlphaSort = fAl
        flagDayHTL = fDHTL
        flagDayLTH = fDLTH
        flagPriceHTL = fPHTL
        flagPriceLTH = fPLTH
        flagVolume = fV
    }

    fun getTeamText(): Int {
        return currencySelected!!.size

    }

    internal inner class Task(var adapter: CurrencyTeamAdapter, var jsonArray: JSONArray) : Runnable {
        override fun run() {
            try {
                runOnUiThread(Runnable {
                    // Stuff that updates the UI
                    try {
                        //forexList!!.clear();
                        listOld!!.clear();
                        listOld!!.addAll(list!!);
                        for (i in 0..jsonArray.length()) {
                            var jsonObject = jsonArray.getJSONObject(i);
                            var model = CurrencyPojo.Currency()
                            try {
                                model.currencyid = jsonObject!!.getString("currencyid").toInt();
                                model.name = jsonObject!!.getString("name");
                                model.latestVolume = jsonObject!!.getString("latestVolume");
                                model.changeper = jsonObject!!.getString("changeper");
                                model.daychange = jsonObject!!.getString("daychange");
                                model.ask = jsonObject!!.getString("ask");
                                model.firstflag = jsonObject!!.getString("firstflag");
                                model.secondflag = jsonObject!!.getString("secondflag");
                                model.symbol = jsonObject!!.getString("symbol");
                                model.bid = jsonObject!!.getString("bid");

                                // forexList!!.add(model)
                                for (i in 0..list!!.size) {
                                    if (model.symbol.equals(list!!.get(i).symbol)) {
                                        Log.d("currency_id", "---" + model.name);
                                        model.firstflag = list!!.get(i).firstflag;
                                        model.secondflag = list!!.get(i).secondflag;
                                        list!!.set(i, model!!)
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