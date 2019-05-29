package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_scores.view.*
import stock.com.R
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.pojo.Scores
import stock.com.utils.StockConstant

class LiveScoreAdapter(
    val mContext: Context,
    val userId: Int,
    val scores: ArrayList<Scores.Score>,
    val flag: Int
) : RecyclerView.Adapter<LiveScoreAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_scores, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    @SuppressLint("WrongConstant")
    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        holder.itemView.tvPoints.setText(scores.get(position).points)
//        holder.itemView.tvPercentage.setText(scores.get(position).)
        holder.itemView.tvRank.setText(scores.get(position).rank)
        holder.itemView.username.setText(scores.get(position).username + " (" + scores.get(position).teamNameCount + ")")
        Glide.with(mContext).load(scores.get(position).image).into(holder.itemView.iv_user)
//        if (flag == 0) {
        holder.itemView.setOnClickListener {
            if (userId == scores.get(position).userid) {
                mContext.startActivity(
                    Intent(mContext, TeamPreviewActivity::class.java)
                        .putExtra(StockConstant.STOCKLIST, scores.get(position).stock)
                )
            } else {

            }
        }
//        }

    }


    override fun getItemCount(): Int {
        return scores.size;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }
}