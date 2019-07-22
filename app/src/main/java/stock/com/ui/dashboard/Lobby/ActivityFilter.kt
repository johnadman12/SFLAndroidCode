package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_filter.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.FilterPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.ViewAnimationUtils
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener
import android.widget.RelativeLayout
import android.util.Log
import android.widget.Toast
import kotlin.collections.ArrayList
import android.content.Intent
import android.content.SharedPreferences
import android.text.TextUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import stock.com.ui.pojo.Country
import stock.com.ui.pojo.LobbyContestPojo
import stock.com.utils.SessionManager
import java.lang.reflect.Type


class ActivityFilter : BaseActivity(), View.OnClickListener {
    private var countryAdapter: CountryListAdapter? = null;
    private var contestListAdapter: ContestListAdapter? = null;
    private var marketListAdapter: MarketListAdapter? = null;

    var maxprice: String = "";
    var canSelect: String = "";
    private var contestTypeFilter: String? = "";
    private var marketTypeFilter: String? = "";
    private var countryTypeFilter: String? = "";

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.llCountry -> {
                rel_market.visibility = GONE
                rel_contesttype.visibility = GONE
                relative_range.visibility = GONE
                clickPlusIcon(recycle_country, ivCountry, rel_country)
            }
            R.id.reset -> {
                val resultIntent = Intent()
                resultIntent.putExtra("resetfilter", "1")
                setResult(Activity.RESULT_OK, resultIntent)
                finish()

            }
            R.id.llMarket -> {
                rel_country.visibility = GONE
                clickPlusIcon(recycle_market, ivMarket, rel_market)
            }
            R.id.llContest -> {
                rel_country.visibility = GONE
                clickPlusIcon(recycle_contest, ivContest, rel_contesttype)
            }
            R.id.llEntry -> {
                rel_country.visibility = GONE
                clickPlusRange(relative_range, ivEntry)
            }

            R.id.img_btn_close -> {
                finish()
            }
            R.id.img_btn_back -> {
                finish()
            }
            R.id.btn_apply -> {
                if (maxprice.toString().equals(canSelect.toString())) {
                    maxprice = ""
                } else {
                    maxprice = tvMax.text.toString();
                }

                if (countryAdapter != null) {
//                    selectedCountry= countryAdapter!!.getSeletedtIds()
                    setCountryContest(countryAdapter!!.getSeletedtIds());
                }
                if (marketListAdapter != null) {
                    //selectedMarket=marketListAdapter!!.getSeletedtIds();
                    setMarketContest(marketListAdapter!!.getSeletedtIds())

                }
                if (contestListAdapter != null) {
                    //selectedConstent =contestListAdapter!!.getSeletedtIds();
                    setContestType(contestListAdapter!!.getSeletedtIds());
                }
                setFilters()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        StockConstant.ACTIVITIES.add(this)
        initViews()
        contestTypeFilter = getFromPrefsString(StockConstant.CONTEST_TYPE);
        marketTypeFilter = getFromPrefsString(StockConstant.MARKET_TYPE);
        countryTypeFilter = getFromPrefsString(StockConstant.COUNTRY_TYPE);

    }

