package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.madapps.liquid.LiquidRefreshLayout
import kotlinx.android.synthetic.main.dialog_view_contest.*
import kotlinx.android.synthetic.main.fragment_lobby.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.dashboard.WebviewActivity
import stock.com.ui.pojo.ExchangeList
import stock.com.ui.pojo.LobbyContestPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockConstant.RESULT_CODE_FILTER
import stock.com.utils.StockDialog
import kotlin.collections.ArrayList


class LobbyFragment : BaseFragment() {
    var categoryId: String = ""
    var flag: String = ""
    var contest: ArrayList<LobbyContestPojo.Contest>? = null;
    var lobbyContestAdapter: LobbyContestAdapter? = null
    var type: String = "all"
    var exchangeId: Int = 0

    private var dashBoardActivity: DashBoardActivity? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        contest = ArrayList();
        dashBoardActivity = activity as DashBoardActivity?
        if (arguments!!.getString("id") != null)
            categoryId = arguments!!.getString("id");


        ll_filter.setOnClickListener {
            val intent = Intent(context, ActivityFilter::class.java)
            intent.putExtra(StockConstant.MARKET_TYPE, type)
            startActivityForResult(intent, RESULT_CODE_FILTER)
        }

        ll_codejoin.setOnClickListener {
            startActivity(Intent(activity!!, ActivityCodeJoin::class.java))
        }

        ll_createContest.setOnClickListener {
            startActivityForResult(
                Intent(activity, ActivityCreateContest::class.java),
                StockConstant.REDIRECT_CREATED
            )
        }

        ll_sort.setOnClickListener {
            startActivityForResult(
                Intent(context, ActivitySort::class.java).putExtra("flagStatus", flag),
                StockConstant.RESULT_CODE_SORT
            )
        }
        rvExchanges.visibility = View.GONE

        rel_crypto.setOnClickListener {
            tv_filter.setText("Crypto\nFilter")
            tv_filter.setTextSize(12.0f)
            tv_createContest.setTextSize(12.0f)
            tv_codejoin.setTextSize(12.0f)
            tv_sort.setTextSize(12.0f)
            rvExchanges.visibility = View.GONE
            if (contest != null)
                contest!!.clear()
            lobbyContestAdapter = LobbyContestAdapter(context!!, contest!!)
            type = "crypto"
            getContestlist(type)
            setContestAdapter()
        }

        rel_stock.setOnClickListener {
            tv_filter.setText("Stock\nFilter")
            tv_filter.setTextSize(12.0f)
            tv_createContest.setTextSize(12.0f)
            tv_codejoin.setTextSize(12.0f)
            tv_sort.setTextSize(12.0f)
            getExchangeNamelist()
            if (contest != null)
                contest!!.clear()
            lobbyContestAdapter = LobbyContestAdapter(context!!, contest!!)
            type = "equity"
            getContestlist(type)
            setContestAdapter()
        }

        rel_forex.setOnClickListener {
            tv_filter.setText("Forex\nFilter")
            tv_filter.setTextSize(12.0f)
            tv_createContest.setTextSize(12.0f)
            tv_codejoin.setTextSize(12.0f)
            tv_sort.setTextSize(12.0f)
            rvExchanges.visibility = View.GONE
            if (contest != null)
                contest!!.clear()
            lobbyContestAdapter = LobbyContestAdapter(context!!, contest!!)
            type = "currency"
            getContestlist(type)
            setContestAdapter()
        }
        rel_commodity.setOnClickListener {
            tv_filter.setText("Commodities\nFilter")
            tv_filter.setTextSize(10.0f)
            tv_createContest.setTextSize(10.0f)
            tv_codejoin.setTextSize(10.0f)
            tv_sort.setTextSize(10.0f)
            rvExchanges.visibility = View.GONE
            type = "all"
            if (contest != null)
                contest!!.clear()
            lobbyContestAdapter = LobbyContestAdapter(context!!, contest!!)
            getContestlist(type)
            setContestAdapter()
        }

        refreshLayout.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                //TODO make api call here
                Handler().postDelayed({
                    getContestlist(type)
                }, 1500)
            }
        })
