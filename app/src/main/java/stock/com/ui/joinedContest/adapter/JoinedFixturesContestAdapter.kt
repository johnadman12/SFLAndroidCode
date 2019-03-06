package stock.com.ui.joinedContest.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_joined_contest.view.*
import stock.com.R
import stock.com.ui.joinedContest.activity.FixtureJoinedContestDetailActivity

class JoinedFixturesContestAdapter(val mContext: AppCompatActivity) :
    RecyclerView.Adapter<JoinedFixturesContestAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_joined_contest, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        holder.itemView.card_view.setOnClickListener {
            mContext.startActivity(Intent(mContext, FixtureJoinedContestDetailActivity::class.java))
        }
//
//        holder.itemView.ll_totalWinners.setOnClickListener {
//            val bottomSheetDialogFragment = BottomSheetWinningListFragment()
//            bottomSheetDialogFragment.show(mContext.supportFragmentManager, "Bottom Sheet Dialog Fragment")
//            //            mContext.startActivity(Intent(mContext, ContestDetailActivity::class.java))
//        }
//        holder.itemView.ll_totalWinnings.setOnClickListener {
//            mContext.startActivity(Intent(mContext, ContestDetailActivity::class.java))
//        }
//        holder.itemView.txt_Join.setOnClickListener {
//            (mContext as BaseActivity).showJoinContestDialogue(mContext)
////            mContext.startActivity(Intent(mContext, ChooseTeamActivity::class.java))
//        }
    }

    override fun getItemCount(): Int {
        return 2;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}