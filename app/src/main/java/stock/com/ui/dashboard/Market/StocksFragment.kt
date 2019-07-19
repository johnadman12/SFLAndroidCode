package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_stocks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.Market.listener.OnLoadMoreListener
import stock.com.ui.dashboard.Market.listener.RecyclerViewLoadMoreScroll
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.ExchangeList
import stock.com.ui.pojo.MarketData
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.util.*

class StocksFragment : BaseFragment() {
    var sector: String = ""
    var exchange: String = ""
    var country: String = ""
    var page: Int = 0
    var limit: Int = 50
    var exchangeId: Int = 0

    private var stockAdapter: StockAdapter? = null;
    private var stockList: ArrayList<StockTeamPojo.Stock>? = null
    private var stockListNew: ArrayList<StockTeamPojo.Stock>? = null
    private var stockListFilter: ArrayList<StockTeamPojo.Stock>? = null
    private var scrollListener: RecyclerViewLoadMoreScroll? = null

    var flagAlphaSort: Boolean = false
    var flagPriceSort: Boolean = false
    var flagDaySort: Boolean = false
    var flagHTLSort: Boolean = false
    var flagDHTLSort: Boolean = false
    var llm: LinearLayoutManager? = null
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stocks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stockList = ArrayList()
        stockListNew = ArrayList()
        stockListFilter = ArrayList()
        llm = LinearLayoutManager(context)
        getExchangeNamelist()
        getStocks("1",exchangeId)

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                if (isVisible) {
                    getStocksAgain("1",exchangeId)
                    mainHandler.postDelayed(this, 20000)
                }
            }
        })

        setStockAdapter()

       /* scrollListener = RecyclerViewLoadMoreScroll(llm)
        scrollListener!!.setOnLoadMoreListener(object : OnLoadMoreListener {
            override fun onLoadMore() {
                getStocks("1",exchangeId)
            }
        })*/

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

    fun setStockNameAdapter(exchangeList: List<ExchangeList.Exchange>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        rvstock!!.layoutManager = llm
        rvstock.visibility = View.VISIBLE

        rvstock!!.adapter = MarketStockAdapter(context!!, exchangeList!!, this)

        // call function news
        autoScrollNews(llm)

        //recyclerView_stock_name.getAdapter()!!.notifyDataSetChanged();
//        rvstock.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
//        rvstock.setItemAnimator(DefaultItemAnimator())
        rvstock!!.adapter!!.notifyDataSetChanged();

    }

    fun autoScrollNews(llm: LinearLayoutManager) {
        val handler = Handler()
        val runnable = object : Runnable {
            var count = 0
            var flag = true
            override fun run() {
                if (rvstock == null)
                    return
                if (count < rvstock!!.adapter!!.getItemCount()) {
                    if (count == rvstock!!.adapter!!.getItemCount() - 1) {
                        flag = false;
                    } else
                        if (count == 0) {
                            flag = true;
                        }
                    if (flag)
                        count++;
                    else
                        count--;
                    var visibleItemCount = rvstock.getChildCount();
                    var totalItemCount = llm.getItemCount();
//                    recyclerView_stock_name.smoothScrollToPosition(count);
                    var dx = count
                    rvstock.scrollBy(count, visibleItemCount + totalItemCount)
                    handler.postDelayed(this, 300);

                }
            }
        }
        handler.postDelayed(runnable, 0);

    }

    fun getStocks(flag: String, exchangeId: Int) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockTeamPojo> =
            apiService.getStockList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                exchangeId,
                getFromPrefsString(StockConstant.USERID)!!.toInt(),
                sector, page.toString(), limit.toString()
            )
        call.enqueue(object : Callback<StockTeamPojo> {

            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
                d.dismiss()
                isLoading = false
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (flag.equals("1")) {
                            stockList = response.body()!!.stock
                            stockListNew = response.body()!!.stock
                            setStockAdapter()
                            try {
                                if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.ACTIVE_CURRENCY_TYPE))) {
                                    setActiveCurrencyType("")
                                }
                            } catch (e: Exception) {

                            }

                        } else {
                            stockList!!.clear()
                            stockList = stockListFilter
                            stockListNew = stockListFilter
                            setStockAdapter()
                        }
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<StockTeamPojo>, t: Throwable) {

                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    fun getStocksAgain(flag: String, exchangeId: Int) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockTeamPojo> =
            apiService.getStockList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                exchangeId,
                getFromPrefsString(StockConstant.USERID)!!.toInt(),
                sector, page.toString(), limit.toString()
            )
        call.enqueue(object : Callback<StockTeamPojo> {

            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
                d.dismiss()

                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (flag.equals("1")) {
                            stockListNew = response.body()!!.stock
                            Handler().postDelayed(Runnable {
                                setStockAdapter()
                            }, 100)

                        }
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
//                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<StockTeamPojo>, t: Throwable) {

                println(t.toString())
//                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    fun setStockAdapter() {
        if (rv_stockList != null) {
            llm!!.orientation = LinearLayoutManager.VERTICAL
            rv_stockList!!.layoutManager = llm
            rv_stockList.visibility = View.VISIBLE
            stockAdapter = StockAdapter(context!!, stockList!!, this, stockListNew!!);
            rv_stockList!!.adapter = stockAdapter;
        } else
            return

    }


    fun saveToWatchList(stockId: Int) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> =
            apiService.addStockWatch(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                stockId,
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    displayToast(response.body()!!.message, "sucess")
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        AppDelegate.showAlert(activity!!, response.body()!!.message)

                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    } else {
                        displayToast(response.body()!!.message, "warning")

                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    fun setFilter(c: CharSequence) {
        /*if (stockAdapter != null)
            stockAdapter!!.getFilter().filter(c)*/
    }

    fun setSorting(type: String) {
        getExchangeNamelist()
        if (type.equals("Alpha")) {
            var sortedList = stockList!!.sortedBy { it.symbol?.toString() }
            for (obj in sortedList) {
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                rv_stockList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("dayChange")) {
            var sortedList = stockList!!.sortedBy { it.changePercent?.toDouble() }
            for (obj in sortedList) {
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                rv_stockList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("price")) {
            var sortedList = stockList!!.sortedBy { it.latestPrice?.toDouble() }
            for (obj in sortedList) {
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                rv_stockList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("HighToLow")) {
            flagHTLSort = true
            val sortedList = stockList!!.sortedByDescending { it.latestPrice?.toDouble() }
            for (obj in sortedList) {
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                rv_stockList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("DayHighToLow")) {
            flagDHTLSort = true
            val sortedList = stockList!!.sortedByDescending { it.changePercent?.toDouble() }
            for (obj in sortedList) {
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                rv_stockList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("nodata")) {
            getStocks("1",exchangeId)
        }

    }

    fun changePercentFilter(type: String) {
        getExchangeNamelist()
        if (type.equals("0")) {
            getStocks("0",exchangeId)
            rv_stockList!!.adapter!!.notifyDataSetChanged()
        } else {
            getStocks(type,exchangeId)
            rv_stockList!!.adapter!!.notifyDataSetChanged()
        }
    }

    fun applyFilter(sector: String, exchange: String, country: String) {
        getExchangeNamelist()
        this.sector = sector
        this.exchange = exchange
        this.country = country
        getStocks("0",exchangeId)
        rv_stockList!!.adapter!!.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 411 && data != null) {
            getExchangeNamelist()
            getStocksAgain("1",exchangeId)
            rv_stockList.adapter!!.notifyDataSetChanged()
        }

    }
}
