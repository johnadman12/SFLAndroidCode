package stock.com.ui.live_contest

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
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
import stock.com.ui.live_contest.adapter.LiveContestAdapter
import stock.com.ui.live_contest.adapter.RecycleTeamAdapter
import stock.com.ui.pojo.ContestDetail
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
    private var filterTeamList: ArrayList<ContestDetail.Score>? = null;

    private var liveAdapter: LiveContestAdapter? = null;
    private var teamAdapter: RecycleTeamAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_contest)
        StockConstant.ACTIVITIES.add(this)
        filterTeamList = ArrayList()
        if (intent != null) {
            contestid = intent.getIntExtra("contestid", 0)
            exchangeid = intent.getIntExtra(StockConstant.EXCHANGEID, 0)
        }

        img_btn_back.setOnClickListener {
            onBackPressed();
        }
        img_btn_close.setOnClickListener {
            finish();
        }

        tvViewTeam.setOnClickListener {
            /* startActivity(
                 Intent(this@LiveContestActivity, TeamPreviewActivity::class.java)
                     .putExtra(StockConstant.STOCKLIST, scores.get(position).stock)
             )*/
        }
        getContestDetail()
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
    fun setLiveTeamAdapter(scores: MutableList<ContestDetail.Score>) {
        teamAdapter = RecycleTeamAdapter(
            this@LiveContestActivity,
            getFromPrefsString(StockConstant.USERID)!!.toInt()
            , scores, this
        );
        val llm = LinearLayoutManager(this@LiveContestActivity);
        llm.orientation = LinearLayoutManager.HORIZONTAL;
        recycle_myteam!!.layoutManager = llm;
        recycle_myteam!!.adapter = teamAdapter;

    }

    fun getContestDetail() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<ContestDetail> =
            apiService.getContestDetail(
                contestid.toString()
                , getFromPrefsString(StockConstant.USERID).toString(), "live"
            )
        call.enqueue(object : Callback<ContestDetail> {

            override fun onResponse(call: Call<ContestDetail>, response: Response<ContestDetail>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        setLiveAdapter(response.body()!!.scores)
                        for (i in 0 until response.body()!!.scores.size)
                            if (response.body()!!.scores.get(i).userid.equals(getFromPrefsString(StockConstant.USERID))) {
                                filterTeamList!!.add(response.body()!!.scores.get(i))
                            }
                        if (response.body()!!.contest.size > 0)
                            setTimer(response.body()!!.contest.get(0))
                        if (filterTeamList!!.size > 0) {
                            if (filterTeamList!!.size == 1) {
                                setDataForTeam(filterTeamList!!, 0)
                                recycle_myteam.visibility = View.GONE
                            } else {
                                setLiveTeamAdapter(filterTeamList!!)
                            }

                        }

                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<ContestDetail>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
                d.dismiss()
            }
        })
    }

    fun setTimer(contest: ContestDetail.Contest) {
        if (!contest.schedule_end.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(contest.schedule_end)
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis
            if (diff.toString().contains("-")) {
                tvTimeLeft.setText("00H:00M:00S")
            } else {
                val newtimer = object : CountDownTimer(1000000000, 1000) {
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


    fun setDataForTeam(contest: MutableList<ContestDetail.Score>, position: Int) {
        tvRank.setText(contest.get(position).rank)
        totalChange.setText(contest.get(position).teamId)

    }
}
