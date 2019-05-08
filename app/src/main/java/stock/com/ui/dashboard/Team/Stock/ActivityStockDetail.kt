package stock.com.ui.dashboard.Team.Stock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.AppDelegate
import stock.com.utils.AppDelegate.showAlert
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog


class ActivityStockDetail : BaseActivity(), View.OnClickListener {
    private var list: ArrayList<StockTeamPojo.Stock>? = null;
    private var fragment: Fragment? = null;
    var stockId: Int = 0
    var position: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_detail_page)
        list = ArrayList()
        StockConstant.ACTIVITIES.add(this)
        if (intent != null) {
            stockId = intent.getIntExtra("Stockid", 0);
            list = intent.getParcelableArrayListExtra(StockConstant.STOCKLIST)
        }

        if (list!!.size > 0)
            for (i in 0 until list!!.size) {
                if (stockId.equals(list!!.get(i).stockid))
                    position = i
            }
        setStockData()

        /*  if (list!!.get(position).getAddedStock().equals("0")) {
              list!!.get(position).addedStock = "1";
          } else if (list!!.get(position).getAddedStock().equals("1"))
              list!!.get(position).addedStock = "0";*/

        setFragment(ChartFragment());
        ll_news.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);
        ll_data.setOnClickListener(this);
        ll_chart.setOnClickListener(this);
        ll_analystics.setOnClickListener(this);
        ll_comments.setOnClickListener(this);
        ivTeam.setOnClickListener(this);
        ivWatchlist.setOnClickListener(this);

    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_btn_back -> {
                onBackPressed()
            }
            R.id.ivWatchlist -> {
                saveToWatchList()
            }

            R.id.ivTeam -> {
                if (list!!.get(position).getAddedToList() == 1) {
                    //show green button
                    AppDelegate.showAlert(this, "already added to stock")
                } else if (list!!.get(position).getAddedToList() == 0) {
                    list!!.get(position).addedToList = 1
                    //show red button
                    AppDelegate.showAlert(this, "added to stock")
                    var intent = Intent();
                    intent.putExtra("list", list)
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }

                Log.e("updatedlist", list!!.get(position).addedToList.toString())
            }

            R.id.ll_chart -> {
                if (fragment is ChartFragment)
                    return;
                setFragment(ChartFragment());
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
                setFragment(NewsFragment());
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
                setFragment(AnalysticFragment());
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
                setFragment(DataFragment());
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
                setFragment(CommentsFragment());
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

    fun setStockData() {
        if (position != -1) {
            stock_name.setText(list!!.get(position).symbol)
            tv_stockcomp.setText(list!!.get(position).companyName)
            tvStockPercentage.setText(list!!.get(position).previousClose)
            tvVol.setText(list!!.get(position).latestVolume)
            tvlatestPrice.setText(list!!.get(position).latestPrice)
            Glide.with(this).load(list!!.get(position).image).into(iv_stock_img)
        }

    }


    fun saveToWatchList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<BasePojo> =
            apiService.addStockWatch(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                stockId,
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    AppDelegate.showToast(this@ActivityStockDetail, response.message())
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        AppDelegate.showAlert(this@ActivityStockDetail, "stock added to watchlist sucessfully ")
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    } else {
                        AppDelegate.showAlert(this@ActivityStockDetail, "stock already added to watchlist")

                    }
                } else {
                    Toast.makeText(
                        this@ActivityStockDetail,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivityStockDetail,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    private fun setLinearLayoutColor(ll: LinearLayoutCompat, color: Int) {
        ll.setBackgroundColor(color);
    }

    private fun setFragment(fragment: Fragment) {
        this.fragment = fragment;
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
//            .setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out)
            .replace(R.id.container1, fragment)
            .commitAllowingStateLoss()
    }
}
