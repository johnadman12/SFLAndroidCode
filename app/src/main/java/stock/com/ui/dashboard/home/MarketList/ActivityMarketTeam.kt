package stock.com.ui.dashboard.home.MarketList

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
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
import stock.com.ui.dashboard.Team.ActivitySortTeam
import stock.com.ui.pojo.MarketList
import stock.com.ui.watch_list.WatchListActivity
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.util.ArrayList

class ActivityMarketTeam : BaseActivity(), View.OnClickListener {
    var marketId: Int = 0
    var marketlistAdapter: MarketListAdapter? = null
    private var marketSelectedItems: ArrayList<MarketList.Crypto>? = null
    private var marketRemovedItems: ArrayList<MarketList.Crypto>? = null
    private var marketWizardSelectedItems: ArrayList<MarketList.Crypto>? = null
    private var list: ArrayList<MarketList.Crypto>? = null;
    var flag: Boolean = false
    var flagCloning: Int = 0
    var teamId: Int = 0
    var contestFee: String = ""
    var contestId: Int = 0
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }

            R.id.imgButtonWizard -> {
                showJoinContestDialogue()
            }
            R.id.ll_watchlist -> {
                startActivity(
                    Intent(this@ActivityMarketTeam, WatchListActivity::class.java)
                )
            }
            R.id.ll_sort -> {
                startActivityForResult(
                    Intent(this@ActivityMarketTeam, ActivitySortTeam::class.java),
                    StockConstant.RESULT_CODE_SORT_MARKET_TEAM
                )
            }
            R.id.tvViewteam -> {
                if (flag) {
                    startActivityForResult(
                        Intent(this@ActivityMarketTeam, ActivityMarketViewTeam::class.java)
                            .putExtra(StockConstant.MARKETLIST, marketWizardSelectedItems)
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)
        StockConstant.ACTIVITIES.add(this)
        list = ArrayList();
        marketRemovedItems = ArrayList();
        initView()
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        marketSelectedItems = ArrayList()
        marketWizardSelectedItems = ArrayList()
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
            contestFee = intent.getStringExtra(StockConstant.CONTESTFEE)
            contestId = intent.getIntExtra(StockConstant.CONTESTID, 0)

        }

        marketlistAdapter = MarketListAdapter(
            this, list as ArrayList,
            this@ActivityMarketTeam,
            object : MarketListAdapter.OnItemCheckListener {
                override fun onItemClick(item: MarketList.Crypto) {
                    /*startActivityForResult(
                        Intent(
                            this@ActivityMarketTeam,
                            ActivityStockDetail::class.java
                        )
                            .putExtra("Stockid", item.stockid)
                            .putExtra(StockConstant.STOCKLIST, list)
                            .putExtra(StockConstant.SELECTEDSTOCK, stockSelectedItems!!.size)
                        , StockConstant.RESULT_CODE_CREATE_TEAM
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
                marketlistAdapter!!.getFilter().filter(s);
            }
        })
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        rv_Players.visibility = View.VISIBLE
        rv_Players!!.adapter = marketlistAdapter;

        getMarketTeamlist()
    }

    fun getMarketTeamlist() {
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
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        list!!.clear()
                        list!!.addAll(response.body()!!.crypto)
                        rv_Players!!.adapter = marketlistAdapter;
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

            override fun onFailure(call: Call<MarketList>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
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
                        list!!.clear()
                        list!!.addAll(response.body()!!.crypto!!);
                        setTeamText(list!!.size.toString())
                        marketWizardSelectedItems!!.addAll(list!!)
//                        stockSelectedItems!!.addAll(response.body()!!.stock!!)
//                        displayToast(list!!.size.toString())
                        setWizardAdapter()

                        //  setStockTeamAdapter(response.body()!!.stock!!)

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

    fun setWizardAdapter() {
        rv_Players!!.adapter = MarketWizardAdapter(
            this,
            list as ArrayList,
            object : MarketWizardAdapter.OnItemCheckListener {
                override fun onItemClick(item: MarketList.Crypto) {
                    /*startActivityForResult(
                        Intent(
                            this@ActivityCreateTeam,
                            ActivityStockDetail::class.java
                        )
                            .putExtra("Stockid", item.stockid)
                            .putExtra(StockConstant.STOCKLIST, list)
                            .putExtra(StockConstant.SELECTEDSTOCK, stockSelectedItems!!.size)
                        , StockConstant.RESULT_CODE_CREATE_TEAM
                    )*/
                }

                override fun onItemUncheck(item: MarketList.Crypto) {
                    marketWizardSelectedItems?.remove(item);
                    setTeamText(marketWizardSelectedItems!!.size.toString())
                    Log.e("stocklistremove", marketWizardSelectedItems.toString())
                }

                override fun onItemCheck(item: MarketList.Crypto) {
                    marketWizardSelectedItems?.add(item);
                    setTeamText(marketWizardSelectedItems!!.size.toString())
                    Log.e("stocklist", marketWizardSelectedItems.toString())
                }
            });
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
                if (data.getStringExtra("flag").equals("Volume")) {

                    var sortedList = list!!.sortedBy { it.latestVolume.toDouble() }

                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("price")) {
                    var sortedList = list!!.sortedWith(compareBy { it.latestPrice })
                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_Players!!.adapter!!.notifyDataSetChanged()
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
        }
    }
}