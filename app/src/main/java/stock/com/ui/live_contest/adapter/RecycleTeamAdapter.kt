package stock.com.ui.live_contest.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_live_team.view.*
import stock.com.R
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.dashboard.Team.LiveScoreActivity
import stock.com.ui.live_contest.LiveContestActivity
import stock.com.ui.pojo.ContestDetail
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant

class RecycleTeamAdapter(
    val mContext: Context,
    val scores: MutableList<ContestDetail.Score>,
    val clickManager: ItemClickListner
) : RecyclerView.Adapter<RecycleTeamAdapter.WatchListHolder>() {


    var checkedHolder: BooleanArray? = null;

    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(scores!!.size)

        for (i in 0 until checkedHolder!!.size) {
            if (i == 0)
                checkedHolder!!.set(i, true);
            else
                checkedHolder!!.set(i, false);
        }
    }

    init {
        createCheckedHolder();
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_live_team, parent, false)
        return WatchListHolder(view)
    }

    interface ItemClickListner {
        fun onItemClick(item: ContestDetail.Score)
    }

    override fun onBindViewHolder(holder: WatchListHolder, position: Int) {
        holder.itemView.tv_team.setText("Team - " + scores.get(position).teamNameCount)
        Log.d("dadadad", "--" + checkedHolder!!.get(position))

        if (checkedHolder!!.get(position) == false) {
            holder.itemView.tv_team.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    mContext,
                    R.drawable.green_empty_layout
                )
            )
        } else {
            holder.itemView.tv_team.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    mContext,
                    R.drawable.green_fill_button
                )
            )
        }

       /* if (position == 1) {
            holder.itemView.performClick()
        }*/
        holder.itemView.setOnClickListener {
            for (i in 0 until checkedHolder!!.size) {
                checkedHolder!!.set(i, false);
            }
            checkedHolder!!.set(position, true);

            clickManager.onItemClick(scores.get(position))
            notifyDataSetChanged();

        }
    }

    override fun getItemCount(): Int {
        return scores.size;
    }

    inner class WatchListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
