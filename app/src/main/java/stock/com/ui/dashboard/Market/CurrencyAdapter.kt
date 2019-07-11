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
import androidx.core.app.ActivityCompat.startActivityForResult
import stock.com.ui.dashboard.Team.ActivityMarketDetail
import stock.com.utils.StockConstant
import java.text.DecimalFormat
import kotlin.collections.ArrayList


class CurrencyAdapter(
    val mContext: Context,
    val marketData: ArrayList<MarketList.Crypto>,
    val frgament: CryptoCurrencyFragment,
    val cryptoListNew: ArrayList<MarketList.Crypto>

) :
    RecyclerView.Adapter<CurrencyAdapter.FeatureListHolder>(), Filterable {
    var checkedHolder: BooleanArray? = null;
    private var search: List<MarketList.Crypto>? = null

    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(marketData.size)
    }

    init {
        this.search = cryptoListNew;
        createCheckedHolder();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_currency_market, parent, false)
        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        val anim = AlphaAnimation(0.1f, 1.0f)
        anim.duration = 50 //You can manage the blinking time with this parameter
        anim.startOffset = 20
//        anim.repeatCount = Animation.REVERSE

        holder.itemView.name.setText(cryptoListNew.get(position).symbol)
        holder.itemView.tv_company.setText(cryptoListNew.get(position).name)
        try {
            if (search!!.get(position).latestPrice != null)
                if (search!!.get(position).latestPrice.toDouble() < 1)
                    holder.itemView.tv_latest_price.setText(
                        "$" + String.format(
                            "%.6f",
                            search!!.get(position).latestPrice.toDouble()
                        )
                    )
                else
                    holder.itemView.tv_latest_price.setText(
                        "$" + String.format(
                            "%.2f",
                            search!!.get(position).latestPrice.toDouble()
                        )
                    )
        } catch (e: Exception) {

        }

        var priceText = ""
        try {
            priceText = marketData.get(position).latestPrice;
        } catch (e: java.lang.Exception) {
        }


        if (marketData.size == cryptoListNew.size) {
            if (!TextUtils.isEmpty(priceText)) {
                if (priceText.equals("$" + search!!.get(position).latestPrice)) {
                    holder.itemView.tv_latest_price.setTextColor(ContextCompat.getColor(mContext, R.color.black));
                } else if (priceText.toDouble() > search!!.get(position).latestPrice.toDouble()) {

                    val newtimer = object : CountDownTimer(1000, 500) {
                        override fun onTick(millisUntilFinished: Long) {
                            Log.e("timeerror", millisUntilFinished.toString())
                            holder.itemView.tv_latest_price.setTextColor(
                                ContextCompat.getColor(mContext, R.color.white)
                            )
                            holder.itemView.llPrice.setBackgroundDrawable(
                                ContextCompat.getDrawable(
                                    mContext,
                                    R.drawable.gray_green_fill
                                )
                            )
                            holder.itemView.llPrice.blink(3)
                            holder.itemView.llPrice.clearAnimation()
//                            holder.itemView.llPrice.animation = anim
                        }

                        override fun onFinish() {
                            try {
                                if (search!!.get(position).latestPrice.toDouble() < 1)
                                    holder.itemView.tv_latest_price.setText(
                                        "$" + String.format(
                                            "%.6f", search!!.get(position).latestPrice.toDouble()
                                        )
                                    )
                                else
                                    holder.itemView.tv_latest_price.setText(
                                        "$" + String.format(
                                            "%.2f",
                                            search!!.get(position).latestPrice.toDouble()
                                        )
                                    )
                                /*  search!!.get(position).latestPrice = cryptoListNew.get(position).latestPrice
                             search!!.get(position).changeper = cryptoListNew.get(position).changeper*/
                                holder.itemView.tv_latest_price.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.white
                                    )
                                )
                                /* holder.itemView.tv_change_percentage.setTextColor(
                                     ContextCompat.getColor(
                                         mContext,
                                         R.color.white
                                     )
                                 )*/
                                holder.itemView.llPrice.setBackgroundDrawable(
                                    ContextCompat.getDrawable(
                                        mContext,
                                        R.drawable.gray_green_fill
                                    )
                                )
                            } catch (e: Exception) {

                            }

                        }
                    }
                    newtimer.start()
                    holder.itemView.tv_latest_price.setTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.black
                        )
                    )


                } else if (priceText.toDouble() < search!!.get(position).latestPrice.toDouble()) {
                    Log.d("sddasdasdad", "-------3333333--")
                    val newtimer = object : CountDownTimer(500, 500) {
                        override fun onTick(millisUntilFinished: Long) {
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
//                            holder.itemView.llPrice.animation = anim
                            holder.itemView.llPrice.blink(3)
                            holder.itemView.llPrice.clearAnimation()
                        }

                        override fun onFinish() {

                            try {
                                if (search!!.get(position).latestPrice.toDouble() < 1)
                                    holder.itemView.tv_latest_price.setText(
                                        "$" + String.format(
                                            "%.6f",
                                            search!!.get(position).latestPrice.toDouble()
                                        )
                                    )
                                else
                                    holder.itemView.tv_latest_price.setText(
                                        "$" + String.format(
                                            "%.2f",
                                            search!!.get(position).latestPrice.toDouble()
                                        )
                                    )
                                /* search!!.get(position).latestPrice = cryptoListNew.get(position).latestPrice
                              search!!.get(position).changeper = cryptoListNew.get(position).changeper*/
                                holder.itemView.tv_latest_price.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.white
                                    )
                                )
                                holder.itemView.tv_change_percentage.setTextColor(
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
                    }
                    newtimer.start()
                    holder.itemView.tv_latest_price.setTextColor(
                        ContextCompat.getColor(
                            mContext,
                            R.color.black
                        )
                    )
                }
            } else {
                Log.e("sddasdasdad", "-------444444444--")
                if (search!!.get(position).latestPrice.toDouble() < 1) {
                    holder.itemView.tv_latest_price.setText(
                        "$" + String.format(
                            "%.6f",
                            search!!.get(position).latestPrice.toDouble()
                        )
                    )
                    Log.e("sddasdasdad", "-------55555555--")
                } else {
                    Log.e("sddasdasdad", "-------66666666--")
                    holder.itemView.tv_latest_price.setText(
                        "$" + String.format(
                            "%.2f",
                            search!!.get(position).latestPrice.toDouble()
                        )
                    )
                }
            }
        }


        Glide.with(mContext).load(cryptoListNew.get(position).image).into(holder.itemView.img_market)



        if (!TextUtils.isEmpty(cryptoListNew.get(position).changeper)) {
            var priceText: Double = (cryptoListNew!!.get(position).changeper).toDouble() * 0.01
            var price = (priceText.toString())
            price = price.substring(0, 1) + "$" + price.substring(4, price.length)


            if (cryptoListNew.get(position).changeper.contains("-")) {
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.redcolor))
                Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.graph)
                holder.itemView.tv_change_percentage.setText(price + " (" + cryptoListNew!!.get(position).changeper + " %)")
            } else {
                Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.graph)
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setText(price + " (+" + cryptoListNew!!.get(position).changeper + " %)")
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
            intent.putExtra("flag", 1)
            startActivityForResult(mContext as Activity, intent, 410, null);
        }

        holder.itemView.llwatch.setOnClickListener {
            frgament.saveToWatchList(cryptoListNew.get(position).cryptocurrencyid)
            cryptoListNew.get(position).cryptoType = "1"
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return search!!.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override
    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    search = cryptoListNew
                } else {
                    val filteredList = ArrayList<MarketList.Crypto>()
                    for (row in cryptoListNew) {
                        if (row.symbol!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        } else if (row.name!!.toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(row)
                    }
                    search = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = search
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                search = filterResults.values as ArrayList<MarketList.Crypto>
                notifyDataSetChanged()
            }
        }
    }


    fun View.blink(
        times: Int = Animation.INFINITE,
        duration: Long = 50L,
        offset: Long = 20L,
        minAlpha: Float = 0.0f,
        maxAlpha: Float = 1.0f,
        repeatMode: Int = Animation.REVERSE
    ) {
        startAnimation(AlphaAnimation(minAlpha, maxAlpha).also {
            it.duration = duration
            it.startOffset = offset
            it.repeatMode = repeatMode
            it.repeatCount = times
        })
    }
}
