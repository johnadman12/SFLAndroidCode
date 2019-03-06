package stock.com.ui.createTeam.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stock.com.R


class PlayerDetailFantasyStatusAdapter(val mContext: Context) : RecyclerView.Adapter<PlayerDetailFantasyStatusAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_fantasy_status, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 15;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }


}