package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
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
import stock.com.ui.pojo.Currency
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class CurrencyFragment : BaseFragment() {
    var page: Int = 0
    var limit: Int = 50
    var forexList: ArrayList<Currency.Currency>? = null
    var forexAdapter: ForexAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forexList = ArrayList();
        getCurrency("1")

    }

    @SuppressLint("WrongConstant")
    fun setCurrencyAdapter(/*item: MutableList<MarketList.Crypto>*/) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        if (rv_currencyList != null) {
            rv_currencyList!!.layoutManager = llm
            rv_currencyList.visibility = View.VISIBLE
            forexAdapter = ForexAdapter(context!!, forexList!!, this, forexList!!);
            rv_currencyList!!.adapter = forexAdapter
        }
    }

    fun getCurrency(flag: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<Currency> =
            apiService.getCurrencyData(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                "currency", page.toString(), limit.toString()
            )
        call.enqueue(object : Callback<Currency> {

            override fun onResponse(call: Call<Currency>, response: Response<Currency>) {
                d.dismiss()
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        forexList = response.body()!!.currency
                        setCurrencyAdapter()
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<Currency>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }
}
