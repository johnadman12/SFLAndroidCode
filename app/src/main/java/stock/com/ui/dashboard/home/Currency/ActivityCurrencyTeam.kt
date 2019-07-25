package stock.com.ui.dashboard.home.Currency

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_create_team.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.Team.ActivityMarketDetail
import stock.com.ui.pojo.CurrencyPojo
import stock.com.ui.pojo.MarketList
import stock.com.utils.StockConstant
import java.util.*

class ActivityCurrencyTeam : BaseActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
        }
    }

    private var currencySelected: ArrayList<CurrencyPojo.Currency>? = null
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
    private var list: ArrayList<stock.com.ui.pojo.CurrencyPojo.Currency>? = null;
    private var listOld: ArrayList<CurrencyPojo.Currency>? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)
        StockConstant.ACTIVITIES.add(this)
        list = ArrayList();
        listOld = ArrayList();
        currencySelected = ArrayList();
        initView()
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
        if (intent != null) {
            marketId = intent.getIntExtra(StockConstant.MARKETID, 0)
            contestId = intent.getIntExtra(StockConstant.CONTESTID, 0)
            flagCloning = intent.getIntExtra("isCloning", 0)

            if (flagCloning == 1) {
//                currencySelected = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)

            } else if (flagCloning == 2) {
//                currencySelected = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)
                teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
                ll_filter.visibility = View.GONE
                tvViewteam.setText("  Save Team  ")
                textTeam.setText("Edit Team")
                relFieldView.visibility = View.VISIBLE
            } else {
                contestFee = intent.getStringExtra(StockConstant.CONTESTFEE)
                teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
            }

        }

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
                                        currencySelected!!.get(j).cryptoType = item.cryptoType

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
                                        currencySelected!!.get(j).cryptoType = item.cryptoType
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
        et_search_stock.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                callSearch(s.toString())
            }
        })
        srl_layout.setOnRefreshListener {
            //            flagRefresh = true
            getCurrencyTeamlist()
        }

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        rv_Players.visibility = View.VISIBLE
        rv_Players!!.adapter = currencyAdapter;

        getCurrencyTeamlist()
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


    fun getCurrencyTeamlist() {
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<CurrencyPojo> =
            apiService.getCurrencyList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), marketId.toString()/*.toString()*/,
                getFromPrefsString(StockConstant.USERID)!!, page.toString(), limit.toString()
            )
        call.enqueue(object : Callback<CurrencyPojo> {
            override fun onResponse(call: Call<CurrencyPojo>, response: Response<CurrencyPojo>) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    //for show my team
                    if (response.body()!!.status == "1") {
                        if (flagCloning == 2)
                            llMyTeam.visibility = View.GONE
                        else {
                            if (response.body()!!.myteam.equals("1"))
                                llMyTeam.visibility = View.VISIBLE
                            else if (response.body()!!.myteam.equals("0"))
                                llMyTeam.visibility = View.GONE
                        }
                        list!!.clear()
                        list = response.body()!!.currency
                        listOld!!.clear()
                        listOld!!.addAll(list!!)
                        for (i in 0 until list!!.size) {
                            list!!.get(i).addedToList = 0
//                            listOld!!.get(i).addedToList = 0

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

                        rv_Players!!.adapter = currencyAdapter;
                        rv_Players!!.adapter!!.notifyDataSetChanged();
                        setTeamText(currencySelected!!.size.toString())
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


    fun getTeamText(): Int {
        return currencySelected!!.size

    }
}