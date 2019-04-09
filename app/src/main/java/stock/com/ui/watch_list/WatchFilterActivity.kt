package stock.com.ui.watch_list

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.WatchListFilterPojo
import stock.com.ui.watch_list.adapter.AssetAdapter
import stock.com.ui.watch_list.adapter.CountryWatchListAdapter
import stock.com.ui.watch_list.adapter.MarketAdapter
import stock.com.ui.watch_list.adapter.SectorAdapter
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import stock.com.utils.ViewAnimationUtils

class WatchFilterActivity : BaseActivity(),View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch_filter)

        img_btn_close.visibility=View.VISIBLE;

        llMarket.setOnClickListener(this);
        ll_assettype.setOnClickListener(this);
       // ll_country_list.setOnClickListener(this);
        ll_sector.setOnClickListener(this);

        img_btn_back.setOnClickListener {
            onBackPressed()
           /* val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
           */
        }

        img_btn_close.setOnClickListener {
            finish()
        }

        getFilterList();
    }
    fun getFilterList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<WatchListFilterPojo> =
          apiService.getWatchListFilter(getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),getFromPrefsString(StockConstant.USERID).toString())
        call.enqueue(object : Callback<WatchListFilterPojo> {
            override fun onResponse(call: Call<WatchListFilterPojo>, response: Response<WatchListFilterPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        ll_main_filter.visibility=View.VISIBLE;
                        displayToast(response.body()!!.sectorList!!.size.toString())
                        setMarketAdapter(response.body()!!.marketList!!)
                        setAssetAdapter(response.body()!!.assetList!!)
                        setSectorAdapter(response.body()!!.sectorList!!)
                        //setCountryAdapter(response.body()!!.countrytList!!)
                       } else if (response.body()!!.status == "2") {
                           appLogout();
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }
            override fun onFailure(call: Call<WatchListFilterPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    private fun setSectorAdapter(list:ArrayList<WatchListFilterPojo.sector>) {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_sector!!.layoutManager = llm
        recycle_sector!!.adapter = SectorAdapter(applicationContext!!,list);
    }
    private fun setMarketAdapter(list:ArrayList<WatchListFilterPojo.market>) {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_market!!.layoutManager = llm
        recycle_market!!.adapter = MarketAdapter(applicationContext!!,list);
    }
    private fun setAssetAdapter(list:ArrayList<WatchListFilterPojo.asset>) {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_asset_type!!.layoutManager = llm
        recycle_asset_type!!.adapter = AssetAdapter(applicationContext!!,list);
    }
    private fun setCountryAdapter(list:ArrayList<WatchListFilterPojo.country>) {
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_country!!.layoutManager = llm
        recycle_country!!.adapter = CountryWatchListAdapter(applicationContext!!,list);
    }

    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.llMarket -> {
                clickPlusIcon(recycle_market,ivMarket)
               // displayToast("dsfdfsfs")
            } R.id.ll_assettype -> {
                clickPlusIcon(recycle_asset_type,ivAsset)
               // displayToast("dsfdfsfs")
            }
            /*R.id.ll_country_list -> {
                clickPlusIcon(recycle_country,ivMarket)
               // displayToast("dsfdfsfs")
            }*/
            R.id.ll_sector -> {
                clickPlusIcon(recycle_sector,ivSector)
               // displayToast("dsfdfsfs")
            }
        }
    }
    private fun clickPlusIcon(lin_child_title: RecyclerView, header_plus_icon: ImageView) {
        if (lin_child_title.visibility == View.GONE) {
            ViewAnimationUtils.expand(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowdown)
            lin_child_title.visibility = View.VISIBLE
        } else {
            ViewAnimationUtils.collapse(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowright)
            lin_child_title.visibility = View.GONE
        }
    }
}
