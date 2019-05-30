package stock.com.ui.dashboard.Market

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_currency_market.view.*
import stock.com.R
import stock.com.ui.pojo.MarketData

class StockAdapter(
    val mContext: Context,
    val list: MarketData,
    val fragment: StocksFragment
) :
    RecyclerView.Adapter<StockAdapter.FeatureListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_currency_market, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.name.setText(list.stock.get(position).symbol)
        holder.itemView.tv_company.setText(list.stock.get(position).companyName)
        holder.itemView.tv_latest_price.setText(list.stock.get(position).latestPrice + " %")
        holder.itemView.tv_change_percentage.setText(list.stock.get(position).changePercent)
        Glide.with(mContext).load(list.stock.get(position).image).into(holder.itemView.img_market)

        if (!TextUtils.isEmpty(list.stock.get(position).changePercent))
            if (list.stock.get(position).changePercent.contains("-"))
                Glide.with(mContext).load(R.mipmap.downred).into(holder.itemView.graph)
            else
                Glide.with(mContext).load(R.mipmap.upgraph).into(holder.itemView.graph)


        if (list.stock.get(position).stock_type.equals("1")) {
            holder.itemView.llAdd.visibility = View.GONE
            holder.itemView.img_check.visibility = View.VISIBLE
            holder.itemView.llwatch.isEnabled= false
        } else {
            holder.itemView.llAdd.visibility = View.VISIBLE
            holder.itemView.img_check.visibility = View.GONE
        }

        holder.itemView.llwatch.setOnClickListener {
            fragment.saveToWatchList(list.stock.get(position).stockid)
            list.stock.get(position).stock_type = "1"
            notifyDataSetChanged()
        }

    }

    override fun getItemCount(): Int {
        return list.stock.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }


}