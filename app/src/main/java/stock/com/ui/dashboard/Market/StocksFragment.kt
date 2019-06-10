package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
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
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class StocksFragment : BaseFragment() {


    private var stockAdapter: StockAdapter? = null;
    private var stockList: ArrayList<StockTeamPojo.Stock>? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stocks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stockList = ArrayList()
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

    }

    fun getStocks(flag: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketData> =
            apiService.getMarketData(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                "Equity"
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
                            setStockAdapter(stockList!!)
                        } else {
                            stockList = response.body()!!.stock
                            for (i in 0 until stockList!!.size) {
                                if (stockList!!.get(i).changePercent.equals("0")) {
                                    stockList!!.removeAt(i)
                                }
                            }
                            setStockAdapter(stockList!!)
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

    @SuppressLint("WrongConstant")
    fun setStockAdapter(item: MutableList<StockTeamPojo.Stock>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_currencyList!!.layoutManager = llm
        rv_currencyList.visibility = View.VISIBLE

        stockAdapter = StockAdapter(context!!, stockList!!, this);
        //rv_currencyList!!.adapter = StockAdapter(context!!, item, this)
        rv_currencyList!!.adapter = stockAdapter;

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
                        displayToast(response.body()!!.message, "sucess")

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

    public fun setFilter(c: CharSequence) {
        if (stockAdapter != null)
            stockAdapter!!.getFilter().filter(c)
    }

    fun setSorting(type: String) {
        if (type.equals("Alpha")) {
            var sortedList = stockList!!.sortedBy { it.symbol?.toString() }
            for (obj in sortedList) {
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                rv_currencyList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("dayChange")) {
            var sortedList = stockList!!.sortedBy { it.changePercent?.toDouble() }
            for (obj in sortedList) {
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                rv_currencyList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("price")) {
            var sortedList = stockList!!.sortedBy { it.latestPrice?.toDouble() }
            for (obj in sortedList) {
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                rv_currencyList!!.adapter!!.notifyDataSetChanged()
            }
        }

    }

    fun changePercentFilter(type: String) {
        if (type.equals("0")) {
            getStocks("0")
            rv_currencyList!!.adapter!!.notifyDataSetChanged()
        }

    }

}
