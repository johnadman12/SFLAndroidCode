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
    lateinit var mainHandler: Handler;
    private var flagSearch: Boolean = true;
    var flagAlphaSort: Boolean = false
    var flagPriceHTL: Boolean = false
    var flagDayLTH: Boolean = false
    var flagPriceLTH: Boolean = false
    var flagVolume: Boolean = false
    var flagDayHTL: Boolean = false
    var array: JsonArray = JsonArray()
    var jsonparams: JsonObject = JsonObject()

    var flagScreen: Boolean = false

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.llMyTeam -> {
                startActivity(
                    Intent(this@ActivityMarketTeam, ActivityMyTeam::class.java)
                        .putExtra(
                            StockConstant.MARKETID,
                            marketId
                        ).putExtra(
                            "flagMarket",
                            true
                        )
                )
            }
            R.id.imgButtonWizard -> {
                showJoinContestDialogue()
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
                    Intent(this@ActivityMarketTeam, ActivitySortTeam::class.java).putExtra("flagStatus", flagSort),
                    StockConstant.RESULT_CODE_SORT_MARKET_TEAM
                )
            }
            R.id.tvViewteam -> {

                if (flagCloning == 2) {
                    if (marketSelectedItems!!.size > 0) {
                        for (i in 0 until marketSelectedItems!!.size) {
                            var postData1 = JsonObject();
                            try {
                                postData1.addProperty(
                                    "crypto_id",
                                    marketSelectedItems!!.get(i).cryptocurrencyid.toString()
                                );
                                postData1.addProperty("price", marketSelectedItems!!.get(i).latestPrice.toString());
                                postData1.addProperty(
                                    "change_percent",
                                    marketSelectedItems!!.get(i).changeper.toString()
                                );
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
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        marketSelectedItems = ArrayList()
//        marketWizardSelectedItems = ArrayList()
        img_btn_back.setOnClickListener(this)
        tvViewteam.setOnClickListener(this)
        ll_watchlist.setOnClickListener(this)
        ll_filter.setOnClickListener(this)
        ll_sort.setOnClickListener(this)
        llMyTeam.setOnClickListener(this)
        tvViewteam.isEnabled = false
        imgButtonWizard.setOnClickListener(this)
        if (intent != null) {
            marketId = intent.getIntExtra(StockConstant.MARKETID, 0)
            contestId = intent.getIntExtra(StockConstant.CONTESTID, 0)
            flagCloning = intent.getIntExtra("isCloning", 0)

            if (flagCloning == 1) {
                marketSelectedItems = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)
            } else if (flagCloning == 2) {
                marketSelectedItems = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)
                teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
                ll_filter.visibility = GONE
                tvViewteam.setText("  Save Team  ")
                textTeam.setText("Edit Team")
            } else {
                contestFee = intent.getStringExtra(StockConstant.CONTESTFEE)
                teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
            }

        }

        marketlistAdapter = MarketListAdapter(
            this, list as ArrayList,
            listOld as ArrayList,
            this@ActivityMarketTeam,
            object : MarketListAdapter.OnItemCheckListener {
                override fun onToggleUncheck(item: MarketList.Crypto) {
                    for (j in 0 until list!!.size) {
                        if (item.cryptocurrencyid.equals(list!!.get(j).cryptocurrencyid)) {
                            item.cryptoType = "1";
                            list!!.get(j).cryptoType = item.cryptoType
                            break;
                        }
                    }

                }

                override fun onToggleCheck(item: MarketList.Crypto) {
                    for (j in 0 until list!!.size) {
                        if (item.cryptocurrencyid.equals(list!!.get(j).cryptocurrencyid)) {
                            item.cryptoType = "0";
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
        et_search_stock.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                callSearch(s.toString())
            }
        })
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        rv_Players.visibility = View.VISIBLE
        rv_Players!!.adapter = marketlistAdapter;

        getMarketTeamlist()
    }


    override fun onResume() {
        super.onResume()
        mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                if (flagSearch) {
                    getMarketTeamlist()
                }
                mainHandler.postDelayed(this, 8000)
            }

        })
    }

    fun callSearch(c: CharSequence) {
        Log.d("dsadada", "sdada--" + c);
        if (c.toString().length >= 3) {
            flagSearch = false;
            Log.d("dsadada", "111111--");
            callApiSearch(c);
        } else {
            flagSearch = true;
            Log.d("dsadada", "sdada--");
        }
    }

    private fun callApiSearch(c: CharSequence) {
        Log.d("dsadada", "22222--");
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketList> =
            apiService.searchCrypto("crypto", c.toString(), getFromPrefsString(StockConstant.USERID).toString())
        call.enqueue(object : Callback<MarketList> {
            override fun onResponse(call: Call<MarketList>, response: Response<MarketList>) {
                d.dismiss()
                if (response.body() != null) {
                    // displayToast(response.body()!!.message, "sucess")


                    if (response.body()!!.status == "1") {
                        if (response.body()!!.myteam.equals("1"))
                            llMyTeam.visibility = View.VISIBLE
                        else if (response.body()!!.myteam.equals("0"))
                            llMyTeam.visibility = View.GONE

                        list!!.clear()
                        listOld!!.clear()
                        rv_Players!!.adapter!!.notifyDataSetChanged();
                        list!!.addAll(response.body()!!.crypto!!);
                        listOld!!.addAll(response.body()!!.crypto!!);
                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
//                            listOld!!.get(i).addedToList = 0

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

                        rv_Players!!.adapter = marketlistAdapter;
                        rv_Players!!.adapter!!.notifyDataSetChanged();
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
                Log.d("serach_error", "---" + t.localizedMessage);
                d.dismiss()
                displayToast(resources.getString(R.string.something_went_wrong), "error")
            }
        })
    }

    fun getMarketTeamlist() {
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketList> =
            apiService.getMarketList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), marketId.toString()/*.toString()*/,
                getFromPrefsString(StockConstant.USERID)!!
            )
        call.enqueue(object : Callback<MarketList> {
            override fun onResponse(call: Call<MarketList>, response: Response<MarketList>) {
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (flagCloning == 2)
                            llMyTeam.visibility = View.GONE
                        else {
                            if (response.body()!!.myteam.equals("1"))
                                llMyTeam.visibility = View.VISIBLE
                            else if (response.body()!!.myteam.equals("0"))
                                llMyTeam.visibility = View.GONE
                        }
                        if (listOld!!.size > 0) {
                            listOld!!.clear()
                            listOld!!.addAll(list!!)
                            list!!.clear()
                        } else {
                            listOld!!.addAll(response.body()!!.crypto!!);
                        }
                        list!!.clear()
                        rv_Players!!.adapter!!.notifyDataSetChanged();
                        list!!.addAll(response.body()!!.crypto!!);
                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
//                            listOld!!.get(i).addedToList = 0

                        }
                        //sortingConcept
                        if (flagAlphaSort) {
                            val sortedList = list!!.sortedBy { it.symbol?.toString() }
                            for (obj in sortedList) {
                                list!!.clear()
                                list!!.addAll(sortedList)
                            }
                        } else if (flagPriceLTH) {
                            val sortedList = list!!.sortedBy { it.latestPrice?.toDouble() }
                            for (obj in sortedList) {
                                list!!.clear()
                                list!!.addAll(sortedList)
                            }

                        } else if (flagDayLTH) {
                            val sortedList = list!!.sortedBy { it.changeper?.toDouble() }
                            for (obj in sortedList) {
                                list!!.clear()
                                list!!.addAll(sortedList)
//                                    rv_currencyList!!.adapter!!.notifyDataSetChanged()
                            }
                        } else if (flagPriceHTL) {
                            val sortedList = list!!.sortedByDescending { it.latestPrice?.toDouble() }
                            for (obj in sortedList) {
                                list!!.clear()
                                list!!.addAll(sortedList)
//                                    rv_currencyList!!.adapter!!.notifyDataSetChanged()
                            }

                        } else if (flagDayHTL) {
                            val sortedList = list!!.sortedByDescending { it.changeper?.toDouble() }
                            for (obj in sortedList) {
                                list!!.clear()
                                list!!.addAll(sortedList)
//                                    rv_currencyList!!.adapter!!.notifyDataSetChanged()
                            }
                        } else if (flagVolume) {
                            val sortedList = list!!.sortedByDescending { it.latestVolume?.toDouble() }
                            for (obj in sortedList) {
                                list!!.clear()
                                list!!.addAll(sortedList)
//                                    rv_currencyList!!.adapter!!.notifyDataSetChanged()
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
                                }
                            }
                        }

                        rv_Players!!.adapter = marketlistAdapter;
                        rv_Players!!.adapter!!.notifyDataSetChanged();
                        setTeamText(marketSelectedItems!!.size.toString())
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

            override fun onFailure(call: Call<MarketList>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
//                d.dismiss()
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


    fun getTeamAgainlist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketList> =
            apiService.getMarketList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), marketId.toString()/*.toString()*/,
                getFromPrefsString(StockConstant.USERID)!!
            )
        call.enqueue(object : Callback<MarketList> {

            override fun onResponse(call: Call<MarketList>, response: Response<MarketList>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        setSectorFilter("")
                        list!!.clear()
                        rv_Players!!.adapter!!.notifyDataSetChanged();
                        list!!.addAll(response.body()!!.crypto!!)

                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
                        }

                        for (i in 0 until list!!.size) {
                            for (j in 0 until marketRemovedItems!!.size) {
                                if (list!!.get(i).cryptocurrencyid == marketRemovedItems!!.get(j).cryptocurrencyid) {
                                    list!!.get(i).addedToList = 1
                                }
                            }
                        }
                        rv_Players!!.adapter = marketlistAdapter;
                        rv_Players!!.adapter!!.notifyDataSetChanged();

                        marketSelectedItems = marketRemovedItems;
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
                    flagVolume = true
                    var sortedList = list!!.sortedByDescending { it.latestVolume.toDouble() }

                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("price")) {
                    flagPriceLTH = true
                    var sortedList = list!!.sortedWith(compareBy { it.latestPrice })
                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("priceHTL")) {
                    flagPriceHTL = true
                    var sortedList = list!!.sortedByDescending { it.latestPrice?.toDouble() }
                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("dayLTH")) {
                    flagDayLTH = true
                    var sortedList = list!!.sortedBy { it.changeper?.toDouble() }
                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("dayHTL")) {
                    flagDayHTL = true
                    var sortedList = list!!.sortedByDescending { it.changeper?.toDouble() }
                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("Alpha")) {
                    flagAlphaSort = true
                    var sortedList = list!!.sortedBy { it.symbol?.toString() }

                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("nodata")) {
                    getTeamAgainlist()
                }
            }
        }
        if (requestCode == StockConstant.RESULT_CODE_CREATE_TEAM) {
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


        if (requestCode == StockConstant.RESULT_CODE_MARKET_REMOVE_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                if (data.getStringExtra("flag").equals("1")) {
                    marketRemovedItems = data.getParcelableArrayListExtra("removedlist")
                    getTeamAgainlist()
                } else if (data.getStringExtra("flag").equals("2")) {
                    var intent = Intent();
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

            }
        } else if (requestCode == 10) {
            if (resultCode == RESULT_OK && data != null) {
                if (data.getStringExtra("resetfiltermarket").equals("0")) {
                    flagFilter = true
                    getMarketTeamlist()
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
                        AppDelegate.showAlert(this@ActivityMarketTeam, response.body()!!.message)
                        Handler().postDelayed({
                            finish()
                        }, 1000)

                    } else if (response.body()!!.status == "0") {
                        AppDelegate.showAlert(this@ActivityMarketTeam, response.body()!!.message)
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

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacksAndMessages(null);
    }
}