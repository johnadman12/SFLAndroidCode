package stock.com.ui.createTeam.activity

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_team_preview.*
import kotlinx.android.synthetic.main.dialog_information.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.lang.Exception

class TeamPreviewActivity : BaseActivity(), View.OnClickListener {
    private var list: ArrayList<StockTeamPojo.Stock>? = null;
    var totalChange: String = ""
    var teamName: String = ""
    var totalChange1: Double = 0.0
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
            list = intent.getSerializableExtra(StockConstant.STOCKLIST) as ArrayList<StockTeamPojo.Stock>?
            teamName = intent.getStringExtra(StockConstant.TEAMNAME)
            totalChange = intent.getStringExtra(StockConstant.TOTALCHANGE)
        }

        if (!TextUtils.isEmpty(totalChange)) {
            if (totalChange.contains("%") ||totalChange.contains("$"))
                totalChange1 = totalChange.substring(0, totalChange.length - 1).toDouble()
            else
                totalChange1 = totalChange.toDouble()
            if (totalChange1 < 0.0)
                rel.setBackgroundResource(R.mipmap.redcircle)
            else if (totalChange1 > 0.0)
                rel.setBackgroundResource(R.mipmap.greencircle)
            else
                rel.setBackgroundResource(R.mipmap.graycircle)
            tvTotal.setText(totalChange + "%")
        }
        try {
            initViews()

        } catch (e: Exception) {
            Log.d("TeamStock",""+e.localizedMessage);
        }

        iv_info.setOnClickListener {
            AppDelegate.showInfoDialogue(getString(R.string.totalchangeinfo), this)

        }
    }


    private fun initViews() {
        img_back.setOnClickListener(this)
        img_close.setOnClickListener(this)
        if (list != null) {
            setData()
            txt_teamName.setText(teamName)
        }

    }

