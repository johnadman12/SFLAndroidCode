package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import java.net.URISyntaxException


class CurrencyFragment : BaseFragment() {
    var forexList: ArrayList<CurrencyPojo.Currency>? = null
    var forexOldList: ArrayList<CurrencyPojo.Currency>? = null
    var forexAdapter: ForexAdapter? = null
    var page: Int = 0
    var limit: Int = 50

    private var socket: Socket? = null;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forexList = ArrayList();
        forexOldList = ArrayList();
        setCurrencyAdapter()
        getCurrency("1")
        try {
            val opts = IO.Options()
            opts.forceNew = true
            opts.reconnection = true
            socket = IO.socket("https://www.dfxchange.com:4000", opts)
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

    fun getCurrency(flag: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<CurrencyPojo> = apiService.getCurrencyData(getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), getFromPrefsString(StockConstant.USERID).toString(), "currency", page.toString(), "50")
        call.enqueue(object : Callback<CurrencyPojo> {
            override fun onResponse(call: Call<CurrencyPojo>, response: Response<CurrencyPojo>) {
                d.dismiss();
                srl_layout.isRefreshing=false;
                if (srl_layout != null)
                    srl_layout.isRefreshing = false;
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        forexList!!.addAll(response.body()!!.currency!!);
                        forexOldList!!.addAll(response.body()!!.currency!!);
                        // setCurrencyAdapter()
                        if (forexAdapter != null)
                            forexAdapter!!.notifyDataSetChanged();
                        page++;
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
                                model.currencyid = jsonObject!!.getString("currencyid").toInt();
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
                                    if (model.currencyid.equals(forexList!!.get(i).currencyid)) {
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
}
