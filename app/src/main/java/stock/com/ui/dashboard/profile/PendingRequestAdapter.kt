package stock.com.ui.dashboard.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_pending_request.view.*
import stock.com.R
import stock.com.ui.pojo.PendingList

class PendingRequestAdapter(
    val mContext: Context,
    val list: ArrayList<PendingList.UserDatum>?,
    val activity: PendingRequestActivity
) : RecyclerView.Adapter<PendingRequestAdapter.FeatureListHolder>() {


    override fun getItemCount(): Int {
        return list!!.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_pending_request, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.tv_username.setText(list!!.get(position).username)
        holder.itemView.tv_user_level.setText(list.get(position).levelType)
        Glide.with(mContext).load(list.get(position).profileImage).into(holder.itemView.profile_image)
        holder.itemView.tv_accept.setOnClickListener {
            activity.addTofriendList(list.get(position).manageFriendId, "1")
        }

        holder.itemView.tv_reject.setOnClickListener {
            activity.addTofriendList(list.get(position).manageFriendId, "0")
        }
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}