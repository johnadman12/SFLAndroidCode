package stock.com.ui.friends

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_friends.view.*

import stock.com.R
import stock.com.ui.dashboard.profile.ActivityOtherUserProfile
import stock.com.ui.pojo.FriendsList
import stock.com.utils.StockConstant


class FriendAdapter(
    val mContext: Context,
    val list1: ArrayList<FriendsList.UserDatum>, val activity: FriendsActivity
) : RecyclerView.Adapter<FriendAdapter.FeatureListHolder>(), Filterable {

    private var list: java.util.ArrayList<FriendsList.UserDatum>? = null

    init {
        this.list = list1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_friends, parent, false)

        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.tv_username.setText(list!!.get(position).username)
        holder.itemView.tv_user_level.setText(list!!.get(position).levelType)
        holder.itemView.tv_add.setText(list!!.get(position).invite_status)
        Glide.with(mContext).load(list!!.get(position).profileImage).into(holder.itemView.profile_image)
        if (list!!.get(position).invite_status.equals("pending")) {
            holder.itemView.llADD.isEnabled = false
            holder.itemView.add.visibility = View.GONE
            holder.itemView.llADD.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    mContext,
                    R.drawable.button_backgroun_light_blue
                )
            )

        } else if (list!!.get(position).invite_status.equals("remove")) {
            holder.itemView.llADD.isEnabled = true
            holder.itemView.llADD.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.red_fill_button))
            holder.itemView.add.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_close))

        } else {
            holder.itemView.llADD.isEnabled = true
            holder.itemView.llADD.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    mContext,
                    R.drawable.green_fill_button
                )
            )
        }

        holder.itemView.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, ActivityOtherUserProfile::class.java)
                    .putExtra(StockConstant.FRIENDID, list!!.get(position).id)
            )
        }

        holder.itemView.llADD.setOnClickListener {
            if (list!!.get(position).invite_status.equals("remove")) {
                activity.addTofriendList(list!!.get(position).id, "0")
            } else if (list!!.get(position).invite_status.equals("Add")) {
                activity.addTofriendList(list!!.get(position).id, "1")
            }


            /* if (list!!.get(position).invite_status.equals("Add")) {
                 holder.itemView.tv_add.setText("Added")
                 holder.itemView.add.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_check))

             } else if (list!!.get(position).invite_status.equals("remove")) {
                 holder.itemView.tv_add.setText("Removed")
                 holder.itemView.add.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_close))
             }*/

        }


    }


    override fun getItemCount(): Int {
        return list!!.size;
    }


    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override
    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    list = list1
                } else {
                    val filteredList = java.util.ArrayList<FriendsList.UserDatum>()
                    for (row in list1) {
                        if (row.username.toLowerCase().contains(charString.toLowerCase()))
                            filteredList.add(row)
                    }
                    list = filteredList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = list
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: Filter.FilterResults) {
                list = filterResults.values as java.util.ArrayList<FriendsList.UserDatum>
                notifyDataSetChanged()
            }
        }
    }
}

