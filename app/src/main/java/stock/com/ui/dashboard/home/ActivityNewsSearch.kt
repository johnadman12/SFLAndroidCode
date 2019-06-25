package stock.com.ui.dashboard.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_searchbar.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.HomeSearchPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class ActivityNewsSearch : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchbar)
        img_btn_back.setOnClickListener {
            onBackPressed()
        }
        imgcross.setOnClickListener {
            et_search.setText("")
        }

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length >= 3)
                    getHomeSearch(s.toString())
            }
        })

    }

    @SuppressLint("WrongConstant")
    private fun setHomeAdapter(data: HomeSearchPojo.Data?) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_home!!.layoutManager = llm
        recycle_home.visibility = View.VISIBLE
        recycle_home!!.adapter = HomeNewsAdapter(this@ActivityNewsSearch, data);
    }


    fun getHomeSearch(search: String) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<HomeSearchPojo> =
            apiService.getHomeSearch(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), search

            )
        call.enqueue(object : Callback<HomeSearchPojo> {

            override fun onResponse(call: Call<HomeSearchPojo>, response: Response<HomeSearchPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        setHomeAdapter(response.body()!!.data)
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<HomeSearchPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }


}