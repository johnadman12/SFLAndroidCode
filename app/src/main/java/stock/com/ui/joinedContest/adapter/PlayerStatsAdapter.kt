package stock.com.ui.joinedContest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_playerstats_completed_joined.view.*
import stock.com.R

class PlayerStatsAdapter(val mContext: AppCompatActivity) :
    RecyclerView.Adapter<PlayerStatsAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_playerstats_completed_joined, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        try {
            if (position % 3 == 0) {
                holder.itemView.img_playerSelected.isSelected = true
            } else {
                holder.itemView.img_playerSelected.isSelected = false
            }
            if (position % 2 == 0) {
                holder.itemView.img_starPlayer.isSelected = true
            } else {
                holder.itemView.img_starPlayer.isSelected = false
            }
        } catch (e: Exception) {
//            holder.itemView.ll_main.setBackgroundColor(mContext.resources.getColor(R.color.colorContestItemBackground))
        }
    }

    override fun getItemCount(): Int {
        return 30;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}