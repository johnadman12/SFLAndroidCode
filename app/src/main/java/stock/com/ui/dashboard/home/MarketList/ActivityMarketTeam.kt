package stock.com.ui.dashboard.home.MarketList

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
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_create_team.*
import kotlinx.android.synthetic.main.dialog_join_wizard.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.ContestNewBottom.ActivityMyTeam
import stock.com.ui.dashboard.Team.ActivityMarketDetail
import stock.com.ui.dashboard.home.ActivityMarketFilter
import stock.com.ui.dashboard.Team.ActivitySortTeam
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.MarketData
import stock.com.ui.pojo.MarketList
import stock.com.ui.watch_list.WatchListActivity
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.util.ArrayList

class ActivityMarketTeam : BaseActivity(), View.OnClickListener {
    var marketId: Int = 0
    var page: Int = 0
    var limit: Int = 50
    var marketlistAdapter: MarketListAdapter? = null
    private var marketSelectedItems: ArrayList<MarketList.Crypto>? = null
    private var marketRemovedItems: ArrayList<MarketList.Crypto>? = null
    //    private var marketWizardSelectedItems: ArrayList<MarketList.Crypto>? = null
    private var list: ArrayList<MarketList.Crypto>? = null;
    private var listOld: ArrayList<MarketList.Crypto>? = null;
    private var listFiltered: ArrayList<MarketList.Crypto>? = null;
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
    var flagDayHTL: Boolean = false
    var array: JsonArray = JsonArray()
    var jsonparams: JsonObject = JsonObject()
    var teamName: String = ""
    var flagScreen: Boolean = false

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.llMyTeam -> {
                startActivityForResult(
                    Intent(this@ActivityMarketTeam, ActivityMyTeam::class.java)
                        .putExtra(
                            StockConstant.MARKETID,
                            marketId
                        ).putExtra(
                            StockConstant.CONTESTID,
                            contestId
                        ).putExtra(
                            "flagMarket",
                            "crypto"
                        ), StockConstant.REDIRECT_UPCOMING_MARKET
                )
            }
            R.id.imgButtonWizard -> {
                showJoinContestDialogue()
            }
            R.id.imgcross -> {
                if (!TextUtils.isEmpty(et_search_stock.text.toString())) {
                    et_search_stock.setText("")
                    page = 0
                    limit = 50
                    getMarketTeamlist("0")
                } else
                    displayToast("no words in search", "warning")
            }
            R.id.ll_watchlist -> {
                startActivity(
                    Intent(this@ActivityMarketTeam, WatchListActivity::class.java)
                )
            }
            R.id.ll_filter -> {
                startActivityForResult(
                    Intent(this@ActivityMarketTeam, ActivityMarketFilter::class.java), 10
                )
            }
            R.id.ll_sort -> {
                startActivityForResult(
                    Intent(this@ActivityMarketTeam, ActivitySortTeam::class.java)
                        .putExtra("flagStatus", flagSort),
                    StockConstant.RESULT_CODE_SORT_MARKET_TEAM
                )
            }
            R.id.relFieldView -> {
                startActivity(
                    Intent(this@ActivityMarketTeam, MarketTeamPreviewActivity::class.java)
                        .putParcelableArrayListExtra(StockConstant.MARKETLIST, marketSelectedItems)
                        .putExtra(StockConstant.TEAMNAME, teamName)
                        .putExtra(StockConstant.TOTALCHANGE, "0.0%")
                )
            }
            R.id.tvViewteam -> {
                if (flagCloning == 2) {
                    if (marketSelectedItems!!.size >= 0) {
                        for (i in 0 until marketSelectedItems!!.size) {
                            var postData1 = JsonObject();
                            try {
                                postData1.addProperty(
                                    "crypto_id",
                                    marketSelectedItems!!.get(i).cryptocurrencyid.toString()
                                );
                                postData1.addProperty("price", marketSelectedItems!!.get(i).latestPrice);
                                postData1.addProperty(
                                    "change_percent", marketSelectedItems!!.get(i).changeper
                                )
                                ;
                                postData1.addProperty("stock_type", marketSelectedItems!!.get(i).cryptoType);
                                Log.e("savedlist", postData1.toString())
                            } catch (e: Exception) {
                                Log.d("Ksadad", "dsdad" + e.localizedMessage)
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
                            Intent(this@ActivityMarketTeam, ActivityMarketViewTeam::class.java)
                                .putExtra(StockConstant.MARKETLIST, marketSelectedItems)
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
                                    StockConstant.TEAMNAME,
                                    teamName
                                ).putExtra(
                                    "isCloning",
                                    flagCloning
                                ), StockConstant.RESULT_CODE_MARKET_REMOVE_TEAM
                        )
                    } else {
                        startActivityForResult(
                            Intent(this@ActivityMarketTeam, ActivityMarketViewTeam::class.java)
                                .putExtra(StockConstant.MARKETLIST, marketSelectedItems)
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
                                    StockConstant.TEAMNAME,
                                    teamName
                                ).putExtra(
                                    "isCloning",
                                    flagCloning
                                ), StockConstant.RESULT_CODE_MARKET_REMOVE_TEAM
                        )
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)
        StockConstant.ACTIVITIES.add(this)
        list = ArrayList();
        listOld = ArrayList();
        listFiltered = ArrayList();
        array = JsonArray()
        jsonparams = JsonObject()
        marketRemovedItems = ArrayList();
        initView()
        getMarketTeamlist("0")
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        marketSelectedItems = ArrayList()
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
                marketSelectedItems = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)
                teamName = intent.getStringExtra(StockConstant.TEAMNAME)
                teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
            } else if (flagCloning == 2) {
                marketSelectedItems = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)
                teamName = intent.getStringExtra(StockConstant.TEAMNAME)
                teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
                ll_filter.visibility = GONE
                tvViewteam.setText("  Save Team  ")
                textTeam.setText("Edit Team")
                relFieldView.visibility = VISIBLE
                Log.d("Change_per1", "--" + marketSelectedItems!!.get(0).changeper)
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

        srl_layout.setOnRefreshListener {
            flagRefresh = true
            if (!flagSearch) {
                page++
                callApiSearch(searchText, 1)
            } else {
                page++
                getMarketTeamlist("1")
            }
        }
        setAdapter()

    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        marketlistAdapter = MarketListAdapter(
            this, list as ArrayList,
            listOld as ArrayList,
            this@ActivityMarketTeam,
            object : MarketListAdapter.OnItemCheckListener {
                override fun onToggleUncheck(item: MarketList.Crypto) {
                    for (j in 0 until list!!.size) {
                        if (item.cryptocurrencyid.equals(list!!.get(j).cryptocurrencyid)) {
                            item.cryptoType = "1";
                            if (marketSelectedItems!!.size > 0) {
                                for (i in 0 until marketSelectedItems!!.size)
                                    if (item.cryptocurrencyid.equals(marketSelectedItems!!.get(i).cryptocurrencyid))
                                        marketSelectedItems!!.get(i).cryptoType = item.cryptoType

                            } else
                                list!!.get(j).cryptoType = item.cryptoType
                            break;
                        }
                    }

                }

                override fun onToggleCheck(item: MarketList.Crypto) {
                    for (j in 0 until list!!.size) {
                        if (item.cryptocurrencyid.equals(list!!.get(j).cryptocurrencyid)) {
                            item.cryptoType = "0";
                            if (marketSelectedItems!!.size > 0) {
                                for (i in 0 until marketSelectedItems!!.size)
                                    if (item.cryptocurrencyid.equals(marketSelectedItems!!.get(i).cryptocurrencyid))
                                        marketSelectedItems!!.get(i).cryptoType = item.cryptoType
                            } else
                                list!!.get(j).cryptoType = item.cryptoType
                            break;
                        }
                    }
                }

                override fun onItemClick(item: MarketList.Crypto) {
                    startActivityForResult(
                        Intent(
                            this@ActivityMarketTeam,
                            ActivityMarketDetail::class.java
                        )
                            .putExtra("cryptoId", item.cryptocurrencyid)
                            .putExtra("flag", 1)
                            .putExtra(StockConstant.MARKETLIST, list)
                            .putExtra(StockConstant.SELECTEDSTOCK, marketSelectedItems!!.size)
                        , StockConstant.RESULT_CODE_CREATE_TEAM
                    )

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
                    marketSelectedItems?.add(item);
                    setTeamText(marketSelectedItems!!.size.toString())

                    Log.e("stocklist", marketSelectedItems.toString())
                }
            });

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        rv_Players.visibility = View.VISIBLE
        rv_Players!!.adapter = marketlistAdapter;
    }

    override fun onResume() {
        super.onResume()
        mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                flagRefresh = false;
                if (flagSearch) {
                    getMarketTeamRefresh()
                }
                mainHandler.postDelayed(this, 3000)
            }
        })
    }

    fun callSearch(c: CharSequence) {
        Log.d("dsadada", "sdada--" + c);
        if (c.toString().length >= 2) {
            flagSearch = false;
            searchText = c.toString()
            Log.d("dsadada", "111111--");
            page = 0
            limit = 50
            callApiSearch(c, 0);
        } else {
            flag = true;
            flagSearch = true
            page = 0
            limit = 50
            getMarketTeamlist("0")
        }
    }

    private fun callApiSearch(c: CharSequence, firstTime: Int) {
        Log.d("dsadada", "22222--");
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketList> =
            apiService.searchCrypto(
                "crypto",
                c.toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                page.toString(),
                limit.toString()
            )
        call.enqueue(object : Callback<MarketList> {
            override fun onResponse(call: Call<MarketList>, response: Response<MarketList>) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                d.dismiss()
                if (response.body() != null) {
                    // displayToast(response.body()!!.message, "sucess")
                    if (response.body()!!.status == "1") {
                        if (response.body()!!.crypto!!.size == 0) {
                            displayToast("No Data Available", "error")
                        }
                        if (response.body()!!.myteam.equals("1"))
                            llMyTeam.visibility = View.VISIBLE
                        else if (response.body()!!.myteam.equals("0"))
                            llMyTeam.visibility = View.GONE
                        if (firstTime == 0) {
                            list!!.clear()
                            listOld!!.clear()
                        }
                        list!!.addAll(response.body()!!.crypto!!);
                        listOld!!.addAll(response.body()!!.crypto!!);
                        if (marketlistAdapter != null)
                            marketlistAdapter!!.notifyDataSetChanged();


                        if (flagAlphaSort) {
                            try {
                                val sortedList = list!!.sortedBy { it.symbol?.toString() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }

                        } else if (flagPriceLTH) {
                            try {
                                val sortedList = list!!.sortedBy { it.latestPrice?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }

                        } else if (flagDayLTH) {
                            try {
                                val sortedList = list!!.sortedBy { it.changeper?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        } else if (flagPriceHTL) {
                            try {
                                val sortedList = list!!.sortedByDescending { it.latestPrice?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        } else if (flagDayHTL) {
                            try {
                                val sortedList = list!!.sortedByDescending { it.changeper?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        } else if (flagVolume) {
                            try {
                                val sortedList = list!!.sortedByDescending { it.latestVolume?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        }

                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
                        }
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
                        if (flagFilter) {
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
                            }
                        if (marketlistAdapter != null)
                            marketlistAdapter!!.notifyDataSetChanged();
                        setTeamText(marketSelectedItems!!.size.toString())
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

            override fun onFailure(call: Call<MarketList>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                Log.d("serach_error", "---" + t.localizedMessage);
                d.dismiss()
                displayToast(resources.getString(R.string.something_went_wrong), "error")
            }
        })
    }

    fun getMarketTeamlist(flag: String) {
        var call: Call<MarketList>? = null;
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        call = apiService.getMarketList(
            getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), marketId.toString(),
            getFromPrefsString(StockConstant.USERID)!!, page.toString(), "50"
        )
        call.enqueue(object : Callback<MarketList> {
            override fun onResponse(call: Call<MarketList>, response: Response<MarketList>) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    //for show my team
                    if (response.body()!!.status == "1") {
                        if (response.body()!!.crypto!!.size == 0) {
                            displayToast("No Data Available", "error")
                        }
                        if (flagCloning == 2)
                            llMyTeam.visibility = View.GONE
                        else {
                            if (response.body()!!.myteam.equals("1"))
                                llMyTeam.visibility = View.VISIBLE
                            else if (response.body()!!.myteam.equals("0"))
                                llMyTeam.visibility = View.GONE
                        }
                        if (flag.equals("0")) {
                            list!!.clear()
                            listOld!!.clear()
                            list!!.addAll(response.body()!!.crypto!!)
                            listOld!!.addAll(response.body()!!.crypto!!)
                        } else if (flag.equals("1")) {
                            limit = limit + 50
                            list!!.addAll(response.body()!!.crypto!!)
                            listOld!!.addAll(response.body()!!.crypto!!)
                        } else {
                            list!!.addAll(response.body()!!.crypto!!)
                            listOld!!.addAll(response.body()!!.crypto!!)
                        }
                        if (rv_Players!!.adapter != null)
                            rv_Players!!.adapter!!.notifyDataSetChanged();

                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0

                        }
                        //sortingConcept

                        if (flagAlphaSort) {
                            try {
                                val sortedList = list!!.sortedBy { it.symbol?.toString() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        } else if (flagPriceLTH) {
                            try {
                                val sortedList = list!!.sortedBy { it.latestPrice?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }

                        } else if (flagDayLTH) {
                            try {
                                val sortedList = list!!.sortedBy { it.changeper?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        } else if (flagPriceHTL) {
                            try {
                                val sortedList = list!!.sortedByDescending { it.latestPrice?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        } else if (flagDayHTL) {
                            try {
                                val sortedList = list!!.sortedByDescending { it.changeper?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        } else if (flagVolume) {
                            try {
                                val sortedList = list!!.sortedByDescending { it.latestVolume?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        }
                        //filter
                        try {
                            if (flagFilter) {
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
                                }
                        } catch (e: Exception) {

                        }


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
                                    list!!.get(i).cryptoType = marketSelectedItems!!.get(j).cryptoType
                                    marketSelectedItems!!.get(j).changeper = list!!.get(i).changeper
                                }
                            }
                        }
                        if (marketlistAdapter != null)
                            marketlistAdapter!!.notifyDataSetChanged();
                        setTeamText(marketSelectedItems!!.size.toString())
//                        d.dismiss()

                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                }
            }

            override fun onFailure(call: Call<MarketList>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
            }
        })
    }


    fun getTeamText(): Int {
        return marketSelectedItems!!.size

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


    fun getTeamAgainlist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketList> =
            apiService.getMarketList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), marketId.toString()/*.toString()*/,
                getFromPrefsString(StockConstant.USERID)!!, "0", limit.toString()
            )
        call.enqueue(object : Callback<MarketList> {

            override fun onResponse(call: Call<MarketList>, response: Response<MarketList>) {
                d.dismiss()
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        setSectorFilter("")
                        list!!.clear()
                        if (marketlistAdapter != null)
                            marketlistAdapter!!.notifyDataSetChanged();

                        list!!.addAll(response.body()!!.crypto!!)

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
                            val sortedList = list!!.sortedBy { it.changeper?.toDouble() }
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

                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
                        }

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
                                    list!!.get(i).cryptoType = marketSelectedItems!!.get(j).cryptoType
                                    marketSelectedItems!!.get(j).changeper = list!!.get(i).changeper
                                }
                            }
                        }
                        if (marketlistAdapter != null)
                            marketlistAdapter!!.notifyDataSetChanged();

//                        marketSelectedItems!!.addAll(marketRemovedItems!!)
                        setTeamText(marketSelectedItems!!.size.toString())

                    } else if (response.body()!!.status == "2") {
                        d.dismiss()
                        appLogout()
                    }
                } else {
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<MarketList>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    fun getMarketTeamRefresh() {
        var call: Call<MarketList>? = null;
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        call = apiService.getMarketList(
            getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), marketId.toString(),
            getFromPrefsString(StockConstant.USERID)!!, "0", limit.toString()
        )
        call.enqueue(object : Callback<MarketList> {
            override fun onResponse(call: Call<MarketList>, response: Response<MarketList>) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    //for show my team
                    if (response.body()!!.status == "1") {
                        if (response.body()!!.crypto!!.size == 0) {
                            displayToast("No Data Available", "error")
                        }
                        if (flagCloning == 2)
                            llMyTeam.visibility = View.GONE
                        else {
                            if (response.body()!!.myteam.equals("1"))
                                llMyTeam.visibility = View.VISIBLE
                            else if (response.body()!!.myteam.equals("0"))
                                llMyTeam.visibility = View.GONE
                        }
                        list!!.clear();
                        list!!.addAll(response.body()!!.crypto!!)
                        //   rv_Players!!.adapter!!.notifyDataSetChanged();
                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
                        }
                        //sortingConcept
                        if (flagAlphaSort) {
                            try {
                                val sortedList = list!!.sortedBy { it.symbol?.toString() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }

                        } else if (flagPriceLTH) {
                            try {
                                val sortedList = list!!.sortedBy { it.latestPrice?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }

                        } else if (flagDayLTH) {
                            try {
                                val sortedList = list!!.sortedBy { it.changeper?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        } else if (flagPriceHTL) {
                            try {
                                val sortedList = list!!.sortedByDescending { it.latestPrice?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        } else if (flagDayHTL) {
                            try {
                                val sortedList = list!!.sortedByDescending { it.changeper?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        } else if (flagVolume) {
                            try {
                                val sortedList = list!!.sortedByDescending { it.latestVolume?.toDouble() }
                                list!!.clear()
                                list!!.addAll(sortedList)
                                listOld!!.clear()
                                listOld!!.addAll(list!!)
                            } catch (e: Exception) {

                            }
                        }
                        //filter
                        if (flagFilter) {
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
                            }
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
                                    list!!.get(i).cryptoType = marketSelectedItems!!.get(j).cryptoType
                                    marketSelectedItems!!.get(j).changeper = list!!.get(i).changeper
                                }
                            }
                        }
                        /* if (marketlistAdapter != null)
                             marketlistAdapter!!.notifyDataSetChanged();*/
                        Thread(Task()).start();
                        setTeamText(marketSelectedItems!!.size.toString())
//                        d.dismiss()

                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                }
            }

            override fun onFailure(call: Call<MarketList>, t: Throwable) {

                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
            }
        })
    }

    internal inner class Task() : Runnable {
        override fun run() {
            try {
                runOnUiThread(Runnable {
                    // Stuff that updates the UI
                    try {
                        //forexList!!.clear();
                        if (marketlistAdapter != null)
                            marketlistAdapter!!.notifyDataSetChanged();

                    } catch (ee: java.lang.Exception) {
                    }


                })
            } catch (e: InterruptedException) {
                e.printStackTrace()

            }
        }
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
                        list!!.addAll(sortedList)
                        //rv_Players!!.adapter!!.notifyDataSetChanged()
                        setAdapter()
                    } catch (e: Exception) {

                    }

                } else if (data.getStringExtra("flag").equals("price")) {
                    try {
                        setFlag(false, false, false, false, true, false)
                        var sortedList = list!!.sortedWith(compareBy { it.latestPrice })
                        list!!.clear()
                        list!!.addAll(sortedList)
                        //  rv_Players!!.adapter!!.notifyDataSetChanged()
                        setAdapter()
                    } catch (e: Exception) {

                    }
                } else if (data.getStringExtra("flag").equals("priceHTL")) {
                    try {
                        setFlag(false, false, false, true, false, false)
                        var sortedList = list!!.sortedByDescending { it.latestPrice?.toDouble() }
                        list!!.clear()
                        list!!.addAll(sortedList)
                        //rv_Players!!.adapter!!.notifyDataSetChanged()
                        setAdapter()
                    } catch (e: Exception) {

                    }

                } else if (data.getStringExtra("flag").equals("dayLTH")) {
                    try {
                        setFlag(false, false, true, false, false, false)
                        var sortedList = list!!.sortedBy { it.changeper?.toDouble() }
                        list!!.clear()
                        list!!.addAll(sortedList)
                        //rv_Players!!.adapter!!.notifyDataSetChanged()
                        setAdapter()
                    } catch (e: Exception) {

                    }

                } else if (data.getStringExtra("flag").equals("dayHTL")) {
                    try {
                        setFlag(false, true, false, false, false, false)
                        var sortedList = list!!.sortedByDescending { it.changeper?.toDouble() }
                        list!!.clear()
                        list!!.addAll(sortedList)
                        //rv_Players!!.adapter!!.notifyDataSetChanged()
                        setAdapter()
                    } catch (e: Exception) {

                    }

                } else if (data.getStringExtra("flag").equals("Alpha")) {
                    try {
                        setFlag(true, false, false, false, false, false)
                        var sortedList = list!!.sortedBy { it.symbol?.toString() }
                        list!!.clear()
                        list!!.addAll(sortedList)
                        //rv_Players!!.adapter!!.notifyDataSetChanged()
                        setAdapter()
                    } catch (e: Exception) {

                    }

                } else if (data.getStringExtra("flag").equals("nodata")) {
                    try {
                        setFlag(false, false, false, false, false, false)
                        page = 0;
                        limit = 50;
                        getMarketTeamlist("0")
                    } catch (e: Exception) {

                    }
                }
            }
        }
        if (requestCode == StockConstant.RESULT_CODE_CREATE_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                try {
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

                } catch (e: Exception) {

                }
            }
        }


        if (requestCode == StockConstant.RESULT_CODE_MARKET_REMOVE_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                if (data.getStringExtra("flag").equals("1")) {
                    if (marketSelectedItems!!.size > 0)
                        marketSelectedItems!!.clear()
                    marketRemovedItems = data.getParcelableArrayListExtra("removedlist")
                    marketSelectedItems!!.addAll(marketRemovedItems!!)
                    getTeamAgainlist()

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
                    flagFilter = true
                    getMarketTeamlist("0")
                }

            }

        } else if (requestCode == StockConstant.REDIRECT_UPCOMING_MARKET) {
            if (resultCode == RESULT_OK && data != null) {
                var intent = Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();

            }

        }
    }

    fun saveTeamList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        jsonparams.addProperty("team_id", teamId)
        jsonparams.addProperty("market_id", marketId)
        jsonparams.addProperty("user_team_name", teamName)
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
                        AppDelegate.showAlert(this@ActivityMarketTeam, response.body()!!.message)
                        Handler().postDelayed({
                            finish()
                        }, 1000)

                    } else if (response.body()!!.status == "0") {
                        AppDelegate.showAlert(this@ActivityMarketTeam, response.body()!!.message)
                        Handler().postDelayed({
                        }, 1000)
//                        finish()
                    } else if (response.body()!!.status == "2") {
                        AppDelegate.showAlert(this@ActivityMarketTeam, response.body()!!.message)
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

    fun getWizardStocklist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketList> =
            apiService.getMarketWizardList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), marketId.toString(),
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<MarketList> {

            override fun onResponse(call: Call<MarketList>, response: Response<MarketList>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        flag = true
                        marketSelectedItems!!.clear()
                        marketSelectedItems!!.addAll(response.body()!!.crypto!!);
                        setTeamText(marketSelectedItems!!.size.toString())
                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
                        }
                        rv_Players!!.adapter!!.notifyDataSetChanged();
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

                        rv_Players!!.adapter = marketlistAdapter;
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

    fun setFlag(fAl: Boolean, fDHTL: Boolean, fDLTH: Boolean, fPHTL: Boolean, fPLTH: Boolean, fV: Boolean) {
        flagAlphaSort = fAl
        flagDayHTL = fDHTL
        flagDayLTH = fDLTH
        flagPriceHTL = fPHTL
        flagPriceLTH = fPLTH
        flagVolume = fV
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacksAndMessages(null);
    }
}