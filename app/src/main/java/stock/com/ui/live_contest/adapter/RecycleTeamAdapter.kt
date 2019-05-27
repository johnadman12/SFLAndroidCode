package stock.com.ui.live_contest.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_live_team.view.*
import stock.com.R
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.dashboard.Team.LiveScoreActivity
import stock.com.ui.live_contest.LiveContestActivity
import stock.com.ui.pojo.ContestDetail
import stock.com.utils.StockConstant

class RecycleTeamAdapter(
    val mContext: Context,
    val userId: Int,
    val scores: MutableList<ContestDetail.Score>,
    val activity: LiveContestActivity
) : RecyclerView.Adapter<RecycleTeamAdapter.WatchListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_live_team, parent, false)
        return WatchListHolder(view)
    }

    override fun onBindViewHolder(holder: WatchListHolder, position: Int) {
        holder.itemView.tv_team.setText(scores.get(position).teamNameCount)

        holder.itemView.setOnClickListener {
            if (userId == scores.get(position).userid) {
                activity.setDataForTeam(scores, position)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return scores.size;
    }

    inner class WatchListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}
