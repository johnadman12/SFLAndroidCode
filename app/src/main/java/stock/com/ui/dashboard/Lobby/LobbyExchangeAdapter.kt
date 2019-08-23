package stock.com.ui.dashboard.Lobby

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_market_exchange.view.*
import stock.com.R
import stock.com.ui.pojo.ExchangeList

class LobbyExchangeAdapter(
    val mContext: Context,
    val mContest: List<ExchangeList.Exchange>,
    val lobbyFragment: LobbyFragment
) :
    RecyclerView.Adapter<LobbyExchangeAdapter.FeatureListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_market_exchange, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        //holder.itemView.ll_scroll.isScrollbarFadingEnabled

        holder.itemView.exchangeName.setText(mContest.get(position).name)
        /* if (mContest.get(position).changePercent!!.substring(0, 1).equals("-")) {
             holder.itemView.tvExchangePercentage.setTextColor(ContextCompat.getColor(mContext, R.color.colorRed))
             holder.itemView.tvExchangePercentage.setText(mContest.get(position).changePercent)
         } else {
             holder.itemView.tvExchangePercentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
             holder.itemView.tvExchangePercentage.setText("+" + mContest.get(position).changePercent)
         }
         holder.itemView.tvValue.setText(mContest.get(position).latestPrice)*/
        Glide.with(mContext).load(mContest.get(position).image_url).into(holder.itemView.imgExchange)

        holder.itemView.setOnClickListener {
            lobbyFragment.filterData(mContest.get(position).id)
        }


    }


    override fun getItemCount(): Int {
        return mContest.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}