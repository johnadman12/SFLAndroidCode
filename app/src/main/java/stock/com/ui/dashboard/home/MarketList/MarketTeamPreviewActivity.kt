package stock.com.ui.dashboard.home.MarketList

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_team_preview.*
import kotlinx.android.synthetic.main.dialog_information.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.pojo.MarketList
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.StockConstant

class MarketTeamPreviewActivity : BaseActivity(), View.OnClickListener {
    private var list: ArrayList<MarketList.Crypto>? = null;
    var totalChange: String = ""
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
        if (intent != null) {
            list = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)
            totalChange = intent.getStringExtra(StockConstant.TOTALCHANGE)
        }

        if (totalChange != null)
            if (totalChange.contains("-"))
                rel.setBackgroundResource(R.mipmap.redcircle)
            else if (totalChange.equals("0.0", true))
                rel.setBackgroundResource(R.mipmap.graycircle)
            else
                rel.setBackgroundResource(R.mipmap.greencircle)
        tvTotal.setText(totalChange)
        initViews()

    }


    private fun initViews() {
        img_back.setOnClickListener(this)
        img_close.setOnClickListener(this)
        if (list != null)
            setData()
        iv_info.setOnClickListener {
            showInfoDialogue(getString(R.string.totalchangeinfo))
        }
    }

    private fun setData() {
        Glide.with(this).load(list!!.get(0).image).placeholder(R.mipmap.cricketer).into(cimg_wk1)
        price1.setText(list!!.get(0).symbol)
        percentage1.setText(list!!.get(0).name)
//        stockname1.setText(list!!.get(0).symbol)
        if (!TextUtils.isEmpty(list!!.get(0).changeper))
            if (list!!.get(0).changeper.contains("-"))
                arrow1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
                /*price1.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage1.setTextColor(ContextCompat.getColor(this, R.color.green))*/
            }

        Glide.with(this).load(list!!.get(1).image).placeholder(R.mipmap.cricketer).into(cimg_wk2)
        price2.setText(list!!.get(1).symbol)
        percentage2.setText(list!!.get(1).name)
        if (!TextUtils.isEmpty(list!!.get(1).changeper))
            if (list!!.get(1).changeper.contains("-"))
                arrow2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))

            }




        Glide.with(this).load(list!!.get(2).image).placeholder(R.mipmap.cricketer).into(cimg_wk3)
        price3.setText(list!!.get(2).symbol)
        percentage3.setText(list!!.get(2).name)
        if (!TextUtils.isEmpty(list!!.get(2).changeper))
            if (list!!.get(2).changeper.contains("-"))
                arrow3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            /*    price3.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage3.setTextColor(ContextCompat.getColor(this, R.color.green))*/
            }


        Glide.with(this).load(list!!.get(3).image).placeholder(R.mipmap.cricketer).into(cimg_wk4)
        price4.setText(list!!.get(3).symbol)
        percentage4.setText(list!!.get(3).name)
        if (!TextUtils.isEmpty(list!!.get(3).changeper))
            if (list!!.get(3).changeper.contains("-"))
                arrow4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))

            }



        Glide.with(this).load(list!!.get(4).image).placeholder(R.mipmap.cricketer).into(cimg_wk5)
        price5.setText(list!!.get(4).symbol)
        percentage5.setText(list!!.get(4).name)
        if (!TextUtils.isEmpty(list!!.get(4).changeper))
            if (list!!.get(4).changeper.contains("-"))
                arrow5.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow5.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }



        Glide.with(this).load(list!!.get(5).image).placeholder(R.mipmap.cricketer).into(cimg_wk6)
        price6.setText(list!!.get(5).symbol)
        percentage6.setText(list!!.get(5).name)
        if (!TextUtils.isEmpty(list!!.get(5).changeper))
            if (list!!.get(5).changeper.contains("-"))
                arrow6.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow6.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }



        Glide.with(this).load(list!!.get(6).image).placeholder(R.mipmap.cricketer).into(cimg_wk7)
        price7.setText(list!!.get(6).symbol)
        percentage7.setText(list!!.get(6).name)
        if (!TextUtils.isEmpty(list!!.get(6).changeper))
            if (list!!.get(6).changeper.contains("-"))
                arrow7.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow7.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }



        Glide.with(this).load(list!!.get(7).image).placeholder(R.mipmap.cricketer).into(cimg_wk8)
        price8.setText(list!!.get(7).symbol)
        percentage8.setText(list!!.get(7).name)
        if (!TextUtils.isEmpty(list!!.get(7).changeper))
            if (list!!.get(7).changeper.contains("-"))
                arrow8.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow8.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }




        Glide.with(this).load(list!!.get(8).image).placeholder(R.mipmap.cricketer).into(cimg_wk9)
        price9.setText(list!!.get(8).symbol)
        percentage9.setText(list!!.get(8).name)
        if (!TextUtils.isEmpty(list!!.get(8).changeper))
            if (list!!.get(8).changeper.contains("-"))
                arrow9.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow9.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }



        Glide.with(this).load(list!!.get(9).image).placeholder(R.mipmap.cricketer).into(cimg_wk10)
        price10.setText(list!!.get(9).symbol)
        percentage10.setText(list!!.get(9).name)
        if (!TextUtils.isEmpty(list!!.get(9).changeper))
            if (list!!.get(9).changeper.contains("-"))
                arrow10.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow10.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }



        Glide.with(this).load(list!!.get(10).image).placeholder(R.mipmap.cricketer).into(cimg_wk11)
        price11.setText(list!!.get(10).symbol)
        percentage11.setText(list!!.get(10).name)
        if (!TextUtils.isEmpty(list!!.get(10).changeper))
            if (list!!.get(10).changeper.contains("-"))
                arrow11.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow11.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }



        Glide.with(this).load(list!!.get(11).image).into(cimg_wk12)
        price12.setText(list!!.get(11).symbol)
        percentage12.setText(list!!.get(11).name)
        if (!TextUtils.isEmpty(list!!.get(11).changeper))
            if (list!!.get(11).changeper.contains("-"))
                arrow12.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow12.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))

            }
    }

    fun showInfoDialogue(textView: String) {
        var dialogue = Dialog(this)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialog_information)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.setCancelable(true)
        dialogue.setCanceledOnTouchOutside(true)
        var text = textView.replace(",", "\n-")
        dialogue.tvInfo.setText("-" + text)
        dialogue.btnOK.setOnClickListener {
            if (dialogue.isShowing)
                dialogue.dismiss()
        }
        dialogue.setCanceledOnTouchOutside(false)
        dialogue.setTitle(null)
        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }
}

