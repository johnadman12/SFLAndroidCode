package stock.com.ui.dashboard.home.Currency

import android.os.Bundle
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_data_currency.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.AssestData
import stock.com.ui.pojo.CurrencyDetail
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class DataCurrencyFragment : BaseFragment() {
    var itemId: Int = 0
    var type: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_data_currency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments != null) {
            itemId = arguments!!.getInt(StockConstant.MARKETID)
            type = arguments!!.getString(StockConstant.MARKET_TYPE)

        }
        getData(itemId.toString())

    }


    fun getData(assestId: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<CurrencyDetail> =
            apiService.getCurrencyDetail(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                assestId, type
            )
        call.enqueue(object : Callback<CurrencyDetail> {
            override fun onResponse(call: Call<CurrencyDetail>, response: Response<CurrencyDetail>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        setData(response.body()!!.stock!!.get(0))
                        d.dismiss()

                    } else if (response.body()!!.status == "0") {
                        displayToast(response.body()!!.message, "error")
                    }
                }
            }

            override fun onFailure(call: Call<CurrencyDetail>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }


    private fun setData(list: CurrencyDetail.Stock) {
        tvOpen.setText(list.latestPrice)
        tvClose.setText(list.previousClose)
        tvHigh.setText(list.day_range)
        tvLow.setText(list.week_range)
//        tvAverage.setText(list.average)
//        tvMarketCap.setText(list.marketCap)
//        tvVolume.setText(list.hVolume)
//        tvCirculating.setText(list.circulating)
//        tvmaxSupply.setText(list.maxSupply)
//        tvRank.setText(list.rank)
        tvCrypto.setText("About " + list.symbol)
//        tvAbout.setText(list.des)

    }
}