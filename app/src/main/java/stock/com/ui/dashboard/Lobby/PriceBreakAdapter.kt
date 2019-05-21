package stock.com.ui.dashboard.Lobby

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stock.com.R
import stock.com.ui.pojo.PriceBreak


class PriceBreakAdapter(val mContext: Context) : RecyclerView.Adapter<PriceBreakAdapter.AppliedCouponCodeHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_winner_list, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
         }

    override fun getItemCount(): Int {
        return 7
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}
