package stock.com.ui.live_contest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_live_contest.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.dashboard.home.Currency.CurrencyPreviewTeamActivity
import stock.com.ui.dashboard.home.MarketList.MarketTeamPreviewActivity
import stock.com.ui.live_contest.adapter.LiveContestAdapter
import stock.com.ui.live_contest.adapter.RecycleTeamAdapter
import stock.com.ui.pojo.ContestDetail
import stock.com.ui.pojo.CurrencyPojo
import stock.com.ui.pojo.MarketList
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class LiveContestActivity : BaseActivity() {
    var contestid: Int = 0
    var exchangeid: Int = 0
    private var stockList: ArrayList<StockTeamPojo.Stock>? = null;
    private var crptoList: ArrayList<MarketList.Crypto>? = null;
    private var currencyList: ArrayList<CurrencyPojo.Currency>? = null;
    private var filterTeamList: ArrayList<ContestDetail.Score>? = null;
    private var scores: MutableList<ContestDetail.Score>? = null

    lateinit var mainHandler: Handler;
    var idTeam: Int = 0
    var totalchange: String = ""
    var teamName: String = ""
    var type: String = ""
    private var liveAdapter: LiveContestAdapter? = null;
    private var teamAdapter: RecycleTeamAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_contest)
        StockConstant.ACTIVITIES.add(this)
        stockList = ArrayList()
        crptoList = ArrayList()
        scores = ArrayList()
        currencyList = ArrayList()
        filterTeamList = ArrayList()
        if (intent != null) {
            contestid = intent.getIntExtra(StockConstant.CONTESTID, 0)
            exchangeid = intent.getIntExtra(StockConstant.EXCHANGEID, 0)
        }

        img_btn_back.setOnClickListener {
            onBackPressed();
        }
        img_btn_close.setOnClickListener {
            finish();
        }

        tvViewTeam.setOnClickListener {
            if (type.equals("crypto"))
                startActivity(
                    Intent(this@LiveContestActivity, MarketTeamPreviewActivity::class.java)
                        .putExtra(StockConstant.MARKETLIST, crptoList)
                        .putExtra(StockConstant.TEAMNAME, teamName)
                        .putExtra(StockConstant.TOTALCHANGE, totalchange)
                ) else if (type.equals("equity"))
                startActivity(
                    Intent(this@LiveContestActivity, TeamPreviewActivity::class.java)
                        .putExtra(StockConstant.STOCKLIST, stockList)
                        .putExtra(StockConstant.TEAMNAME, teamName)
                        .putExtra(StockConstant.TOTALCHANGE, totalchange)
                )
            else if (type.equals("currency"))
                startActivity(
                    Intent(this@LiveContestActivity, CurrencyPreviewTeamActivity::class.java)
                        .putExtra(StockConstant.MARKETLIST, currencyList)
                        .putExtra(StockConstant.TEAMNAME, teamName)
                        .putExtra(StockConstant.TOTALCHANGE, totalchange)
                )
        }

        getContestDetail(0)

    }

    override fun onResume() {
        super.onResume()
        mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                getContestDetail(1)
                mainHandler.postDelayed(this, 10000)
            }
        })
    }

    @SuppressLint("WrongConstant")
    fun setLiveAdapter(scores: MutableList<ContestDetail.Score>) {
        liveAdapter = LiveContestAdapter(
            this@LiveContestActivity,
            getFromPrefsString(StockConstant.USERID)!!.toInt(), scores
        );
        val llm = LinearLayoutManager(applicationContext);
        llm.orientation = LinearLayoutManager.VERTICAL;
        recyclerView_Live!!.layoutManager = llm;
        recyclerView_Live!!.adapter = liveAdapter;
    }

    @SuppressLint("WrongConstant")
    fun setLiveTeamAdapter() {
        teamAdapter = RecycleTeamAdapter(
            this@LiveContestActivity
            , scores!!, object : RecycleTeamAdapter.ItemClickListner {
                override fun onItemClick(item: ContestDetail.Score) {
                    teamName = item.userteamname
                    idTeam = item.teamId
                    tvRank.setText(item.rank)
                    totalchange = item.totalchange_Per
                    totalChange.setText(item.totalchange_Per + "%")
                    tvRankLable.setText(getString(R.string.rank) + " (" + item.teamNameCount + ")")
                    if (item.crypto.size > 0) {
                        type = "crypto"
                        crptoList!!.clear()
                        crptoList!!.addAll(item.crypto)
                    } else if (item.stock.size > 0) {
                        type = "equity"
                        stockList!!.clear()
                        stockList!!.addAll(item.stock)
                    } else if (item.currencies.size > 0) {
                        type = "currency"
                        currencyList!!.clear()
                        currencyList!!.addAll(item.currencies)
                    }
                }
            }
        );
        val llm = LinearLayoutManager(this@LiveContestActivity);
        llm.orientation = LinearLayoutManager.HORIZONTAL;
        recycle_myteam!!.layoutManager = llm;
        recycle_myteam!!.adapter = teamAdapter;
    }

    fun getContestDetail(refreshing: Int) {
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<ContestDetail> =
            apiService.getContestDetail(
                contestid.toString()
                , getFromPrefsString(StockConstant.USERID).toString(), "live", exchangeid.toString()
            )
        call.enqueue(object : Callback<ContestDetail> {

            override fun onResponse(call: Call<ContestDetail>, response: Response<ContestDetail>) {
//                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (response.body()!!.contest.size > 0) {
                            setTimer(response.body()!!.contest.get(0))
                            tvcontesttype.setText(response.body()!!.contest.get(0).catname)
                        }
                        setLiveAdapter(response.body()!!.scores)
                        filterTeamList!!.clear();

                        for (i in 0 until response.body()!!.scores.size) {
                            if (response.body()!!.scores.get(i).userid.toString().equals(
                                    getFromPrefsString(
                                        StockConstant.USERID
                                    )
                                )
                            ) {
                                filterTeamList!!.add(response.body()!!.scores.get(i));

                                if (refreshing == 0) {
                                    tvRank.setText(filterTeamList!!.get(0).rank)
                                    totalChange.setText(filterTeamList!!.get(0).totalchange_Per + "%")
                                    totalchange = filterTeamList!!.get(0).totalchange_Per
                                } else {
                                    if (idTeam > 0)
                                        for (i in 0 until filterTeamList!!.size) {
                                            if (idTeam.equals(filterTeamList!!.get(i).teamId)) {
                                                tvRank.setText(filterTeamList!!.get(i).rank)
                                                totalChange.setText(filterTeamList!!.get(i).totalchange_Per + "%")
                                                totalchange = filterTeamList!!.get(i).totalchange_Per
                                            }
                                        }
                                }

                                if (filterTeamList!!.size > 0) {
//                                    setLiveTeamAdapter(filterTeamList!!)
                                    if (filterTeamList!!.size == 1) {
                                        recycle_myteam.visibility = View.GONE
                                        tvRank.setText(filterTeamList!!.get(0).rank)
                                        totalChange.setText(filterTeamList!!.get(0).totalchange_Per + "%")
                                        totalchange = filterTeamList!!.get(0).totalchange_Per
                                        stockList!!.clear()
                                        crptoList!!.clear()
                                        currencyList!!.clear()
                                        if (filterTeamList!!.get(0).stock.size > 0) {
                                            type = "equity"
                                            stockList!!.addAll(filterTeamList!!.get(0).stock)

                                        } else if (filterTeamList!!.get(0).crypto.size > 0) {
                                            type = "crypto"
                                            crptoList!!.addAll(filterTeamList!!.get(0).crypto)

                                        } else if (filterTeamList!!.get(0).currencies.size > 0) {
                                            type = "currency"
                                            currencyList!!.addAll(filterTeamList!!.get(0).currencies)
                                        }

                                    } else {
                                        if (refreshing == 0) {
                                            tvRank.setText(filterTeamList!!.get(0).rank)
                                            totalChange.setText(filterTeamList!!.get(0).totalchange_Per + "%")
                                            totalchange = filterTeamList!!.get(0).totalchange_Per
                                            stockList!!.clear()
                                            crptoList!!.clear()
                                            currencyList!!.clear()
                                            if (filterTeamList!!.get(0).stock.size > 0) {
                                                type = "equity"
                                                stockList!!.addAll(filterTeamList!!.get(0).stock)

                                            } else if (filterTeamList!!.get(0).crypto.size > 0) {
                                                type = "crypto"
                                                crptoList!!.addAll(filterTeamList!!.get(0).crypto)

                                            } else if (filterTeamList!!.get(0).currencies.size > 0) {
                                                type = "currency"
                                                currencyList!!.addAll(filterTeamList!!.get(0).currencies)
                                            }
                                        } else if (refreshing == 1) {
                                            if (idTeam > 0)
                                                for (i in 0 until filterTeamList!!.size) {
                                                    if (idTeam.equals(filterTeamList!!.get(i).teamId)) {
                                                        tvRank.setText(filterTeamList!!.get(i).rank)
                                                        totalChange.setText(filterTeamList!!.get(i).totalchange_Per + "%")
                                                        totalchange = filterTeamList!!.get(i).totalchange_Per
                                                        stockList!!.clear()
                                                        crptoList!!.clear()
                                                        currencyList!!.clear()
                                                        if (filterTeamList!!.get(i).stock.size > 0) {
                                                            type = "equity"
                                                            stockList!!.addAll(filterTeamList!!.get(0).stock)

                                                        } else if (filterTeamList!!.get(i).crypto.size > 0) {
                                                            type = "crypto"
                                                            crptoList!!.addAll(filterTeamList!!.get(0).crypto)

                                                        } else if (filterTeamList!!.get(i).currencies.size > 0) {
                                                            type = "currency"
                                                            currencyList!!.addAll(filterTeamList!!.get(0).currencies)
                                                        }
                                                    }
                                                }
                                        }
                                        //setLiveTeamAdapter(filterTeamList!!)
                                    }

                                }
                            }
                        }
                        scores!!.clear()
                        scores!!.addAll(filterTeamList!!)

                        if (scores!!.size > 0) {
                            if (refreshing == 0) {
                                setLiveTeamAdapter()
                            } else
                                recycle_myteam.visibility = View.VISIBLE;
                            if (teamAdapter != null)
                                teamAdapter!!.notifyDataSetChanged()

                        }
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
//                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<ContestDetail>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
//                d.dismiss()
            }
        })
    }


    fun setTimer(contest: ContestDetail.Contest) {
        if (!contest.schedule_end.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(contest.schedule_end)
            var timeZone: String = Calendar.getInstance().getTimeZone().getID();
            date = Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis

            if (diff.toString().contains("-")) {
                tvTimeLeft.setText("00H:00M:00S")
            } else {
                val newtimer = object : CountDownTimer(diff, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val cTime = Calendar.getInstance()
                        val diff = thatDay.timeInMillis - cTime.timeInMillis
                        val diffSec = diff / 1000
                        val seconds = diffSec % 60
                        val minutes = diffSec / 60 % 60
                        val hours = diffSec / 3600

                        tvTimeLeft.setText(hours.toString() + "H: " + minutes.toString() + "M: " + seconds.toString() + "S")
                    }

                    override fun onFinish() {
                    }
                }
                newtimer.start()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mainHandler.removeCallbacksAndMessages(null);
    }
}
