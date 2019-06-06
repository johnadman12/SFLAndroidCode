package stock.com.ui.createTeam.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.core.content.ContextCompat
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
        img_close.setOnClickListener(this)
        if (list != null)
            setData()

    }

    private fun setData() {
        Glide.with(this).load(list!!.get(0).image).placeholder(R.mipmap.cricketer).into(cimg_wk1)
        price1.setText(list!!.get(0).latestPrice)
        percentage1.setText(list!!.get(0).changePercent)
        stockname1.setText(list!!.get(0).symbol)
        if (!TextUtils.isEmpty(list!!.get(0).changePercent))
            if (list!!.get(0).changePercent.contains("-"))
                arrow1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow))
            else {
                arrow1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                price1.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage1.setTextColor(ContextCompat.getColor(this, R.color.green))
            }


        Glide.with(this).load(list!!.get(1).image).placeholder(R.mipmap.cricketer).into(cimg_wk2)
        price2.setText(list!!.get(1).latestPrice)
        percentage2.setText(list!!.get(1).changePercent)
        stockname2.setText(list!!.get(1).symbol)
        if (!TextUtils.isEmpty(list!!.get(1).changePercent))
            if (list!!.get(1).changePercent.contains("-"))
                arrow2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow))
            else {
                arrow2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                price2.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage2.setTextColor(ContextCompat.getColor(this, R.color.green))
            }




        Glide.with(this).load(list!!.get(2).image).placeholder(R.mipmap.cricketer).into(cimg_wk3)
        price3.setText(list!!.get(2).latestPrice)
        percentage3.setText(list!!.get(2).changePercent)
        stockname3.setText(list!!.get(2).symbol)
        if (!TextUtils.isEmpty(list!!.get(2).changePercent))
            if (list!!.get(2).changePercent.contains("-"))
                arrow3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow))
            else {
                arrow3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                price3.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage3.setTextColor(ContextCompat.getColor(this, R.color.green))
            }



        Glide.with(this).load(list!!.get(3).image).placeholder(R.mipmap.cricketer).into(cimg_wk4)
        price4.setText(list!!.get(3).latestPrice)
        percentage4.setText(list!!.get(3).changePercent)
        stockname4.setText(list!!.get(3).symbol)
        if (!TextUtils.isEmpty(list!!.get(3).changePercent))
            if (list!!.get(3).changePercent.contains("-"))
                arrow4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow))
            else {
                arrow4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                price4.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage4.setTextColor(ContextCompat.getColor(this, R.color.green))
            }



        Glide.with(this).load(list!!.get(4).image).placeholder(R.mipmap.cricketer).into(cimg_wk5)
        price5.setText(list!!.get(4).latestPrice)
        percentage5.setText(list!!.get(4).changePercent)
        stockname5.setText(list!!.get(4).symbol)
        if (!TextUtils.isEmpty(list!!.get(4).changePercent))
            if (list!!.get(4).changePercent.contains("-"))
                arrow5.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow))
            else {
                arrow5.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                price5.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage5.setTextColor(ContextCompat.getColor(this, R.color.green))
            }



        Glide.with(this).load(list!!.get(5).image).placeholder(R.mipmap.cricketer).into(cimg_wk6)
        price6.setText(list!!.get(5).latestPrice)
        percentage6.setText(list!!.get(5).changePercent)
        stockname6.setText(list!!.get(5).symbol)
        if (!TextUtils.isEmpty(list!!.get(5).changePercent))
            if (list!!.get(5).changePercent.contains("-"))
                arrow6.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow))
            else {
                arrow6.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                price6.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage6.setTextColor(ContextCompat.getColor(this, R.color.green))
            }



        Glide.with(this).load(list!!.get(6).image).placeholder(R.mipmap.cricketer).into(cimg_wk7)
        price7.setText(list!!.get(6).latestPrice)
        percentage7.setText(list!!.get(6).changePercent)
        stockname7.setText(list!!.get(6).symbol)
        if (!TextUtils.isEmpty(list!!.get(6).changePercent))
            if (list!!.get(6).changePercent.contains("-"))
                arrow7.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow))
            else {
                arrow7.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                price7.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage7.setTextColor(ContextCompat.getColor(this, R.color.green))
            }



        Glide.with(this).load(list!!.get(7).image).placeholder(R.mipmap.cricketer).into(cimg_wk8)
        price8.setText(list!!.get(7).latestPrice)
        percentage8.setText(list!!.get(7).changePercent)
        stockname8.setText(list!!.get(7).symbol)
        if (!TextUtils.isEmpty(list!!.get(7).changePercent))
            if (list!!.get(7).changePercent.contains("-"))
                arrow8.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow))
            else {
                arrow8.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                price8.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage8.setTextColor(ContextCompat.getColor(this, R.color.green))
            }




        Glide.with(this).load(list!!.get(8).image).placeholder(R.mipmap.cricketer).into(cimg_wk9)
        price9.setText(list!!.get(8).latestPrice)
        percentage9.setText(list!!.get(8).changePercent)
        stockname9.setText(list!!.get(8).symbol)
        if (!TextUtils.isEmpty(list!!.get(8).changePercent))
            if (list!!.get(8).changePercent.contains("-"))
                arrow9.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow))
            else {
                arrow9.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                price9.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage9.setTextColor(ContextCompat.getColor(this, R.color.green))
            }



        Glide.with(this).load(list!!.get(9).image).placeholder(R.mipmap.cricketer).into(cimg_wk10)
        price10.setText(list!!.get(9).latestPrice)
        percentage10.setText(list!!.get(9).changePercent)
        stockname10.setText(list!!.get(9).symbol)
        if (!TextUtils.isEmpty(list!!.get(9).changePercent))
            if (list!!.get(9).changePercent.contains("-"))
                arrow10.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow))
            else {
                arrow10.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                price10.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage10.setTextColor(ContextCompat.getColor(this, R.color.green))
            }



        Glide.with(this).load(list!!.get(10).image).placeholder(R.mipmap.cricketer).into(cimg_wk11)
        price11.setText(list!!.get(10).latestPrice)
        percentage11.setText(list!!.get(10).changePercent)
        stockname11.setText(list!!.get(10).symbol)
        if (!TextUtils.isEmpty(list!!.get(10).changePercent))
            if (list!!.get(10).changePercent.contains("-"))
                arrow11.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow))
            else {
                arrow11.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                price11.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage11.setTextColor(ContextCompat.getColor(this, R.color.green))
            }



        Glide.with(this).load(list!!.get(11).image).into(cimg_wk12)
        price12.setText(list!!.get(11).latestPrice)
        percentage12.setText(list!!.get(11).changePercent)
        stockname12.setText(list!!.get(11).symbol)
        if (!TextUtils.isEmpty(list!!.get(11).changePercent))
            if (list!!.get(11).changePercent.contains("-"))
                arrow12.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_down_arrow))
            else {
                arrow12.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_up))
                price12.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage12.setTextColor(ContextCompat.getColor(this, R.color.green))
            }
    }


}