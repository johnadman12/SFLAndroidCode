package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
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
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener
import android.util.Log
import android.content.ClipData.Item
import stock.com.ui.pojo.LobbyContestPojo
import kotlin.collections.ArrayList


class ActivityFilter : BaseActivity(), View.OnClickListener {
    private var currentSelectedItems: ArrayList<String>? = null
    private var marketSelectedItems: ArrayList<String>? = null
    private var countrySelectedItems: ArrayList<String>? = null

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.llCountry -> {
                clickPlusIcon(recycle_country, ivCountry)
            }
            R.id.llMarket -> {
                clickPlusIcon(recycle_market, ivMarket)
            }
            R.id.llContest -> {
                clickPlusIcon(recycle_contest, ivContest)
            }
            R.id.llEntry -> {
                clickPlusRange(relative_range, ivEntry)
            }

            R.id.img_btn_close -> {
                finish()
            }
            R.id.img_btn_back -> {
                finish()
            }
            R.id.btn_apply -> {
                setFilters()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)
        StockConstant.ACTIVITIES.add(this)
        initViews()
    }

    private fun initViews() {
        countrySelectedItems = ArrayList();
        currentSelectedItems = ArrayList();
        marketSelectedItems = ArrayList();
        img_btn_back.setOnClickListener(this)
        img_btn_close.setOnClickListener(this)
        llMarket.setOnClickListener(this)
        llCountry.setOnClickListener(this)
        llContest.setOnClickListener(this)
        llEntry.setOnClickListener(this)
        btn_apply.setOnClickListener(this)
        img_btn_close.visibility = VISIBLE
        getFilterlist()
        rangeSeekbar1.setOnRangeSeekbarChangeListener(OnRangeSeekbarChangeListener { minValue, maxValue ->
            tvMin.setText(minValue.toString())
            tvMax.setText(maxValue.toString())
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
            apiService.getFilterList()
        call.enqueue(object : Callback<FilterPojo> {

            override fun onResponse(call: Call<FilterPojo>, response: Response<FilterPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        setCountryAdapter(response.body()!!.country)
                        setMarketAdapter(response.body()!!.market)
                        setContestAdapter(response.body()!!.category!!)
                        setRangebar(response.body()!!.entryFees)
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<FilterPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    fun setFilters() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<LobbyContestPojo> =
            apiService.setContestFilter(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                android.text.TextUtils.join(",", currentSelectedItems),
                android.text.TextUtils.join(",", marketSelectedItems),
                android.text.TextUtils.join(",", countrySelectedItems),
                tvMin.text.toString(),
                tvMax.text.toString()
            )
        call.enqueue(object : Callback<LobbyContestPojo> {

            override fun onResponse(call: Call<LobbyContestPojo>, response: Response<LobbyContestPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)

                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<LobbyContestPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    fun setRangebar(entryFees: List<FilterPojo.EntryFee>?) {
        if (entryFees != null) {
            rangeSeekbar1.setMinValue(entryFees.get(0).minValue!!.toFloat())
            rangeSeekbar1.setMaxValue(entryFees.get(0).maxValue!!.toFloat())
            tvMin.setText(entryFees.get(0).minValue!!.toString())
//            tvMin.setText("test")
            tvMax.setText(entryFees.get(0).maxValue!!.toString())
        }
    }

    @SuppressLint("WrongConstant")
    private fun setCountryAdapter(country: List<FilterPojo.Country>?) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_country!!.layoutManager = llm
        recycle_country!!.adapter =
            CountryListAdapter(this, country!!, object : CountryListAdapter.OnItemCheckListener {
                override fun onItemUncheck(item: String) {
                    countrySelectedItems!!.remove(item);
                }

                override fun onItemCheck(item: String) {
                    countrySelectedItems!!.add(item);
                    Log.e("value", item)
                }
            })
    }

    @SuppressLint("WrongConstant")
    private fun setMarketAdapter(market: List<FilterPojo.Market>?) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_market!!.layoutManager = llm
        recycle_market!!.adapter = MarketListAdapter(this, market!!, object : MarketListAdapter.OnItemCheckListener {
            override fun onItemUncheck(item: String) {
                marketSelectedItems!!.remove(item);
//                Log.e("valueremove", item.name.toString())
            }

            override fun onItemCheck(item: String) {
                marketSelectedItems!!.add(item);
            }
        })
    }


    @SuppressLint("WrongConstant")
    private fun setContestAdapter(contest: List<FilterPojo.Category>) {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        recycle_contest!!.layoutManager = llm
        recycle_contest!!.adapter = ContestListAdapter(this, contest, object : ContestListAdapter.OnItemCheckListener {
            override fun onItemUncheck(item: String) {
                currentSelectedItems?.remove(item);
            }

            override fun onItemCheck(item: String) {
                currentSelectedItems?.add(item)
            }
        })

    }


    private fun clickPlusIcon(lin_child_title: RecyclerView, header_plus_icon: ImageView) {
        if (lin_child_title.visibility == View.GONE) {
            ViewAnimationUtils.expand(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowdown)
            lin_child_title.visibility = VISIBLE
        } else {
            ViewAnimationUtils.collapse(lin_child_title)
            header_plus_icon.setImageResource(R.mipmap.arrowright)
            lin_child_title.visibility = GONE
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
}