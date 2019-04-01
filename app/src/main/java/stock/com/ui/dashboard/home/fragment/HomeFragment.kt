package stock.com.ui.dashboard.home.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.home_expandable_layout.*
import kotlinx.android.synthetic.main.home_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.contest.activity.AllContestActivity
import stock.com.ui.dashboard.home.adapter.*
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.HomePojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.ViewAnimationUtils
import stock.com.utils.custom.CirclePagerIndicatorDecoration
import antonkozyriatskyi.circularprogressindicator.CircularProgressIndicator


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

        txt_title.visibility = GONE;

    }

    private fun setHomeBannerAdapter(listImage: List<HomePojo.Banner>) {
        if (!isAdded)
            return
        viewPager_Banner.adapter = SlidingImageAdapterHomeBanner(activity!!, listImage)
        viewPager_Banner.setClipToPadding(false);
        // viewPager_Banner.setPadding(0, 0, 50, 0)
        viewPager_Banner.setPageMargin(10);
//        pageIndicatorView.setViewPager(viewPager_Banner)
        viewPager_Banner.startAutoScroll()
        viewPager_Banner.isCycle = true

        tab_layout.setupWithViewPager(viewPager_Banner);

    }

    private fun clickPlusIcon(lin_child_title: LinearLayout, header_plus_icon: ImageView) {
        if (lin_child_title.visibility == View.GONE) {
            ViewAnimationUtils.expand(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowdown)
        } else {
            ViewAnimationUtils.collapse(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowright)
        }
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
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView_features!!.layoutManager = llm
        recyclerView_features.visibility = View.VISIBLE
        recyclerView_features!!.adapter = FeatureContestAdapter(context!!/*, listItem*/)
        recyclerView_features.addItemDecoration(CirclePagerIndicatorDecoration(activity));

        setStockNameAdapter();
        setContestAdapter();
        setLatestNewAdapter();
    }

    private fun setStockNameAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView_stock_name!!.layoutManager = llm
        recyclerView_stock_name.visibility = View.VISIBLE
        recyclerView_stock_name!!.adapter = StockNameAdapter(context!!/*, listItem*/)
        //recyclerView_stock_name.addItemDecoration(CirclePagerIndicatorDecoration(activity))


    }

    private fun setContestAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        recyclerView_tranning_contest!!.layoutManager = llm
        recyclerView_tranning_contest.visibility = View.VISIBLE
        recyclerView_tranning_contest!!.adapter = TranningContestAdapter(context!!/*, listItem*/)
        recyclerView_tranning_contest.addItemDecoration(CirclePagerIndicatorDecoration(activity))
    }

    @SuppressLint("WrongConstant")
    private fun setLatestNewAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_latest_new!!.layoutManager = llm
        recyclerView_latest_new.visibility = View.VISIBLE
        recyclerView_latest_new!!.adapter = LatestNewsAdapter(context!!/*, listItem*/);
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
            apiService.getFeatureContentlist(getFromPrefsString(StockConstant.ACCESSTOKEN).toString())
        call.enqueue(object : Callback<HomePojo> {
            override fun onResponse(call: Call<HomePojo>, response: Response<HomePojo>) {
                d.dismiss()
                if (response?.body() != null) {
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

    private fun setVisibility() {
        tv_tranning_contest.visibility = VISIBLE;
        tv_latest_new.visibility = VISIBLE;
        txt_title.visibility = VISIBLE;
    }


}