//    fun person(block: StockTeamPojo.() -> Unit): StockTeamPojo = StockTeamPojo().apply(block)

    private fun setData() {
        if (!TextUtils.isEmpty(list!!.get(0).symbol)) {
            rel1.visibility = View.VISIBLE
            Glide.with(this).load(list!!.get(0).image).placeholder(R.mipmap.cricketer).into(cimg_wk1)
            price1.setText(list!!.get(0).symbol)
            percentage1.setText(list!!.get(0).companyName)
            if (!TextUtils.isEmpty(list!!.get(0).stock_type))
                if (list!!.get(0).stock_type.equals("1"))
                    arrow1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
                else {
                    arrow1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
                    /*price1.setTextColor(ContextCompat.getColor(this, R.color.green))
                    percentage1.setTextColor(ContextCompat.getColor(this, R.color.green))*/
                }
        } else {
            rel1.visibility = View.GONE
        }


        if (!TextUtils.isEmpty(list!!.get(1).symbol)) {
            rel2.visibility = View.VISIBLE
            Glide.with(this).load(list!!.get(1).image).placeholder(R.mipmap.cricketer).into(cimg_wk2)
            price2.setText(list!!.get(1).symbol)
            percentage2.setText(list!!.get(1).companyName)
            if (!TextUtils.isEmpty(list!!.get(1).stock_type))
                if (list!!.get(1).stock_type.equals("1"))
                    arrow2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
                else {
                    arrow2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))

                }

        } else {
            rel2.visibility = View.GONE
        }



        if (!TextUtils.isEmpty(list!!.get(2).symbol)) {
            rel3.visibility = View.VISIBLE

            Glide.with(this).load(list!!.get(2).image).placeholder(R.mipmap.cricketer).into(cimg_wk3)
            price3.setText(list!!.get(2).symbol)
            percentage3.setText(list!!.get(2).companyName)
            if (!TextUtils.isEmpty(list!!.get(2).stock_type))
                if (list!!.get(2).stock_type.equals("1"))
                    arrow3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
                else {
                    arrow3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
                    /*    price3.setTextColor(ContextCompat.getColor(this, R.color.green))
                        percentage3.setTextColor(ContextCompat.getColor(this, R.color.green))*/
                }
        } else {
            rel3.visibility = View.GONE
        }


        if (!TextUtils.isEmpty(list!!.get(3).symbol)) {
            rel4.visibility = View.VISIBLE
            Glide.with(this).load(list!!.get(3).image).placeholder(R.mipmap.cricketer).into(cimg_wk4)
            price4.setText(list!!.get(3).symbol)
            percentage4.setText(list!!.get(3).companyName)
            if (!TextUtils.isEmpty(list!!.get(3).stock_type))
                if (list!!.get(3).stock_type.equals("1"))
                    arrow4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
                else {
                    arrow4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))

                }
        } else {
            rel4.visibility = View.GONE
        }


        if (!TextUtils.isEmpty(list!!.get(4).symbol)) {
            rel5.visibility = View.VISIBLE
            Glide.with(this).load(list!!.get(4).image).placeholder(R.mipmap.cricketer).into(cimg_wk5)
            price5.setText(list!!.get(4).symbol)
            percentage5.setText(list!!.get(4).companyName)
            if (!TextUtils.isEmpty(list!!.get(4).stock_type))
                if (list!!.get(4).stock_type.equals("1"))
                    arrow5.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
                else {
                    arrow5.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
                }
        } else {
            rel5.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(list!!.get(5).symbol)) {
            rel6.visibility = View.VISIBLE
            Glide.with(this).load(list!!.get(5).image).placeholder(R.mipmap.cricketer).into(cimg_wk6)
            price6.setText(list!!.get(5).symbol)
            percentage6.setText(list!!.get(5).companyName)
            if (!TextUtils.isEmpty(list!!.get(5).stock_type))
                if (list!!.get(5).stock_type.equals("1"))
                    arrow6.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
                else {
                    arrow6.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
                }
        } else {
            rel6.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(list!!.get(6).symbol)) {
            rel7.visibility = View.VISIBLE
            Glide.with(this).load(list!!.get(6).image).placeholder(R.mipmap.cricketer).into(cimg_wk7)
            price7.setText(list!!.get(6).symbol)
            percentage7.setText(list!!.get(6).companyName)
            if (!TextUtils.isEmpty(list!!.get(6).stock_type))
                if (list!!.get(6).stock_type.equals("1"))
                    arrow7.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
                else {
                    arrow7.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
                }
        } else {
            rel7.visibility = View.GONE
        }


        if (!TextUtils.isEmpty(list!!.get(7).symbol)) {
            rel8.visibility = View.VISIBLE
            Glide.with(this).load(list!!.get(7).image).placeholder(R.mipmap.cricketer).into(cimg_wk8)
            price8.setText(list!!.get(7).symbol)
            percentage8.setText(list!!.get(7).companyName)
            if (!TextUtils.isEmpty(list!!.get(7).stock_type))
                if (list!!.get(7).stock_type.equals("1"))
                    arrow8.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
                else {
                    arrow8.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
                }
        } else {
            rel8.visibility = View.GONE
        }


        if (!TextUtils.isEmpty(list!!.get(8).symbol)) {
            rel9.visibility = View.VISIBLE

            Glide.with(this).load(list!!.get(8).image).placeholder(R.mipmap.cricketer).into(cimg_wk9)
            price9.setText(list!!.get(8).symbol)
            percentage9.setText(list!!.get(8).companyName)
            if (!TextUtils.isEmpty(list!!.get(8).stock_type))
                if (list!!.get(8).stock_type.equals("1"))
                    arrow9.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
                else {
                    arrow9.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
                }
        } else {
            rel9.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(list!!.get(9).symbol)) {
            rel10.visibility = View.VISIBLE

            Glide.with(this).load(list!!.get(9).image).placeholder(R.mipmap.cricketer).into(cimg_wk10)
            price10.setText(list!!.get(9).symbol)
            percentage10.setText(list!!.get(9).companyName)
            if (!TextUtils.isEmpty(list!!.get(9).stock_type))
                if (list!!.get(9).stock_type.equals("1"))
                    arrow10.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
                else {
                    arrow10.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
                }
        } else {
            rel10.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(list!!.get(10).symbol)) {
            rel11.visibility = View.VISIBLE

            Glide.with(this).load(list!!.get(10).image).placeholder(R.mipmap.cricketer).into(cimg_wk11)
            price11.setText(list!!.get(10).symbol)
            percentage11.setText(list!!.get(10).companyName)
            if (!TextUtils.isEmpty(list!!.get(10).stock_type))
                if (list!!.get(10).stock_type.equals("1"))
                    arrow11.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
                else {
                    arrow11.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
                }
        } else {
            rel11.visibility = GONE
        }

        if (!TextUtils.isEmpty(list!!.get(11).symbol)) {
            rel12.visibility = View.VISIBLE
            Glide.with(this).load(list!!.get(11).image).into(cimg_wk12)
            price12.setText(list!!.get(11).symbol)
            percentage12.setText(list!!.get(11).companyName)
            if (!TextUtils.isEmpty(list!!.get(11).stock_type))
                if (list!!.get(11).stock_type.equals("1"))
                    arrow12.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
                else {
                    arrow12.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))

                }
        } else {
            rel12.visibility = GONE
        }
    }


}