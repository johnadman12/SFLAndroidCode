package stock.com.ui.dashboard.home.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_match.view.*
import stock.com.BuildConfig
import stock.com.R


class MatchFixturesAdapter(val mContext: Context) :
    RecyclerView.Adapter<MatchFixturesAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        if (BuildConfig.APPLICATION_ID == "os.real11") {
            /*holder.itemView.txt_Countdown.setTextColor(mContext.resources.getColor(R.color.colorRed))
            for (drawable in holder.itemView.txt_Countdown.compoundDrawables) {
                if (drawable != null) {
                    drawable.colorFilter = PorterDuffColorFilter(
                        mContext.resources.getColor(R.color.colorRed), PorterDuff.Mode.SRC_IN
                    )
                }
            }*/
        }
        holder.itemView.view2.visibility = View.GONE
        holder.itemView.txt_contestJoined.visibility = View.GONE
        holder.itemView.card_view.setOnClickListener {
        }
    }


    override fun getItemCount(): Int {
        return 15;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }


}