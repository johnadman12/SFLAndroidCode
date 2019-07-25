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
    var flagAlphaSort: Boolean = false
    var flagPriceSort: Boolean = false
    var flagDaySort: Boolean = false
    var flagHTLSort: Boolean = false
    var flagDHTLSort: Boolean = false
    var flagPagination: Boolean = false

    private var flag: Boolean = true;
    var page: Int = 0
    var limit: Int = 50
    lateinit var mainHandler: Handler;


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cryptoList = ArrayList()
        cryptoListNew = ArrayList()
        cryptoListFiltered = ArrayList()

        srl_layout.setOnRefreshListener {
            flagPagination = true
            getCurrency("2")
        }

        getCurrency("1")
        setCryptoCurrencyAdapter()


    }


    override fun onResume() {
        super.onResume()
        mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                if (isVisible) {
                    if (flag) {
                        getCurrencyAgain("1")
                    }
                    mainHandler.postDelayed(this, 8000)
                } else {
                    flagAlphaSort = false
                    flagPriceSort = false
                    flagDaySort = false
                    flagHTLSort = false
                    flagDHTLSort = false
                }
            }
        })
    }

    fun setFilter(c: CharSequence) {
        /*if (cryptoAdapter != null)
            cryptoAdapter!!.getFilter().filter(c)*/
        Log.d("dsadada", "sdada--" + c);
        if (c.toString().length >= 2) {
            flag = false;
            Log.d("dsadada", "111111--");
            callApiSearch(c);
        } else {
            flag = true;
            Log.d("dsadada", "sdada--");
        }
    }

    fun getCurrency(flag: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketData> =
            apiService.getMarketData(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                "crypto", "", "", "", "", page.toString(), limit.toString()
            )
        call.enqueue(object : Callback<MarketData> {

            override fun onResponse(call: Call<MarketData>, response: Response<MarketData>) {
                d.dismiss()
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
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
                        } else if (flag.equals("2")) {
                            limit = limit + 50
                            cryptoList = response.body()!!.crypto
                            cryptoListNew = response.body()!!.crypto
                            cryptoAdapter!!.notifyDataSetChanged()
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
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }


    fun getCurrencyAgain(flag: String) {
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketData> =
            apiService.getMarketData(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                "crypto", "", "", "", "", page.toString(), limit.toString()
            )
        call.enqueue(object : Callback<MarketData> {
            override fun onResponse(call: Call<MarketData>, response: Response<MarketData>) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        if (flag.equals("1")) {
                            cryptoList!!.clear();
                            cryptoList!!.addAll(cryptoListNew!!);
                            cryptoListNew = response.body()!!.crypto
                            if (flagAlphaSort) {
                                val sortedList = cryptoListNew!!.sortedBy { it.symbol?.toString() }
                                for (obj in sortedList) {
                                    cryptoListNew!!.clear()
                                    cryptoListNew!!.addAll(sortedList)
                                }
                            } else if (flagPriceSort) {
                                val sortedList = cryptoListNew!!.sortedBy { it.latestPrice?.toDouble() }
                                for (obj in sortedList) {
                                    cryptoListNew!!.clear()
                                    cryptoListNew!!.addAll(sortedList)
//                                    rv_currencyList!!.adapter!!.notifyDataSetChanged()
                                }

                            } else if (flagDaySort) {
                                val sortedList = cryptoListNew!!.sortedBy { it.changeper?.toDouble() }
                                for (obj in sortedList) {
                                    cryptoListNew!!.clear()
                                    cryptoListNew!!.addAll(sortedList)
//                                    rv_currencyList!!.adapter!!.notifyDataSetChanged()
                                }
                            } else if (flagHTLSort) {
                                val sortedList = cryptoListNew!!.sortedByDescending { it.latestPrice?.toDouble() }
                                for (obj in sortedList) {
                                    cryptoListNew!!.clear()
                                    cryptoListNew!!.addAll(sortedList)
//                                    rv_currencyList!!.adapter!!.notifyDataSetChanged()
                                }

                            } else if (flagDHTLSort) {
                                val sortedList = cryptoListNew!!.sortedByDescending { it.changeper?.toDouble() }
                                for (obj in sortedList) {
                                    cryptoListNew!!.clear()
                                    cryptoListNew!!.addAll(sortedList)
//                                    rv_currencyList!!.adapter!!.notifyDataSetChanged()
                                }

                            } else {
                                if (rv_currencyList != null)
                                    rv_currencyList!!.adapter!!.notifyDataSetChanged()
                            }

                            if (rv_currencyList != null)
                                rv_currencyList!!.adapter!!.notifyDataSetChanged()
                            setCryptoCurrencyAdapter()


                        } else {
                            cryptoList!!.clear()
                            cryptoListNew!!.clear()
                            cryptoListNew = response.body()!!.crypto
                            cryptoList!!.addAll(cryptoListNew!!)
                            setCryptoCurrencyAdapter()
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
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
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
        if (rv_currencyList != null) {
            rv_currencyList!!.layoutManager = llm
            rv_currencyList.visibility = View.VISIBLE
            cryptoAdapter = CurrencyAdapter(context!!, cryptoList!!, this, cryptoListNew!!);
            rv_currencyList!!.adapter = cryptoAdapter
        }
    }

    private fun callApiSearch(c: CharSequence) {
        Log.d("dsadada", "22222--");
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketList> =
            apiService.searchCrypto(
                "crypto",
                c.toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                "0",
                "50"
            )
        call.enqueue(object : Callback<MarketList> {
            override fun onResponse(call: Call<MarketList>, response: Response<MarketList>) {
                d.dismiss()
                if (response.body() != null) {
                    // displayToast(response.body()!!.message, "sucess")
                    if (response.body()!!.status == "1") {
                        Log.d("dsadada", "sdada--4646464646464");
                        cryptoList!!.clear();
                        cryptoListNew!!.clear();
                        cryptoList = response.body()!!.crypto;
                        cryptoListNew = response.body()!!.crypto;
                        setCryptoCurrencyAdapter()
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    } else {
                        displayToast(response.body()!!.message, "warning")
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                }
            }

            override fun onFailure(call: Call<MarketList>, t: Throwable) {
                Log.d("serach_error", "---" + t.localizedMessage);
                d.dismiss()
                if (activity != null)
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
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


    fun setSorting(type: String) {
        if (type.equals("Alpha")) {
            setFlag(true, false, false, false, false)
            var sortedList = cryptoListNew!!.sortedBy { it.symbol?.toString() }
            for (obj in sortedList) {
                cryptoListNew!!.clear()
                cryptoListNew!!.addAll(sortedList)
                cryptoList!!.clear()
                cryptoList!!.addAll(sortedList)
                rv_currencyList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("dayChange")) {
            setFlag(false, false, true, false, false)
            var sortedList = cryptoListNew!!.sortedBy { it.changeper?.toDouble() }
            for (obj in sortedList) {
                cryptoListNew!!.clear()
                cryptoListNew!!.addAll(sortedList)
                cryptoList!!.clear()
                cryptoList!!.addAll(sortedList)
                rv_currencyList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("price")) {
            setFlag(false, true, false, false, false)
            var sortedList = cryptoListNew!!.sortedBy { it.latestPrice?.toDouble() }
            for (obj in sortedList) {
                cryptoListNew!!.clear()
                cryptoListNew!!.addAll(sortedList)
                cryptoList!!.clear()
                cryptoList!!.addAll(sortedList)
                rv_currencyList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("HighToLow")) {
            setFlag(false, false, false, true, false)
            val sortedList = cryptoListNew!!.sortedByDescending { it.latestPrice?.toDouble() }
            for (obj in sortedList) {
                cryptoListNew!!.clear()
                cryptoListNew!!.addAll(sortedList)
                cryptoList!!.clear()
                cryptoList!!.addAll(sortedList)
                rv_currencyList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("DayHighToLow")) {
            setFlag(false, false, false, false, true)
            val sortedList = cryptoListNew!!.sortedByDescending { it.changeper?.toDouble() }
            for (obj in sortedList) {
                cryptoListNew!!.clear()
                cryptoListNew!!.addAll(sortedList)
                cryptoList!!.clear()
                cryptoList!!.addAll(sortedList)
                rv_currencyList!!.adapter!!.notifyDataSetChanged()
            }
        } else if (type.equals("nodata")) {
            getCurrencyAgain("0")
        }
    }

    fun changePercentFilter(type: String) {
        if (type.equals("0")) {
            getCurrency("0")
            rv_currencyList!!.adapter!!.notifyDataSetChanged()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == 410 && data != null) {
            getCurrencyAgain("1")
            rv_currencyList.adapter!!.notifyDataSetChanged()

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
        mainHandler.removeCallbacksAndMessages(null)
    }

}