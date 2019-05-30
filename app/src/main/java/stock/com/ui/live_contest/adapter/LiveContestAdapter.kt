package stock.com.ui.live_contest.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_live_contest.view.*
import stock.com.R
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.pojo.ContestDetail
import stock.com.utils.StockConstant

class LiveContestAdapter(
    val mContext: Context,
    val userId: Int,
    val scores: MutableList<ContestDetail.Score>
) : RecyclerView.Adapter<LiveContestAdapter.WatchListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_live_contest, parent, false)
        return WatchListHolder(view)
    }

    override fun onBindViewHolder(holder: WatchListHolder, position: Int) {
        holder.itemView.tvPoints.setText(scores.get(position).points)
        holder.itemView.tvRank.setText("Rank :" + scores.get(position).rank)
        holder.itemView.username.setText(scores.get(position).username + " (" + scores.get(position).teamNameCount + ")")
        Glide.with(mContext).load(scores.get(position).image).into(holder.itemView.profile_image)



        holder.itemView.setOnClickListener {
            if (userId == scores.get(position).userid) {
                mContext.startActivity(
                    Intent(mContext, TeamPreviewActivity::class.java)
                        .putExtra(StockConstant.STOCKLIST, scores.get(position).stock)
                )
            } else {
//                Toast.makeText(mContext, "Contest is not live yet.", 10000).show()

            }
        }
    }

    override fun getItemCount(): Int {
        return scores!!.size;
    }

    inner class WatchListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}

