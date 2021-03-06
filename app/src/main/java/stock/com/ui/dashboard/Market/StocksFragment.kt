package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import io.socket.client.IO
import io.socket.client.Socket
import kotlinx.android.synthetic.main.fragment_stocks.*
import kotlinx.android.synthetic.main.row_country_list.*
import org.json.JSONArray
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
import java.net.URISyntaxException
import java.util.*

class StocksFragment : BaseFragment() {
    var sector: String = ""
    var exchange: String = ""
    var country: String = ""
    var page: Int = 0
    var limit: Int = 50
    var exchangeId: Int = 0
    private var socket: Socket? = null;

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
    var search: String = ""
    var flagSearch: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stocks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stockList = ArrayList()
        stockListNew = ArrayList()
        stockListFilter = ArrayList()

        llm = LinearLayoutManager(context)
        setStockAdapter()
        getExchangeNamelist()

        srl_layout.setOnRefreshListener {
            if (flagSearch) {
                page++;
                callApiSearch(search, 1);
            } else {
                page++
                getStocks("1", exchangeId, page)
            }

        }

    }

    override fun onResume() {
        super.onResume()
        if (!isVisible) {
            setFlag(false, false, false, false, false)
        }
        try {
            val opts = IO.Options()
            opts.forceNew = false
            opts.reconnection = true
            socket = IO.socket(StockConstant.SOCKET, opts)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        socket!!.on(Socket.EVENT_CONNECT) {
        }.on("new_stock_message") {
        }.on(Socket.EVENT_DISCONNECT) {
            socket!!.connect()
        }

        socket!!.connect()
        socket!!.on("new_stock_message") { args ->
            val jsonArray = args[0] as JSONArray
            Log.d("socket_data_stock", "---" + jsonArray);
            Thread(Task(stockAdapter!!, jsonArray)).start()
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
                        setStockNameAdapter(response.body()!!.exchange)
                        exchangeId = response.body()!!.exchange.get(0).id
                        getStocks("1", exchangeId, 0)
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

    private fun callApiSearch(c: CharSequence, firstTime: Int) {
        Log.d("dsadada", "22222--");
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockTeamPojo> =
            apiService.searchExchange(
                exchangeId.toString(), c.toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                "Equity",
                page.toString(),
                limit.toString()
            )
        call.enqueue(object : Callback<StockTeamPojo> {
            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (srl_layout != null)
                        srl_layout.isRefreshing = false
                    // displayToast(response.body()!!.message, "sucess")
                    d.dismiss()
                    if (response.body()!!.status == "1") {
                        if (firstTime == 0) {
                            stockList!!.clear();
                            stockListNew!!.clear();
                        }
                        stockList!!.addAll(response.body()!!.stock!!)
                        stockListNew!!.addAll(response.body()!!.stock!!)

                        if (flagAlphaSort) {
                            try {
                                val sortedList = stockListNew!!.sortedBy { it.symbol }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockListNew!!.addAll(sortedList)
                                stockList!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagPriceSort) {
                            try {
                                val sortedList = stockListNew!!.sortedBy { it.latestPrice?.toDouble() }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockList!!.addAll(sortedList)
                                stockListNew!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagDaySort) {
                            try {
                                val sortedList = stockListNew!!.sortedBy { it.changePercent?.toDouble() }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockList!!.addAll(sortedList)
                                stockListNew!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagHTLSort) {
                            try {
                                val sortedList = stockListNew!!.sortedByDescending { it.latestPrice?.toDouble() }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockList!!.addAll(sortedList)
                                stockListNew!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }


                        } else if (flagDHTLSort) {
                            try {
                                val sortedList = stockListNew!!.sortedByDescending { it.changePercent?.toDouble() }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockList!!.addAll(sortedList)
                                stockListNew!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        }
                        if (stockAdapter != null)
                            stockAdapter!!.notifyDataSetChanged()


                    } else if (response.body()!!.status == "2") {
                        d.dismiss()
                        appLogout()
                    }
                } else {
                    displayToast(response.body()!!.message, "warning")
                }

            }

            override fun onFailure(call: Call<StockTeamPojo>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                Log.d("serach_error", "---" + t.localizedMessage);
                d.dismiss()
                displayToast(resources.getString(R.string.something_went_wrong), "error")
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

    fun getStocks(flag: String, exchangeId: Int, page: Int) {
        this.exchangeId = exchangeId
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockTeamPojo> =
            apiService.getStockList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                exchangeId,
                getFromPrefsString(StockConstant.USERID)!!.toInt(),
                sector, page.toString(), "50"
            )
        call.enqueue(object : Callback<StockTeamPojo> {

            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
                d.dismiss()
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (flag.equals("1")) {
                            stockList!!.addAll(response.body()!!.stock!!)
                            stockListNew!!.addAll(response.body()!!.stock!!)
                        } else {
                            stockList!!.clear()
                            stockListNew!!.clear()
                            stockList!!.addAll(response.body()!!.stock!!)
                            stockListNew!!.addAll(response.body()!!.stock!!)
                        }

                        for (i in 0 until response.body()!!.stock!!.size) {
                            if (TextUtils.isEmpty(response.body()!!.stock!!.get(i).changePercent))
                                response.body()!!.stock!!.get(i).changePercent = "0.000"
                        }

                        //sorting
                        if (flagAlphaSort) {
                            try {
                                val sortedList = stockListNew!!.sortedBy { it.symbol }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockListNew!!.addAll(sortedList)
                                stockList!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagPriceSort) {
                            try {
                                val sortedList = stockListNew!!.sortedBy { it.latestPrice?.toDouble() }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockList!!.addAll(sortedList)
                                stockListNew!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagDaySort) {
                            try {
                                val sortedList = stockListNew!!.sortedBy { it.changePercent?.toDouble() }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockList!!.addAll(sortedList)
                                stockListNew!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagHTLSort) {
                            try {
                                val sortedList = stockListNew!!.sortedByDescending { it.latestPrice?.toDouble() }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockList!!.addAll(sortedList)
                                stockListNew!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }


                        } else if (flagDHTLSort) {
                            try {
                                val sortedList = stockListNew!!.sortedByDescending { it.changePercent?.toDouble() }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockList!!.addAll(sortedList)
                                stockListNew!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        }
                        if (stockAdapter != null)
                            stockAdapter!!.notifyDataSetChanged()

                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<StockTeamPojo>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
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
                    if (srl_layout != null)
                        srl_layout.isRefreshing = false
                    if (response.body()!!.status == "1") {
                        if (flag.equals("1")) {
                            stockListNew!!.addAll(response.body()!!.stock!!)
                            if (flagAlphaSort) {
                                val sortedList = stockListNew!!.sortedBy { it.symbol }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockListNew!!.addAll(sortedList)
                                stockList!!.addAll(sortedList)

                            } else if (flagPriceSort) {
                                val sortedList = stockListNew!!.sortedBy { it.latestPrice?.toDouble() }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockList!!.addAll(sortedList)
                                stockListNew!!.addAll(sortedList)

                            } else if (flagDaySort) {
                                val sortedList = stockListNew!!.sortedBy { it.changePercent?.toDouble() }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockList!!.addAll(sortedList)
                                stockListNew!!.addAll(sortedList)

                            } else if (flagHTLSort) {
                                val sortedList = stockListNew!!.sortedByDescending { it.latestPrice?.toDouble() }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockList!!.addAll(sortedList)
                                stockListNew!!.addAll(sortedList)


                            } else if (flagDHTLSort) {
                                val sortedList = stockListNew!!.sortedByDescending { it.changePercent?.toDouble() }
                                stockListNew!!.clear()
                                stockList!!.clear()
                                stockList!!.addAll(sortedList)
                                stockListNew!!.addAll(sortedList)

                            }
                            if (stockAdapter != null)
                                stockAdapter!!.notifyDataSetChanged()

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
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    fun setStockAdapter() {
//        if (rv_stockList != null) {
        llm!!.orientation = LinearLayoutManager.VERTICAL
        rv_stockList!!.layoutManager = llm
        rv_stockList.visibility = View.VISIBLE
        stockAdapter = StockAdapter(context!!, stockList!!, this, stockListNew!!);
        rv_stockList!!.adapter = stockAdapter;
        /*} else
            return*/

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
                    if (response.body()!!.status == "1") {
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
        Log.d("dsadada", "sdada--" + c);
        if (c.toString().length >= 2) {
            Log.d("dsadada", "111111--");
            search = c.toString()
            flagSearch = true
            page = 0
            limit = 50
            callApiSearch(c, 0);
        } else if (c.toString().length == 0) {
            page = 0
            limit = 50
            flagSearch = false
            Log.d("dsadada", "sdada--");
            getStocks("0", exchangeId, page)
        }
    }

    fun setSorting(type: String) {
        if (type.equals("Alpha")) {
            setFlag(true, false, false, false, false)
            try {
                var sortedList = stockList!!.sortedBy { it.symbol?.toString() }
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                stockListNew!!.clear()
                stockListNew!!.addAll(sortedList)
                if (stockAdapter != null)
                    stockAdapter!!.notifyDataSetChanged()
            } catch (e: Exception) {

            }

        } else if (type.equals("dayChange")) {
            setFlag(false, false, true, false, false)
            try {
                var sortedList = stockList!!.sortedBy { it.changePercent?.toDouble() }
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                stockListNew!!.clear()
                stockListNew!!.addAll(sortedList)
                if (stockAdapter != null)
                    stockAdapter!!.notifyDataSetChanged()
            } catch (e: Exception) {

            }

        } else if (type.equals("price")) {
            setFlag(false, true, false, false, false)
            try {
                var sortedList = stockList!!.sortedBy { it.latestPrice?.toDouble() }
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                stockListNew!!.clear()
                stockListNew!!.addAll(sortedList)
                if (stockAdapter != null)
                    stockAdapter!!.notifyDataSetChanged()
            } catch (e: Exception) {

            }

        } else if (type.equals("HighToLow")) {
            setFlag(false, false, false, true, false)
            try {
                val sortedList = stockList!!.sortedByDescending { it.latestPrice?.toDouble() }
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                stockListNew!!.clear()
                stockListNew!!.addAll(sortedList)
                if (stockAdapter != null)
                    stockAdapter!!.notifyDataSetChanged()
            } catch (e: Exception) {

            }
        } else if (type.equals("DayHighToLow")) {
            setFlag(false, false, false, false, true)
            try {
                val sortedList = stockList!!.sortedByDescending { it.changePercent?.toDouble() }
                stockList!!.clear()
                stockList!!.addAll(sortedList)
                stockListNew!!.clear()
                stockListNew!!.addAll(sortedList)
                if (stockAdapter != null)
                    stockAdapter!!.notifyDataSetChanged()
            } catch (e: Exception) {

            }

        } else if (type.equals("nodata")) {
//            page = 0
            setFlag(false, false, false, false, false)
            flagAlphaSort = false
            flagPriceSort = false
            flagDaySort = false
            flagHTLSort = false
            flagDHTLSort = false
            getStocks("0", exchangeId, page)
        }

    }

    fun changePercentFilter(type: String) {
        getExchangeNamelist()
        if (type.equals("0")) {
            page = 0
            getStocks("0", exchangeId, page)
//            rv_stockList!!.adapter!!.notifyDataSetChanged()
        } else {
            page = 0
            getStocks(type, exchangeId, page)
//            rv_stockList!!.adapter!!.notifyDataSetChanged()
        }
    }

    fun applyFilter(sector: String, exchange: String, country: String) {
        getExchangeNamelist()
        this.sector = sector
        this.exchange = exchange
        this.country = country
        page = 0
        getStocks("0", exchangeId, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 411 && data != null) {
            getExchangeNamelist()
            getStocksAgain("1", exchangeId)
            rv_stockList.adapter!!.notifyDataSetChanged()
        }

    }

    /*internal inner class Task(var adapter: StockAdapter, var jsonArray: JSONArray) : Runnable {
        override fun run() {
            try {
                activity!!.runOnUiThread(Runnable {
                    // Stuff that updates the UI
                    try {
                        stockList!!.clear()
                        stockList!!.addAll(stockListNew!!);
                        for (i in 0..jsonArray.length()) {
                            var jsonObject = jsonArray.getJSONObject(i);
                            var model = StockTeamPojo.Stock()
                            try {
                                Log.d("errroroororor", "change")
                                model.changePercent = jsonObject.getString("changePercent");
                                model.latestPrice = jsonObject.getString("latestPrice");
                                model.slug = jsonObject.getString("slug");

//                                stockListNew!!.add(model)
                                for (i in 0..stockListNew!!.size) {
                                    if (model.slug.equals(stockListNew!!.get(i).slug)) {
                                        model.latestPrice = stockListNew!!.get(i).latestPrice;
                                        model.changePercent = stockListNew!!.get(i).changePercent;
                                        stockListNew!!.set(i, model)
                                    }
                                }
                            } catch (e: Exception) {
                                Log.d("errroroororor", e.message)
                            }
                        }
                    } catch (ee: java.lang.Exception) {
                    }
                    if (adapter != null)
                        adapter!!.notifyDataSetChanged();

                })
            } catch (e: InterruptedException) {
                e.printStackTrace()

            }
        }
    }*/

    internal inner class Task(var adapter: StockAdapter, var jsonArray: JSONArray) : Runnable {
        override fun run() {
            try {
                activity!!.runOnUiThread(Runnable {
                    // Stuff that updates the UI
                    try {
                        Log.d("errroroororor", "--dadadadasddadasdda")
                        stockList!!.clear()
                        stockList!!.addAll(stockListNew!!);
                        for (i in 0..jsonArray.length()) {
                            var jsonObject = jsonArray.getJSONObject(i);
                            var model = StockTeamPojo.Stock()
                            try {
                                model.changePercent = jsonObject.getString("changePercent");
                                model.latestVolume = jsonObject.getString("latestVolume");
                                model.latestPrice = jsonObject.getString("latestPrice");
//                                model.symbol = jsonObject.getString("symbol");
                                model.slug = jsonObject.getString("slug");
                                //stockListNew!!.add(model)
                                for (i in 0..stockListNew!!.size) {
                                    if (model.slug.equals(stockListNew!!.get(i).slug)) {
                                        model.companyName = stockList!!.get(i).companyName
                                        model.symbol = stockList!!.get(i).symbol
                                        stockListNew!!.set(i, model);
                                    }
                                }
                            } catch (e: Exception) {

                            }
                        }
                    } catch (ee: java.lang.Exception) {
                    }
                    if (adapter != null)
                        adapter!!.notifyDataSetChanged();

                })
            } catch (e: InterruptedException) {
                e.printStackTrace()

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            socket!!.off()
            socket!!.disconnect()
            Log.e("Disss", "ok")
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    fun setFlag(fAl: Boolean, fP: Boolean, fD: Boolean, fHTL: Boolean, fDHTL: Boolean) {
        flagAlphaSort = fAl
        flagPriceSort = fP
        flagDaySort = fD
        flagHTLSort = fHTL
        flagDHTLSort = fDHTL
    }


    override fun onPause() {
        super.onPause()
        try {
            socket!!.off()
            socket!!.disconnect()
            Log.e("Disss", "ok")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
