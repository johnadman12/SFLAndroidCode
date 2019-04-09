package stock.com.ui.contest.adapter.ContestAdapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_contest.view.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.contest.activity.ContestDetailActivity
import stock.com.ui.winningBreakup.dialogues.BottomSheetWinningListFragment


class ContestAdapter(val mContext: AppCompatActivity) : RecyclerView.Adapter<ContestAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contest, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        holder.itemView.ll_entryFee.setOnClickListener {
            mContext.startActivity(Intent(mContext, ContestDetailActivity::class.java))
        }
//        holder.itemView.ll_contestname.setOnClickListener {
//            mContext.startActivity(Intent(mContext, ContestDetailActivity::class.java))
//        }
        holder.itemView.ll_totalWinners.setOnClickListener {
          /*  val bottomSheetDialogFragment = BottomSheetWinningListFragment(list.get(position).priceBreak)
            bottomSheetDialogFragment.show(mContext.supportFragmentManager, "Bottom Sheet Dialog Fragment")
         */   //            mContext.startActivity(Intent(mContext, ContestDetailActivity::class.java))
        }
        holder.itemView.ll_totalWinnings.setOnClickListener {
            mContext.startActivity(Intent(mContext, ContestDetailActivity::class.java))
        }
        holder.itemView.txt_Join.setOnClickListener {
            (mContext as BaseActivity).showJoinContestDialogue(mContext)
//            mContext.startActivity( Intent(mContext, ChooseTeamActivity::class.java))
        }

    }


    override fun getItemCount(): Int {
        return 3;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }


}