package stock.com.ui.createTeam.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_player.view.*
import stock.com.BuildConfig
import stock.com.R
import stock.com.ui.createTeam.activity.PlayerDetailActivity
import stock.com.interfaces.OnClickCVC
import stock.com.model.PlayerData
import stock.com.ui.createTeam.activity.StockDetailActivity


class ChooseC_VC_Adapter(val mContext: Context, val onClickCVC: OnClickCVC, val playerList: MutableList<PlayerData>) :
    RecyclerView.Adapter<ChooseC_VC_Adapter.AppliedCouponCodeHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        if (BuildConfig.APPLICATION_ID == "os.real11") {
            holder.itemView.ll_main.setBackgroundColor(mContext.resources.getColor(R.color.white))

        }
        holder.itemView.ll_stock_detial.setOnClickListener {
            mContext.startActivity(Intent(mContext, StockDetailActivity::class.java))
        }
       /* holder.itemView.cimg_player.setOnClickListener {
            mContext.startActivity(Intent(mContext, PlayerDetailActivity::class.java))
        }
        if (position == 0) {
            holder.itemView.txt_playerType.visibility = View.VISIBLE
            holder.itemView.txt_playerType.text = "Wicket-Keeper"
        } else if (position == 1) {
            holder.itemView.txt_playerType.visibility = View.VISIBLE
            holder.itemView.txt_playerType.text = "Batsmen"
        } else if (position == 5) {
            holder.itemView.txt_playerType.visibility = View.VISIBLE
            holder.itemView.txt_playerType.text = "All-Rounder"
        } else if (position == 7) {
            holder.itemView.txt_playerType.visibility = View.VISIBLE
            holder.itemView.txt_playerType.text = "Bowler"
        } else {
            holder.itemView.txt_playerType.visibility = View.GONE
        }

        holder.itemView.img_captain.isSelected = playerList[position].isCaptain
        holder.itemView.img_vc.isSelected = playerList[position].isViceCaptain
        holder.itemView.img_captain.setOnClickListener {
            onClickCVC.onClick("c", holder.adapterPosition)
        }
        holder.itemView.img_vc.setOnClickListener {
            onClickCVC.onClick("vc", holder.adapterPosition)
        }*/
    }


    override fun getItemCount(): Int {
        return playerList.size
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }


}