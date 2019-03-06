package stock.com.ui.notification.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stock.com.R


class NotificationAdapter(val mContext: Context) : RecyclerView.Adapter<NotificationAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notification, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
//        holder.itemView.ll_entryFee.setOnClickListener {
//            mContext.startActivity(Intent(mContext, ContestDetailActivity::class.java))
//        }
//        holder.itemView.ll_totalWinners.setOnClickListener {
//            //            mContext.startActivity(Intent(mContext, ContestDetailActivity::class.java))
//        }
//        holder.itemView.ll_totalWinnings.setOnClickListener {
//            mContext.startActivity(Intent(mContext, ContestDetailActivity::class.java))
//        }

    }

    override fun getItemCount(): Int {
        return 15;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }


}