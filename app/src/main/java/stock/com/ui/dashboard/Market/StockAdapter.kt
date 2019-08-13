package stock.com.ui.dashboard.Market

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_currency_market.view.*
import stock.com.R
import stock.com.ui.dashboard.Team.Stock.ActivityStockDetail
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.StockConstant

class StockAdapter(
    val mContext: Context,
    val list: ArrayList<StockTeamPojo.Stock>,
    val fragment: StocksFragment,
    val stockListNew: java.util.ArrayList<StockTeamPojo.Stock>
) :
    RecyclerView.Adapter<StockAdapter.FeatureListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockAdapter.FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_currency_market, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: StockAdapter.FeatureListHolder, position: Int) {
        val animBlink: Animation
        animBlink = AnimationUtils.loadAnimation(
            mContext,
            R.anim.blink
        )

        holder.itemView.name.setText(list.get(position).slug)
        holder.itemView.tv_company.setText(list.get(position).companyName)
        Glide.with(mContext).load(list.get(position).image).into(holder.itemView.img_market)


        try {
            if (stockListNew.get(position).latestPrice != null)
                if (stockListNew.get(position).latestPrice!!.toDouble() < 1)
                    holder.itemView.tv_latest_price.setText(
                        "$" + String.format(
                            "%.6f",
                            stockListNew.get(position).latestPrice!!.toDouble()
                        )
                    )
                else
                    holder.itemView.tv_latest_price.setText(
                        "$" + String.format(
                            "%.4f",
                            stockListNew.get(position).latestPrice!!.toDouble()
                        )
                    )
        } catch (e: Exception) {

        }
        var priceText = ""

//latest price flickering

        try {
            priceText = list.get(position).latestPrice!!;
            if (list.size == stockListNew.size) {
                if (!TextUtils.isEmpty(priceText)) {
                    if (priceText.equals("$" + stockListNew.get(position).latestPrice)) {
                        holder.itemView.tv_latest_price.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                    } else if (priceText.toDouble() < stockListNew.get(position).latestPrice!!.toDouble()) {
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
                                    if (stockListNew!!.get(position).latestPrice!!.toDouble() < 1)
                                        holder.itemView.tv_latest_price.setText(
                                            "$" + String.format(
                                                "%.6f", stockListNew!!.get(position).latestPrice!!.toDouble()
                                            )
                                        )
                                    else
                                        holder.itemView.tv_latest_price.setText(
                                            "$" + String.format(
                                                "%.2f",
                                                stockListNew!!.get(position).latestPrice!!.toDouble()
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
                    } else if (priceText.toDouble() > stockListNew!!.get(position).latestPrice!!.toDouble()) {
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
                                    if (stockListNew!!.get(position).latestPrice!!.toDouble() < 1)
                                        holder.itemView.tv_latest_price.setText(
                                            "$" + String.format(
                                                "%.6f", stockListNew!!.get(position).latestPrice!!.toDouble()
                                            )
                                        )
                                    else
                                        holder.itemView.tv_latest_price.setText(
                                            "$" + String.format(
                                                "%.2f",
                                                stockListNew!!.get(position).latestPrice!!.toDouble()
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
                    if (stockListNew!!.get(position).latestPrice!!.toDouble() < 1) {
                        holder.itemView.tv_latest_price.setText(
                            "$" + String.format(
                                "%.6f",
                                stockListNew!!.get(position).latestPrice!!.toDouble()
                            )
                        )
                        Log.e("sddasdasdad", "-------55555555--")
                    } else {
                        Log.e("sddasdasdad", "-------66666666--")
                        holder.itemView.tv_latest_price.setText(
                            "$" + String.format(
                                "%.2f",
                                stockListNew.get(position).latestPrice!!.toDouble()
                            )
                        )
                    }
                }
            }
        } catch (e: java.lang.Exception) {
        }


        try {
            if (!TextUtils.isEmpty(list.get(position).changePercent)) {
                var priceText: Double = (list.get(position).changePercent)!!.toDouble() * 0.01
                var price = (priceText.toString())


                if (list.get(position).changePercent!!.contains("-")) {
                    price = price.substring(0, 1) + "$" + price.substring(4, price.length)
                    holder.itemView.tv_change_percentage.setTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.redcolor
                        )
                    )
                    Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.graph)
//                    holder.itemView.tv_change_percentage.setText(price + " (" + list!!.get(position).changePercent + " %)")
                    holder.itemView.tv_change_percentage.setText(/*list.get(position).decimalchange +*/ " (" + list!!.get(
                        position
                    ).changePercent + " %)"
                    )
                } else {
                    Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.graph)
//                    holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                    holder.itemView.tv_change_percentage.setText(/*"$" + list.get(position).decimalchange +*/ " (+" + list!!.get(
                        position
                    ).changePercent + " %)"
                    )
                }
            }

            if (list.get(position).stock_type.equals("1")) {
                holder.itemView.llAdd.visibility = View.GONE
                holder.itemView.img_check.visibility = View.VISIBLE
                holder.itemView.llwatch.isEnabled = false
            } else {
                holder.itemView.llAdd.visibility = View.VISIBLE
                holder.itemView.img_check.visibility = View.GONE
            }


            holder.itemView.setOnClickListener {
                var intent = Intent(mContext, ActivityStockDetail::class.java);
                intent.putExtra(StockConstant.STOCKID, stockListNew.get(position).stockid)
                intent.putExtra(StockConstant.STOCKLIST, stockListNew)
                intent.putExtra(StockConstant.SELECTEDSTOCK, 0)
                intent.putExtra("flag", 2)
                ActivityCompat.startActivityForResult(mContext as Activity, intent, 411, null);
            }
            holder.itemView.llwatch.setOnClickListener {
                fragment.saveToWatchList(list.get(position).stockid)
                list.get(position).stock_type = "1"
                holder.itemView.llAdd.visibility = View.GONE
                holder.itemView.img_check.visibility = View.VISIBLE
                holder.itemView.llwatch.isEnabled = false
            }

        } catch (e: java.lang.Exception) {

        }
    }

    override fun getItemCount(): Int {
        return stockListNew.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }


}