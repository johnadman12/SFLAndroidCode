package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_spin_name.view.*
import stock.com.R
import stock.com.ui.pojo.ContestList

class ExchangespinAdapter(
    val mContext: Context,
    val list: ArrayList<ContestList.Market>, val activity: ActivityCreateContest
) :

    RecyclerView.Adapter<ExchangespinAdapter.AppliedCouponCodeHolder>() {

    override fun onBindViewHolder(holder: ExchangespinAdapter.AppliedCouponCodeHolder, position: Int) {
        holder.itemView.spinText.setText(list.get(position).name)

        holder.itemView.setOnClickListener {
            if (list.get(position).name.equals("Equity"))
                activity.setExchange(list.get(position).name, list.get(position).exchangelist,list.get(position).id)
            else
                activity.setExchange(list.get(position).name,list.get(position).id)
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