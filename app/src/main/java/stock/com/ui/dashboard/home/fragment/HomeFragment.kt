package stock.com.ui.dashboard.home.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.home_fragment.*
import kotlinx.android.synthetic.main.item_my_team.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.home.adapter.*
import stock.com.ui.pojo.ExchangeList
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.NewsPojo
import stock.com.ui.pojo.TrainingPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.custom.CirclePagerIndicatorDecoration


class HomeFragment : BaseFragment(), View.OnClickListener {


    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.txt_Fixtures -> matchSelector(FIXTURES)
            R.id.txt_Live -> matchSelector(LIVE)
            R.id.txt_Results -> matchSelector(RESULTS)
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
        matchSelector(FIXTURES)
        txt_Fixtures.setOnClickListener(this)
        txt_Live.setOnClickListener(this)
        txt_Results.setOnClickListener(this)
        getFeatureContentlist();
        getTrainingContentlist()
        getExchangeNamelist()
        txt_title.visibility = GONE;
    }

    private fun setHomeBannerAdapter(listImage: List<HomePojo.Banner>) {
        if (!isAdded)
            return
        viewPager_Banner.adapter = SlidingImageAdapterHomeBanner(activity!!, listImage)
        viewPager_Banner.setClipToPadding(false);
        viewPager_Banner.setPageMargin(10);
        viewPager_Banner.startAutoScroll()
        viewPager_Banner.isCycle = true

        viewPager_Banner.setClipToPadding(false);
        viewPager_Banner.setPadding(40, 0, 40, 0);
        viewPager_Banner.setPageMargin(20);

        tab_layout.visibility= VISIBLE;
        tab_layout.setupWithViewPager(viewPager_Banner);

    }


    private var LIVE = 1
    private var FIXTURES = 2
    private var RESULTS = 3

    fun matchSelector(value: Int) {
        txt_Fixtures.isSelected = false
        txt_Live.isSelected = false
        txt_Results.isSelected = false
        view1.visibility = View.VISIBLE
        view2.visibility = View.VISIBLE
        when (value) {
            LIVE -> {
                txt_title.visibility = GONE
                txt_Live.isSelected = true
                view1.visibility = View.GONE
                view2.visibility = View.GONE
                setLiveAdapter()
            }
            FIXTURES -> {
                txt_title.visibility = VISIBLE
                txt_title.setText(R.string.featured_contest)
                txt_Fixtures.isSelected = true
                view1.visibility = View.GONE
                setFixturesAdapter()
            }
            RESULTS -> {
                txt_title.visibility = GONE
                txt_title.setText(R.string.results)
                txt_Results.isSelected = true
                view2.visibility = View.GONE
                setCompletedAdapter()
            }
        }
    }

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

        tab_layout_features.visibility= VISIBLE;
        tab_layout_features.setupWithViewPager(viewPager_features);



    }

    private fun setStockNameAdapter(exchangeList: List<ExchangeList.Exchange>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView_stock_name!!.layoutManager = llm
        recyclerView_stock_name.visibility = View.VISIBLE
        recyclerView_stock_name!!.adapter = StockNameAdapter(context!!, exchangeList)
        //recyclerView_stock_name.addItemDecoration(CirclePagerIndicatorDecoration(activity))
    }

    private fun setTrainingContestAdapter(traniningContest: List<TrainingPojo.TraniningContest>) {
        /*val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView_tranning_contest!!.layoutManager = llm
        recyclerView_tranning_contest.visibility = View.VISIBLE
        recyclerView_tranning_contest!!.adapter = TranningContestAdapter(context!!, traniningContest)
        recyclerView_tranning_contest.addItemDecoration(CirclePagerIndicatorDecoration(activity))*/


        viewPager_training.visibility = View.VISIBLE
        viewPager_training.setClipToPadding(false);
        viewPager_training.setPadding(30, 0, 30, 0);
        viewPager_training.setPageMargin(10);

        val adapter = ViewPagerTraining(context!!,traniningContest)
        viewPager_training.setAdapter(adapter)

        tab_layout_training.visibility= VISIBLE;
        tab_layout_training.setupWithViewPager(viewPager_training);

    }

    @SuppressLint("WrongConstant")
    private fun setLatestNewAdapter(news: List<NewsPojo.News>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_latest_new!!.layoutManager = llm
        recyclerView_latest_new.visibility = View.VISIBLE
        recyclerView_latest_new!!.adapter = LatestNewsAdapter(context!!, news);
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
                        Handler().postDelayed(Runnable {
                            setHomeBannerAdapter(response.body()!!.banner!!)
                        }, 100)
                        setFeatureContestAdapter(response.body()!!.featureContest!!)

                        setVisibility()
                        //  displayToast(response.body()!!.message)
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<HomePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
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
                        tv_tranning_contest.visibility = VISIBLE;
                        getLatestNewslist();
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<TrainingPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
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
                        tv_latest_new.visibility = VISIBLE;
                        setLatestNewAdapter(response.body()!!.news)
                    }else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                    else {
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
                        setStockNameAdapter(response.body()!!.exchange)
                    } else {
                        displayToast(resources.getString(R.string.internal_server_error))
                        d.dismiss()
                    }
                }
            }

            override fun onFailure(call: Call<ExchangeList>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    private fun setVisibility() {

        txt_title.visibility = VISIBLE;
    }
}
