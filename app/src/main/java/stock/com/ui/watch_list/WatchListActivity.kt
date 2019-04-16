package stock.com.ui.watch_list

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
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
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.StockPojo
import stock.com.ui.watch_list.adapter.WatchListAdapter_
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class WatchListActivity : BaseActivity() {
    private var watchListAdapter: WatchListAdapter_? = null;
    private var list: ArrayList<StockPojo.Stock>? = null;

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_list)

        list = ArrayList();
        watchListAdapter = WatchListAdapter_(applicationContext!!, list as ArrayList, this)
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_watch_list!!.layoutManager = llm
        recyclerView_watch_list!!.adapter = watchListAdapter;


        img_btn_back.setOnClickListener {
            onBackPressed();
        }

        getWatchList();

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                watchListAdapter!!.getFilter().filter(s);
            }
        })

        ll_filter.setOnClickListener {
            startActivityForResult(
                Intent(this@WatchListActivity, WatchFilterActivity::class.java),
                StockConstant.RESULT_CODE_FILTER_WATCH
            );
        }
        ll_sort.setOnClickListener {
            startActivityForResult(
                Intent(this@WatchListActivity, WatchSortActivity::class.java),
                StockConstant.RESULT_CODE_SORT_WATCH
            );
        }

    }

    private fun setWatchListAdapter() {
        recyclerView_watch_list.visibility = View.VISIBLE
        recyclerView_watch_list.adapter!!.notifyDataSetChanged();
    }

    private fun getWatchList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockPojo> =
            apiService.getWatchList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<StockPojo> {
            override fun onResponse(call: Call<StockPojo>, response: Response<StockPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status.equals("1")) {
                        // displayToast("dsdadadadada"+""+response.body()!!.stockList!!.size);
                        if (response.body()!!.stockList!!.size != 0) {
                            //setWatchListAdapter(response.body()!!.stockList!!);
                            list!!.addAll(response.body()!!.stockList!!);
                            setWatchListAdapter();
                            ll_search.visibility = View.VISIBLE;
                            ll_filter.visibility = View.VISIBLE;
                            ll_sort.visibility = View.VISIBLE;
                        } else {
                            displayToast(resources.getString(R.string.no_data));
                        }
                    } else if (response.body()!!.status.equals("2")) {
                        appLogout();
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<StockPojo>, t: Throwable) {
                println(t.toString())
                Log.d("WatchList--", "" + t.localizedMessage)
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }
    fun callApiRemoveWatch(id: String) {
        Log.d("Remove ", "--" + id);
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> = apiService.removeWatch(
            getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
            getFromPrefsString(StockConstant.USERID).toString(),
            id
        )
        call.enqueue(object : Callback<BasePojo> {
            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status.equals("1")) {
                        // displayToast("Remove "+""+response.body()!!.message);
                        getWatchList()
                    } else if (response.body()!!.status.equals("2")) {
                        appLogout();
                    } else {
                        displayToast(response.body()!!.message)
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }
            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
               // Log.d("WatchList--", "" + t.localizedMessage)
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.RESULT_CODE_FILTER_WATCH) {
            // displayToast("filter");
        } else if (requestCode == StockConstant.RESULT_CODE_SORT_WATCH) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                   var flag=data.getStringExtra("flag");
                    if(flag.equals("high")) {
                        var sortedList = list!!.sortedWith(compareBy({ it.latestPrice})).asReversed();
                        list!!.clear();
                        setWatchListAdapter();
                        list!!.addAll(sortedList);
                        setWatchListAdapter();
                    }else if(flag.equals("low")){
                        var sortedList = list!!.sortedWith(compareBy({ it.latestPrice}));
                        list!!.clear();
                        setWatchListAdapter();
                        list!!.addAll(sortedList);
                        setWatchListAdapter();
                    }
                }
            }
        }
    }
}
