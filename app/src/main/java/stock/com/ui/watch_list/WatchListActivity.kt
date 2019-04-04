package stock.com.ui.watch_list

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_watch_list.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.StockPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class WatchListActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_list)
       // setWatchListAdapter();

        img_btn_back.setOnClickListener {
            onBackPressed();
        }

        getWatchList();

    }

    private fun setWatchListAdapter(list:List<StockPojo.Stock>) {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_watch_list!!.layoutManager = llm
        recyclerView_watch_list.visibility = View.VISIBLE
        recyclerView_watch_list!!.adapter = WatchListAdapter(applicationContext!!,list);
    }

    fun getWatchList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockPojo> =
        apiService.getWatchList(getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),getFromPrefsString(StockConstant.USERID).toString())
        call.enqueue(object : Callback<StockPojo> {
            override fun onResponse(call: Call<StockPojo>, response: Response<StockPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if(response.body()!!.status.equals("1")){
                       // displayToast("dsdadadadada"+""+response.body()!!.stockList!!.size);
                       if(response.body()!!.stockList!!.size!=0){
                           setWatchListAdapter(response.body()!!.stockList!!);
                       }else{
                           displayToast(resources.getString(R.string.no_data));
                       }
                    }else if(response.body()!!.status.equals("2")){
                        appLogout();
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }
            override fun onFailure(call: Call<StockPojo>, t: Throwable) {
                println(t.toString())
                Log.d("WatchList--",""+t.localizedMessage)
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

}
