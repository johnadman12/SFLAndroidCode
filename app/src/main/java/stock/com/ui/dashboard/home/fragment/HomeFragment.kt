package stock.com.ui.dashboard.home.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
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


class HomeFragment : BaseFragment(), View.OnClickListener {

    var identifires: String = ""
    private var dashBoradACtivity: DashBoardActivity? = null;
    var dataExchange: ArrayList<String>? = null;
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
        initViews()
    }

    private fun initViews() {
        ll_expandable_view.visibility = View.VISIBLE
        ll_matchSelector.visibility = View.GONE
        txt_Fixtures.setOnClickListener(this)
        txt_Live.setOnClickListener(this)
        txt_Results.setOnClickListener(this)
        seeall.setOnClickListener(this)
        getFeatureContentlist();
        dataExchange = ArrayList()
        newsStories = ArrayList()
        txt_title.visibility = GONE;
    }

    private fun setHomeBannerAdapter(listImage: List<HomePojo.Banner>) {
        if (!isAdded)
            return
        /*  viewPager_Banner.adapter = SlidingImageAdapterHomeBanner(activity!!, listImage)
          viewPager_Banner.setClipToPadding(false);
          viewPager_Banner.setPageMargin(10);
          viewPager_Banner.startAutoScroll()
          viewPager_Banner.isCycle = true

          viewPager_Banner.setClipToPadding(false);
          viewPager_Banner.setPadding(40, 0, 40, 0);
          viewPager_Banner.setPageMargin(20);

          tab_layout.visibility = VISIBLE;
          tab_layout.setupWithViewPager(viewPager_Banner);*/
        hicvp.adapter = HorizontalPagerAdapter(context, listImage!!, this)
    }


    fun setintent() {
        dashBoradACtivity!!.changeFragment(LobbyFragment())
    }

    private var LIVE = 1
    private var FIXTURES = 2
    private var RESULTS = 3


    @SuppressLint("WrongConstant")
    private fun setFeatureContestAdapter(listItem: List<HomePojo.FeatureContest>) {
        /* val llm = LinearLayoutManager(context)
         llm.orientation = LinearLayoutManager.HORIZONTAL
         recyclerView_features!!.layoutManager = llm
         recyclerView_features.visibility = View.VISIBLE
         recyclerView_features!!.adapter = FeatureContestAdapter(context!!, listItem)
         recyclerView_features.addItemDecoration(CirclePagerIndicatorDecoration(activity));*/
        viewPager_features.visibility = View.VISIBLE
        viewPager_features.setClipToPadding(false);
        viewPager_features.setPadding(30, 0, 30, 0);
        viewPager_features.setPageMargin(10);


        val adapter = ViewPagerFeature(context!!, listItem)
        viewPager_features.setAdapter(adapter)

        tab_layout_features.visibility = VISIBLE;
        tab_layout_features.setupWithViewPager(viewPager_features);


    }

    private fun setStockNameAdapter(exchangeList: List<HomePojo.Exchange>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView_stock_name!!.layoutManager = llm
        recyclerView_stock_name.visibility = View.VISIBLE
        recyclerView_stock_name!!.adapter = StockNameAdapter(context!!, exchangeList)
        //recyclerView_stock_name.addItemDecoration(CirclePagerIndicatorDecoration(activity))
    }

    private fun setTrainingContestAdapter(traniningContest: List<TrainingPojo.TraniningContest>) {
        viewPager_training.visibility = View.VISIBLE
        viewPager_training.setClipToPadding(false);
        viewPager_training.setPadding(30, 0, 30, 0);
        viewPager_training.setPageMargin(10);

        val adapter = ViewPagerTraining(context!!, traniningContest)
        viewPager_training.setAdapter(adapter)

        tab_layout_training.visibility = VISIBLE;
        tab_layout_training.setupWithViewPager(viewPager_training);

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

    @SuppressLint("WrongConstant")
    private fun setFixturesAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_Match!!.layoutManager = llm
        recyclerView_Match!!.adapter = MatchFixturesAdapter(context!!)
    }

    @SuppressLint("WrongConstant")
    private fun setLiveAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_Match!!.layoutManager = llm
        recyclerView_Match!!.adapter = MatchLiveAdapter(context!!)
    }

    @SuppressLint("WrongConstant")
    private fun setCompletedAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_Match!!.layoutManager = llm
        recyclerView_Match!!.adapter = MatchCompletedAdapter(context!!)
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
//                        progressBar.visibility = GONE
                        setHomeBannerAdapter(response.body()!!.banner!!)
                        setFeatureContestAdapter(response.body()!!.featureContest!!)

                        setStockNameAdapter(response.body()!!.exchange!!)

                        if (response.body()!!.exchange != null) {
                            for (i in 0 until response.body()!!.exchange!!.size)
                                dataExchange!!.add(response.body()!!.exchange!!.get(i).name)
                        }
                        setIdentifire()
                        getTrainingContentlist()
                        getNewslist()
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
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }


    fun setIdentifire() {
        identifires = android.text.TextUtils.join(",", dataExchange)
    }

    fun getTrainingContentlist() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<TrainingPojo> =
            apiService.getTrainingContest()
        call.enqueue(object : Callback<TrainingPojo> {

            override fun onResponse(call: Call<TrainingPojo>, response: Response<TrainingPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        setTrainingContestAdapter(response.body()!!.traniningContest!!)
//                        getLatestNewslist();
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error),"error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<TrainingPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong),"error")
                d.dismiss()
            }
        })
    }

    val categories: String = "op"

    fun getNewslist() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClientNews()!!.create(ApiInterface::class.java)
        val call: Call<CityfalconNewsPojo> =
            apiService.getNewsHome(
                "tickers",
                identifires, categories, "20",
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
                displayToast(resources.getString(R.string.something_went_wrong),"error")
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
                        displayToast(resources.getString(R.string.internal_server_error),"error")
                        d.dismiss()
                    }
                }
            }

            override fun onFailure(call: Call<ExchangeList>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong),"error")
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
