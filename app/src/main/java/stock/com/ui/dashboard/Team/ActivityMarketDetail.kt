package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_stock_detail_page.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.Team.Stock.*
import stock.com.ui.pojo.AssestData
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.MarketList
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class ActivityMarketDetail : BaseActivity(), View.OnClickListener {
    private var list: ArrayList<MarketList.Crypto>? = null;
    private var selectedItems: Int = 0
    private var fragment: Fragment? = null;
    var marketId: Int = 0
    var position: Int = -1
    var flagData: Int = 0
    var symbol: String = ""
    var slug: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_detail)
        list = ArrayList()
        StockConstant.ACTIVITIES.add(this)
        if (intent != null) {
            marketId = intent.getIntExtra("cryptoId", 0);
            flagData = intent.getIntExtra("flag", 0);
            if (flagData == 1) {
                list = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)
                selectedItems = intent.getIntExtra(StockConstant.SELECTEDSTOCK, 0)
            }
        }
        getData(marketId.toString())

        if (flagData == 1) {
            if (list != null) {
                if (list!!.size > 0)
                    for (i in 0 until list!!.size) {
                        try {
                            if (marketId.equals(list!!.get(i).cryptocurrencyid))
                                position = i
//                            symbol = list!!.get(position).name!!
                        } catch (e: Exception) {

                        }

                    }
            }
        } else {
            ivTeam.visibility = GONE
        }


        ll_news.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);
        ll_data.setOnClickListener(this);
        ll_chart.setOnClickListener(this);
        ll_analystics.setOnClickListener(this);
        ll_comments.setOnClickListener(this);
        ivTeam.setOnClickListener(this);
        ivWatchlist.setOnClickListener(this);

    }

    fun getData(assestId: String) {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<AssestData> =
            apiService.getAssestData(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                assestId, "crypto"
            )
        call.enqueue(object : Callback<AssestData> {
            override fun onResponse(call: Call<AssestData>, response: Response<AssestData>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        symbol = response.body()!!.stock!!.get(0).symbol
                        slug = response.body()!!.stock!!.get(0).slug
                        /*  val fragment: ChartFragment = ChartFragment()
                          var nd: Bundle = Bundle()
                          nd.putString("Stockname", symbol)
                          setFragment(fragment, nd);*/

                        val fragment: DataFragment = DataFragment()
                        var nd: Bundle = Bundle()
                        nd.putInt(StockConstant.MARKETID, marketId)
                        nd.putString(StockConstant.MARKET_TYPE, "crypto")
                        setFragment(fragment, nd);

                        setStockData(response.body()!!.stock)
                        d.dismiss()

                    } else if (response.body()!!.status == "0") {
                        displayToast(response.body()!!.message, "error")
                    }
                }
            }

            override fun onFailure(call: Call<AssestData>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_btn_back -> {
                onBackPressed()
            }
            R.id.ivWatchlist -> {
                saveToWatchList()
            }

            R.id.ivTeam -> {
                if (list != null)
                    if (list!!.get(position).addedToList == 1) {
                        //show green button
                        AppDelegate.showAlert(this, "already added to Crypto")
                    } else if (list!!.get(position).addedToList == 0) {
                        if (selectedItems >= 12) {
                            displayToast("You have selected maximum number of Crypto for your team.", "error")
                        } else {
                            list!!.get(position).addedToList = 1
                            //show red button
                            AppDelegate.showAlert(this, "added to Crypto")
                            var intent = Intent();
                            intent.putExtra("list", list)
                            setResult(Activity.RESULT_OK, intent);
                            finish();

                        }
                    }

                Log.e("updatedlist", list!!.get(position).addedToList.toString())
            }

            R.id.ll_chart -> {
                if (fragment is ChartFragment)
                    return;
                val fragment: ChartFragment = ChartFragment()
                var nd: Bundle = Bundle()
                if (list != null)
                    nd.putString("Stockname", slug)
                else
                    nd.putString("Stockname", "")
                nd.putInt(StockConstant.MARKETID, 7)// for show chart its fix
                setFragment(fragment, nd);
                setLinearLayoutColor(ll_news, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_chart, ContextCompat.getColor(this, R.color.colorbutton))
                setLinearLayoutColor(ll_analystics, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_comments, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_data, ContextCompat.getColor(this, R.color.white));

                setTextViewColor(tv_news, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_data, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_chart, ContextCompat.getColor(this, R.color.white));
                setTextViewColor(tv_analystics, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_comments, ContextCompat.getColor(this, R.color.textColorLightBlack));

                img_btn_news.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_data.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_chart.setColorFilter(ContextCompat.getColor(this, R.color.white));
                img_btn_analystics.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_comments.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));

            }

            R.id.ll_news -> {
                if (fragment is NewsFragment)
                    return;
                val fragment: NewsFragment = NewsFragment()
                var nd: Bundle = Bundle()
                if (list != null)
                    nd.putString("Stockname", symbol)
                else
                    nd.putString("Stockname", "")
                nd.putString(StockConstant.IDENTIFIRE, "tickers")
                setFragment(fragment, nd);
                setLinearLayoutColor(ll_news, ContextCompat.getColor(this, R.color.colorbutton));
                setLinearLayoutColor(ll_chart, ContextCompat.getColor(this, R.color.white))
                setLinearLayoutColor(ll_analystics, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_comments, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_data, ContextCompat.getColor(this, R.color.white));

                setTextViewColor(tv_news, ContextCompat.getColor(this, R.color.white));
                setTextViewColor(tv_data, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_chart, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_analystics, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_comments, ContextCompat.getColor(this, R.color.textColorLightBlack));

                img_btn_news.setColorFilter(ContextCompat.getColor(this, R.color.white));
                img_btn_data.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_chart.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_analystics.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_comments.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
            }

            R.id.ll_analystics -> {
                if (fragment is AnalysticFragment)
                    return;
                setFragment(AnalysticFragment(), Bundle());
                setLinearLayoutColor(ll_news, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_chart, ContextCompat.getColor(this, R.color.white))
                setLinearLayoutColor(ll_analystics, ContextCompat.getColor(this, R.color.colorbutton));
                setLinearLayoutColor(ll_comments, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_data, ContextCompat.getColor(this, R.color.white));

                setTextViewColor(tv_news, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_data, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_chart, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_analystics, ContextCompat.getColor(this, R.color.white));
                setTextViewColor(tv_comments, ContextCompat.getColor(this, R.color.textColorLightBlack));

                img_btn_news.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_data.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_chart.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_analystics.setColorFilter(ContextCompat.getColor(this, R.color.white));
                img_btn_comments.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
            }
            R.id.ll_data -> {
                if (fragment is DataFragment)
                    return;
                val fragment: DataFragment = DataFragment()
                var nd: Bundle = Bundle()
                nd.putInt(StockConstant.MARKETID, marketId)
                nd.putString(StockConstant.MARKET_TYPE, "crypto")
                setFragment(fragment, nd);
                setLinearLayoutColor(ll_news, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_chart, ContextCompat.getColor(this, R.color.white))
                setLinearLayoutColor(ll_analystics, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_comments, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_data, ContextCompat.getColor(this, R.color.colorbutton));

                setTextViewColor(tv_news, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_data, ContextCompat.getColor(this, R.color.white));
                setTextViewColor(tv_chart, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_analystics, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_comments, ContextCompat.getColor(this, R.color.textColorLightBlack));

                img_btn_news.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_data.setColorFilter(ContextCompat.getColor(this, R.color.white));
                img_btn_chart.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_analystics.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_comments.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
            }
            R.id.ll_comments -> {
                if (fragment is CommentsFragment)
                    return;
                val fragment1: CommentsFragment = CommentsFragment()
                var nd: Bundle = Bundle()
                nd.putString(StockConstant.STOCKID, marketId.toString())
                nd.putString(StockConstant.CONTEST_TYPE, "crypto")
                setFragment(fragment1, nd);
                setLinearLayoutColor(ll_news, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_chart, ContextCompat.getColor(this, R.color.white))
                setLinearLayoutColor(ll_analystics, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_comments, ContextCompat.getColor(this, R.color.colorbutton));
                setLinearLayoutColor(ll_data, ContextCompat.getColor(this, R.color.white));

                setTextViewColor(tv_news, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_data, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_chart, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_analystics, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_comments, ContextCompat.getColor(this, R.color.white));

                img_btn_news.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_data.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_chart.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_analystics.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_comments.setColorFilter(ContextCompat.getColor(this, R.color.white));
            }

        }
    }

    private fun setTextViewColor(tv: TextView, color: Int) {
        tv.setTextColor(color);
    }

    fun setStockData(stock: ArrayList<AssestData.Stock>?) {
        stock_name.setText(stock!!.get(0).symbol)
        tv_stockcomp.setText(stock.get(0).companyName)
        tvStockPercentage.setText(stock.get(0).changePercent)
        tvVol.setText(stock.get(0).hVolume)
        tvlatestPrice.setText(stock.get(0).latestPrice)
        Glide.with(this).load(stock.get(0).image).into(iv_stock_img)
        if (!TextUtils.isEmpty(stock.get(0).changePercent))
            if (stock.get(0).changePercent.contains("-")) {
                Glide.with(this).load(R.drawable.ic_down_arrow).into(stockgraph)
                tvStockPercentage.setTextColor(ContextCompat.getColor(this, R.color.colorRed))
            } else {
                Glide.with(this).load(R.drawable.ic_arrow_up).into(stockgraph)
                tvStockPercentage.setTextColor(ContextCompat.getColor(this, R.color.colorRed))
            }
    }


    fun saveToWatchList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> =
            apiService.addCurrencyToWatch(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                marketId,
                getFromPrefsString(StockConstant.USERID).toString(), "crypto"
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        AppDelegate.showAlert(this@ActivityMarketDetail, response.body()!!.message)
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    } else {
                        displayToast(response.body()!!.message, "warning")

                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    private fun setLinearLayoutColor(ll: LinearLayoutCompat, color: Int) {
        ll.setBackgroundColor(color);
    }

    fun setFragment(fragment: Fragment, bundle: Bundle) {
        this.fragment = fragment;
        fragment.arguments = bundle;
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
//            .setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out)
            .replace(R.id.container1, fragment)
            .commitAllowingStateLoss()
    }
}