package stock.com.ui.dashboard.Market

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.home_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.home.adapter.StockNameAdapter
import stock.com.ui.pojo.ExchangeList
import stock.com.ui.pojo.HomePojo
import stock.com.utils.StockDialog

class MarketFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lobby, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExchangeNamelist()
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
//                        setStockNameAdapter(5)
                    } else {
                        displayToast(resources.getString(R.string.internal_server_error))
                        d.dismiss()
                    }
                }
            }
                override fun onFailure(call: Call<ExchangeList>, t: Throwable) {
            println(t.toString())
            displayToast(resources.getString(R.string.something_went_wrong))
            d.dismiss()
        }
    })


}

    fun setStockNameAdapter(exchangeList: List<HomePojo.Exchange>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView_stock_name!!.layoutManager = llm
        recyclerView_stock_name.visibility = View.VISIBLE
        recyclerView_stock_name!!.adapter = StockNameAdapter(context!!, exchangeList)
        //recyclerView_stock_name.addItemDecoration(CirclePagerIndicatorDecoration(activity))
    }
}