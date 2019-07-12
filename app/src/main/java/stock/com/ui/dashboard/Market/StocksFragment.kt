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
import com.madapps.liquid.LiquidRefreshLayout
import kotlinx.android.synthetic.main.fragment_stocks.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.BasePojo
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

    private var stockAdapter: StockAdapter? = null;
    private var stockList: ArrayList<StockTeamPojo.Stock>? = null
    private var stockListNew: ArrayList<StockTeamPojo.Stock>? = null
    private var stockListFilter: ArrayList<StockTeamPojo.Stock>? = null

    var flagAlphaSort: Boolean = false
    var flagPriceSort: Boolean = false
    var flagDaySort: Boolean = false
    var flagHTLSort: Boolean = false
    var flagDHTLSort: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stocks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stockList = ArrayList()
        stockListNew = ArrayList()
        stockListFilter = ArrayList()
        refreshData.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                //TODO make api call here
                Handler().postDelayed({
                }, 5000)
                getStocks("1")
            }
        })
        getStocks("1")

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                if (isVisible) {
                    getStocksAgain("1")
                    mainHandler.postDelayed(this, 20000)
                }
            }
        })

        setStockAdapter()

    }


    fun getStocks(flag: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketData> =
            apiService.getMarketData(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                "Equity",
                sector, exchange, country, ""
            )
        call.enqueue(object : Callback<MarketData> {

            override fun onResponse(call: Call<MarketData>, response: Response<MarketData>) {
                d.dismiss()
                if (refreshData != null)
                    refreshData.finishRefreshing()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (flag.equals("1")) {
                            stockList = response.body()!!.stock
                            stockListNew = response.body()!!.stock
                            setStockAdapter()
                            if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.ACTIVE_CURRENCY_TYPE))) {
                                setActiveCurrencyType("")
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

            override fun onFailure(call: Call<MarketData>, t: Throwable) {
                if (refreshData != null)
                    refreshData.finishRefreshing()
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    fun getStocksAgain(flag: String) {
        /*  val d = StockDialog.showLoading(activity!!)
          d.setCanceledOnTouchOutside(false)*/
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketData> =
            apiService.getMarketData(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                "Equity",
                sector, exchange, country, ""
            )
        call.enqueue(object : Callback<MarketData> {

            override fun onResponse(call: Call<MarketData>, response: Response<MarketData>) {
//                d.dismiss()
                if (refreshData != null)
                    refreshData.finishRefreshing()
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
//                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<MarketData>, t: Throwable) {
                if (refreshData != null)
                    refreshData.finishRefreshing()
                println(t.toString())
//                displayToast(resources.getString(R.string.something_went_wrong), "error")
//                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    fun setStockAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        if (rv_stockList != null) {
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
            getStocks("1")
        }

    }

    fun changePercentFilter(type: String) {
        if (type.equals("0")) {
            getStocks("0")
            rv_stockList!!.adapter!!.notifyDataSetChanged()
        } else {
            getStocks(type)
            rv_stockList!!.adapter!!.notifyDataSetChanged()
        }
    }

    fun applyFilter(sector: String, exchange: String, country: String) {
        this.sector = sector
        this.exchange = exchange
        this.country = country
        getStocks("0")
        rv_stockList!!.adapter!!.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 411 && data != null) {
            getStocksAgain("1")
            rv_stockList.adapter!!.notifyDataSetChanged()
        }

    }
}
