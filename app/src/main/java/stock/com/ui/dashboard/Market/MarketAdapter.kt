package stock.com.ui.dashboard.Market

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_market.view.*
import stock.com.R

class MarketAdapter(val mContext: Context/*, val mContest: List<ExchangeList.Exchange>*/) :
    RecyclerView.Adapter<MarketAdapter.FeatureListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_market, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        if (position % 2 == 0) {
            holder.itemView.img_check.visibility = VISIBLE
            holder.itemView.llAdd.visibility = GONE

        } else {
            holder.itemView.llAdd.visibility = VISIBLE
            holder.itemView.img_check.visibility = GONE
        }
    }


    override fun getItemCount(): Int {
        return 5
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}