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
    private var sectorSelected: ArrayList<SectorListPojo.Sector>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sector_listing)
        StockConstant.ACTIVITIES.add(this)
        img_btn_close.visibility = View.VISIBLE
        img_btn_back.setOnClickListener {
            finish()
        }
        img_btn_close.setOnClickListener {
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
                    }
                } else {
                    Toast.makeText(
                        this@ActivitySectorFilter,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<SectorListPojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivitySectorFilter,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    @SuppressLint("WrongConstant")
    private fun setSectorAdapter(sector: List<SectorListPojo.Sector>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_sector!!.layoutManager = llm
        recycle_sector!!.adapter = SectorAdapter(this, sector, object : SectorAdapter.OnItemCheckListener {
            override fun onItemUncheck(item: SectorListPojo.Sector) {
                sectorSelected?.remove(item);
            }

            override fun onItemCheck(item: SectorListPojo.Sector) {
                sectorSelected?.add(item)
            }
        })

    }

}