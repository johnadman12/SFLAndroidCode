package stock.com.ui.dashboard.home.Currency

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_currency_preview.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.pojo.CurrencyPojo
import stock.com.ui.pojo.MarketList
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.lang.Exception

class CurrencyPreviewTeamActivity : BaseActivity(), View.OnClickListener {
    private var list: ArrayList<CurrencyPojo.Currency>? = null;
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
        setContentView(R.layout.activity_currency_preview)
        list = ArrayList()
        if (intent != null) {
            list = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)
            teamName = intent.getStringExtra(StockConstant.TEAMNAME)
            totalChange = intent.getStringExtra(StockConstant.TOTALCHANGE)
        }

        if (!TextUtils.isEmpty(totalChange)) {
            if (totalChange.contains("%") || totalChange.contains("$"))
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

        }

    }


    private fun initViews() {
        img_back.setOnClickListener(this)
        img_close.setOnClickListener(this)
        if (list != null) {
            setData()
            txt_teamName.setText(teamName)
        }
        iv_info.setOnClickListener {
            AppDelegate.showInfoDialogue(getString(R.string.totalchangeinfo), this)
        }
    }

    private fun setData() {
        if (!TextUtils.isEmpty(list!!.get(0).symbol)) {
            rel_1.visibility = View.VISIBLE
        Glide.with(this).load(list!!.get(0).firstflag).placeholder(R.mipmap.cricketer).into(img1)
        Glide.with(this).load(list!!.get(0).secondflag).placeholder(R.mipmap.cricketer).into(img2)
        price1.setText(list!!.get(0).symbol)
        if (!TextUtils.isEmpty(list!!.get(0).changeper))
            if (list!!.get(0).changeper.contains("-"))
                arrow1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
                /*price1.setTextColor(ContextCompat.getColor(this, R.color.green))
                percentage1.setTextColor(ContextCompat.getColor(this, R.color.green))*/
            }

        } else {
            rel_1.visibility = View.GONE
        }


        if (!TextUtils.isEmpty(list!!.get(1).symbol)) {
            rel_2.visibility = View.VISIBLE
        Glide.with(this).load(list!!.get(1).firstflag).placeholder(R.mipmap.cricketer).into(img3)
        Glide.with(this).load(list!!.get(1).secondflag).placeholder(R.mipmap.cricketer).into(img4)
        price2.setText(list!!.get(1).symbol)
        if (!TextUtils.isEmpty(list!!.get(1).changeper))
            if (list!!.get(1).changeper.contains("-"))
                arrow2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow2.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))

            }
        } else {
            rel_2.visibility = View.GONE
        }



        if (!TextUtils.isEmpty(list!!.get(2).symbol)) {
            rel_3.visibility = View.VISIBLE
        Glide.with(this).load(list!!.get(2).firstflag).placeholder(R.mipmap.cricketer).into(img5)
        Glide.with(this).load(list!!.get(2).secondflag).placeholder(R.mipmap.cricketer).into(img6)
        price3.setText(list!!.get(2).symbol)
        if (!TextUtils.isEmpty(list!!.get(2).changeper))
            if (list!!.get(2).changeper.contains("-"))
                arrow3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow3.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
                /*    price3.setTextColor(ContextCompat.getColor(this, R.color.green))
                    percentage3.setTextColor(ContextCompat.getColor(this, R.color.green))*/
            }
        } else {
            rel_3.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(list!!.get(3).symbol)) {
            rel_4.visibility = View.VISIBLE
        Glide.with(this).load(list!!.get(3).firstflag).placeholder(R.mipmap.cricketer).into(img7)
        Glide.with(this).load(list!!.get(3).secondflag).placeholder(R.mipmap.cricketer).into(img8)
        price4.setText(list!!.get(3).symbol)
        if (!TextUtils.isEmpty(list!!.get(3).changeper))
            if (list!!.get(3).changeper.contains("-"))
                arrow4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow4.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))

            }
        } else {
            rel_4.visibility = View.GONE
        }



        if (!TextUtils.isEmpty(list!!.get(4).symbol)) {
            rel_5.visibility = View.VISIBLE
        Glide.with(this).load(list!!.get(4).firstflag).placeholder(R.mipmap.cricketer).into(img9)
        Glide.with(this).load(list!!.get(4).secondflag).placeholder(R.mipmap.cricketer).into(img10)
        price5.setText(list!!.get(4).symbol)
        if (!TextUtils.isEmpty(list!!.get(4).changeper))
            if (list!!.get(4).changeper.contains("-"))
                arrow5.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow5.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }
        } else {
            rel_5.visibility = View.GONE
        }


        if (!TextUtils.isEmpty(list!!.get(5).symbol)) {
            rel_6.visibility = View.VISIBLE
        Glide.with(this).load(list!!.get(5).firstflag).placeholder(R.mipmap.cricketer).into(img11)
        Glide.with(this).load(list!!.get(5).secondflag).placeholder(R.mipmap.cricketer).into(img12)
        price6.setText(list!!.get(5).symbol)
        if (!TextUtils.isEmpty(list!!.get(5).changeper))
            if (list!!.get(5).changeper.contains("-"))
                arrow6.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow6.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }
        } else {
            rel_6.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(list!!.get(6).symbol)) {
            rel_7.visibility = View.VISIBLE
        Glide.with(this).load(list!!.get(6).firstflag).placeholder(R.mipmap.cricketer).into(img13)
        Glide.with(this).load(list!!.get(6).secondflag).placeholder(R.mipmap.cricketer).into(img14)
        price7.setText(list!!.get(6).symbol)
        if (!TextUtils.isEmpty(list!!.get(6).changeper))
            if (list!!.get(6).changeper.contains("-"))
                arrow7.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow7.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }
        } else {
            rel_7.visibility = View.GONE
        }


        if (!TextUtils.isEmpty(list!!.get(7).symbol)) {
            rel_8.visibility = View.VISIBLE
        Glide.with(this).load(list!!.get(7).firstflag).placeholder(R.mipmap.cricketer).into(img15)
        Glide.with(this).load(list!!.get(7).secondflag).placeholder(R.mipmap.cricketer).into(img16)
        price8.setText(list!!.get(7).symbol)
        if (!TextUtils.isEmpty(list!!.get(7).changeper))
            if (list!!.get(7).changeper.contains("-"))
                arrow8.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow8.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }
        } else {
            rel_8.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(list!!.get(8).symbol)) {
            rel_9.visibility = View.VISIBLE
        Glide.with(this).load(list!!.get(8).firstflag).placeholder(R.mipmap.cricketer).into(img17)
        Glide.with(this).load(list!!.get(8).secondflag).placeholder(R.mipmap.cricketer).into(img18)
        price9.setText(list!!.get(8).symbol)
        if (!TextUtils.isEmpty(list!!.get(8).changeper))
            if (list!!.get(8).changeper.contains("-"))
                arrow9.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow9.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }
        } else {
            rel_9.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(list!!.get(9).symbol)) {
            rel_10.visibility = View.VISIBLE
        Glide.with(this).load(list!!.get(9).firstflag).placeholder(R.mipmap.cricketer).into(img19)
        Glide.with(this).load(list!!.get(9).secondflag).placeholder(R.mipmap.cricketer).into(img20)
        price10.setText(list!!.get(9).symbol)
        if (!TextUtils.isEmpty(list!!.get(9).changeper))
            if (list!!.get(9).changeper.contains("-"))
                arrow10.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow10.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }

        } else {
            rel_10.visibility = View.GONE
        }

        if (!TextUtils.isEmpty(list!!.get(10).symbol)) {
            rel_11.visibility = View.VISIBLE
        Glide.with(this).load(list!!.get(10).firstflag).placeholder(R.mipmap.cricketer).into(img21)
        Glide.with(this).load(list!!.get(10).secondflag).placeholder(R.mipmap.cricketer).into(img22)
        price11.setText(list!!.get(10).symbol)
        if (!TextUtils.isEmpty(list!!.get(10).changeper))
            if (list!!.get(10).changeper.contains("-"))
                arrow11.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow11.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))
            }
        } else {
            rel_11.visibility = View.GONE
        }


        if (!TextUtils.isEmpty(list!!.get(11).symbol)) {
            rel_12.visibility = View.VISIBLE
        Glide.with(this).load(list!!.get(11).firstflag).into(img23)
        Glide.with(this).load(list!!.get(11).secondflag).into(img24)
        price12.setText(list!!.get(11).symbol)
        if (!TextUtils.isEmpty(list!!.get(11).changeper))
            if (list!!.get(11).changeper.contains("-"))
                arrow12.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sell))
            else {
                arrow12.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.buy))

            }
        } else {
            rel_12.visibility = View.GONE
        }
    }

}