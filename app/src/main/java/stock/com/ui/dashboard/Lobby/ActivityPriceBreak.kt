package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_price_break.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.WinningList
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.text.SimpleDateFormat
import java.util.*

class ActivityPriceBreak : BaseActivity() {


    private var sport: String = "";
    private var winningAmount: String = "";
    private var Time: String? = null;
    private var EntryFee: String = "";
    private var startTime: String = "";
    private var endTime: String = "";
    private var startDate: String = "";
    private var endDate: String = "";
    private var marketId: String = "";
    private var exchangeId: String = "";
    private var ContestName: String = "";
    private var joinmultiple: String = "";
    var contestSizeId: String = "";
    var contestSizeWinner: String = "";


    var list: ArrayList<WinningList.Pricebreaklist>? = null;


    private var count: Int = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_price_break)
        list = ArrayList()
        if (intent != null) {
            sport = intent.getStringExtra("spot");
            winningAmount = intent.getStringExtra("winningamount");
            EntryFee = intent.getStringExtra("fee");
            startTime = intent.getStringExtra("startTime")
            endTime = intent.getStringExtra("endTime")
            startDate = intent.getStringExtra("startDate")
            endDate = intent.getStringExtra("endDate")
            marketId = intent.getStringExtra("marketId")
            exchangeId = intent.getStringExtra("exchangeId")
            ContestName = intent.getStringExtra("contestName")
            joinmultiple = intent.getStringExtra("joinMultiple")
        }

       /* localToUTC(startDate + " " + startTime)
        localToUTC(endDate + " " + endTime)*/
        if (contestName != null)
            contestName.setText(ContestName)

        val stime = startDate +" "+ startTime
        val etime = endDate+ " "+endTime

        val startTime = SimpleDateFormat("dd-MM-yyyy HH:mm a").parse(stime)
        val endTime = SimpleDateFormat("dd-MM-yyyy HH:mm a").parse(etime)
        val diff = endTime.getTime() - startTime.getTime()
        Log.d("show","onCreate1"+diff)

        val timer = object: CountDownTimer(diff, 1000) {
            override fun onTick(millisUntilFinished:Long) {
                val diffSec = millisUntilFinished / 1000
                val seconds = diffSec % 60
                Log.d("show","onCreate"+seconds)
                val minutes = diffSec / 60 % 60
                Log.d("show","onCreate11"+minutes)
                val hours = diffSec / 3600
                Log.d("show","onCreate12"+hours)
                timer1.setText(hours.toString() + "H: " + minutes.toString() + "M: " + seconds.toString() + "S")
            }
            override fun onFinish() {
                timer1.setText("Time Up")
            }
        }
        timer.start()
//        var list = ArrayList<String>();

        /*   if (sport!!.toInt() >= 2 && sport!!.toInt() < 7) {
               count = 5;
               list.add("5")
               list.add("4")
               list.add("3")
               list.add("2")
               list.add("1")
           } else if (sport!!.toInt() >= 7 && sport!!.toInt() < 14) {
               count = 3;

               list.add("7")
               list.add("6")
               list.add("5")
           } else if (sport!!.toInt() >= 15 && sport!!.toInt() <= 24) {
               count = 3;
               list.add("15")
               list.add("10")
               list.add("7")
           } else if (sport!!.toInt() >= 25 && sport!!.toInt() <= 49) {
               count = 3;
               list.add("25")
               list.add("15")
               list.add("10")
           } else if (sport!!.toInt() >= 50 && sport!!.toInt() <= 100) {
               count = 3;
               list.add("50")
               list.add("25")
               list.add("15")
           }
   */
        img_btn_back.setOnClickListener {
            onBackPressed();
        }

        ll_winners.setOnClickListener {
            val bottomSheetFragment = BottonSheetPriceBreakup(count, list!!, this, contestSizeWinner)
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.getTag())
        }
        tv_entryfee.setText(EntryFee)
        tv_prizepool.setText(winningAmount)
        tv_contest.setText(sport)
        getExchangeForContest()
        tv_createContest.setOnClickListener {
            createContest()
        }
    }

    fun getExchangeForContest() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<WinningList> =
            apiService.getWinningPercentage(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                winningAmount,
                getFromPrefsString(StockConstant.USERID).toString(),
                sport,
                EntryFee
            )
        call.enqueue(object : Callback<WinningList> {

            override fun onResponse(call: Call<WinningList>, response: Response<WinningList>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        list = response.body()!!.pricebreaklist
                        contestSizeId = list!!.get(0).usercontestSizeId
                        contestSizeWinner = list!!.get(0).winner
                        setAdapter(list!!.get(0).winners)

                    } else {
                        displayToast(resources.getString(R.string.internal_server_error), "error")
                        d.dismiss()
                    }
                }
            }

            override fun onFailure(call: Call<WinningList>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
                d.dismiss()
            }
        })
    }


    @SuppressLint("WrongConstant")
    fun setAdapter(item: ArrayList<WinningList.Winner>) {
        tv_winner.setText(contestSizeWinner)
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_listWinner!!.layoutManager = llm
        rv_listWinner!!.adapter = PriceBreakUpAdapter(this@ActivityPriceBreak, item)
    }


    fun createContest() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> =
            apiService.createUserContest(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                winningAmount,
                getFromPrefsString(StockConstant.USERID).toString(),
                exchangeId,
                marketId,
                contestSizeId,
                sport,
                contestSizeWinner,
                joinmultiple,
                ContestName,
                "Paid",/*"",""*/
                localToUTC(startDate + " " + startTime),
                localToUTC(endDate + " " + endTime)
                ,
                EntryFee
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        AppDelegate.showAlert(this@ActivityPriceBreak, response.body()!!.message)
                        var intent = Intent();
                        intent.putExtra("flag", "1")
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } else {
                        displayToast(resources.getString(R.string.internal_server_error), "error")
                        d.dismiss()
                    }
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.internal_server_error), "error")
                d.dismiss()
            }
        })
    }


    @Suppress("DEPRECATION")
    fun localToUTC(inputdate: String): String {
        val inputPattern = "dd-MM-yyyy hh:mm a"
        val inputFormat = SimpleDateFormat(inputPattern)
        var date: Date? = null
        date = inputFormat.parse(inputdate)
        val formatterUTC = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        formatterUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
//        val dateStr:Date= Date(formatterUTC.format(date))
        val strDate: String = formatterUTC.format(date)
        return strDate
    }

}
