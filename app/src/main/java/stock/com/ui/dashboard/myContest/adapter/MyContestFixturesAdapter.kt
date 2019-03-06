package stock.com.ui.dashboard.myContest.adapter

import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_match.view.*
import stock.com.BuildConfig
import stock.com.R
import stock.com.ui.joinedContest.activity.FixtureJoinedContestActivity


class MyContestFixturesAdapter(val mContext: Context) : RecyclerView.Adapter<MyContestFixturesAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        if (BuildConfig.APPLICATION_ID == "os.real11") {
            /*holder.itemView.txt_Countdown.setTextColor(mContext.resources.getColor(R.color.colorRed))
            for (drawable in holder.itemView.txt_Countdown.getCompoundDrawables()) {
                if (drawable != null) {
                    drawable.colorFilter = PorterDuffColorFilter(
                        ContextCompat.getColor(
                            holder.itemView.txt_Countdown.context,
                            (R.color.colorRed)
                        ), PorterDuff.Mode.SRC_IN
                    )
                }
            }*/
        }
        holder.itemView.card_view.setOnClickListener {
            mContext.startActivity(Intent(mContext, FixtureJoinedContestActivity::class.java))
        }
    }


    override fun getItemCount(): Int {
        return 9
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }


}