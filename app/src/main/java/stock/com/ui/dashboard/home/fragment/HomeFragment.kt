package stock.com.ui.dashboard.home.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.home_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.dashboard.Lobby.LobbyFragment
import stock.com.ui.dashboard.home.ActivityNewsListing
import stock.com.ui.dashboard.home.adapter.*
import stock.com.ui.pojo.*
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import androidx.viewpager.widget.ViewPager


class HomeFragment : BaseFragment(), View.OnClickListener {

    var identifires: String = ""
    private var dashBoradACtivity: DashBoardActivity? = null;
    var dataExchange: ArrayList<String>? = null;
    var exchangeList: ArrayList<HomePojo.Exchange>? = null;
    var bannerAdapter: HorizontalPagerAdapter? = null
    var stockNameAdapter: StockNameAdapter? = null
    var bannerList: ArrayList<HomePojo.Banner>? = null;
    var newsStories: ArrayList<CityfalconNewsPojo.Story>? = null

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.seeall -> {
                startActivity(
                    Intent(context, ActivityNewsListing::class.java)
                        .putExtra(StockConstant.IDENTIFIRE, identifires)
                        .putExtra(StockConstant.NEWSLIST, newsStories)
                )

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ll_expandable_view.visibility = View.VISIBLE
        seeall.setOnClickListener(this)
        exchangeList = ArrayList()
        dataExchange =
            ArrayList()
        bannerList =
            ArrayList()
        newsStories = ArrayList()
        txt_title.visibility = GONE;
        setHomeBannerAdapter()
        setStockNameAdapter()
    }


    override fun onResume() {
        super.onResume()
        initViews()
    }

    private fun initViews() {
        setStockNameAdapter()
        getFeatureContentlist();
        getTrainingContentlist()
        getNewslist()

    }

    private fun setHomeBannerAdapter() {
        if (!isAdded)
            return
        bannerAdapter = HorizontalPagerAdapter(context, bannerList, this, getFromPrefsString(StockConstant.USERID))
        hicvp.adapter = bannerAdapter
    }


    private fun setStockNameAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView_stock_name!!.layoutManager = llm
        recyclerView_stock_name.visibility = View.VISIBLE

        stockNameAdapter = StockNameAdapter(context!!, exchangeList!!)
        recyclerView_stock_name!!.adapter = stockNameAdapter

