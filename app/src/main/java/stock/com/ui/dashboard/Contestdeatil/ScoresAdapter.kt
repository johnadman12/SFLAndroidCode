package stock.com.ui.dashboard.Contestdeatil

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_contest_score.view.*
import stock.com.R
import stock.com.ui.pojo.ContestDetail

class ScoresAdapter(
    val mContext: Context,
    val scores: MutableList<ContestDetail.Score>
) : RecyclerView.Adapter<ScoresAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_contest_score, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        holder.itemView.tvPoints.setText(scores.get(position).points)
        holder.itemView.tvRank.setText(scores.get(position).rank + " (T -" + scores.get(position).teamId + ")")
        holder.itemView.username.setText(scores.get(position).username)
        Glide.with(mContext).load(scores.get(position).image).into(holder.itemView.iv_user)


    }


    override fun getItemCount(): Int {
        return scores.size;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }
}