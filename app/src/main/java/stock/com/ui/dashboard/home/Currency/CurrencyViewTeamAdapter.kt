package stock.com.ui.dashboard.home.Currency

import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_view_team_currency.view.*
import stock.com.R
import stock.com.ui.pojo.CurrencyPojo

class CurrencyViewTeamAdapter(
    val mContext: Context, val mContest: MutableList<CurrencyPojo.Currency>,
    val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<CurrencyViewTeamAdapter.FeatureListHolder>() {
    var list: ArrayList<CurrencyPojo.Currency> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_team_currency, parent, false)
        return FeatureListHolder(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(sizeContest: Int, item: CurrencyPojo.Currency)
        fun onItemClick(item: CurrencyPojo.Currency)
        fun onToggleSell(item: CurrencyPojo.Currency)
        fun onToggleBuy(item: CurrencyPojo.Currency)
        fun onRemoveIteam(item: CurrencyPojo.Currency)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        list = ArrayList()
        list = mContest as ArrayList
        holder.itemView.tvSymbol.setText(mContest.get(position).symbol)
        holder.itemView.tvCompanyName.setText(mContest.get(position).name)
        holder.itemView.ll_market.visibility = View.VISIBLE
        holder.itemView.prev.visibility = View.GONE
        holder.itemView.img_time.visibility = View.GONE
        holder.itemView.tvPrevClose.visibility = View.GONE
        holder.itemView.llVol.visibility = View.GONE
        holder.itemView.tvlatestVolume.setText(mContest.get(position).latestVolume)
        Glide.with(mContext).load(mContest.get(position).firstflag).into(holder.itemView.img1)
        Glide.with(mContext).load(mContest.get(position).secondflag).into(holder.itemView.img2)

        holder.itemView.llremoveStock.visibility = View.VISIBLE
        holder.itemView.img_add.visibility = View.GONE
        mContest.get(position).addedToList = 1

        /* if (!TextUtils.isEmpty(mContest.get(position).changeper))
             if (mContest.get(position).changeper.contains("-")) {
                 Glide.with(mContext).load(R.mipmap.downred).into(holder.itemView.img_graph)
                 holder.itemView.tvPercentage.setText(mContest.get(position).changeper)
             } else {
                 Glide.with(mContext).load(R.mipmap.upgraph).into(holder.itemView.img_graph)
                 holder.itemView.tvPercentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                 holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                 holder.itemView.tv_change_percentage.setText("+" + mContest.get(position).changeper)
             }*/



        holder.itemView.llremoveStock.setOnClickListener {
            mContest.remove(mContest.get(position))
            onItemCheckListener.onRemoveIteam(mContest.get(position))
            notifyDataSetChanged()
           /* mContest.remove(mContest.get(position))
            notifyDataSetChanged()
            onItemCheckListener.onItemCheck((mContest.size), mContest.get(position))
            mContest.get(position).addedToList = 0*/
        }


        holder.itemView.setOnClickListener {
            onItemCheckListener.onItemClick(mContest.get(position))
        }

        if (mContest.get(position).addedToList == 1) {
            holder.itemView.llremoveStock.visibility = View.VISIBLE
            holder.itemView.img_add.visibility = View.GONE
        } else if (mContest.get(position).addedToList == 0) {
            holder.itemView.llremoveStock.visibility = View.GONE
            holder.itemView.img_add.visibility = View.VISIBLE
        }

        if (mContest.get(position).cryptoType.equals("0"))
            holder.itemView.toggleButton1.isChecked = true
        else if (mContest.get(position).cryptoType.equals("1")) {
            holder.itemView.toggleButton1.isChecked = false
        }

        holder.itemView.toggleButton1.setOnClickListener {
            if (holder.itemView.toggleButton1.isChecked) {
                onItemCheckListener.onToggleSell(mContest.get(position))
            } else
            onItemCheckListener.onToggleBuy(mContest.get(position))
        }

    }


    override fun getItemCount(): Int {
        return mContest.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.toggleButton1.setOnClickListener(onClickListener)
            itemView.llremoveStock.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }
}