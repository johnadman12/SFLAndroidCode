package stock.com.ui.winningBreakup.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_winning_list.view.*
import stock.com.R


class WinningsListAdapter(val mContext: Context) : RecyclerView.Adapter<WinningsListAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_winning_list, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        try {
            if (position%2==0){
                holder.itemView.ll_main.setBackgroundColor(mContext.resources.getColor(R.color.colorContestItemBackground))
            }else{
                holder.itemView.ll_main.setBackgroundColor(mContext.resources.getColor(R.color.white))
            }
        }catch (e:Exception){
            holder.itemView.ll_main.setBackgroundColor(mContext.resources.getColor(R.color.colorContestItemBackground))
        }

//        holder.itemView.txt_Join.setOnClickListener {
//            mContext.startActivity(Intent(mContext, ContestDetailActivity::class.java))
//        }
    }


    override fun getItemCount(): Int {
        return 45;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }


}