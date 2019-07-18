package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_sector_listing.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.SectorListPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class ActivitySectorFilter : BaseActivity() {
    private var sectorAdapter: SectorAdapter? = null;
    private var sectorTypeFilter: String? = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sector_listing)
        StockConstant.ACTIVITIES.add(this)
        sectorTypeFilter = getFromPrefsString(StockConstant.SECTOR_TYPE);
        reset.visibility = View.VISIBLE
        img_btn_back.setOnClickListener {
            finish()
        }
        reset.setOnClickListener {
            setSectorFilter("")
            val resultIntent = Intent()
            resultIntent.putExtra("resetfilter", "1")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()

        }
        var selectedSector: String = "";

        btn_filterApply.setOnClickListener {
            if (sectorAdapter != null) {
                selectedSector = sectorAdapter!!.getSeletedtIds();
            }
            Log.e("sectorlist", selectedSector)
            var resultIntent = Intent()
            resultIntent.putExtra("sectorlist", selectedSector)
            resultIntent.putExtra("resetfilter", "0")
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }
        getFilterlist()
    }

    fun getFilterlist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<SectorListPojo> =
            apiService.getSectorList(getFromPrefsString(StockConstant.ACCESSTOKEN).toString())
        call.enqueue(object : Callback<SectorListPojo> {

            override fun onResponse(call: Call<SectorListPojo>, response: Response<SectorListPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        setSectorAdapter(response.body()!!.sectorList!!)
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<SectorListPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun setSectorAdapter(sector: List<SectorListPojo.Sector>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_sector!!.layoutManager = llm
        sectorAdapter = SectorAdapter(this, sector, sectorTypeFilter!!)
        recycle_sector!!.adapter = sectorAdapter

    }


}