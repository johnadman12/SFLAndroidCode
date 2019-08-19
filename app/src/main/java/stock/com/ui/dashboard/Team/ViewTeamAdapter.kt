package stock.com.ui.dashboard.Team

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_team.view.*
import stock.com.R
import stock.com.ui.pojo.StockTeamPojo

class ViewTeamAdapter(
    val mContext: Context, val mContest: MutableList<StockTeamPojo.Stock>,
    val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<ViewTeamAdapter.FeatureListHolder>() {
    var list: ArrayList<StockTeamPojo.Stock> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_team, parent, false)
        return FeatureListHolder(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(sizeContest: Int, item: StockTeamPojo.Stock)
        fun onItemClick(item: StockTeamPojo.Stock)
        fun onToggleSell(item: StockTeamPojo.Stock)
        fun onToggleBuy(item: StockTeamPojo.Stock)
        fun onRemoveIteam(item: StockTeamPojo.Stock)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        list = ArrayList()
        list = mContest as ArrayList
        holder.itemView.tvSymbol.setText(mContest.get(position).symbol)
        holder.itemView.tvCompanyName.setText(mContest.get(position).companyName)
        holder.itemView.tvPrevClose.setText(mContest.get(position).previousClose)
        holder.itemView.tvlatestVolume.setText(mContest.get(position).latestVolume)
        holder.itemView.tvPercentage.setText(mContest.get(position).changePercent)
        Glide.with(mContext).load(mContest.get(position).image).into(holder.itemView.ivsTOCK)
        holder.itemView.llVol.visibility = View.GONE
        holder.itemView.llPrice.visibility = View.GONE
        holder.itemView.img_graph.visibility = View.GONE
        holder.itemView.llremoveStock.visibility = VISIBLE
        holder.itemView.img_add.visibility = GONE
        holder.itemView.tv_change_percentage.visibility = View.GONE
        mContest.get(position).addedToList = 1

        if (!TextUtils.isEmpty(mContest.get(position).changePercent))
            if (mContest.get(position).changePercent!!.contains("-")) {
                Glide.with(mContext).load(R.mipmap.downred).into(holder.itemView.img_graph)
                holder.itemView.tv_change_percentage.setText(mContest.get(position).changePercent)
            } else {
                Glide.with(mContext).load(R.mipmap.upgraph).into(holder.itemView.img_graph)
                holder.itemView.tvPercentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setText("+" + mContest.get(position).changePercent)
            }



        holder.itemView.llremoveStock.setOnClickListener {
            mContest.remove(mContest.get(position))
            notifyDataSetChanged()
            onItemCheckListener.onRemoveIteam(mContest.get(position))
            notifyDataSetChanged()
        }


        holder.itemView.setOnClickListener {
            onItemCheckListener.onItemClick(mContest.get(position))
        }

        if (mContest.get(position).addedToList == 1) {
            holder.itemView.llremoveStock.visibility = VISIBLE
            holder.itemView.img_add.visibility = GONE
        } else if (mContest.get(position).addedToList == 0) {
            holder.itemView.llremoveStock.visibility = GONE
            holder.itemView.img_add.visibility = VISIBLE
        }

        if (mContest.get(position).stock_type == "0")
            holder.itemView.toggleButton1.isChecked = true
        else if (mContest.get(position).stock_type == "1") {
            holder.itemView.toggleButton1.isChecked = false
        }

        holder.itemView.toggleButton1.setOnClickListener {
            if (holder.itemView.toggleButton1.isChecked) {
//                mContest.get(position).stock_type = "1";
                onItemCheckListener.onToggleSell(mContest.get(position))
            } else
//                mContest.get(position).stock_type = "0";
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