package stock.com.ui.dashboard.Team

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_team.view.*
import stock.com.R
import stock.com.ui.dashboard.Team.Stock.ActivityStockDetail
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.StockConstant

class ViewMyTeamAdapter(
    val mContext: Context, val mContest: MutableList<StockTeamPojo.Stock>,
    val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<ViewMyTeamAdapter.FeatureListHolder>() {
    var list: ArrayList<StockTeamPojo.Stock> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_team, parent, false)
        return FeatureListHolder(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(item: Int)
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

        holder.itemView.llremoveStock.visibility = VISIBLE
        holder.itemView.img_add.visibility = GONE


        holder.itemView.llremoveStock.setOnClickListener {
            mContest.remove(mContest.get(position))
            notifyDataSetChanged()
            onItemCheckListener.onItemCheck((mContest.size))
        }


        holder.itemView.setOnClickListener {
            mContext.startActivity(
                Intent(
                    mContext,
                    ActivityStockDetail::class.java
                )
//                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .putExtra("Stockid", mContest.get(position).stockid)
                    .putExtra(StockConstant.STOCKLIST, list)
            )
        }

        if (mContest.get(position).addedToList == 0) {
            holder.itemView.llremoveStock.visibility = VISIBLE
            holder.itemView.img_add.visibility = GONE
        } else if (mContest.get(position).addedToList == 1) {
            holder.itemView.llremoveStock.visibility = GONE
            holder.itemView.img_add.visibility = VISIBLE
        }


        if (mContest.get(position).getAddedStock().equals("0")) {
            holder.itemView.toggleButton1.isChecked = true
        } else if (mContest.get(position).getAddedStock().equals("1"))
            holder.itemView.toggleButton1.isChecked = false


        holder.itemView.toggleButton1.setOnClickListener {
            if (holder.itemView.toggleButton1.isChecked) {
                mContest.get(position).addedStock = "1";
            } else
                mContest.get(position).addedStock = "0";
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