        // call function news
        autoScrollNews(llm)
        recyclerView_stock_name.setItemAnimator(DefaultItemAnimator())
        recyclerView_stock_name!!.adapter!!.notifyDataSetChanged();

    }

    fun autoScrollNews(llm: LinearLayoutManager) {
        val handler = Handler()
        val runnable = object : Runnable {
            var count = 0
            var flag = true
            override fun run() {
                if (recyclerView_stock_name == null)
                    return
                if (count < recyclerView_stock_name!!.adapter!!.getItemCount()) {
                    if (count == recyclerView_stock_name!!.adapter!!.getItemCount() - 1) {
                        flag = false;
                    } else
                        if (count == 0) {
                            flag = true;
                        }
                    if (flag)
                        count++;
                    else
                        count--;
                    var visibleItemCount = recyclerView_stock_name.getChildCount();
                    var totalItemCount = llm.getItemCount();
//                    recyclerView_stock_name.smoothScrollToPosition(count);
                    var dx = count
                    recyclerView_stock_name.scrollBy(count, visibleItemCount + totalItemCount)
                    handler.postDelayed(this, 300);

                }
            }
        }
        handler.postDelayed(runnable, 100);

    }

    fun setintent() {
        dashBoradACtivity!!.changeFragment(LobbyFragment())
    }

    @SuppressLint("WrongConstant")
    private fun setFeatureContestAdapter(listItem: List<HomePojo.FeatureContest>) {
        viewPager_features.visibility = View.VISIBLE
        viewPager_features.setClipToPadding(false);
        viewPager_features.setPadding(30, 0, 30, 0);
        viewPager_features.setPageMargin(10);
        var adapter = ViewPagerFeature(context!!, listItem, getFromPrefsString(StockConstant.USERID).toString())
        viewPager_features.setAdapter(adapter)

        if (listItem.size > 5) {
            pageIndicatorView.visibility = VISIBLE
            // specify total count of indicators
            pageIndicatorView.setCount(5)
        } else if (listItem.size == 0) {
            pageIndicatorView.visibility = GONE
        } else {
            pageIndicatorView.visibility = VISIBLE
            // specify total count of indicators
            pageIndicatorView.setCount(listItem.size)
        }
        viewPager_features.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {/*empty*/
            }

            override fun onPageSelected(position: Int) {
                pageIndicatorView.selection = position
            }

            override fun onPageScrollStateChanged(state: Int) {/*empty*/
            }
        });

    }


    private fun setTrainingContestAdapter(traniningContest: List<TrainingPojo.TraniningContest>) {
        viewPager_training.visibility = View.VISIBLE
        viewPager_training.setClipToPadding(false);
        viewPager_training.setPadding(30, 0, 30, 0);
        viewPager_training.setPageMargin(10);

        val adapter =
            ViewPagerTraining(context!!, traniningContest, getFromPrefsString(StockConstant.USERID).toString())
        viewPager_training.setAdapter(adapter)

        if (traniningContest.size > 5) {
            pageIndicatorTraining.visibility = VISIBLE
            // specify total count of indicators
            pageIndicatorTraining.setCount(5)
        } else if (traniningContest.size == 0) {
            pageIndicatorTraining.visibility = GONE
        } else {
            pageIndicatorTraining.visibility = VISIBLE
            pageIndicatorTraining.setCount(traniningContest!!.size)
        }

        viewPager_training.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {/*empty*/
            }

            override fun onPageSelected(position: Int) {
                pageIndicatorTraining.selection = position
            }

            override fun onPageScrollStateChanged(state: Int) {/*empty*/
            }
        });


    }

    @SuppressLint("WrongConstant")
    private fun setLatestNewAdapter(news: ArrayList<CityfalconNewsPojo.Story>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_latest_new!!.layoutManager = llm
        recyclerView_latest_new.visibility = View.VISIBLE
        recyclerView_latest_new!!.adapter = LatestNewsAdapter(context!!, news, identifires);
        //recyclerView_latest_new.addItemDecoration(CirclePagerIndicatorDecoration(activity));
    }

    fun getFeatureContentlist() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<HomePojo> =
            apiService.getFeatureContentlist(/*getFromPrefsString(StockConstant.ACCESSTOKEN).toString()*/)
        call.enqueue(object : Callback<HomePojo> {
            override fun onResponse(call: Call<HomePojo>, response: Response<HomePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        bannerList!!.addAll(response.body()!!.banner!!)
                        exchangeList!!.addAll(response.body()!!.exchange!!)
                        /*setStockNameAdapter()
                        setHomeBannerAdapter()*/
                        if (bannerAdapter != null) {
                            bannerAdapter!!.notifyDataSetChanged()
                        }
                        exchangeList = response.body()!!.exchange!!
                        if (stockNameAdapter != null)
                            stockNameAdapter!!.notifyDataSetChanged();
                        setFeatureContestAdapter(response.body()!!.featureContest!!)

                        if (response.body()!!.exchange != null) {
                            for (i in 0 until response.body()!!.exchange!!.size)
                                dataExchange!!.add(response.body()!!.exchange!!.get(i).name)
                        }
                        setIdentifire()
                        setVisibility()
                        //  displayToast(response.body()!!.message)
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<HomePojo>, t: Throwable) {
//                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }


    fun setIdentifire() {
        identifires = android.text.TextUtils.join(",", dataExchange)
    }

    fun getTrainingContentlist() {
        /* val d = StockDialog.showLoading(activity!!)
         d.setCanceledOnTouchOutside(false)*/
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<TrainingPojo> =
            apiService.getTrainingContest()
        call.enqueue(object : Callback<TrainingPojo> {

            override fun onResponse(call: Call<TrainingPojo>, response: Response<TrainingPojo>) {
//                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        try {
                            setTrainingContestAdapter(response.body()!!.traniningContest!!)
                        } catch (e: Exception) {

                        }
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
//                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<TrainingPojo>, t: Throwable) {
                displayToast(resources.getString(R.string.something_went_wrong), "error")
//                d.dismiss()
            }
        })
    }

    val categories: String = "op"
    val topic: String = "stocks,indices,commodities,cryptocurrencies"
    fun getNewslist() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClientNews()!!.create(ApiInterface::class.java)
        val call: Call<CityfalconNewsPojo> =
            apiService.getHomeNews(
                topic,
                categories, "20",
                "top", "d1", false,
                StockConstant.NEWS_ACCESS_TOKEN
            )
        call.enqueue(object : Callback<CityfalconNewsPojo> {
            override fun onResponse(call: Call<CityfalconNewsPojo>, response: Response<CityfalconNewsPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    d.dismiss()
                    newsStories = response.body()!!.stories
                    setLatestNewAdapter(response.body()!!.stories)
                }
            }

            override fun onFailure(call: Call<CityfalconNewsPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }

        })
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
//                        setStockNameAdapter(response.body()!!.exchange)
                    } else {
                        displayToast(resources.getString(R.string.internal_server_error), "error")
                        d.dismiss()
                    }
                }
            }

            override fun onFailure(call: Call<ExchangeList>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    private fun setVisibility() {
        tv_tranning_contest.visibility = VISIBLE;
        txt_title.visibility = VISIBLE;
        tv_latest_new.visibility = VISIBLE;
        seeall.visibility = VISIBLE;
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is DashBoardActivity) {
            dashBoradACtivity = context as DashBoardActivity
        } else {
            throw ClassCastException(context.toString() + "must implement FragmentToHomeActivity")
        }
    }


}
