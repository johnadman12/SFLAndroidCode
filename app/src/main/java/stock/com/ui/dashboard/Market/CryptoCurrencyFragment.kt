package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.madapps.liquid.LiquidRefreshLayout
import kotlinx.android.synthetic.main.fragment_currency.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.MarketData
import stock.com.ui.pojo.MarketList
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class CryptoCurrencyFragment : BaseFragment() {
    private var cryptoAdapter: CurrencyAdapter? = null;
    private var cryptoList: ArrayList<MarketList.Crypto>? = null
    private var cryptoListNew: ArrayList<MarketList.Crypto>? = null
    private var cryptoListFiltered: ArrayList<MarketList.Crypto>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cryptoList = ArrayList()
        cryptoListNew = ArrayList()
        cryptoListFiltered = ArrayList()
        refreshData.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                //TODO make api call here
                Handler().postDelayed({
                }, 5000)
                getCurrency("1")
            }
        })
        getCurrency("1")
        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                if (isVisible) {
                    getCurrencyAgain("1")
                    mainHandler.postDelayed(this, 8000)
                }
            }
        })
        setCryptoCurrencyAdapter()
    }


    fun getCurrency(flag: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketData> =
            apiService.getMarketData(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                "crypto", "", "", "", ""
            )
        call.enqueue(object : Callback<MarketData> {

            override fun onResponse(call: Call<MarketData>, response: Response<MarketData>) {
                d.dismiss()
                if (refreshData != null)
                    refreshData.finishRefreshing()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        if (flag.equals("1")) {
                            cryptoList = response.body()!!.crypto
                            cryptoListNew = response.body()!!.crypto
//                            cryptoAdapter!!.notifyDataSetChanged()
//                            rv_currencyList!!.adapter = cryptoAdapter
                            setCryptoCurrencyAdapter()
                            if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.ACTIVE_CURRENCY_TYPE))) {
                                setActiveCurrencyType("")
                            }
                        } else {
                            cryptoList = response.body()!!.crypto
                            for (i in 0 until cryptoList!!.size) {
                                if (cryptoList!!.get(i).changeper != null)
                                    if (!cryptoList!!.get(i).changeper.equals("0")) {
                                        cryptoListFiltered!!.add(cryptoList!!.get(i))
//                                        stockList!!.remove(stockList!!.get(i))
                                        Log.d("stocklist", cryptoListFiltered!!.size.toString())
                                    }
                            }
                            cryptoList!!.clear()
                            cryptoList = cryptoListFiltered
                            cryptoAdapter!!.notifyDataSetChanged()
                            rv_currencyList!!.adapter = cryptoAdapter
                            setCryptoCurrencyAdapter(/*cryptoList!!*/)
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


    fun getCurrencyAgain(flag: String) {
//        val d = StockDialog.showLoading(activity!!)
//        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketData> =
            apiService.getMarketData(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                "crypto", "", "", "", ""
            )
        call.enqueue(object : Callback<MarketData> {

            override fun onResponse(call: Call<MarketData>, response: Response<MarketData>) {
//                d.dismiss()
                if (refreshData != null)
                    refreshData.finishRefreshing()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (flag.equals("1")) {
                            cryptoListNew = response.body()!!.crypto
                            cryptoAdapter!!.notifyDataSetChanged()

                            Handler().postDelayed(Runnable {
                                setCryptoCurrencyAdapter()
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
    fun setCryptoCurrencyAdapter(/*item: MutableList<MarketList.Crypto>*/) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        if (rv_currencyList!=null) {
            rv_currencyList!!.layoutManager = llm
            rv_currencyList.visibility = View.VISIBLE
            cryptoAdapter = CurrencyAdapter(context!!, cryptoList!!, this, cryptoListNew!!);
            rv_currencyList!!.adapter = cryptoAdapter
        }
    }

    fun saveToWatchList(cryptoId: Int) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> =
            apiService.addCurrencyToWatch(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                cryptoId,
                getFromPrefsString(StockConstant.USERID).toString(), "crypto"
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    displayToast(response.body()!!.message, "sucess")
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        AppDelegate.showAlert(activity!!,response.body()!!.message)
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
        if (cryptoAdapter != null)
            cryptoAdapter!!.getFilter().filter(c)
    }

    fun setSorting(type: String) {
        if (type.equals("Alpha")) {
            var sortedList = cryptoList!!.sortedBy { it.symbol?.toString() }
            for (obj in sortedList) {
                cryptoList!!.clear()
                cryptoList!!.addAll(sortedList)
                rv_currencyList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("dayChange")) {
            var sortedList = cryptoList!!.sortedBy { it.changeper?.toDouble() }
            for (obj in sortedList) {
                cryptoList!!.clear()
                cryptoList!!.addAll(sortedList)
                rv_currencyList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("price")) {
            var sortedList = cryptoList!!.sortedBy { it.latestPrice?.toDouble() }
            for (obj in sortedList) {
                cryptoList!!.clear()
                cryptoList!!.addAll(sortedList)
                rv_currencyList!!.adapter!!.notifyDataSetChanged()
            }
        }
    }

    fun changePercentFilter(type: String) {
        if (type.equals("0")) {
            getCurrency("0")
            rv_currencyList!!.adapter!!.notifyDataSetChanged()
        }

    }

}