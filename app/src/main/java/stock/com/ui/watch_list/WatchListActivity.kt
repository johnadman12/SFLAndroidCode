package stock.com.ui.watch_list

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_watch_list.*
import kotlinx.android.synthetic.main.include_back.*
import me.rishabhkhanna.recyclerswipedrag.OnDragListener
import me.rishabhkhanna.recyclerswipedrag.RecyclerHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.WatchlistPojo
import stock.com.ui.watch_list.adapter.WatchListAdapter_
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import kotlin.Exception


class WatchListActivity : BaseActivity() {
    private var watchListAdapter: WatchListAdapter_? = null;
    private var list: ArrayList<WatchlistPojo.WatchStock>? = null;
    var flag: String = ""
    var page: Int = 0
    var limit: Int = 50
    private var flagSearch: Boolean = false;
    var searchWatch: String = ""
    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_list)
        list = ArrayList();
        setAdapter();

        val touchHelper = RecyclerHelper<WatchlistPojo.WatchStock>(
            list!!,
            watchListAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>
        )
        touchHelper.setRecyclerItemDragEnabled(true);
        touchHelper.setRecyclerItemSwipeEnabled(false);


        touchHelper.setOnDragItemListener(object : OnDragListener {
            override fun onDragItemListener(fromPosition: Int, toPosition: Int) {
                Log.d("4564646464", "--" + fromPosition + "----" + toPosition);
            }
        })


        val itemTouchHelper = ItemTouchHelper(touchHelper)
        itemTouchHelper.attachToRecyclerView(recyclerView_watch_list)


        img_btn_back.setOnClickListener {
            onBackPressed();
        }

        imgcross.setOnClickListener {
            if (!TextUtils.isEmpty(et_search.text.toString()))
                et_search.setText("")
            getWatchList(0)
        }

        srl_layout.setOnRefreshListener {
            if (flagSearch) {
                page++
                callApiSearch(searchWatch)
            } else {
                page++;
                getWatchList(1);
            }
        }

        getWatchList(0)

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.length >= 2) {
                    flagSearch = true
                    searchWatch = s.toString()
                    callApiSearch(s)
                } else {
                    flagSearch = false
                    searchWatch = ""
                    getWatchList(0)
                }
            }
        })

        ll_filter.setOnClickListener {
            et_search.setText("")
            startActivityForResult(
                Intent(this@WatchListActivity, WatchFilterActivity::class.java),
                StockConstant.RESULT_CODE_FILTER_WATCH
            );
        }

        ll_sort.setOnClickListener {
            startActivityForResult(
                Intent(this@WatchListActivity, WatchSortActivity::class.java)
                    .putExtra("flagStatus", flag),
                StockConstant.RESULT_CODE_SORT_WATCH
            );
        }

    }

    private fun setAdapter() {
        watchListAdapter = WatchListAdapter_(applicationContext!!, list as ArrayList, this)
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_watch_list!!.layoutManager = llm
        recyclerView_watch_list!!.adapter = watchListAdapter;
    }

    private fun setWatchListAdapter() {
        recyclerView_watch_list.visibility = View.VISIBLE
        recyclerView_watch_list.adapter!!.notifyDataSetChanged();
    }


    private fun callApiSearch(c: CharSequence) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<WatchlistPojo> =
            apiService.getSearchWatchList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                c.toString(),
                page.toString(),
                limit.toString()
            )
        call.enqueue(object : Callback<WatchlistPojo> {
            override fun onResponse(call: Call<WatchlistPojo>, response: Response<WatchlistPojo>) {
                d.dismiss()
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                if (response.body() != null) {
                    // displayToast(response.body()!!.message, "sucess")
                    if (response.body()!!.status == "1") {
                        if (srl_layout != null)
                            srl_layout.isRefreshing = false
                        if (response.body()!!.status.equals("1")) {
                            try {
                                setAssetWatchlistFilter(" ")
                                setSectorWatchlistFilter(" ")
                                setCountryWatchlistFilter(" ")
                                setMarketWatchlistFilter(" ")
                                if (response.body()!!.stock!!.size > 0) {
                                    list!!.clear()
                                    list!!.addAll(response.body()!!.stock!!);
                                }
                            } catch (e: java.lang.Exception) {

                            }
                        }
                        setWatchListAdapter();
                        d.dismiss()

                    } else if (response.body()!!.status == "2") {
                        d.dismiss()
                        appLogout()
                    }
                } else if (response.body()!!.status == "2") {
                    appLogout()
                } else {
                    displayToast(response.body()!!.message, "warning")
                }

            }

            override fun onFailure(call: Call<WatchlistPojo>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                Log.d("serach_error", "---" + t.localizedMessage);
                d.dismiss()
                displayToast(resources.getString(R.string.something_went_wrong), "error")
            }
        })
    }


    private fun getWatchList(firstTime: Int) {
        /*val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)*/
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<WatchlistPojo> =
            apiService.getWatchList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(), "", "", "", "",
                page.toString(),
                limit.toString()
            )
        call.enqueue(object : Callback<WatchlistPojo> {
            override fun onResponse(call: Call<WatchlistPojo>, response: Response<WatchlistPojo>) {
//                d.dismiss()
                if (response.body() != null) {
                    if (srl_layout != null)
                        srl_layout.isRefreshing = false
                    if (response.body()!!.status.equals("1")) {
                        try {
                            setAssetWatchlistFilter(" ")
                            setSectorWatchlistFilter(" ")
                            setCountryWatchlistFilter(" ")
                            setMarketWatchlistFilter(" ")
                        } catch (e: java.lang.Exception) {

                        }
                        if (firstTime == 0) {
                            list!!.clear()
                            list!!.addAll(response.body()!!.stock!!);
                        } else if (firstTime == 2) {
                            list!!.clear()
                            list!!.addAll(response.body()!!.stock!!);
                        } else {
                            if (response.body()!!.stock!!.size > 0) {
                                list!!.addAll(response.body()!!.stock!!);
                            } else {
                                displayToast("no more data", "warning")
                            }
                        }
                        setWatchListAdapter();
                        ll_search.visibility = View.VISIBLE;
                        ll_filter.visibility = View.VISIBLE;
                        ll_sort.visibility = View.VISIBLE;

                        /*  if (response.body()!!.stock!!.size == 0) {
                              list!!.clear()
                              setWatchListAdapter();*/
                    } else if (response.body()!!.status.equals("2")) {
                        appLogout();
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
//                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<WatchlistPojo>, t: Throwable) {
                if (srl_layout != null)
                    srl_layout.isRefreshing = false
                println(t.toString())
                Log.d("WatchList--", "" + t.localizedMessage)
                displayToast(resources.getString(R.string.something_went_wrong), "error")
//                d.dismiss()
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
                        page = 0
                        if (!TextUtils.isEmpty(et_search.text.toString()))
                            et_search.setText("")
                        getWatchList(2)

                    } else if (response.body()!!.status.equals("2")) {
                        appLogout();
                    } else {
                        displayToast(response.body()!!.message, "warning")
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                // Log.d("WatchList--", "" + t.localizedMessage)
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.RESULT_CODE_FILTER_WATCH) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                var flagreset = data.getStringExtra("resetStockfilter")
                if (flagreset.equals("0")) {
                    var testing = data.getSerializableExtra("stocklist") as ArrayList<WatchlistPojo.WatchStock>;
                    try {
                        if (testing.size > 0) {
                            Log.d("sdadada---Filter", "--" + testing.size)
                            list!!.clear()
                            list!!.addAll(testing)
                            //recyclerView_watch_list!!.adapter!!.notifyDataSetChanged();
                            setAdapter()
                        } else {
                            list!!.clear()
                            recyclerView_watch_list!!.adapter!!.notifyDataSetChanged();
                            AppDelegate.showAlert(
                                this@WatchListActivity,
                                "No result Found\n Please Reset Filter to Get Data"
                            )
                        }
                    } catch (e: Exception) {

                    }

                } else {
                    setAssetWatchlistFilter(" ")
                    setSectorWatchlistFilter(" ")
                    setCountryWatchlistFilter(" ")
                    setMarketWatchlistFilter(" ")
                    getWatchList(0)
                }
            }

        } else if (requestCode == StockConstant.RESULT_CODE_SORT_WATCH) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    flag = data.getStringExtra("flag");
                    if (flag.equals("high")) {
                        try {
                            var sortedList = list!!.sortedByDescending { it.latestPrice?.toDouble() }
                            list!!.clear();
                            // setWatchListAdapter();
                            list!!.addAll(sortedList);
                            //if (watchListAdapter != null)
                            //  watchListAdapter!!.notifyDataSetChanged()
                            setAdapter()
                        } catch (e: Exception) {

                        }
                    } else if (flag.equals("low")) {
                        try {
                            var sortedList = list!!.sortedWith(compareBy({ it.latestPrice!!.toDouble() }));
                            list!!.clear();
                            // setWatchListAdapter();
                            list!!.addAll(sortedList);
                            //if (watchListAdapter != null)
                            //  watchListAdapter!!.notifyDataSetChanged()
                            setAdapter()
                        } catch (e: Exception) {

                        }
                    } else if (flag.equals("daily")) {
                        try {
                            var sortedList = list!!.sortedByDescending { it.changePercent?.toDouble() }
                            list!!.clear();
                            //setWatchListAdapter();
                            list!!.addAll(sortedList);
                            //if (watchListAdapter != null)
                            //  watchListAdapter!!.notifyDataSetChanged()
                            setAdapter()
                        } catch (e: Exception) {

                        }
                    } else if (flag.equals("dailyLTH")) {
                        try {
                            var sortedList = list!!.sortedWith(compareBy({ it.changePercent!!.toDouble() }));
                            list!!.clear();
                            // setWatchListAdapter();
                            list!!.addAll(sortedList);
                            // if (watchListAdapter != null)
                            //   watchListAdapter!!.notifyDataSetChanged()
                            setAdapter()
                        } catch (e: Exception) {

                        }
                    } else if (flag.equals("nodata")) {
                        getWatchList(0)
                        recyclerView_watch_list!!.adapter!!.notifyDataSetChanged();
                    }
                }
            }
        }
    }
}
