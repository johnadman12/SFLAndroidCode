package stock.com.ui.live_contest

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
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
import stock.com.ui.live_contest.adapter.LiveContestAdapter
import stock.com.ui.pojo.ContestDetail
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.text.SimpleDateFormat
import java.util.*

class LiveContestActivity : BaseActivity() {
    var contestid: Int = 0
    var exchangeid: Int = 0

    private var liveAdapter: LiveContestAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_contest)
        StockConstant.ACTIVITIES.add(this)
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
        getContestDetail()
    }

    @SuppressLint("WrongConstant")
    fun setLiveAdapter(scores: MutableList<ContestDetail.Score>) {
        liveAdapter = LiveContestAdapter(applicationContext, scores);
        val llm = LinearLayoutManager(applicationContext);
        llm.orientation = LinearLayoutManager.VERTICAL;
        recyclerView_Live!!.layoutManager = llm;
        recyclerView_Live!!.adapter = liveAdapter;

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
//                        setRulesAdapter(response.body()!!.rules)
                        setLiveAdapter(response.body()!!.scores)
                        if (response.body()!!.contest.size > 0)
                            setData(response.body()!!.contest.get(0))
                    }
                } else {
                    Toast.makeText(
                        this@LiveContestActivity,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<ContestDetail>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@LiveContestActivity,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                d.dismiss()
            }
        })
    }

    private fun setData(contest: ContestDetail.Contest) {
        tvRank.setText(contest.rank)
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
}
