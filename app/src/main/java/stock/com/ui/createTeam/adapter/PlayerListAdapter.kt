package stock.com.ui.createTeam.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_teamplayer.view.*
import stock.com.BuildConfig
import stock.com.R
import stock.com.ui.createTeam.activity.Choose_C_VC_Activity
import stock.com.ui.createTeam.activity.PlayerDetailActivity

class PlayerListAdapter(val mContext: Context) : RecyclerView.Adapter<PlayerListAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teamplayer, parent, false)
        return AppliedCouponCodeHolder(view)
        /*if (BuildConfig.APPLICATION_ID == "os.real11")
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_teamplayer, parent, false)
            return AppliedCouponCodeHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_teamplayer_real11,
                    parent,
                    false
                )
            )
        else
            return AppliedCouponCodeHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_teamplayer,
                    parent,
                    false
                )
            )*/
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        /*try {
            if (position % 2 == 0) {
                holder.itemView.ll_ADD.setBackgroundColor(mContext.resources.getColor(R.color.colorItemBackgroundGray))
                holder.itemView.ll_main.setBackgroundColor(mContext.resources.getColor(R.color.colorItemBackgroundLightGray))
            } else {
                holder.itemView.ll_main.setBackgroundColor(mContext.resources.getColor(R.color.white))
                holder.itemView.ll_ADD.setBackgroundColor(mContext.resources.getColor(R.color.colorItemBackgroundLightGray))
            }
        } catch (e: Exception) {
            holder.itemView.ll_main.setBackgroundColor(mContext.resources.getColor(R.color.colorContestItemBackground))
        }*/
        /*holder.itemView.img_add.setOnClickListener {
            holder.itemView.img_add.isSelected = !holder.itemView.img_add.isSelected
        }
        holder.itemView.cimg_player.setOnClickListener {
            mContext.startActivity(Intent(mContext, PlayerDetailActivity::class.java))
        }*/

        holder.itemView.setOnClickListener {
            mContext.startActivity(Intent(mContext, Choose_C_VC_Activity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return 11
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }


}