package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_market_type_filter.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.Lobby.CountryListAdapter
import stock.com.ui.watch_list.adapter.MarketAdapter
import stock.com.ui.pojo.Country
import stock.com.ui.pojo.MarketTypeFilters
import stock.com.ui.pojo.WatchlistPojo
import stock.com.utils.SessionManager
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.ViewAnimationUtils

class ActivityMarketTypeFilter : BaseActivity(), View.OnClickListener {
    private var countrySelectedItems: ArrayList<String>? = null
    private var marketSelectedItems: ArrayList<String>? = null
    private var sectorSelectedItems: ArrayList<String>? = null

    private var activeCurrencyFilter: String? = "";
    private var sectorTypeFilter: String? = "";
    private var marketTypeFilter: String? = "";
    private var countryTypeFilter: String? = "";
    var isMarket: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market_type_filter)
        reset.visibility = View.VISIBLE;
        countrySelectedItems = ArrayList()
        marketSelectedItems = ArrayList()
        sectorSelectedItems = ArrayList()
        if (intent != null)
            isMarket = intent.getIntExtra("isMarket", 0);

        if (isMarket == 0) {
            ll_active_currency.visibility = VISIBLE
            ll_country_list.visibility = GONE
            ll_sector.visibility = GONE
            llMarket.visibility = GONE
            activeCurrencyFilter = getFromPrefsString(StockConstant.ACTIVE_CURRENCY_TYPE);
            if (activeCurrencyFilter!!.equals("0"))
                checkBoxActive.isChecked = true
        } else {
            ll_active_currency.visibility = GONE
            ll_country_list.visibility = VISIBLE
            ll_sector.visibility = VISIBLE
            llMarket.visibility = VISIBLE
        }
        llMarket.setOnClickListener(this);
        ll_country_list.setOnClickListener(this);
        ll_sector.setOnClickListener(this);

        img_btn_back.setOnClickListener {
            onBackPressed()
        }
        val country: Country =
            Gson().fromJson(
                SessionManager.getInstance(this@ActivityMarketTypeFilter).getString(StockConstant.COUNTRYLIST),
                Country::class.java
            )
        Log.e("json", country.country.toString())
        setCountryAdapter(country)

        img_btn_close.setOnClickListener {
            finish()
        }

        reset.setOnClickListener {
            if (isMarket == 0) {
                setActiveCurrencyType("1")
                val resultIntent = Intent()
                resultIntent.putExtra("resetfiltermarket", "1")
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                setSectorWatchlistFilter(" ")
                setCountryWatchlistFilter(" ")
                setMarketWatchlistFilter(" ")
                val resultIntent = Intent()
                resultIntent.putExtra("resetfiltermarket", "2")
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
        sectorTypeFilter = getFromPrefsString(StockConstant.SECTOR_WATCHLIST_TYPE);
        marketTypeFilter = getFromPrefsString(StockConstant.MARKET_WATCHLIST_TYPE);
        countryTypeFilter = getFromPrefsString(StockConstant.COUNTRY_WATCHLIST_TYPE);

        getFilterList();

        btn_apply.setOnClickListener {
            if (isMarket == 0) {
                if (checkBoxActive.isChecked) {
                    setActiveCurrencyType("0")
                    val resultIntent = Intent()
                    resultIntent.putExtra("resetfiltermarket", "0")
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                }
            } else {
                val selectedSector: String = android.text.TextUtils.join(",", sectorSelectedItems)
                val selectedExchange: String = android.text.TextUtils.join(",", marketSelectedItems)
                val selectedCountry: String = android.text.TextUtils.join(",", countrySelectedItems)
                Log.e("sectorlist", selectedSector)
                var resultIntent = Intent()
                resultIntent.putExtra("sectorlist", selectedSector)
                resultIntent.putExtra("exchangelist", selectedExchange)
                resultIntent.putExtra("countrylist", selectedCountry)
                resultIntent.putExtra("resetfiltermarket", "3")
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    fun getFilterList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<MarketTypeFilters> =
            apiService.getMarketTypeFilter(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<MarketTypeFilters> {
            override fun onResponse(call: Call<MarketTypeFilters>, response: Response<MarketTypeFilters>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        ll_main_filter.visibility = View.VISIBLE;
                        setMarketAdapter(response.body()!!.stocks.exchanges)
                        setSectorAdapter(response.body()!!.stocks.sector)
                    } else if (response.body()!!.status == "2") {
                        appLogout();
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<MarketTypeFilters>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun setSectorAdapter(list: MutableList<MarketTypeFilters.Sector>) {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_sector!!.layoutManager = llm
        recycle_sector!!.adapter = SectorMarketAdapter(applicationContext!!, list, sectorTypeFilter!!,
            object : SectorMarketAdapter.OnItemCheckListener {
                override fun onItemUncheck(item: String) {
                    sectorSelectedItems?.remove(item);
                    setSectorWatchlistFilter(TextUtils.join(",", sectorSelectedItems));

                }

                override fun onItemCheck(item: String) {
                    sectorSelectedItems?.add(item)
                    setSectorWatchlistFilter(TextUtils.join(",", sectorSelectedItems));
                }
            })
    }

    @SuppressLint("WrongConstant")
    private fun setMarketAdapter(list: MutableList<MarketTypeFilters.Exchange>) {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_market!!.layoutManager = llm
        recycle_market!!.adapter = MarketListFilterAdapter(applicationContext!!, list, marketTypeFilter!!,
            object : MarketListFilterAdapter.OnItemCheckListener {
                override fun onItemUncheck(item: String) {
                    marketSelectedItems?.remove(item);
                    setMarketWatchlistFilter(android.text.TextUtils.join(",", marketSelectedItems));

                }

                override fun onItemCheck(item: String) {
                    marketSelectedItems?.add(item)
                    setMarketWatchlistFilter(android.text.TextUtils.join(",", marketSelectedItems));
                }
            })
    }

    @SuppressLint("WrongConstant")
    private fun setCountryAdapter(country: Country) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_country!!.layoutManager = llm
        recycle_country!!.adapter =
            CountryListAdapter(this, country, countryTypeFilter!!, object : CountryListAdapter.OnItemCheckListener {
                override fun onItemUncheck(item: String) {
                    countrySelectedItems!!.remove(item);
                    setCountryWatchlistFilter(TextUtils.join(",", countrySelectedItems));
                }

                override fun onItemCheck(item: String) {
                    countrySelectedItems!!.add(item);
                    setCountryWatchlistFilter(TextUtils.join(",", countrySelectedItems));
                    Log.e("value", item)
                }
            })
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.llMarket -> {
                rel_country.visibility = GONE
                rel_sector.visibility = GONE
                clickPlusIcon(recycle_market, ivMarket, rel_market)
            }
            R.id.ll_country_list -> {
                rel_sector.visibility = GONE
                rel_country.visibility = VISIBLE
                clickPlusIcon(recycle_country, ivMarket, rel_country)
            }
            R.id.ll_sector -> {
                rel_country.visibility = GONE
                rel_sector.visibility = VISIBLE
                clickPlusIcon(recycle_sector, ivSector, rel_sector)
            }
        }
    }

    private fun clickPlusIcon(
        lin_child_title: RecyclerView,
        header_plus_icon: ImageView,
        relative: RelativeLayout
    ) {
        if (lin_child_title.visibility == View.GONE) {
            ViewAnimationUtils.expand(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowdown)
            lin_child_title.visibility = View.VISIBLE
            relative.visibility = View.VISIBLE
        } else {
            ViewAnimationUtils.collapse(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowright)
            lin_child_title.visibility = View.GONE
            relative.visibility = View.GONE
        }
    }


}