package stock.com.ui.dashboard.my_contest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_invite_user.view.*
import stock.com.R
import stock.com.ui.pojo.InviteData
import java.util.ArrayList

class InviteUserAdapter(
    val mContext: Context,
    val userData: ArrayList<InviteData.UserDatum>, val activity: ActivityInviteUser
) :

    RecyclerView.Adapter<InviteUserAdapter.FeatureListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_invite_user, parent, false)
        return FeatureListHolder(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(item: String)
        fun onItemUncheck(item: String)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.tv_username.setText(userData.get(position).username)
        holder.itemView.tv_level.setText(userData.get(position).levelType)
        holder.itemView.invite.setText(userData.get(position).contestInvite)
        Glide.with(mContext).load(userData.get(position).profileImage).into(holder.itemView.profile_image)
        if (userData.get(position).contestInvite.equals("Invited")) {
            holder.itemView.llInvite.isEnabled = false
            holder.itemView.llInvite.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    mContext,
                    R.drawable.button_backgroun_light_blue
                )
            )
            holder.itemView.imgbtnPlus.visibility = View.GONE
        } else {
            holder.itemView.llInvite.isEnabled = true
            holder.itemView.llInvite.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    mContext,
                    R.drawable.green_fill_button
                )
            )
            holder.itemView.imgbtnPlus.visibility = View.VISIBLE
        }

        holder.itemView.llInvite.setOnClickListener {
            activity.sendInvitation(userData.get(position).id)
        }

    }


    override fun getItemCount(): Int {
        return userData.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }
}