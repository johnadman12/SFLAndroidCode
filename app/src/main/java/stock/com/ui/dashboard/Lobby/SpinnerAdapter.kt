package stock.com.ui.dashboard.Lobby

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_spin_name.view.*
import stock.com.R
import stock.com.ui.pojo.ContestList

class SpinnerAdapter(
    val mContext: Context,
    val list: ArrayList<ContestList.Exchangelist>, val activity: ActivityCreateContest
) :

    RecyclerView.Adapter<SpinnerAdapter.AppliedCouponCodeHolder>() {

    override fun onBindViewHolder(holder: SpinnerAdapter.AppliedCouponCodeHolder, position: Int) {
        holder.itemView.spinText.setText(list.get(position).name)
        holder.itemView.setOnClickListener {
            activity.setMarket(list.get(position).name, list.get(position).exchangesId)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_spin_name, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }

}