    fun setFilters() {
          countryTypeFilter = getFromPrefsString(StockConstant.COUNTRY_TYPE);
          contestTypeFilter = getFromPrefsString(StockConstant.CONTEST_TYPE);
          marketTypeFilter = getFromPrefsString(StockConstant.MARKET_TYPE);

        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<LobbyContestPojo> =
            apiService.setContestFilter(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                contestTypeFilter!!,
                marketTypeFilter!!,
                countryTypeFilter!!,
                tvMin.text.toString(),
                maxprice
            )
        call.enqueue(object : Callback<LobbyContestPojo> {
            override fun onResponse(call: Call<LobbyContestPojo>, response: Response<LobbyContestPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        var testing = ArrayList<LobbyContestPojo.Contest>()
                        testing = response.body()!!.contest
                        Log.e("nckshbj", testing.size.toString())

                        if (testing != null /*&& testing.size != 0*/) {
                            val resultIntent = Intent()
                            resultIntent.putExtra("contestlist", testing)
                            resultIntent.putExtra("resetfilter", "0")
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        } else if (response.body()!!.status == "2") {
                            appLogout();
                        } else {
                            displayToast("no data exist", "warning")
                            finish()
                        }
                    }
                } else {
                    displayToast(resources.getString(stock.com.R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<LobbyContestPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    private fun initViews() {
        img_btn_back.setOnClickListener(this)
        reset.setOnClickListener(this)
        reset.visibility = VISIBLE
        llMarket.setOnClickListener(this)
        llCountry.setOnClickListener(this)
        llContest.setOnClickListener(this)
        llEntry.setOnClickListener(this)
        btn_apply.setOnClickListener(this)
        getFilterlist()
        val country: Country =
            Gson().fromJson(
                SessionManager.getInstance(this@ActivityFilter).getString(StockConstant.COUNTRYLIST),
                Country::class.java
            )
        Log.e("json", country.country.toString())
        if (country != null)
            setCountryAdapter(country)
        llContest.performClick()
        rangeSeekbar1.setOnRangeSeekbarChangeListener(OnRangeSeekbarChangeListener { minValue, maxValue ->
            tvMin.setText(minValue.toString())
            tvMax.setText(maxValue.toString())
            maxprice = maxValue.toString()


        })
        rangeSeekbar1.setOnRangeSeekbarFinalValueListener({ minValue, maxValue ->
            Log.d(
                "CRS=>",
                "$minValue : $maxValue"
            )
        })
    }

    fun getFilterlist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<FilterPojo> =
            apiService.getFilterList(getFromPrefsString(StockConstant.USERID).toString())
        call.enqueue(object : Callback<FilterPojo> {

            override fun onResponse(call: Call<FilterPojo>, response: Response<FilterPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        setMarketAdapter(response.body()!!.market)
                        setContestAdapter(response.body()!!.category!!)
                        setRangebar(/*response.body()!!.entryFees*/)
                    }
                } else {
                    displayToast(response.body()!!.message, "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<FilterPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }


    fun setRangebar(/*entryFees: List<FilterPojo.EntryFee>?*/) {
        var minValue = 1.0f
        var maxValue = 3000.0f
//        if (entryFees != null) {
        rangeSeekbar1.setMinValue(minValue)
        rangeSeekbar1.setMaxValue(maxValue)
        tvMin.setText(minValue.toString() + "$")
        tvMax.setText(maxValue.toString() + "$")

        maxprice = minValue.toString()
        canSelect = maxValue.toString()
//        }
    }

    @SuppressLint("WrongConstant")
    private fun setCountryAdapter(country: Country) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_country!!.layoutManager = llm
        countryAdapter = CountryListAdapter(this, country, countryTypeFilter!!)
        recycle_country!!.adapter = countryAdapter;

        /*recycle_country!!.adapter = CountryListAdapter(this, country, countryTypeFilter!!, object : CountryListAdapter.OnItemCheckListener {
                override fun onItemUncheck(item: String) {
                    countrySelectedItems!!.remove(item);
                    setCountryContest(android.text.TextUtils.join(",", countrySelectedItems));
                }

                override fun onItemCheck(item: String) {
                    countrySelectedItems!!.add(item);
                    setCountryContest(android.text.TextUtils.join(",", countrySelectedItems));
                    Log.e("value", item)
                }
            })*/
    }

    @SuppressLint("WrongConstant")
    private fun setMarketAdapter(market: List<FilterPojo.Market>?) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_market!!.layoutManager = llm
        marketListAdapter = MarketListAdapter(this, market!!, marketTypeFilter!!)
        recycle_market!!.adapter = marketListAdapter
    }


    @SuppressLint("WrongConstant")
    private fun setContestAdapter(contest: List<FilterPojo.Category>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_contest!!.layoutManager = llm
        contestListAdapter = ContestListAdapter(this, contest, contestTypeFilter!!)
        recycle_contest!!.adapter = contestListAdapter

    }

    private fun clickPlusIcon(
        lin_child_title: RecyclerView,
        header_plus_icon: ImageView,
        perspectiveLay: RelativeLayout
    ) {
        if (lin_child_title.visibility == View.GONE) {
            ViewAnimationUtils.expand(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowdown)
            lin_child_title.visibility = VISIBLE
            perspectiveLay.visibility = VISIBLE
        } else {
            ViewAnimationUtils.collapse(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowright)
            lin_child_title.visibility = GONE
            perspectiveLay.visibility = GONE
        }
    }

    private fun clickPlusRange(range: RelativeLayout, header_plus_icon: ImageView) {
        if (range.visibility == View.GONE) {
            ViewAnimationUtils.expand(range)
            header_plus_icon.setImageResource(R.mipmap.arrowdown)
            range.visibility = VISIBLE
        } else {
            ViewAnimationUtils.collapse(range)
            header_plus_icon.setImageResource(R.mipmap.arrowright)
            range.visibility = GONE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}