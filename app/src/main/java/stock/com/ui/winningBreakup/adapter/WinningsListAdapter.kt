package stock.com.ui.winningBreakup.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_winning_list.view.*
import stock.com.R
import stock.com.ui.pojo.HomePojo
import stock.com.ui.pojo.PriceBreak


class WinningsListAdapter(
    val mContext: Context,
    val list: ArrayList<PriceBreak>
) : RecyclerView.Adapter<WinningsListAdapter.AppliedCouponCodeHolder>() {

    var Rank: String = ""
    var Price: String = ""
    var contestID: String = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_winning_list, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        try {
            if (position % 2 == 0) {
                holder.itemView.ll_main.setBackgroundColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.colorContestItemBackground
                    )
                )
            } else {
                holder.itemView.ll_main.setBackgroundColor(ContextCompat.getColor(mContext, R.color.white))
            }
        } catch (e: Exception) {
            holder.itemView.ll_main.setBackgroundColor(mContext.resources.getColor(R.color.colorContestItemBackground))
        }
        if (list != null) {
            if (list.get(position).startNum == list.get(position).endNum) {
                Rank = "Rank " + list.get(position).startNum;        // It will display like Rank 1
                Price = list.get(position).priceEach.toString()
                contestID = list.get(position).contestId.toString()
            } else {
                Rank =
                    "Rank " + list.get(position).startNum + "-" + list.get(position).endNum;    // It will display like Rank 2 - 5
                Price = list.get(position).priceEach.toString()
                contestID = list.get(position).contestId.toString()
            }
        }
        holder.itemView.txt_TeamName.setText(Rank)
        holder.itemView.txt_rank.setText(Price)

//        holder.itemView.txt_Join.setOnClickListener {
//            mContext.startActivity(Intent(mContext, ContestDetailActivity::class.java))
//        }
    }


    override fun getItemCount(): Int {
        return list.size;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }


}