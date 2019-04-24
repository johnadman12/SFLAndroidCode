package stock.com.ui.contest.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.contest_detail_activity.*
import kotlinx.android.synthetic.main.dialog_information.*
import kotlinx.android.synthetic.main.include_back.*
import kotlinx.android.synthetic.main.row_view_featured_contest.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.Contestdeatil.RulesAdapter
import stock.com.ui.dashboard.Contestdeatil.ScoresAdapter
import stock.com.ui.dashboard.Team.ActivityCreateTeam
import stock.com.ui.pojo.ContestDetail
import stock.com.ui.winningBreakup.dialogues.BottomSheetWinningListFragment
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.text.SimpleDateFormat
import java.util.*


class ContestDetailActivity : BaseActivity(), View.OnClickListener {
    var contestid: Int = 0
    var exchangeid: Int = 0
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.img_btn_close -> {
                finish()
            }
            R.id.img_btn_back -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contest_detail_activity)
        initViews()
    }


    private fun initViews() {
        if (intent != null) {
            contestid = intent.getIntExtra("contestid", 0)
            exchangeid = intent.getIntExtra(StockConstant.EXCHANGEID, 0)
        }
        img_btn_back.setOnClickListener(this)
        img_btn_close.setOnClickListener(this)
        img_btn_close.visibility = View.VISIBLE
        getContestDetail()
        ll_Circular.setOnClickListener {
            startActivity(
                Intent(this, ActivityCreateTeam::class.java).putExtra(
                    StockConstant.EXCHANGEID, exchangeid
                )
            )
        }

    }

    @SuppressLint("WrongConstant")
    private fun setRulesAdapter(rules: MutableList<ContestDetail.Rule>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_rules!!.layoutManager = llm
        rv_rules!!.adapter = RulesAdapter(this, rules)
    }

    @SuppressLint("WrongConstant")
    private fun setScoreAdapter(scores: MutableList<ContestDetail.Score>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_score!!.layoutManager = llm
        rv_score!!.adapter = ScoresAdapter(this, scores)
    }

    fun getContestDetail() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<ContestDetail> =
            apiService.getContestDetail(contestid.toString())
        call.enqueue(object : Callback<ContestDetail> {

            override fun onResponse(call: Call<ContestDetail>, response: Response<ContestDetail>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        setRulesAdapter(response.body()!!.rules)
                        setScoreAdapter(response.body()!!.scores)
                        setData(response.body()!!.contest.get(0))
                    }
                } else {
                    Toast.makeText(
                        this@ContestDetailActivity,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<ContestDetail>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ContestDetailActivity,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                d.dismiss()
            }
        })
    }

    private fun setData(contest: ContestDetail.Contest) {
        entry_fee.setText(contest.entryFees)
        tvStockName.setText(contest.exchangename)
        tvTime.setText(contest.exchangename)
        tvWinnersTotal.setText(contest.totalwinners)
        tvTotalWinnings.setText(contest.winningAmount)
        circular_progress.setProgressTextAdapter(TIME_TEXT_ADAPTER)
        Glide.with(this).load(AppDelegate.EXCHANGE_URL + contest.exchangeimage.trim())
            .into(ivStock)
        iv_info.setOnClickListener {
            showInfoDialogue(contest.description);
        }
        var sports: Double =
            (contest.contestSize.toInt() - contest.teamsJoined.toInt()).toDouble()
        tvSprortsLeft.setText(
            sports.toString() + "/" +
                    contest.contestSize
        )
        if (!contest.scheduleStart.equals(" ")) {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(contest.scheduleStart)
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff = thatDay.timeInMillis - today.timeInMillis
            if (diff.toString().contains("-")) {
                tvTimeLeft.setText("0:0:0: Left")
            } else {
                val newtimer = object : CountDownTimer(1000000000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        val cTime = Calendar.getInstance()
                        val diff =thatDay.timeInMillis-cTime.timeInMillis

                        val diffSec = diff / 1000
                        val seconds = diffSec % 60
                        val minutes = diffSec / 60%60
                        val hours = diffSec / 3600

                        tvTimeLeft.setText(hours.toString() +"H: " + minutes.toString() + "M: " + seconds.toString() + "S")
                    }
                    override fun onFinish() {
                    }
                }
                newtimer.start()
            }
        }
        llWinners.setOnClickListener {
            val manager = supportFragmentManager
            val bottomSheetDialogFragment =
                BottomSheetWinningListFragment(contest.priceBreak, contest.winningAmount)
            bottomSheetDialogFragment.show(manager, "Bottom Sheet Dialog Fragment")
        }

        tvTime.setText(parseDateToddMMyyyy(contest.scheduleStart))
    }

    fun showInfoDialogue(textView: String) {
        var dialogue = Dialog(this)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialog_information)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.setCancelable(true)
        dialogue.setCanceledOnTouchOutside(true)
        dialogue.tvInfo.setText(textView)
        dialogue.btnOK.setOnClickListener {
            if (dialogue.isShowing)
                dialogue.dismiss()
        }
        dialogue.setCanceledOnTouchOutside(false)
        dialogue.setTitle(null)
        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }

    private val TIME_TEXT_ADAPTER =
        CircularProgressIndicator.ProgressTextAdapter { time ->
            val sb = ""
            sb
        }

}