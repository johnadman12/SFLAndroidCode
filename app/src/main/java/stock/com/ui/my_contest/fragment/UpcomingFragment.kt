package stock.com.ui.my_contest.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.fragment_finished.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.my_contest.adapter.LiveAdapter
import stock.com.ui.my_contest.adapter.UpcomingAdapter
import stock.com.ui.pojo.LobbyContestPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import java.util.ArrayList

class UpcomingFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_finished, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getContests()

    }

    fun getContests() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<LobbyContestPojo> =
            apiService.getContest(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), "upcoming",
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<LobbyContestPojo> {

            override fun onResponse(call: Call<LobbyContestPojo>, response: Response<LobbyContestPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        setAdapter(response.body()!!.contest)
                    }
                } else {
                    Toast.makeText(
                        activity!!,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<LobbyContestPojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    activity!!,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                d.dismiss()
            }
        })
    }

    private fun setAdapter(contest: ArrayList<LobbyContestPojo.Contest>) {
        val llm = GridLayoutManager(context,2)
        //llm.orientation = GridLayoutManager(applicationContext,2)
        recycler_finished!!.layoutManager = llm
        recycler_finished!!.adapter = UpcomingAdapter(context!!,contest);
    }

}