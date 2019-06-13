package stock.com.ui.dashboard.my_contest.fragment

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import com.madapps.liquid.LiquidRefreshLayout
import kotlinx.android.synthetic.main.fragment_created.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.my_contest.adapter.CreatedAdapter
import stock.com.ui.pojo.CreateContest
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class CreatedFragment : BaseFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_created, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        refreshData.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                //TODO make api call here
                Handler().postDelayed({
                    refreshData.finishRefreshing()
                }, 5000)
                getContests()
            }
        })
        getContests()
    }

    fun getContests() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<CreateContest> =
            apiService.getCreatedContest(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<CreateContest> {

            override fun onResponse(call: Call<CreateContest>, response: Response<CreateContest>) {
                d.dismiss()
                if (refreshData != null)
                    refreshData.finishRefreshing()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        setAdapter(response.body()!!.usercontest)
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<CreateContest>, t: Throwable) {
                if (refreshData != null)
                    refreshData.finishRefreshing()
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }


    private fun setAdapter(usercontest: MutableList<CreateContest.Usercontest>) {
        val llm = GridLayoutManager(context, 2)
        //llm.orientation = GridLayoutManager(applicationContext,2)
        recycler_my_contest!!.layoutManager = llm
        recycler_my_contest!!.adapter = CreatedAdapter(context!!, usercontest);
    }


}