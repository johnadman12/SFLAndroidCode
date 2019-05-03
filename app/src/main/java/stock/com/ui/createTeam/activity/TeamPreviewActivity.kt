package stock.com.ui.createTeam.activity

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_team_preview.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.StockConstant

class TeamPreviewActivity : BaseActivity(), View.OnClickListener {
    private var list: ArrayList<StockTeamPojo.Stock>? = null;
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.img_back -> {
                finish()
            }
            R.id.img_close -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_preview)
        list = ArrayList()
        if (intent != null)
            list = intent.getParcelableArrayListExtra(StockConstant.STOCKLIST)
        initViews()
    }


    private fun initViews() {
        img_back.setOnClickListener(this)
        img_close.visibility = VISIBLE
        img_close.setOnClickListener(this)
        if (list != null)
            setData()

    }

    private fun setData() {
        Glide.with(this).load(list!!.get(0).image).placeholder(R.mipmap.cricketer).into(cimg_wk1)
        price1.setText(list!!.get(0).latestPrice)
        percentage1.setText(list!!.get(0).changePercent)
        stockname1.setText(list!!.get(0).symbol)

        Glide.with(this).load(list!!.get(1).image).placeholder(R.mipmap.cricketer).into(cimg_wk2)
        price2.setText(list!!.get(1).latestPrice)
        percentage2.setText(list!!.get(1).changePercent)
        stockname2.setText(list!!.get(1).symbol)

        Glide.with(this).load(list!!.get(2).image).placeholder(R.mipmap.cricketer).into(cimg_wk3)
        price3.setText(list!!.get(2).latestPrice)
        percentage3.setText(list!!.get(2).changePercent)
        stockname3.setText(list!!.get(2).symbol)

        Glide.with(this).load(list!!.get(3).image).placeholder(R.mipmap.cricketer).into(cimg_wk4)
        price4.setText(list!!.get(3).latestPrice)
        percentage4.setText(list!!.get(3).changePercent)
        stockname4.setText(list!!.get(3).symbol)

        Glide.with(this).load(list!!.get(4).image).placeholder(R.mipmap.cricketer).into(cimg_wk5)
        price5.setText(list!!.get(4).latestPrice)
        percentage5.setText(list!!.get(4).changePercent)
        stockname5.setText(list!!.get(4).symbol)

        Glide.with(this).load(list!!.get(5).image).placeholder(R.mipmap.cricketer).into(cimg_wk6)
        price6.setText(list!!.get(5).latestPrice)
        percentage6.setText(list!!.get(5).changePercent)
        stockname6.setText(list!!.get(5).symbol)

        Glide.with(this).load(list!!.get(6).image).placeholder(R.mipmap.cricketer).into(cimg_wk7)
        price7.setText(list!!.get(6).latestPrice)
        percentage7.setText(list!!.get(6).changePercent)
        stockname7.setText(list!!.get(6).symbol)

        Glide.with(this).load(list!!.get(7).image).placeholder(R.mipmap.cricketer).into(cimg_wk8)
        price8.setText(list!!.get(7).latestPrice)
        percentage8.setText(list!!.get(7).changePercent)
        stockname8.setText(list!!.get(7).symbol)


        Glide.with(this).load(list!!.get(8).image).placeholder(R.mipmap.cricketer).into(cimg_wk9)
        price9.setText(list!!.get(8).latestPrice)
        percentage9.setText(list!!.get(8).changePercent)
        stockname9.setText(list!!.get(8).symbol)

        Glide.with(this).load(list!!.get(9).image).placeholder(R.mipmap.cricketer).into(cimg_wk10)
        price10.setText(list!!.get(9).latestPrice)
        percentage10.setText(list!!.get(9).changePercent)
        stockname10.setText(list!!.get(9).symbol)

        Glide.with(this).load(list!!.get(10).image).placeholder(R.mipmap.cricketer).into(cimg_wk11)
        price11.setText(list!!.get(10).latestPrice)
        percentage11.setText(list!!.get(10).changePercent)
        stockname11.setText(list!!.get(10).symbol)

//        Glide.with(this).load(list!!.get(11).image).placeholder(R.mipmap.cricketer).into(cimg_wk12)
        price12.setText(list!!.get(11).latestPrice)
        percentage12.setText(list!!.get(11).changePercent)
        stockname12.setText(list!!.get(11).symbol)


    }


}