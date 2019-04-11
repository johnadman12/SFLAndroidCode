package stock.com.ui.dashboard.Market

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_view_stock_name.view.*
import stock.com.R
import stock.com.ui.pojo.ExchangeList
import stock.com.ui.pojo.HomePojo

class ExchangeAdapter(val mContext: Context, val mContest: List<ExchangeList.Exchange>) :
    RecyclerView.Adapter<ExchangeAdapter.FeatureListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_stock_name, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.tvExchangeName.setText(mContest.get(position).name)
        if (mContest.get(position).changePercent!!.substring(0, 1).equals("-")) {
            holder.itemView.tvExchangePercentage.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed))
        } else {
            holder.itemView.tvExchangePercentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
        }
        holder.itemView.tvExchangePercentage.setText(mContest.get(position).latestPrice + " (" + mContest.get(position).changePercent+")")
    }


    override fun getItemCount(): Int {
        return mContest.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


}

