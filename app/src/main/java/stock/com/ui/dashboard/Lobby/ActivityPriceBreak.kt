package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.os.Bundle
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
        if (contestName != null)
            contestName.setText(ContestName)
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
            val bottomSheetFragment = BottonSheetPriceBreakup(count, list!!, this)
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
                        tv_winner.setText(list!!.get(0).winner)
                        setAdapter(list!!.get(0).winners)
                        contestSizeId = list!!.get(0).usercontestSizeId
                        contestSizeWinner = list!!.get(0).winner
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
                exchangeId, marketId, contestSizeId, sport,
                contestSizeWinner, joinmultiple, ContestName, "Paid", startDate + startTime, endDate + endTime
                ,
                EntryFee
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {


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


    fun localToGMT(): Date {
        val date = Date()
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
        return Date(sdf.format(date))
    }

}
