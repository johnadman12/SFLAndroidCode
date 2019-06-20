package stock.com.ui.dashboard.Market

import android.content.Context
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
        this.search = marketData;
        createCheckedHolder();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_currency_market, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        val anim = AlphaAnimation(0.5f, 1.0f)
        anim.duration = 100 //You can manage the blinking time with this parameter
        anim.startOffset = 20
        anim.repeatCount = Animation.REVERSE

        holder.itemView.name.setText(search!!.get(position).symbol)
        holder.itemView.tv_company.setText(search!!.get(position).name)
        holder.itemView.tv_latest_price.setText(search!!.get(position).latestPrice)
//        val priceText: String = holder.itemView.tv_latest_price.text.toString();
        val priceText: String = cryptoListNew.get(position).latestPrice;


        if (marketData.size == cryptoListNew.size) {
            if (!TextUtils.isEmpty(priceText)) {
                if (priceText.equals(search!!.get(position).latestPrice)) {
                    holder.itemView.tv_latest_price.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                } else if (priceText.toDouble() > search!!.get(position).latestPrice.toDouble()) {
                    val newtimer = object : CountDownTimer(2000, 2000) {
                        override fun onTick(millisUntilFinished: Long) {
                            Log.e("timeerror", millisUntilFinished.toString())
                            holder.itemView.tv_latest_price.setTextColor(
                                ContextCompat.getColor(
                                    mContext,
                                    R.color.green
                                )
                            )
                            holder.itemView.tv_latest_price.animation = anim
                        }

                        override fun onFinish() {
                            try {
                                holder.itemView.tv_latest_price.setText(cryptoListNew.get(position).latestPrice)
                                search!!.get(position).latestPrice = cryptoListNew.get(position).latestPrice
                                search!!.get(position).changeper = cryptoListNew.get(position).changeper
                                holder.itemView.tv_latest_price.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.black
                                    )
                                )
                            } catch (e: Exception) {

                            }

                        }
                    }
                    newtimer.start()

                } else if (priceText.toDouble() < search!!.get(position).latestPrice.toDouble()) {
                    val newtimer = object : CountDownTimer(2000, 2000) {
                        override fun onTick(millisUntilFinished: Long) {
                            holder.itemView.tv_latest_price.setTextColor(
                                ContextCompat.getColor(
                                    mContext,
                                    R.color.redcolor
                                )
                            )
                            holder.itemView.tv_latest_price.animation = anim
                        }

                        override fun onFinish() {
                            try {
                                holder.itemView.tv_latest_price.setText(cryptoListNew.get(position).latestPrice)
                                search!!.get(position).latestPrice = cryptoListNew.get(position).latestPrice
                                search!!.get(position).changeper = cryptoListNew.get(position).changeper
                                holder.itemView.tv_latest_price.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.black
                                    )
                                )
                            } catch (e: Exception) {
                            }

                        }
                    }
                    newtimer.start()
                }
            } else {
                holder.itemView.tv_latest_price.setText(cryptoListNew.get(position).latestPrice)
            }
        }


        Glide.with(mContext).load(search!!.get(position).image).into(holder.itemView.img_market)

        if (!TextUtils.isEmpty(cryptoListNew.get(position).changeper))
            if (cryptoListNew.get(position).changeper.contains("-")) {
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.redcolor))
                Glide.with(mContext).load(R.mipmap.downred).into(holder.itemView.graph)
                holder.itemView.tv_change_percentage.setText(cryptoListNew!!.get(position).changeper + " %")
            } else {
                Glide.with(mContext).load(R.mipmap.upgraph).into(holder.itemView.graph)
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setText("+" + cryptoListNew!!.get(position).changeper + " %")
            }


        if (search!!.get(position).cryptoType.equals("1")) {
            holder.itemView.llAdd.visibility = View.GONE
            holder.itemView.img_check.visibility = View.VISIBLE
            holder.itemView.llwatch.isEnabled = false
        } else {
            holder.itemView.llAdd.visibility = View.VISIBLE
            holder.itemView.img_check.visibility = View.GONE
        }




        holder.itemView.llwatch.setOnClickListener {
            frgament.saveToWatchList(search!!.get(position).cryptocurrencyid)
            search!!.get(position).cryptoType = "1"
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
                    search = marketData
                } else {
                    val filteredList = ArrayList<MarketList.Crypto>()
                    for (row in marketData) {
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
}
