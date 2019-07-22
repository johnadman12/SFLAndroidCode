package stock.com.ui.watch_list

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_watch_filter.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.Lobby.CountryListAdapter
import stock.com.ui.pojo.*
import stock.com.ui.watch_list.adapter.AssetAdapter
import stock.com.ui.watch_list.adapter.CountryWatchListAdapter
import stock.com.ui.watch_list.adapter.MarketAdapter
import stock.com.ui.watch_list.adapter.SectorAdapter
import stock.com.utils.SessionManager
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.ViewAnimationUtils

class WatchFilterActivity : BaseActivity(), View.OnClickListener {

    private var assetsTypeFilter: String? = "";
    private var sectorTypeFilter: String? = "";
    private var marketTypeFilter: String? = "";
    private var countryTypeFilter: String? = "";

    private var sectorAdapter: SectorAdapter? = null;
    private var assetAdapter: AssetAdapter? = null;
    private var marketAdapter: MarketAdapter? = null;
    private var countryAdapter: CountryListAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_filter)

        reset.visibility = View.VISIBLE;
        llMarket.setOnClickListener(this);
        ll_assettype.setOnClickListener(this);
        ll_country_list.setOnClickListener(this);
        ll_sector.setOnClickListener(this);

        img_btn_back.setOnClickListener {
            onBackPressed()
        }
        val country: Country =
            Gson().fromJson(
                SessionManager.getInstance(this@WatchFilterActivity).getString(StockConstant.COUNTRYLIST),
                Country::class.java
            )
        Log.e("json", country.country.toString())


        img_btn_close.setOnClickListener {
            finish()
        }

        reset.setOnClickListener {

            setSectorWatchlistFilter("");
            setAssetWatchlistFilter("");
            setMarketWatchlistFilter("");
            setCountryWatchlistFilter("");

            val resultIntent = Intent()
            resultIntent.putExtra("resetStockfilter", "1")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()


        }
        assetsTypeFilter = getFromPrefsString(StockConstant.ASSETS_WATCHLIST_TYPE);
        sectorTypeFilter = getFromPrefsString(StockConstant.SECTOR_WATCHLIST_TYPE);
        marketTypeFilter = getFromPrefsString(StockConstant.MARKET_WATCHLIST_TYPE);
        countryTypeFilter = getFromPrefsString(StockConstant.COUNTRY_WATCHLIST_TYPE);

        getFilterList();
        setCountryAdapter(country)
        btn_apply.setOnClickListener {
            if (sectorAdapter != null) {
                setSectorWatchlistFilter(sectorAdapter!!.getSeletedtIds());
            }
            if (assetAdapter != null) {
                setAssetWatchlistFilter(assetAdapter!!.getSeletedtIds());
            }
            if (marketAdapter != null) {
                setMarketWatchlistFilter(marketAdapter!!.getSeletedtIds());
            }
            if (countryAdapter != null) {
                Log.d("546546464adadad","---"+countryAdapter!!.getSeletedtIds())
                setCountryWatchlistFilter(countryAdapter!!.getSeletedtIds());
                Log.d("546546464adadad","---"+getFromPrefsString(StockConstant.COUNTRY_WATCHLIST_TYPE))
            }
            setWatchlistFilters();
        }
    }

    fun getFilterList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<WatchListFilterPojo> =
            apiService.getWatchListFilter(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<WatchListFilterPojo> {
            override fun onResponse(call: Call<WatchListFilterPojo>, response: Response<WatchListFilterPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        ll_main_filter.visibility = View.VISIBLE;
                        setMarketAdapter(response.body()!!.marketList!!)
                        setAssetAdapter(response.body()!!.assetList!!)
                        setSectorAdapter(response.body()!!.sectorList!!)
                    } else if (response.body()!!.status == "2") {
                        appLogout();
                    } else if (response.body()!!.status == "0") {
                        displayToast("No Filters", "error")
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<WatchListFilterPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun setSectorAdapter(list: ArrayList<WatchListFilterPojo.sector>) {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_sector!!.layoutManager = llm;
        sectorAdapter = SectorAdapter(applicationContext!!, list, sectorTypeFilter!!)
        recycle_sector!!.adapter = sectorAdapter;
    }

    @SuppressLint("WrongConstant")
    private fun setMarketAdapter(list: ArrayList<WatchListFilterPojo.market>) {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_market!!.layoutManager = llm
        marketAdapter = MarketAdapter(applicationContext!!, list, marketTypeFilter!!)
        recycle_market!!.adapter = marketAdapter;

    }

    @SuppressLint("WrongConstant")
    private fun setAssetAdapter(list: ArrayList<WatchListFilterPojo.asset>) {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_asset_type!!.layoutManager = llm

        assetAdapter = AssetAdapter(applicationContext!!, list, assetsTypeFilter!!)
        recycle_asset_type!!.adapter = assetAdapter;

    }

    @SuppressLint("WrongConstant")
    private fun setCountryAdapter(country: Country) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_country!!.layoutManager = llm
        Log.d("sdadadad646464--","---"+countryTypeFilter)
        countryAdapter = CountryListAdapter(this, country, countryTypeFilter!!)
        recycle_country!!.adapter = countryAdapter;
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.llMarket -> {
                rel_country.visibility = GONE
                rel_sector.visibility = GONE
                clickPlusIcon(recycle_market, ivMarket, rel_market)
            }
            R.id.ll_assettype -> {
                rel_sector.visibility = GONE
                rel_country.visibility = GONE
                clickPlusIcon(recycle_asset_type, ivAsset, rel_asset)
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

    private fun clickPlusIcon(lin_child_title: RecyclerView, header_plus_icon: ImageView, relative: RelativeLayout) {
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


    private fun setWatchlistFilters() {
        sectorTypeFilter = getFromPrefsString(StockConstant.SECTOR_WATCHLIST_TYPE);
        assetsTypeFilter = getFromPrefsString(StockConstant.ASSETS_WATCHLIST_TYPE);
        marketTypeFilter = getFromPrefsString(StockConstant.MARKET_WATCHLIST_TYPE);
        countryTypeFilter = getFromPrefsString(StockConstant.COUNTRY_WATCHLIST_TYPE);

        Log.d("sectorTypeFilter", "---" + sectorTypeFilter);
        Log.d("sectorTypeFilter", "---" + assetsTypeFilter);
        Log.d("sectorTypeFilter", "---" + marketTypeFilter);
        Log.d("sectorTypeFilter", "---" + countryTypeFilter);


        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<WatchlistPojo> =
            apiService.getWatchList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                assetsTypeFilter!!,
                sectorTypeFilter!!,
                marketTypeFilter!!,
                countryTypeFilter!!
            )
        call.enqueue(object : Callback<WatchlistPojo> {
            override fun onResponse(call: Call<WatchlistPojo>, response: Response<WatchlistPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        var testing = ArrayList<WatchlistPojo.WatchStock>()
                        testing = response.body()!!.stock!!
                        Log.e("nckshbj", testing.size.toString())

                        if (testing != null /*&& testing.size != 0*/) {
                            val resultIntent = Intent()
                            resultIntent.putExtra("stocklist", testing)
                            resultIntent.putExtra("resetStockfilter", "0")
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        }
                    } else if (response.body()!!.status == "2") {
                        appLogout();
                    } else if (response.body()!!.status == "0") {
                        displayToast("No Filters", "error")
                        finish()
                    } else {
                        displayToast("no data exist", "warning")
                        finish()
                    }
                }

            }

            override fun onFailure(call: Call<WatchlistPojo>, t: Throwable) {
                println(t.toString())
                Log.d("WatchList--", "" + t.localizedMessage)
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

}
