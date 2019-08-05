package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_currency_market.view.*
import stock.com.R
import stock.com.ui.pojo.MarketList
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.app.ActivityCompat.startActivityForResult
import stock.com.ui.dashboard.Team.ActivityMarketDetail
import stock.com.utils.StockConstant
import java.text.DecimalFormat
import kotlin.collections.ArrayList
import android.view.animation.AnimationUtils.loadAnimation


class CurrencyAdapter(
    val mContext: Context,
    val marketData: ArrayList<MarketList.Crypto>,
    val frgament: CryptoCurrencyFragment,
    val cryptoListNew: ArrayList<MarketList.Crypto>

) :
    RecyclerView.Adapter<CurrencyAdapter.FeatureListHolder>() {
    var checkedHolder: BooleanArray? = null;
//    private var search: List<MarketList.Crypto>? = null

    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(marketData.size)
    }

    init {
//        this.search = cryptoListNew;
        createCheckedHolder();
//        Log.d("gugugugugu", "---5465466---" + search!!.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_currency_market, parent, false)
        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        val animBlink: Animation
        animBlink = AnimationUtils.loadAnimation(
            mContext,
            R.anim.blink
        )
        holder.itemView.name.setText(cryptoListNew.get(position).symbol)
        holder.itemView.tv_company.setText(cryptoListNew.get(position).name)
        try {
            if (cryptoListNew.get(position).latestPrice != null)
                if (cryptoListNew.get(position).latestPrice.toDouble() < 1)
                    holder.itemView.tv_latest_price.setText(
                        "$" + String.format(
                            "%.6f",
                            cryptoListNew.get(position).latestPrice.toDouble()
                        )
                    )
                else
                    holder.itemView.tv_latest_price.setText(
                        "$" + String.format(
                            "%.2f",
                            cryptoListNew.get(position).latestPrice.toDouble()
                        )
                    )
        } catch (e: Exception) {

        }

        var priceText = ""
        try {
            priceText = marketData.get(position).latestPrice;
            if (marketData.size == cryptoListNew.size) {
                if (!TextUtils.isEmpty(priceText)) {
                    if (priceText.equals("$" + cryptoListNew!!.get(position).latestPrice)) {
                        holder.itemView.tv_latest_price.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    } else if (priceText.toDouble() < cryptoListNew!!.get(position).latestPrice.toDouble()) {
                        holder.itemView.llPrice.startAnimation(animBlink);
                        holder.itemView.tv_latest_price.startAnimation(animBlink);
                        animBlink.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationRepeat(p0: Animation?) {
                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                holder.itemView.tv_latest_price.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.black
                                    )
                                )
                                holder.itemView.llPrice.setBackgroundDrawable(
                                    ContextCompat.getDrawable(
                                        mContext,
                                        R.drawable.gray_empty_rect
                                    )
                                )
                            }

                            override fun onAnimationStart(p0: Animation?) {
                                try {
                                    if (cryptoListNew!!.get(position).latestPrice.toDouble() < 1)
                                        holder.itemView.tv_latest_price.setText(
                                            "$" + String.format(
                                                "%.6f", cryptoListNew!!.get(position).latestPrice.toDouble()
                                            )
                                        )
                                    else
                                        holder.itemView.tv_latest_price.setText(
                                            "$" + String.format(
                                                "%.2f",
                                                cryptoListNew!!.get(position).latestPrice.toDouble()
                                            )
                                        )
                                    holder.itemView.tv_latest_price.setTextColor(
                                        ContextCompat.getColor(
                                            mContext,
                                            R.color.white
                                        )
                                    )
                                    holder.itemView.llPrice.setBackgroundDrawable(
                                        ContextCompat.getDrawable(
                                            mContext,
                                            R.drawable.gray_green_fill
                                        )
                                    )
                                } catch (e: Exception) {

                                }
                            }
                        })
                    } else if (priceText.toDouble() > cryptoListNew!!.get(position).latestPrice.toDouble()) {
                        holder.itemView.llPrice.startAnimation(animBlink);
                        holder.itemView.tv_latest_price.startAnimation(animBlink);
                        animBlink.setAnimationListener(object : Animation.AnimationListener {
                            override fun onAnimationRepeat(p0: Animation?) {

                            }

                            override fun onAnimationEnd(p0: Animation?) {
                                holder.itemView.tv_latest_price.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.black
                                    )
                                )
                                holder.itemView.llPrice.setBackgroundDrawable(
                                    ContextCompat.getDrawable(
                                        mContext,
                                        R.drawable.gray_empty_rect
                                    )
                                )
                            }

                            override fun onAnimationStart(p0: Animation?) {
                                try {
                                    if (cryptoListNew!!.get(position).latestPrice.toDouble() < 1)
                                        holder.itemView.tv_latest_price.setText(
                                            "$" + String.format(
                                                "%.6f", cryptoListNew!!.get(position).latestPrice.toDouble()
                                            )
                                        )
                                    else
                                        holder.itemView.tv_latest_price.setText(
                                            "$" + String.format(
                                                "%.2f",
                                                cryptoListNew!!.get(position).latestPrice.toDouble()
                                            )
                                        )
                                    holder.itemView.tv_latest_price.setTextColor(
                                        ContextCompat.getColor(
                                            mContext,
                                            R.color.white
                                        )
                                    )
                                    holder.itemView.llPrice.setBackgroundDrawable(
                                        ContextCompat.getDrawable(
                                            mContext,
                                            R.drawable.gray_red_fill
                                        )
                                    )
                                } catch (e: Exception) {

                                }
                            }
                        })

                    }

                } else {
                    Log.e("sddasdasdad", "-------444444444--")
                    if (cryptoListNew!!.get(position).latestPrice.toDouble() < 1) {
                        holder.itemView.tv_latest_price.setText(
                            "$" + String.format(
                                "%.6f",
                                cryptoListNew!!.get(position).latestPrice.toDouble()
                            )
                        )
                        Log.e("sddasdasdad", "-------55555555--")
                    } else {
                        Log.e("sddasdasdad", "-------66666666--")
                        holder.itemView.tv_latest_price.setText(
                            "$" + String.format(
                                "%.2f",
                                cryptoListNew.get(position).latestPrice.toDouble()
                            )
                        )
                    }
                }
            }
        } catch (e: java.lang.Exception) {
        }

        try {
            Glide.with(mContext).load(cryptoListNew.get(position).image).into(holder.itemView.img_market)

            if (!TextUtils.isEmpty(cryptoListNew.get(position).changeper)) {
                var priceText: Double = (cryptoListNew.get(position).changeper).toDouble() * 0.01
                var price = (priceText.toString())


                if (cryptoListNew.get(position).changeper.contains("-")) {
                    price = price.substring(0, 1) + "$" + price.substring(4, price.length)
                    holder.itemView.tv_change_percentage.setTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.redcolor
                        )
                    )
                    Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.graph)
                    holder.itemView.tv_change_percentage.setText(price + " (" + cryptoListNew!!.get(position).changeper + " %)")
                    holder.itemView.tv_change_percentage.setText(cryptoListNew.get(position).decimalchange + " (" + cryptoListNew!!.get(position).changeper + " %)")
                } else {
                    Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.graph)
                    holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                    holder.itemView.tv_change_percentage.setText("$" + cryptoListNew.get(position).decimalchange + " (+" + cryptoListNew!!.get(position).changeper + " %)")
                }
            }

            if (cryptoListNew.get(position).cryptoType.equals("1")) {
                holder.itemView.llAdd.visibility = View.GONE
                holder.itemView.img_check.visibility = View.VISIBLE
                holder.itemView.llwatch.isEnabled = false
            } else {
                holder.itemView.llAdd.visibility = View.VISIBLE
                holder.itemView.img_check.visibility = View.GONE
            }


            holder.itemView.setOnClickListener {
                var intent = Intent(mContext, ActivityMarketDetail::class.java);
                intent.putExtra("cryptoId", cryptoListNew.get(position).cryptocurrencyid)
                intent.putExtra(StockConstant.MARKETLIST, cryptoListNew)
                intent.putExtra(StockConstant.SELECTEDSTOCK, 0)
                intent.putExtra("flag", 2)
                startActivityForResult(mContext as Activity, intent, 410, null);
            }

            holder.itemView.llwatch.setOnClickListener {
                frgament.saveToWatchList(cryptoListNew.get(position).cryptocurrencyid)
                cryptoListNew.get(position).cryptoType = "1"
                holder.itemView.llAdd.visibility = View.GONE
                holder.itemView.img_check.visibility = View.VISIBLE
                holder.itemView.llwatch.isEnabled = false
            }
        } catch (e: java.lang.Exception) {

        }
    }

    override fun getItemCount(): Int {
        return cryptoListNew.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}
