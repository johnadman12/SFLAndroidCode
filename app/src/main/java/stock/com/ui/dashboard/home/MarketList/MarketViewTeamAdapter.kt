package stock.com.ui.dashboard.home.MarketList

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_team.view.*
import stock.com.R
import stock.com.ui.pojo.MarketList

class MarketViewTeamAdapter
    (
    val mContext: Context, val mContest: MutableList<MarketList.Crypto>,
    val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<MarketViewTeamAdapter.FeatureListHolder>() {
    var list: ArrayList<MarketList.Crypto> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_team, parent, false)
        return FeatureListHolder(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(sizeContest: Int, item: MarketList.Crypto)
        fun onItemClick(item: MarketList.Crypto)
        fun onToggleSell(item: MarketList.Crypto)
        fun onToggleBuy(item: MarketList.Crypto)
        fun onRemoveIteam(item: MarketList.Crypto)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        list = ArrayList()
        list = mContest as ArrayList
        holder.itemView.tvSymbol.setText(mContest.get(position).symbol)
        holder.itemView.tvCompanyName.setText(mContest.get(position).name)
        holder.itemView.market.setText("marketCap :" + mContest.get(position).marketcapital)
        holder.itemView.ll_market.visibility = View.VISIBLE
        holder.itemView.prev.visibility = View.GONE
        holder.itemView.img_time.visibility = View.GONE
        holder.itemView.tvPrevClose.visibility = View.GONE
        holder.itemView.llVol.visibility = View.GONE
        holder.itemView.llPrice.visibility = View.GONE
        holder.itemView.img_graph.visibility = View.GONE
        holder.itemView.tvlatestVolume.setText(mContest.get(position).latestVolume)
        Glide.with(mContext).load(mContest.get(position).image).into(holder.itemView.ivsTOCK)

        holder.itemView.llremoveStock.visibility = View.VISIBLE
        holder.itemView.img_add.visibility = View.GONE
        mContest.get(position).addedToList = 1

        if (!TextUtils.isEmpty(mContest.get(position).changeper))
            if (mContest.get(position).changeper!!.contains("-")) {
                Glide.with(mContext).load(R.mipmap.downred).into(holder.itemView.img_graph)
                holder.itemView.tvPercentage.setText(mContest.get(position).changeper)
            } else {
                Glide.with(mContext).load(R.mipmap.upgraph).into(holder.itemView.img_graph)
                holder.itemView.tvPercentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setText("+" + mContest.get(position).changeper)
            }



        holder.itemView.llremoveStock.setOnClickListener {
            /*mContest.remove(mContest.get(position))
            notifyDataSetChanged()
            onItemCheckListener.onItemCheck((mContest.size), mContest.get(position))*/
            onItemCheckListener.onRemoveIteam(mContest.get(position))
            notifyDataSetChanged()
        }


        holder.itemView.setOnClickListener {
            onItemCheckListener.onItemClick(mContest.get(position))
        }

        if (mContest.get(position).addedToList == 1) {
            holder.itemView.llremoveStock.visibility = View.VISIBLE
            holder.itemView.img_add.visibility = View.GONE
        } else if (mContest.get(position).addedToList == 0) {
            holder.itemView.llremoveStock.visibility = View.GONE
            holder.itemView.img_add.visibility = View.VISIBLE
        }

        if (mContest.get(position).cryptoType.equals("0"))
            holder.itemView.toggleButton1.isChecked = true
        else if (mContest.get(position).cryptoType.equals("1")) {
            holder.itemView.toggleButton1.isChecked = false
        }

        holder.itemView.toggleButton1.setOnClickListener {
            if (holder.itemView.toggleButton1.isChecked) {
                mContest.get(position).cryptoType = "1";
                onItemCheckListener.onToggleSell(mContest.get(position))
            } else
                mContest.get(position).cryptoType = "0";
            onItemCheckListener.onToggleBuy(mContest.get(position))
        }

    }


    override fun getItemCount(): Int {
        return mContest.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.toggleButton1.setOnClickListener(onClickListener)
            itemView.llremoveStock.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }
}