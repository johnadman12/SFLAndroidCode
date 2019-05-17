package stock.com.ui.dashboard.Team.Stock

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.CityfalconNewsPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.util.ArrayList
import android.content.Intent.getIntent



class NewsFragment : BaseFragment() {
    var bd:Bundle= Bundle()
    var identifires: String = "AAPL,TSLA,FTSE"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments!=null)
            identifires= arguments!!.getString("Stockname")
        getNewslist()
    }


    val categories: String = "op"
    fun getNewslist() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClientNews()!!.create(ApiInterface::class.java)
        val call: Call<CityfalconNewsPojo> =
            apiService.getNewsHome(
                "tickers",
                identifires, categories, "0",
                "latest", "d1", false,
                StockConstant.NEWS_ACCESS_TOKEN
            )
        call.enqueue(object : Callback<CityfalconNewsPojo> {
            override fun onResponse(call: Call<CityfalconNewsPojo>, response: Response<CityfalconNewsPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    d.dismiss()
                    setLatestNewAdapter(response.body()!!.stories, identifires)
                }
            }

            override fun onFailure(call: Call<CityfalconNewsPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong),"error")
                d.dismiss()
            }
        })
    }


    @SuppressLint("WrongConstant")
    private fun setLatestNewAdapter(
        news: ArrayList<CityfalconNewsPojo.Story>,
        identifires: String
    ) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        rvNews!!.layoutManager = llm
        rvNews.visibility = View.VISIBLE
        rvNews!!.adapter = StockNewsAdapter(context!!, news,identifires);
    }
}