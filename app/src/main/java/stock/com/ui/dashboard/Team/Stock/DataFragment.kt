package stock.com.ui.dashboard.Team.Stock

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_data.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.AssestData
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class DataFragment : BaseFragment() {
    var itemId: Int = 0
    var type: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_data, container, false)
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
        val call: Call<AssestData> =
            apiService.getAssestData(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                assestId, type
            )
        call.enqueue(object : Callback<AssestData> {
            override fun onResponse(call: Call<AssestData>, response: Response<AssestData>) {
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

            override fun onFailure(call: Call<AssestData>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }
    private fun setData(list: AssestData.Stock) {
        tvOpen.setText(list.open)
        tvClose.setText(list.close)
        tvHigh.setText(list.high)
        tvLow.setText(list.low)
        tvAverage.setText(list.average)
        tvChange.setText(list.change)
        tvMarketCap.setText(list.marketCap)
        tvVolume.setText(list.hVolume)
        tvCirculating.setText(list.circulating)
        tvmaxSupply.setText(list.maxSupply)
        tvRank.setText(list.rank)
        tvCrypto.setText("About " + list.symbol)
        tvAbout.setText(list.cryptodescription)
        if (list.urls != null) {
            if (list.urls!!.websiteUrl!!.size > 0) {
                tvWebsite.setText(list.urls!!.websiteUrl!!.get(0))
                tvWebsite.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
            }
            if (list.urls!!.redditUrl!!.size > 0) {
                tvReddit.setText(list.urls!!.redditUrl!!.get(0))
                tvReddit.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
            }
            if (list.urls!!.messageBoardUrl!!.size > 0) {
                tvMessageBoard.setText(list.urls!!.messageBoardUrl!!.get(0))
                tvMessageBoard.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
            }
            if (list.urls!!.sourceCodeUrl!!.size > 0) {
                tvSourceCode.setText(list.urls!!.sourceCodeUrl!!.get(0))
                tvSourceCode.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
            }
            if (list.urls!!.explorerUrl!!.size > 0) {
                tvExplorer.setText(list.urls!!.explorerUrl!!.get(0))
                tvExplorer.setTextColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
            }
        }
        if (list.cryptologo != null)
            Glide.with(activity!!).load(list.cryptologo).into(logo)
    }


}