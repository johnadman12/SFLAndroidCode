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
    RecyclerView.Adapter<StockAdapter.FeatureListHolder>(), Filterable {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_currency_market, parent, false)
        return FeatureListHolder(view)
    }

    private var searchList: List<StockTeamPojo.Stock>? = null
    var priceText: String = ""

    init {
        this.searchList = list;
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {

        val anim = AlphaAnimation(0.5f, 1.0f)
        anim.duration = 100 //You can manage the blinking time with this parameter
        anim.startOffset = 20
//        anim.repeatCount = Animation.REVERSE


        holder.itemView.name.setText(stockListNew!!.get(position).symbol)
        holder.itemView.tv_company.setText(stockListNew!!.get(position).companyName)
        holder.itemView.tv_latest_price.setText("$" + searchList!!.get(position).latestPrice)


        if (!TextUtils.isEmpty(stockListNew.get(position).latestPrice)) {
            priceText = stockListNew.get(position).latestPrice;
        } else {
            priceText = "0";
        }



        if (list.size == stockListNew.size) {
            if (!TextUtils.isEmpty(priceText)) {
                if (!TextUtils.isEmpty(searchList!!.get(position).latestPrice)) {
                    if (priceText.equals(searchList!!.get(position).latestPrice)) {
                        holder.itemView.tv_latest_price.setTextColor(ContextCompat.getColor(mContext, R.color.black))
                    } else if (priceText.toDouble() > searchList!!.get(position).latestPrice.toDouble()) {
                        val newtimer = object : CountDownTimer(500, 500) {
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
                                holder.itemView.tv_latest_price.setText("$" + stockListNew.get(position).latestPrice)
                                searchList!!.get(position).latestPrice = stockListNew.get(position).latestPrice
                                searchList!!.get(position).changePercent = stockListNew.get(position).changePercent
                                holder.itemView.tv_latest_price.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.black
                                    )
                                )
                            }
                        }
                        newtimer.start()

                    } else if (priceText.toDouble() < searchList!!.get(position).latestPrice.toDouble()) {
                        val newtimer = object : CountDownTimer(500, 500) {
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
                                holder.itemView.tv_latest_price.setText("$" + stockListNew.get(position).latestPrice)
                                searchList!!.get(position).latestPrice = stockListNew.get(position).latestPrice
                                searchList!!.get(position).changePercent = stockListNew.get(position).changePercent
                                holder.itemView.tv_latest_price.setTextColor(
                                    ContextCompat.getColor(
                                        mContext,
                                        R.color.black
                                    )
                                )
                            }
                        }
                        newtimer.start()
                    }
                } else {
                    holder.itemView.tv_latest_price.setText("$" + stockListNew.get(position).latestPrice)
                }
            }
        }

        Glide.with(mContext).load(searchList!!.get(position).image).into(holder.itemView.img_market)


        holder.itemView.setOnClickListener {
            var intent = Intent(mContext, ActivityStockDetail::class.java);
            intent.putExtra(StockConstant.STOCKID, stockListNew.get(position).stockid)
            intent.putExtra(StockConstant.STOCKLIST, stockListNew)
            intent.putExtra(StockConstant.SELECTEDSTOCK, 0)
            intent.putExtra("flag", 1)
            ActivityCompat.startActivityForResult(mContext as Activity, intent, 411, null);
        }


        if (!TextUtils.isEmpty(stockListNew!!.get(position).changePercent)) {
            var priceText: Double = (stockListNew!!.get(position).changePercent).toDouble() * 0.01
            var price = (priceText.toString())
            price = price.substring(0, 1) + "$" + price.substring(4, price.length)

            if (stockListNew!!.get(position).changePercent.contains("-")) {
                Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.graph)
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.redcolor))
                holder.itemView.tv_change_percentage.setText(price.toString() + " (" + stockListNew!!.get(position).changePercent + " %)")
            } else {
                Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.graph)
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setText(price.toString() + " (+" + stockListNew!!.get(position).changePercent + " %)")
            }
        }
        if (searchList!!.get(position).stock_type.equals("1")) {
            holder.itemView.llAdd.visibility = View.GONE
            holder.itemView.img_check.visibility = View.VISIBLE
            holder.itemView.llwatch.isEnabled = false
        } else {
            holder.itemView.llAdd.visibility = View.VISIBLE
            holder.itemView.img_check.visibility = View.GONE
        }

        holder.itemView.llwatch.setOnClickListener {
            fragment.saveToWatchList(searchList!!.get(position).stockid)
            searchList!!.get(position).stock_type = "1"
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return searchList!!.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                Log.d("dsadadfsfsfsfsa ", charString)

                if (charString.isEmpty()) {
                    searchList = list
                    Log.d("dsadada ", charString)
                } else {
                    val filteredList = ArrayList<StockTeamPojo.Stock>()
                    for (row in list) {

                        if (row.symbol!!.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row)
                        } else if (row.companyName!!.toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(row)
                    }
                    searchList = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = searchList
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                searchList = filterResults.values as ArrayList<StockTeamPojo.Stock>
                notifyDataSetChanged()
            }
        }
    }
}