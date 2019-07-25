package stock.com.ui.dashboard.home.Currency

import android.content.Context
import android.os.CountDownTimer
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Filter
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_team.view.*
import stock.com.R
import stock.com.ui.dashboard.home.MarketList.ActivityMarketTeam
import stock.com.ui.pojo.CurrencyPojo
import stock.com.ui.pojo.MarketList
import stock.com.utils.AppDelegate

class CurrencyTeamAdapter(
    val mContext: Context, val mContest: MutableList<CurrencyPojo.Currency>,
    var mContestOld: MutableList<CurrencyPojo.Currency>,
    val activity: ActivityCurrencyTeam, val onItemCheckListener: OnItemCheckListener
) :
    RecyclerView.Adapter<CurrencyTeamAdapter.FeatureListHolder>() {


    override fun getItemCount(): Int {
        return mContestOld.size
    }

    var checkedHolder: BooleanArray? = null;


    private var searchList: List<CurrencyPojo.Currency>? = null


    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(mContest.size)
    }

    init {
        this.searchList = mContest;
        createCheckedHolder();
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        val anim = AlphaAnimation(0.1f, 1.0f)
        anim.duration = 50 //You can manage the blinking time with this parameter
        anim.startOffset = 20

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_currency_team, parent, false)
        return FeatureListHolder(view)
    }

    interface OnItemCheckListener {
        fun onItemCheck(item: CurrencyPojo.Currency)
        fun onItemUncheck(item: CurrencyPojo.Currency)
        fun onItemClick(item: CurrencyPojo.Currency)
        fun onToggleCheck(item: CurrencyPojo.Currency)
        fun onToggleUncheck(item:CurrencyPojo.Currency)
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setOnClickListener(onClickListener: View.OnClickListener) {
            itemView.img_add.setOnClickListener(onClickListener)
            itemView.llremoveStock.setOnClickListener(onClickListener)
            notifyDataSetChanged()
        }
    }


}