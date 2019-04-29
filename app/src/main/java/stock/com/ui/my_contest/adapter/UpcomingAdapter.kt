package stock.com.ui.my_contest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stock.com.R
import stock.com.ui.pojo.LobbyContestPojo

class UpcomingAdapter(
    val mContext: Context,
    val contest: ArrayList<LobbyContestPojo.Contest>
) : RecyclerView.Adapter<UpcomingAdapter.ContestListHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContestListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_my_contest, parent, false)

        return ContestListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ContestListHolder, position: Int) {
        /* holder.itemView.card_view.setPadding(0,0,0,0);
         holder.itemView.card_view.setUseCompatPadding(true);
         holder.itemView.card_view.setContentPadding(-6,-600,-6,-6);
         holder.itemView.card_view.setPreventCornerOverlap(false);*/


    }


    override fun getItemCount(): Int {
        return contest.size;
    }


    inner class ContestListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}
