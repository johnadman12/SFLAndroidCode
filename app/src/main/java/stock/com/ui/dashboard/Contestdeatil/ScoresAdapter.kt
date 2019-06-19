package stock.com.ui.dashboard.Contestdeatil

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irozon.sneaker.Sneaker
import kotlinx.android.synthetic.main.row_contest_score.view.*
import stock.com.R
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.dashboard.home.MarketList.MarketTeamPreviewActivity
import stock.com.ui.pojo.ContestDetail
import stock.com.utils.StockConstant

class ScoresAdapter(
    val mContext: Context,
    val userId: Int,
    val scores: MutableList<ContestDetail.Score>,
    val flag: Int,
    val marketName: String
) : RecyclerView.Adapter<ScoresAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_contest_score, parent, false)
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
                if (!TextUtils.isEmpty(marketName) && marketName.equals("Equity"))
                    mContext.startActivity(
                        Intent(mContext, TeamPreviewActivity::class.java)
                            .putExtra(StockConstant.STOCKLIST, scores.get(position).stock)
                            .putExtra(StockConstant.TOTALCHANGE, scores.get(position).totalchange_Per)
                    ) else
                    mContext.startActivity(
                        Intent(mContext, MarketTeamPreviewActivity::class.java)
                            .putExtra(StockConstant.MARKETLIST, scores.get(position).crypto)
                            .putExtra(StockConstant.TOTALCHANGE, scores.get(position).totalchange_Per)
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