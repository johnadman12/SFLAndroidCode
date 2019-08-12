package stock.com.ui.contest.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator
import com.bumptech.glide.Glide
import com.irozon.sneaker.Sneaker
import kotlinx.android.synthetic.main.activity_create_team.*
import kotlinx.android.synthetic.main.contest_detail_activity.*
import kotlinx.android.synthetic.main.dialog_choose_team.*
import kotlinx.android.synthetic.main.dialog_information.*
import kotlinx.android.synthetic.main.include_back.*
import kotlinx.android.synthetic.main.row_contest_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.Contestdeatil.RulesAdapter
import stock.com.ui.dashboard.Contestdeatil.ScoresAdapter
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.dashboard.Team.ActivityCreateTeam
import stock.com.ui.dashboard.home.Currency.ActivityCurrencyTeam
import stock.com.ui.dashboard.home.MarketList.ActivityMarketTeam
import stock.com.ui.pojo.ContestDetail
import stock.com.ui.winningBreakup.dialogues.BottomSheetWinningListFragment
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ContestDetailActivity : BaseActivity(), View.OnClickListener {
    var contestid: Int = 0
    var exchangeid: Int = 0
    var marketname: String = ""
    var flag: Boolean = false
    var diff: Long = 0
    var listScores: MutableList<ContestDetail.Score>? = null
    var listMy: MutableList<ContestDetail.Score>? = null
    var activity: DashBoardActivity = DashBoardActivity()
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.img_btn_close -> {
                finish()
            }
            R.id.img_btn_back -> {
                finish()
            }
            R.id.tvTeams -> {
                changeTextColor(tvTeams, ContextCompat.getColor(this, R.color.white))
                changeBackGroundColor(tvTeams, ContextCompat.getColor(this, R.color.colorbutton))

                changeTextColor(tvTeamsMy, ContextCompat.getColor(this, R.color.textColorLightBlack))
                changeBackGroundColor(tvTeamsMy, ContextCompat.getColor(this, R.color.white))
                if (listScores!!.size > 0) {
                    setScoreAdapter(listScores!!, marketname, diff)
                }

            }
            R.id.tvTeamsMy -> {
                changeTextColor(tvTeamsMy, ContextCompat.getColor(this, R.color.white))
                changeBackGroundColor(tvTeamsMy, ContextCompat.getColor(this, R.color.colorbutton))

                changeTextColor(tvTeams, ContextCompat.getColor(this, R.color.textColorLightBlack))
                changeBackGroundColor(tvTeams, ContextCompat.getColor(this, R.color.white))
                if (listScores!!.size > 0) {
                    listMy!!.clear()
                    for (i in 0 until listScores!!.size) {
                        if (listScores!!.get(i).userid.toString().equals(getFromPrefsString(StockConstant.USERID))) {
                            listMy!!.add(listScores!!.get(i))
                        }
                    }
                    tvTeamsMy.setText("My Teams (" + listMy!!.size + ")")
                    setScoreAdapter(listMy!!, marketname, diff)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.contest_detail_activity)
        StockConstant.ACTIVITIES.add(this)
        activity = DashBoardActivity()
        changeTextColor(tvTeams, ContextCompat.getColor(this, R.color.white))
        changeBackGroundColor(tvTeams, ContextCompat.getColor(this, R.color.colorbutton))


        changeTextColor(tvTeamsMy, ContextCompat.getColor(this, R.color.textColorLightBlack))
        changeBackGroundColor(tvTeamsMy, ContextCompat.getColor(this, R.color.white))

        listScores = ArrayList()
        listMy = ArrayList()
        if (intent != null) {
            contestid = intent.getIntExtra(StockConstant.CONTESTID, 0)
            exchangeid = intent.getIntExtra(StockConstant.EXCHANGEID, 0)
        }
        img_btn_back.setOnClickListener(this)
        img_btn_close.setOnClickListener(this)
        tvTeams.setOnClickListener(this)
        tvTeamsMy.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        initViews()
    }


    private fun initViews() {
        getContestDetail()
        circular_progress.setProgressTextAdapter(TIME_TEXT_ADAPTER)
    }

    @SuppressLint("WrongConstant")
    private fun setRulesAdapter(rules: MutableList<ContestDetail.Rule>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_rules!!.layoutManager = llm
        rv_rules!!.adapter = RulesAdapter(this, rules)
    }

    @SuppressLint("WrongConstant")
    private fun setScoreAdapter(scores: MutableList<ContestDetail.Score>, marketname: String, difference: Long) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_score!!.layoutManager = llm
        rv_score!!.adapter =
            ScoresAdapter(this, getFromPrefsString(StockConstant.USERID)!!.toInt(), scores, 1, marketname, difference)
    }

    fun getContestDetail() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<ContestDetail> =
            apiService.getContestDetail(
                contestid.toString()
                , getFromPrefsString(StockConstant.USERID).toString(), "", exchangeid.toString()
            )
        call.enqueue(object : Callback<ContestDetail> {

            override fun onResponse(call: Call<ContestDetail>, response: Response<ContestDetail>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (response.body()!!.scores.size == 0)
                            tvTeams.setText("Team Joined (0)")
                        else if (response.body()!!.scores.size == 1)
                            tvTeams.setText("Team Joined (1)")
                        else
                            tvTeams.setText(" Teams Joined (" + response.body()!!.scores.size.toString() + ")")

                        if (response.body()!!.rules.size == 0)
                            tvContestRules.visibility = View.GONE
                        setRulesAdapter(response.body()!!.rules)
                        listScores = response.body()!!.scores
                        marketname = response.body()!!.contest.get(0).marketname
                        listMy!!.clear()
                        for (i in 0 until listScores!!.size) {
                            if (listScores!!.get(i).userid.toString().equals(getFromPrefsString(StockConstant.USERID))) {
                                listMy!!.add(listScores!!.get(i))
                            }
                        }
                        tvTeamsMy.setText("My Teams (" + listMy!!.size + ")")
                        try {
                            setData(response.body()!!.contest.get(0))
                        } catch (e: java.lang.Exception) {

                        }
                        setScoreAdapter(listScores!!, marketname, diff)
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

    private fun setData(contest: ContestDetail.Contest) {
        entry_fee.setText(contest.entryFees)
        if (!TextUtils.isEmpty(contest.catname))
            tvContestType.setText(contest.catname)
        else
            tvContestType.setText("Private Contest")

        if (contest.marketname.equals("Equity")) {
            Glide.with(this).load(AppDelegate.EXCHANGE_URL + contest.exchangeimage.trim())
                .into(ivStock)
            tvStockName.setText(contest.exchangename)
        } else {
            tvStockName.setText(contest.marketname)
            Glide.with(this).load(R.drawable.ic_business)
                .into(ivStock)
        }
        ll_Circular.setOnClickListener {
            if (flag)
                AppDelegate.showSneakBarRed(this@ContestDetailActivity, "Contest is not live yet.", "DFX")
            else
                if (contest.marketname.equals("Equity")) {
                    exchangeid = contest.exchangeid
                    var intent = Intent(this, ActivityCreateTeam::class.java)
                    intent.putExtra(StockConstant.EXCHANGEID, exchangeid)
                    intent.putExtra(StockConstant.CONTESTID, contestid)
                    intent.putExtra("isCloning", 0)
                    startActivityForResult(intent, 405);
                } else if (contest.marketname.equals("Cryptocurrencies")) {
                    var intent = Intent(this@ContestDetailActivity, ActivityMarketTeam::class.java)
                    intent
                        .putExtra(StockConstant.MARKETID, contest.mid)
                        .putExtra(StockConstant.CONTESTID, contestid)
                        .putExtra(StockConstant.CONTESTFEE, contest.entryFees)
                        .putExtra("isCloning", 0)
                    startActivityForResult(intent, StockConstant.REDIRECT_UPCOMING_MARKET)
                } else if (contest.marketname.equals("Currencies")) {
                    var intent = Intent(this@ContestDetailActivity, ActivityCurrencyTeam::class.java)
                    intent
                        .putExtra(StockConstant.MARKETID, contest.mid)
                        .putExtra(StockConstant.CONTESTID, contestid)
                        .putExtra(StockConstant.CONTESTFEE, contest.entryFees)
                        .putExtra("isCloning", 0)
                    startActivityForResult(intent, StockConstant.REDIRECT_UPCOMING_MARKET)
                }

        }

        tvWinnersTotal.setText(contest.totalwinners)
        tvTotalWinnings.setText(contest.winningAmount)
        var amount: String = contest.entryFees.substring(1)
        if (amount.equals("0") && contest.priceBreak.size <= 0) {
            tvTotalWinnings.visibility = GONE
            text_totalwin.visibility = GONE
            llWinners.isEnabled = false
        } else {
            tvTotalWinnings.visibility = VISIBLE
            text_totalwin.visibility = VISIBLE
            llWinners.isEnabled = true
        }

        iv_info.setOnClickListener {
            AppDelegate.showInfoDialogue(contest.description, this);
        }

        if (TextUtils.isEmpty(contest.confirm_winning))
            tvConfirmWin.visibility = View.GONE
        else
            tvConfirmWin.visibility = View.VISIBLE

        if (TextUtils.isEmpty(contest.join_multiple))
            tvMulJoin.visibility = View.GONE
        else
            tvMulJoin.visibility = View.VISIBLE


        tvSprortsLeft.setText(
            contest.contest_teamremaining.toString() + "/" +
                    contest.contestSize
        )

        try {
            if (!contest.scheduleStart.equals(" ")) {
                val inputPattern = "yyyy-MM-dd HH:mm:ss"
                val inputFormat = SimpleDateFormat(inputPattern)
                var date: Date? = null
                date = inputFormat.parse(contest.scheduleStart)
                var timeZone: String = Calendar.getInstance().getTimeZone().getID();
                date = Date(date.getTime() + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()));
                val thatDay = Calendar.getInstance()
                thatDay.setTime(date);
                val today = Calendar.getInstance()
                diff = thatDay.timeInMillis - today.timeInMillis
                if (diff.toString().contains("-")) {
                    tvTimeLeft.setText("00H:00M:00S")
                    flag = true
                    AppDelegate.showSneakBarRed(this@ContestDetailActivity, "Contest is not live yet.", "DFX")
                    circular_progress.progressBackgroundColor =
                        ContextCompat.getColor(this@ContestDetailActivity, R.color.GrayColor)
                } else if (diff.equals("3600000")) {
                    val newtimer = object : CountDownTimer(diff, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val cTime = Calendar.getInstance()
                            val diff = thatDay.timeInMillis - cTime.timeInMillis
                            val diffSec = diff / 1000
                            val minutes = diffSec / 60 % 60
                            val hours = diffSec / 3600
                            tvTimeLeft.setText(hours.toString() + "H: \n " + minutes.toString() + "M: ")
                        }

                        override fun onFinish() {
                        }
                    }
                    newtimer.start()

                } else {
                    val newtimer = object : CountDownTimer(diff, 1000) {
                        override fun onTick(millisUntilFinished: Long) {
                            val cTime = Calendar.getInstance()
                            val diff = thatDay.timeInMillis - cTime.timeInMillis
                            if (diff < 900000) {
                                ll_Circular.isEnabled = false
                                txtjoin.setTextSize(16.00f)
                                txtjoin.setText("Starts\nSoon")
                                circular_progress.progressBackgroundColor =
                                    ContextCompat.getColor(this@ContestDetailActivity, R.color.GrayColor)
                            } else {
                                ll_Circular.isEnabled = true
                                circular_progress.progressBackgroundColor =
                                    ContextCompat.getColor(this@ContestDetailActivity, R.color.green)
                            }

                            val diffSec = diff / 1000
                            val seconds = diffSec % 60
                            val minutes = diffSec / 60 % 60
                            val hours = diffSec / 3600

                            tvTimeLeft.setText(hours.toString() + "H: \n " + minutes.toString() + "M: \n" + seconds.toString() + "S")
                        }

                        override fun onFinish() {
                        }
                    }
                    newtimer.start()
                }
            }
        } catch (e: Exception) {

        }

        val contestLeft: Double = contest.contestSize.toDouble() - contest.contest_teamremaining.toDouble()
        circular_progress.isAnimationEnabled
        circular_progress.setProgress(
            contestLeft,
            contest.contestSize.toDouble()
        )
        llWinners.setOnClickListener {
            val manager = supportFragmentManager
            val bottomSheetDialogFragment =
                BottomSheetWinningListFragment(contest.priceBreak, contest.winningAmount)
            bottomSheetDialogFragment.show(manager, "Bottom Sheet Dialog Fragment")
        }

        tvTime.setText(parseDateToddMMyyyy(contest.scheduleStart))
    }


    fun showJoinTeamDialogue() {
        var dialogue = Dialog(this)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialog_choose_team)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.setCancelable(true)
        dialogue.setCanceledOnTouchOutside(true)
        dialogue.ll_new.setOnClickListener {
            if (dialogue.isShowing)
                dialogue.dismiss()

            var intent = Intent(this, ActivityCreateTeam::class.java)
            intent.putExtra(StockConstant.EXCHANGEID, exchangeid)
            intent.putExtra(StockConstant.CONTESTID, contestid)
            intent.putExtra("isCloning", 0)
            startActivityForResult(intent, 405);
        }
        dialogue.ll_saved.setOnClickListener {
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
            val sb = " "
            sb
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 405) {
            if (resultCode == RESULT_OK && data != null) {
                var intent = Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        } else if (requestCode == StockConstant.REDIRECT_UPCOMING_MARKET) {
            if (resultCode == RESULT_OK && data != null) {
                var intent = Intent();
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        }

    }

    private fun changeTextColor(textView: TextView, color: Int) {
        textView.setTextColor(color)
    }

    private fun changeBackGroundColor(textView: TextView, color: Int) {
        textView.setBackgroundColor(color);
    }

}