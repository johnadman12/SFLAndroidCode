package stock.com.ui.dashboard.Contestdeatil

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.row_rules.view.*
import stock.com.R
import stock.com.ui.pojo.ContestDetail

class RulesAdapter  (
    val mContext: Context,
    val rules: MutableList<ContestDetail.Rule>
) : RecyclerView.Adapter<RulesAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_rules, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {
        holder.itemView.tvRule.setText(rules.get(position).description)

//        holder.itemView.txt_Join.setOnClickListener {
//            mContext.startActivity(Intent(mContext, ContestDetailActivity::class.java))
//        }
    }


    override fun getItemCount(): Int {
        return rules.size;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


    }
}
