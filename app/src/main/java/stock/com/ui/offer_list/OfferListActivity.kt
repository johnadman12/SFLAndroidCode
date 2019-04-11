package stock.com.ui.offer_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_offer_list.*
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.home.adapter.MatchLiveAdapter
import stock.com.ui.pojo.OffersPojo
import stock.com.ui.pojo.SocialLinkPojo
import stock.com.utils.StockDialog

class OfferListActivity : BaseActivity() {


    var list: ArrayList<OffersPojo.offers>? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_list)
        list = ArrayList();
        getOfferList();


        img_btn_back.setOnClickListener {
            onBackPressed();
        }


    }

    private fun setAdapter() {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_offer!!.layoutManager = llm
        rv_offer!!.adapter = OfferListAdapter(applicationContext!!, list!!);
    }

    fun getOfferList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<OffersPojo> =
            apiService.getOfferList()
        call.enqueue(object : Callback<OffersPojo> {
            override fun onResponse(call: Call<OffersPojo>, response: Response<OffersPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        ll_main.visibility = View.VISIBLE;
                        list!!.addAll(response.body()!!.offerList!!)
                        setAdapter();
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }
            override fun onFailure(call: Call<OffersPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }
}
