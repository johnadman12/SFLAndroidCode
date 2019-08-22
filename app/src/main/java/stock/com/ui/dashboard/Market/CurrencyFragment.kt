package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_currency.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.CurrencyPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import io.socket.client.Socket;
import io.socket.client.IO;
import org.json.JSONArray
import java.lang.ref.WeakReference
import java.net.URISyntaxException


class CurrencyFragment : BaseFragment() {
    var forexList: ArrayList<CurrencyPojo.Currency>? = null
    var forexOldList: ArrayList<CurrencyPojo.Currency>? = null
    var forexAdapter: ForexAdapter? = null
    var page: Int = 0
    var limit: Int = 50


    private var flag: Boolean = true;
    private var socket: Socket? = null;
    var search: String = ""
    var flagSearch: Boolean = false
    var flagPagination: Boolean = false

    var flagAlphaSort: Boolean = false
    var flagPriceSort: Boolean = false
    var flagDaySort: Boolean = false
    var flagHTLSort: Boolean = false
    var flagDHTLSort: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forexList = ArrayList();
        forexOldList = ArrayList();
        setCurrencyAdapter()
        getCurrency("1")

        srl_layout.setOnRefreshListener {
            flagPagination = true
            if (flagSearch) {
                page++;
                callApiSearch(search, 1);
            } else {
                page++;
                getCurrency("1")
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
            opts.forceNew = true
            opts.reconnection = true
            socket = IO.socket(StockConstant.SOCKET, opts)
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }
        socket!!.on(Socket.EVENT_CONNECT) {
        }.on("new_message") {
        }.on(Socket.EVENT_DISCONNECT) {
            socket!!.connect()
        }

        socket!!.connect()
        socket!!.on("new_message") { args ->
            val jsonArray = args[0] as JSONArray
            Log.d("socket_data", "---" + jsonArray);
            Thread(Task(forexAdapter!!, jsonArray)).start()

//            InternetTask().execute()


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

    fun setFilter(c: CharSequence) {
        Log.d("dsadada", "sdada--" + c);
        if (c.toString().length >= 2) {
            flag = false;
            Log.d("dsadada", "111111--");
            search = c.toString()
            flagSearch = true
            page = 0
            limit = 50
            callApiSearch(c, 0);
        } else if (c.toString().length == 0) {
            flag = true;
            page = 0
            limit = 50
            flagSearch = false
            Log.d("dsadada", "sdada--");
            getCurrency("2")
        }
    }

    fun getCurrency(flag: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<CurrencyPojo> = apiService.getCurrencyData(
            getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
            getFromPrefsString(StockConstant.USERID).toString(),
            "currency",
            page.toString(),
            "50"
        )
        call.enqueue(object : Callback<CurrencyPojo> {
            override fun onResponse(call: Call<CurrencyPojo>, response: Response<CurrencyPojo>) {
                d.dismiss();
                if (srl_layout != null)
                    srl_layout.isRefreshing = false;
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (flag.equals("2")) {
                            forexList!!.clear()
                            forexOldList!!.clear()
                        }
                        forexList!!.addAll(response.body()!!.currency!!);
                        forexOldList!!.addAll(response.body()!!.currency!!);

                        if (flagAlphaSort) {
                            try {
                                val sortedList = forexList!!.sortedBy { it.symbol }
                                forexList!!.clear()
                                forexOldList!!.clear()
                                forexList!!.addAll(sortedList)
                                forexOldList!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagPriceSort) {
                            try {
                                val sortedList = forexList!!.sortedBy { it.ask?.toDouble() }
                                forexList!!.clear()
                                forexOldList!!.clear()
                                forexList!!.addAll(sortedList)
                                forexOldList!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }


                        } else if (flagDaySort) {
                            try {
                                val sortedList = forexList!!.sortedBy { it.changeper?.toDouble() }
                                forexList!!.clear()
                                forexOldList!!.clear()
                                forexList!!.addAll(sortedList)
                                forexOldList!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagHTLSort) {
                            try {
                                val sortedList = forexList!!.sortedByDescending { it.ask?.toDouble() }

                                forexList!!.clear()
                                forexOldList!!.clear()
                                forexList!!.addAll(sortedList)
                                forexOldList!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagDHTLSort) {
                            try {
                                val sortedList = forexList!!.sortedByDescending { it.changeper?.toDouble() }
                                forexList!!.clear()
                                forexOldList!!.clear()
                                forexList!!.addAll(sortedList)
                                forexOldList!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        }
                        if (forexAdapter != null)
                            forexAdapter!!.notifyDataSetChanged();
//                        page++;
                    } else if (response.body()!!.status == "2") {
                        appLogout();
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<CurrencyPojo>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false;
                println(t.toString());
                displayToast(resources.getString(R.string.something_went_wrong), "error");
                d.dismiss();
            }
        })
    }

    fun saveToWatchList(cryptoId: Int) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> =
            apiService.addCurrencyToWatch(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                cryptoId,
                getFromPrefsString(StockConstant.USERID).toString(), "currency"
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

    @SuppressLint("WrongConstant")
    fun setCurrencyAdapter(/*item: MutableList<MarketList.Crypto>*/) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        if (rv_currencyList != null) {
            rv_currencyList!!.layoutManager = llm
            rv_currencyList.visibility = View.VISIBLE
            forexAdapter = ForexAdapter(context!!, forexOldList!!, this, forexList!!);
            rv_currencyList!!.adapter = forexAdapter
        }
    }

    internal inner class Task(var adapter: ForexAdapter, var jsonArray: JSONArray) : Runnable {
        override fun run() {
            try {
                activity!!.runOnUiThread(Runnable {
                    // Stuff that updates the UI
                    try {
                        //forexList!!.clear();
                        forexOldList!!.clear();
                        forexOldList!!.addAll(forexList!!);
                        for (i in 0..jsonArray.length()) {
                            var jsonObject = jsonArray.getJSONObject(i);
                            var model = CurrencyPojo.Currency()
                            try {
                                model.name = jsonObject!!.getString("name");
                                model.latestVolume = jsonObject!!.getString("latestVolume");
                                model.changeper = jsonObject!!.getString("changeper");
                                model.daychange = jsonObject!!.getString("daychange");
                                model.ask = jsonObject!!.getString("ask");
                                model.firstflag = jsonObject!!.getString("firstflag");
                                model.secondflag = jsonObject!!.getString("secondflag");
                                model.symbol = jsonObject!!.getString("symbol");
                                model.bid = jsonObject!!.getString("bid");

                                // forexList!!.add(model)
                                for (i in 0..forexList!!.size) {
                                    if (model.symbol.equals(forexList!!.get(i).symbol)) {
                                        Log.d("currency_id", "---" + model.name);
                                        model.firstflag = forexList!!.get(i).firstflag;
                                        model.secondflag = forexList!!.get(i).secondflag;
                                        forexList!!.set(i, model!!)
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

    private fun callApiSearch(c: CharSequence, firstTime: Int) {
        Log.d("dsadada", "22222--");
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<CurrencyPojo> =
            apiService.searchCurrency(
                "currency",
                c.toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                page.toString(),
                limit.toString()
            )
        call.enqueue(object : Callback<CurrencyPojo> {
            override fun onResponse(call: Call<CurrencyPojo>, response: Response<CurrencyPojo>) {
                d.dismiss()
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    // displayToast(response.body()!!.message, "sucess")
                    AppDelegate.hideKeyBoard(activity!!)
                    if (response.body()!!.status == "1") {
                        Log.d("dsadada", "sdada--4646464646464");
                        if (firstTime == 0) {
                            forexOldList!!.clear();
                            forexList!!.clear();
                        }
                        forexOldList!!.addAll(response.body()!!.currency!!)
                        forexList!!.addAll(response.body()!!.currency!!)

                        if (flagAlphaSort) {
                            try {
                                val sortedList = forexList!!.sortedBy { it.symbol?.toString() }
                                forexList!!.clear()
                                forexOldList!!.clear()
                                forexOldList!!.addAll(sortedList)
                                forexList!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagPriceSort) {
                            try {
                                val sortedList = forexList!!.sortedBy { it.ask?.toDouble() }
                                forexList!!.clear()
                                forexOldList!!.clear()
                                forexOldList!!.addAll(sortedList)
                                forexList!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagDaySort) {
                            try {
                                val sortedList = forexList!!.sortedBy { it.changeper?.toDouble() }
                                forexList!!.clear()
                                forexOldList!!.clear()
                                forexOldList!!.addAll(sortedList)
                                forexList!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagHTLSort) {
                            try {
                                val sortedList = forexList!!.sortedByDescending { it.ask?.toDouble() }
                                forexList!!.clear()
                                forexOldList!!.clear()
                                forexOldList!!.addAll(sortedList)
                                forexList!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }

                        } else if (flagDHTLSort) {
                            try {
                                val sortedList = forexList!!.sortedByDescending { it.changeper?.toDouble() }
                                forexList!!.clear()
                                forexOldList!!.clear()
                                forexOldList!!.addAll(sortedList)
                                forexList!!.addAll(sortedList)
                            } catch (e: Exception) {

                            }
                        }
                        if (forexAdapter != null)
                            forexAdapter!!.notifyDataSetChanged()

                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    } else {
                        displayToast(response.body()!!.message, "warning")
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                }
            }

            override fun onFailure(call: Call<CurrencyPojo>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                Log.d("serach_error", "---" + t.localizedMessage);
                d.dismiss()
                if (activity != null)
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
            }
        })
    }

    fun setSorting(type: String) {
        if (type.equals("Alpha")) {
            try {
                setFlag(true, false, false, false, false)
                var sortedList = forexList!!.sortedBy { it.symbol?.toString() }
                forexList!!.clear()
                forexList!!.addAll(sortedList)
                forexOldList!!.clear()
                forexOldList!!.addAll(sortedList)
                setCurrencyAdapter();
            } catch (e: Exception) {

            }
        } else if (type.equals("dayChange")) {
            try {
                setFlag(false, false, true, false, false)
                var sortedList = forexList!!.sortedBy { it.changeper?.toDouble() }
                forexList!!.clear()
                forexList!!.addAll(sortedList)
                forexOldList!!.clear()
                forexOldList!!.addAll(sortedList)
                setCurrencyAdapter();
            } catch (e: Exception) {

            }

        } else if (type.equals("price")) {
            try {
                setFlag(false, true, false, false, false)
                var sortedList = forexList!!.sortedBy { it.ask?.toDouble() }
                forexList!!.clear()
                forexList!!.addAll(sortedList)
                forexOldList!!.clear()
                forexOldList!!.addAll(sortedList)
                setCurrencyAdapter();
            } catch (e: Exception) {

            }
        } else if (type.equals("HighToLow")) {
            try {
                setFlag(false, false, false, true, false)
                val sortedList = forexList!!.sortedByDescending { it.ask!!.toDouble() }
                forexList!!.clear()
                forexList!!.addAll(sortedList)
                forexOldList!!.clear()
                forexOldList!!.addAll(sortedList)
                setCurrencyAdapter();
            } catch (e: Exception) {

            }
        } else if (type.equals("DayHighToLow")) {
            try {
                setFlag(false, false, false, false, true)
                val sortedList = forexList!!.sortedByDescending { it.changeper?.toDouble() }
                forexList!!.clear()
                forexList!!.addAll(sortedList)
                forexOldList!!.clear()
                forexOldList!!.addAll(sortedList)
                setCurrencyAdapter();
            } catch (e: Exception) {

            }
        } else if (type.equals("nodata")) {
            try {
                flagAlphaSort = false
                flagPriceSort = false
                flagDaySort = false
                flagHTLSort = false
                flagDHTLSort = false
                getCurrency("2")
            } catch (e: Exception) {

            }
        }
    }

    fun setFlag(fAl: Boolean, fP: Boolean, fD: Boolean, fHTL: Boolean, fDHTL: Boolean) {
        flagAlphaSort = fAl
        flagPriceSort = fP
        flagDaySort = fD
        flagHTLSort = fHTL
        flagDHTLSort = fDHTL
    }

//    @SuppressLint("StaticFieldLeak")
/*    inner class InternetTask : AsyncTask<JSONArray, Int, Void>() {

        override fun doInBackground(vararg jsonArray: JSONArray): Void? {
            try {
                //forexList!!.clear();
                forexOldList!!.clear();
                forexOldList!!.addAll(forexList!!);
                for (i in 0..jsonArray.size) {
                    var jsonObject = jsonArray.get(i)!!.getJSONObject(i);

                    Log.d("jsonssnd", jsonObject.toString())
                    var model = CurrencyPojo.Currency()
                    try {
                        model.name = jsonObject!!.getString("name");
                        model.latestVolume = jsonObject!!.getString("latestVolume");
                        model.changeper = jsonObject!!.getString("changeper");
                        model.daychange = jsonObject!!.getString("daychange");
                        model.ask = jsonObject!!.getString("ask");
                        model.firstflag = jsonObject!!.getString("firstflag");
                        model.secondflag = jsonObject!!.getString("secondflag");
                        model.symbol = jsonObject!!.getString("symbol");
                        model.bid = jsonObject!!.getString("bid");

                        // forexList!!.add(model)
                        for (i in 0..forexList!!.size) {
                            if (model.symbol.equals(forexList!!.get(i).symbol)) {
                                Log.d("currency_id", "---" + model.name);
                                model.firstflag = forexList!!.get(i).firstflag;
                                model.secondflag = forexList!!.get(i).secondflag;
                                forexList!!.set(i, model!!)
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
            } catch (ee: java.lang.Exception) {
            }

            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)
            if (forexAdapter != null)
                forexAdapter!!.notifyDataSetChanged();
        }

    }*/
}
