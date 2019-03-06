package stock.com.ui.joinedContest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_joined_completed_list.view.*
import stock.com.R
import stock.com.ui.joinedContest.activity.LeaderShipBoardActivity

class JoinedCompletedContestAdapter(val mContext: AppCompatActivity) :
    RecyclerView.Adapter<JoinedCompletedContestAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_joined_completed_list, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        holder.itemView.txt_ViewLeaderShipBoard.setOnClickListener {
            mContext.startActivity(Intent(mContext, LeaderShipBoardActivity::class.java))
        }

    }

    override fun getItemCount(): Int {
        return 15;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}