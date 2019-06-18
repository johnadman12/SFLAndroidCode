package stock.com.ui.friends

import android.annotation.SuppressLint
import android.content.Context
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
import stock.com.ui.pojo.FriendsList


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
        Glide.with(mContext).load(list!!.get(position).profileImage).into(holder.itemView.profile_image)
        holder.itemView.llADD.setOnClickListener {
            holder.itemView.tv_add.setText("Added")
            holder.itemView.add.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.ic_check_white))
            activity.addTofriendList(list!!.get(position).id)
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

