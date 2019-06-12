package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
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
import stock.com.ui.dashboard.ContestNewBottom.ActivityMyTeam
import stock.com.ui.dashboard.Team.Stock.ActivityStockDetail
import java.util.*


class ActivityCreateTeam : BaseActivity(), View.OnClickListener {
    private var stockSelectedItems: ArrayList<StockTeamPojo.Stock>? = null
    //    private var stockSelectedWizardItems: ArrayList<StockTeamPojo.Stock>? = null
    private var stockRemovedItems: ArrayList<StockTeamPojo.Stock>? = null
    private var stockTeamAdapter: StockTeamAdapter? = null;
    private var list: ArrayList<StockTeamPojo.Stock>? = null;
    private var parseList: ArrayList<StockTeamPojo.Stock>? = null;
    val RESULT_DATA = 1003
    var flag: Boolean = false
    var flagCloning: Int = 0
    var sector: String = ""

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
                            "flagMarket",
                            false
                        )
                )
            }
            R.id.imgButtonWizard -> {
                showJoinContestDialogue()
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
                    Intent(this@ActivityCreateTeam, ActivitySortTeam::class.java),
                    StockConstant.RESULT_CODE_SORT_CREATE_TEAM
                )
            }
            R.id.tvViewteam -> {
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

    var exchangeId: Int = 0
    var contestId: Int = 0
    var teamId: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)
        StockConstant.ACTIVITIES.add(this)
        list = ArrayList();
        initView()
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
        tvViewteam.isEnabled = false
        imgButtonWizard.setOnClickListener(this)
        if (intent != null) {
            exchangeId = intent.getIntExtra(StockConstant.EXCHANGEID, 0)
            contestId = intent.getIntExtra(StockConstant.CONTESTID, 0)
            flagCloning = intent.getIntExtra("isCloning", 0)
            if (flagCloning == 1) {
                stockSelectedItems = intent.getParcelableArrayListExtra(StockConstant.STOCKLIST)
                teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
            }
        }
      setTeamText(stockSelectedItems!!.size.toString())
        stockTeamAdapter = StockTeamAdapter(
            this, list as ArrayList,
            this@ActivityCreateTeam,
            object : StockTeamAdapter.OnItemCheckListener {
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

    //    fun getTeamlist() {
//        val d = StockDialog.showLoading(this)
//        d.setCanceledOnTouchOutside(false)
//        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
//        val call: Call<StockTeamPojo> =
//            apiService.getStockList(
//                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), exchangeId/*.toString()*/,
//                getFromPrefsString(StockConstant.USERID)!!.toInt()/*.toString()*/
//            )
//        call.enqueue(object : Callback<StockTeamPojo> {
//            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
//                d.dismiss()
//                if (response.body() != null) {
//                    if (response.body()!!.status == "1") {
//                        Handler().postDelayed(Runnable {
//                        }, 100)
//                        list!!.addAll(response.body()!!.stock!!);
////                        displayToast(list!!.size.toString())
//                        rv_Players.adapter!!.notifyDataSetChanged();
//
//                        //  setStockTeamAdapter(response.body()!!.stock!!)
//
//                    } else if (response.body()!!.status == "2") {
//                        appLogout()
//                    }
//                } else {
//                    Toast.makeText(
//                        this@ActivityCreateTeam,
//                        resources.getString(R.string.internal_server_error),
//                        Toast.LENGTH_LONG
//                    ).show()
//                    d.dismiss()
//                }
//            }
//
//            override fun onFailure(call: Call<StockTeamPojo>, t: Throwable) {
//                println(t.toString())
//                Toast.makeText(
//                    this@ActivityCreateTeam,
//                    resources.getString(R.string.something_went_wrong),
//                    Toast.LENGTH_LONG
//                ).show()
//                displayToast(resources.getString(R.string.something_went_wrong))
//                d.dismiss()
//            }
//        })
//    }
    fun getTeamlist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockTeamPojo> =
            apiService.getStockList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), exchangeId/*.toString()*/,
                getFromPrefsString(StockConstant.USERID)!!.toInt()/*.toString()*/, sector
            )
        call.enqueue(object : Callback<StockTeamPojo> {
            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (response.body()!!.myteam.equals("1"))
                            llMyTeam.visibility = VISIBLE
                        else if (response.body()!!.myteam.equals("0"))
                            llMyTeam.visibility = GONE

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
                        for (i in 0 until list!!.size) {
                            for (j in 0 until stockSelectedItems!!.size) {
                                if (list!!.get(i).stockid == stockSelectedItems!!.get(j).stockid) {
                                    list!!.get(i).stock_type = stockSelectedItems!!.get(j).stock_type
                                }
                            }
                        }
                        rv_Players!!.adapter = stockTeamAdapter;
                        rv_Players!!.adapter!!.notifyDataSetChanged();
                        d.dismiss()
                    } else if (response.body()!!.status == "2") {
                        d.dismiss()
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

    fun getTeamAgainlist() {
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
                        setSectorFilter("")
                        list!!.clear()
                        rv_Players!!.adapter!!.notifyDataSetChanged();
                        list!!.addAll(response.body()!!.stock!!)

                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
                        }

                        for (i in 0 until list!!.size) {
                            for (j in 0 until stockRemovedItems!!.size) {
                                if (list!!.get(i).stockid == stockRemovedItems!!.get(j).stockid) {
                                    list!!.get(i).addedToList = 1
                                }
                            }
                        }
                        rv_Players!!.adapter = stockTeamAdapter;
                        rv_Players!!.adapter!!.notifyDataSetChanged();

                        stockSelectedItems = stockRemovedItems;
                        setTeamText(stockSelectedItems!!.size.toString())

                    } else if (response.body()!!.status == "2") {
                        d.dismiss()
                        appLogout()
                    }
                } else {
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
                    stockRemovedItems = data.getParcelableArrayListExtra("removedlist")
                    getTeamAgainlist()
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
                    getTeamlist()
                } else {
                    sector = ""
                    getTeamlist()
                }
            }
        }
    }


}