//        getExchangeNamelist()
        setContestAdapter()
    }

    private fun initViews() {
        if (!TextUtils.isEmpty(categoryId)) {
            setFilters(categoryId)
        } else {
            getContestlist(type)
        }

    }

    fun getExchangeNamelist() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<ExchangeList> =
            apiService.getExchangelist()
        call.enqueue(object : Callback<ExchangeList> {
            override fun onResponse(call: Call<ExchangeList>, response: Response<ExchangeList>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        rvExchanges.visibility = View.VISIBLE
                        setStockNameAdapter(response.body()!!.exchange)
                        exchangeId = response.body()!!.exchange.get(0).id
                    } else {
                        displayToast(resources.getString(R.string.internal_server_error), "error")
                        d.dismiss()
                    }
                }
            }

            override fun onFailure(call: Call<ExchangeList>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })

    }


    fun filterData(exchangeId: Int) {

        // Toast.makeText(activity,""+contest!!.size,Toast.LENGTH_LONG).show();
        var sorted = ArrayList<LobbyContestPojo.Contest>();
        if (!TextUtils.isEmpty(exchangeId.toString())) {
            if (contest!!.size > 0) {
                for (i in 0 until contest!!.size) {
                    if (exchangeId == contest!!.get(i).exchangeid.toInt()) {
                        sorted!!.add(contest!!.get(i))
                        Log.d("ExchangeMatchId","----"+contest!!.get(i));
                    }
                }



                val llm = LinearLayoutManager(context)
                llm.orientation = LinearLayoutManager.VERTICAL
                recyclerView_contest!!.layoutManager = llm
                recyclerView_contest?.itemAnimator = DefaultItemAnimator()
                lobbyContestAdapter = LobbyContestAdapter(context!!, sorted!!)
                recyclerView_contest!!.adapter = lobbyContestAdapter

            }
        }

    }

    fun setStockNameAdapter(exchangeList: List<ExchangeList.Exchange>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        rvExchanges!!.layoutManager = llm
        rvExchanges.visibility = View.VISIBLE

        rvExchanges!!.adapter = LobbyExchangeAdapter(context!!, exchangeList!!, this)

        // call function news
        autoScrollNews(llm)
        rvExchanges!!.adapter!!.notifyDataSetChanged();

    }


    fun autoScrollNews(llm: LinearLayoutManager) {
        val handler = Handler()
        val runnable = object : Runnable {
            var count = 0
            var flag = true
            override fun run() {
                if (rvExchanges == null)
                    return
                if (count < rvExchanges!!.adapter!!.getItemCount()) {
                    if (count == rvExchanges!!.adapter!!.getItemCount() - 1) {
                        flag = false;
                    } else
                        if (count == 0) {
                            flag = true;
                        }
                    if (flag)
                        count++;
                    else
                        count--;
                    var visibleItemCount = rvExchanges.getChildCount();
                    var totalItemCount = llm.getItemCount();
//                    recyclerView_stock_name.smoothScrollToPosition(count);
                    var dx = count
                    rvExchanges.scrollBy(count, visibleItemCount + totalItemCount)
                    handler.postDelayed(this, 300);

                }
            }
        }
        handler.postDelayed(runnable, 0);

    }

    override fun onResume() {
        super.onResume()
        initViews()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lobby, container, false)
    }

    fun getContestlist(typeFilter: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<LobbyContestPojo> =
            apiService.getContestList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), typeFilter
            )
        call.enqueue(object : Callback<LobbyContestPojo> {
            override fun onResponse(call: Call<LobbyContestPojo>, response: Response<LobbyContestPojo>) {
                d.dismiss()
                if (refreshLayout != null)
                    refreshLayout.finishRefreshing()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        try {
                            if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.CONTEST_TYPE))) {
                                setContestType(" ")
                            }
                            if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.MARKET_TYPE))) {
                                setMarketContest(" ")
                            }
                            if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.COUNTRY_TYPE))) {
                                setCountryContest(" ")
                            }
                        } catch (e: Exception) {

                        }
                        if (contest != null) {
                            contest!!.clear()
                        }
                        contest!!.addAll(response.body()!!.contest!!)
                        if (lobbyContestAdapter != null) {
                            lobbyContestAdapter!!.notifyDataSetChanged()
                        }
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<LobbyContestPojo>, t: Throwable) {
                /*if (refreshLayout != null)
                    refreshLayout.finishRefreshing()*/
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }

        })
    }

    @SuppressLint("WrongConstant")
    private fun setContestAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_contest!!.layoutManager = llm
        recyclerView_contest?.itemAnimator = DefaultItemAnimator()
        lobbyContestAdapter = LobbyContestAdapter(context!!, contest!!)
        recyclerView_contest!!.adapter = lobbyContestAdapter

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.RESULT_CODE_SORT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                flag = data.getStringExtra("flag")
                if (data.getStringExtra("flag").equals("Entry")) {
                    var sortedList = contest!!.sortedWith(compareBy({ it.fees.toDouble() }))
                    for (obj in sortedList) {
                        Log.d("sdadada---", "--" + obj.fees)
                        recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        recyclerView_contest!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("time")) {

                    var sortedList = contest!!.sortedWith(compareBy { it.getDate() })
                    for (obj in sortedList) {
                        Log.d("sdadada---", "--" + obj.fees)
                        recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        recyclerView_contest!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("price")) {

                    contest!!.sortByDescending { it.win_amount.toDouble() };
                    //var sortedList1 = tempList!!.sortByDescending { it.getWinningAmount_temp().toDouble() };
                    recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, contest!!)
                    recyclerView_contest!!.adapter!!.notifyDataSetChanged();

                } else if (data.getStringExtra("flag").equals("position")) {
                    val sortedList = contest!!.sortedWith(compareBy({ it.getCalculatePosition() }))
                    for (obj in sortedList) {
                        Log.d("sdadada---", "--" + obj.calculatePosition)
                        recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        recyclerView_contest!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("nodata")) {
                    getContestlist(type)
                }
            }
        } else if (requestCode == RESULT_CODE_FILTER) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                //contest!!.clear()
                var flagreset = data.getStringExtra("resetfilter")
                if (flagreset.equals("0")) {
                    var testing = data.getSerializableExtra("contestlist") as ArrayList<LobbyContestPojo.Contest>;
                    try {
                        if (testing.size > 0) {
                            Log.d("sdadada---Filter", "--" + testing.size)
                            recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, testing)
                            recyclerView_contest!!.adapter!!.notifyDataSetChanged();
                        } else {
                            testing.clear()
                            recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, testing)
                            recyclerView_contest!!.adapter!!.notifyDataSetChanged();
                            AppDelegate.showAlert(activity!!, "No result Found\n Please Pull down to get Contests")
                        }
                    } catch (e: java.lang.Exception) {

                    }


                } else {
                    getContestlist(type)
                }
                /*recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, contest!!)
                recyclerView_contest!!.adapter!!.notifyDataSetChanged();*/
            }
        } else if (requestCode == StockConstant.REDIRECT_CREATED) {
            if (resultCode == AppCompatActivity.RESULT_OK /*&& data != null*/) {
                var intent = Intent();
                activity!!.setResult(Activity.RESULT_OK, intent);
                dashBoardActivity!!.callToCreated()
//                activity!!.finish();

            }
        } else if (requestCode == StockConstant.REDIRECT_UPCOMING_MARKET) {
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                var intent = Intent();
                activity!!.setResult(Activity.RESULT_OK, intent);
                dashBoardActivity!!.test()
//                activity!!.finish();

            }
        }
    }

    fun showViewContestDialogue() {
        var dialogue = Dialog(activity!!)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialog_view_contest)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.setCancelable(true)
        dialogue.setCanceledOnTouchOutside(false)
        dialogue.setTitle(null)
        dialogue.tvViewContest.setOnClickListener {

            dialogue.dismiss()
        }
        dialogue.tv_hide.setOnClickListener {
            dialogue.dismiss()
        }

        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }

    fun setFilters(categoryId: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<LobbyContestPojo> =
            apiService.setContestFilter(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                categoryId,
                "", "",
                "",
                ""
            )
        call.enqueue(object : Callback<LobbyContestPojo> {
            override fun onResponse(call: Call<LobbyContestPojo>, response: Response<LobbyContestPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        contest!!.clear()
                        contest!!.addAll(response.body()!!.contest)
                        if (lobbyContestAdapter != null)
                            lobbyContestAdapter!!.notifyDataSetChanged()
                    } else if (response.body()!!.status == "2") {
                        appLogout();
                    } else {
                        displayToast("no data exist", "warning")

                    }
                } else {
                    displayToast(resources.getString(stock.com.R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<LobbyContestPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

}