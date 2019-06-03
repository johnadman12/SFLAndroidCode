package stock.com.ui.dashboard.Market

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_currency_market.view.*
import stock.com.R
import stock.com.ui.pojo.MarketData
import stock.com.ui.pojo.MarketList

class CurrencyAdapter(
    val mContext: Context,
    val marketData: MarketData,
    val frgament: CryptoCurrencyFragment
) :
    RecyclerView.Adapter<CurrencyAdapter.FeatureListHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_currency_market, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.name.setText(marketData.crypto.get(position).symbol)
        holder.itemView.tv_company.setText(marketData.crypto.get(position).name)
        holder.itemView.tv_latest_price.setText(marketData.crypto.get(position).latestPrice + " %")
        holder.itemView.tv_change_percentage.setText(marketData.crypto.get(position).changeper)

        Glide.with(mContext).load(marketData.crypto.get(position).image).into(holder.itemView.img_market)

        if (!TextUtils.isEmpty(marketData.crypto.get(position).changeper))
            if (marketData.crypto.get(position).changeper.contains("-"))
                Glide.with(mContext).load(R.mipmap.downred).into(holder.itemView.graph)
            else {
                Glide.with(mContext).load(R.mipmap.upgraph).into(holder.itemView.graph)
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setText("+" + marketData.crypto.get(position).changeper)


            }


        if (marketData.crypto.get(position).cryptoType.equals("1")) {
            holder.itemView.llAdd.visibility = View.GONE
            holder.itemView.img_check.visibility = View.VISIBLE
            holder.itemView.llwatch.isEnabled = false
        } else {
            holder.itemView.llAdd.visibility = View.VISIBLE
            holder.itemView.img_check.visibility = View.GONE
        }

        holder.itemView.llwatch.setOnClickListener {
            frgament.saveToWatchList(marketData.crypto.get(position).cryptocurrencyid)
            marketData.crypto.get(position).cryptoType = "1"
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return marketData.crypto.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}
