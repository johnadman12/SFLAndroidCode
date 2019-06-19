package stock.com.ui.dashboard.Market

import android.content.Context
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
import stock.com.ui.pojo.StockTeamPojo
import kotlin.math.log

class StockAdapter(
    val mContext: Context,
    val list: ArrayList<StockTeamPojo.Stock>,
    val fragment: StocksFragment
) :
    RecyclerView.Adapter<StockAdapter.FeatureListHolder>(), Filterable {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_currency_market, parent, false)
        return FeatureListHolder(view)
    }

    private var searchList: List<StockTeamPojo.Stock>? = null

    init {
        this.searchList = list;
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.name.setText(searchList!!.get(position).symbol)
        holder.itemView.tv_company.setText(searchList!!.get(position).companyName)
        holder.itemView.tv_latest_price.setText(searchList!!.get(position).latestPrice)

        Glide.with(mContext).load(searchList!!.get(position).image).into(holder.itemView.img_market)


      /*  val marketopen: Long = searchList!!.get(position).marketopen.toLong()
        if (marketopen == searchList!!.get(position).latestPrice.toLong())
            holder.itemView.tv_latest_price.setTextColor(ContextCompat.getColor(mContext, R.color.black))
        else if (marketopen > searchList!!.get(position).latestPrice.toLong())
            holder.itemView.tv_latest_price.setTextColor(ContextCompat.getColor(mContext, R.color.redcolor))
        else if (marketopen < searchList!!.get(position).latestPrice.toLong())
            holder.itemView.tv_latest_price.setTextColor(ContextCompat.getColor(mContext, R.color.green))
*/

        if (!TextUtils.isEmpty(searchList!!.get(position).changePercent))
            if (searchList!!.get(position).changePercent.contains("-")) {
                Glide.with(mContext).load(R.mipmap.downred).into(holder.itemView.graph)
                holder.itemView.tv_change_percentage.setText(searchList!!.get(position).changePercent + " %")
            } else {
                Glide.with(mContext).load(R.mipmap.upgraph).into(holder.itemView.graph)
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setText("+" + searchList!!.get(position).changePercent + " %")
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