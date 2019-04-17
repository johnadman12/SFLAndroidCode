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
import stock.com.ui.dashboard.home.adapter.LatestNewsAdapter
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.NewsPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class NewsFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLatestNewslist()
    }


    fun getLatestNewslist() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<NewsPojo> =
            apiService.getLatestNewslist(getFromPrefsString(StockConstant.ACCESSTOKEN).toString())
        call.enqueue(object : Callback<NewsPojo> {
            override fun onResponse(call: Call<NewsPojo>, response: Response<NewsPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        setLatestNewAdapter(response.body()!!.news)

                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    } else {
                        displayToast(resources.getString(R.string.internal_server_error))
                        d.dismiss()
                    }
                }
            }

            override fun onFailure(call: Call<NewsPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }

        })
    }

    @SuppressLint("WrongConstant")
    private fun setLatestNewAdapter(news: List<NewsPojo.News>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        rvNews!!.layoutManager = llm
        rvNews.visibility = View.VISIBLE
        rvNews!!.adapter = StockNewsAdapter(context!!, news);
    }
}