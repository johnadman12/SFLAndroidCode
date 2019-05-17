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
import stock.com.ui.pojo.Country
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.StockPojo
import stock.com.ui.pojo.WatchListFilterPojo
import stock.com.ui.watch_list.adapter.AssetAdapter
import stock.com.ui.watch_list.adapter.CountryWatchListAdapter
import stock.com.ui.watch_list.adapter.MarketAdapter
import stock.com.ui.watch_list.adapter.SectorAdapter
import stock.com.utils.SessionManager
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.ViewAnimationUtils

class WatchFilterActivity : BaseActivity(), View.OnClickListener {
    private var countrySelectedItems: ArrayList<String>? = null
    private var marketSelectedItems: ArrayList<String>? = null
    private var assetsSelectedItems: ArrayList<String>? = null
    private var sectorSelectedItems: ArrayList<String>? = null

    private var assetsTypeFilter: String? = "";
    private var sectorTypeFilter: String? = "";
    private var marketTypeFilter: String? = "";
    private var countryTypeFilter: String? = "";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_filter)

        reset.visibility = View.VISIBLE;
        countrySelectedItems = ArrayList()
        marketSelectedItems = ArrayList()
        assetsSelectedItems = ArrayList()
        sectorSelectedItems = ArrayList()
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
        setCountryAdapter(country)

        img_btn_close.setOnClickListener {
            finish()
        }

        reset.setOnClickListener {
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

        btn_apply.setOnClickListener {
            setWatchlistFilters()
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
        recycle_sector!!.layoutManager = llm
        recycle_sector!!.adapter = SectorAdapter(applicationContext!!, list, sectorTypeFilter!!,
            object : SectorAdapter.OnItemCheckListener {
                override fun onItemUncheck(item: String) {
                    sectorSelectedItems?.remove(item);
                    setSectorWatchlistFilter(android.text.TextUtils.join(",", sectorSelectedItems));

                }

                override fun onItemCheck(item: String) {
                    sectorSelectedItems?.add(item)
                    setSectorWatchlistFilter(android.text.TextUtils.join(",", sectorSelectedItems));
                }
            })
    }

    @SuppressLint("WrongConstant")
    private fun setMarketAdapter(list: ArrayList<WatchListFilterPojo.market>) {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_market!!.layoutManager = llm
        recycle_market!!.adapter = MarketAdapter(applicationContext!!, list, marketTypeFilter!!,
            object : MarketAdapter.OnItemCheckListener {
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
    private fun setAssetAdapter(list: ArrayList<WatchListFilterPojo.asset>) {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_asset_type!!.layoutManager = llm
        recycle_asset_type!!.adapter = AssetAdapter(applicationContext!!, list, assetsTypeFilter!!,
            object : AssetAdapter.OnItemCheckListener {
                override fun onItemUncheck(item: String) {
                    assetsSelectedItems?.remove(item);
                    setAssetWatchlistFilter(android.text.TextUtils.join(",", assetsSelectedItems));

                }

                override fun onItemCheck(item: String) {
                    assetsSelectedItems?.add(item)
                    setAssetWatchlistFilter(android.text.TextUtils.join(",", assetsSelectedItems));
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
                    setCountryWatchlistFilter(android.text.TextUtils.join(",", countrySelectedItems));
                }

                override fun onItemCheck(item: String) {
                    countrySelectedItems!!.add(item);
                    setCountryWatchlistFilter(android.text.TextUtils.join(",", countrySelectedItems));
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
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockPojo> =
            apiService.getWatchList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                android.text.TextUtils.join(",", assetsSelectedItems),
                android.text.TextUtils.join(",", sectorSelectedItems),
                android.text.TextUtils.join(",", marketSelectedItems),
                android.text.TextUtils.join(",", countrySelectedItems)
            )
        call.enqueue(object : Callback<StockPojo> {
            override fun onResponse(call: Call<StockPojo>, response: Response<StockPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status.equals("1")) {
                        if (response.body()!!.status == "1") {
                            Handler().postDelayed(Runnable {
                            }, 100)
                            var testing = ArrayList<StockPojo.Stock>()
                            testing = response.body()!!.stockList!!
                            Log.e("nckshbj", testing.size.toString())

                            if (testing != null && testing.size != 0) {
                                val resultIntent = Intent()
                                resultIntent.putExtra("stocklist", testing)
                                resultIntent.putExtra("resetStockfilter", "0")
                                setResult(Activity.RESULT_OK, resultIntent)
                                finish()
                            } else if (response.body()!!.status == "2") {
                                appLogout();
                            } else {
                                displayToast("no data exist","warning")
                                finish()
                            }
                        }
                    } else if (response.body()!!.status.equals("2")) {
                        appLogout();
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<StockPojo>, t: Throwable) {
                println(t.toString())
                Log.d("WatchList--", "" + t.localizedMessage)
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

}
