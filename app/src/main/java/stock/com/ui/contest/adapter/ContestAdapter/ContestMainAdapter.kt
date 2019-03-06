package stock.com.ui.contest.adapter.ContestAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_contest_header.view.*
import stock.com.R


class ContestMainAdapter(val mContext: AppCompatActivity) : RecyclerView.Adapter<ContestMainAdapter.AppliedCouponCodeHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppliedCouponCodeHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contest_header, parent, false)
        return AppliedCouponCodeHolder(view)
    }

    override fun onBindViewHolder(holder: AppliedCouponCodeHolder, position: Int) {

    }


    override fun getItemCount(): Int {
        return 5;
    }

    inner class AppliedCouponCodeHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /* init method call itself when class call & set layout manger for recycler view*/
      init {
            val llm = LinearLayoutManager(mContext)
            llm.orientation = LinearLayoutManager.VERTICAL
            itemView.rv_subContest.layoutManager = llm
            itemView.rv_subContest.adapter = ContestAdapter(mContext)


        }
    }


}