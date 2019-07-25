package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_forex.view.*
import stock.com.R
import stock.com.ui.dashboard.Team.ActivityMarketDetail
import stock.com.ui.pojo.CurrencyPojo
import stock.com.utils.StockConstant

class ForexAdapter(
    val mContext: Context,
    val marketData: ArrayList<CurrencyPojo.Currency>,
    val frgament: CurrencyFragment,
    val forex: ArrayList<CurrencyPojo.Currency>
) :
    RecyclerView.Adapter<ForexAdapter.FeatureListHolder>() {
    var checkedHolder: BooleanArray? = null;
    private var search: List<CurrencyPojo.Currency>? = null

    private fun createCheckedHolder() {
        checkedHolder = BooleanArray(marketData.size)
    }

    init {
        this.search = forex;
        createCheckedHolder();
        Log.d("gugugugugu", "---5465466---" + search!!.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForexAdapter.FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_forex, parent, false)
        return FeatureListHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ForexAdapter.FeatureListHolder, position: Int) {

        Glide.with(mContext).load(forex.get(position).firstflag).into(holder.itemView.img1)
        Glide.with(mContext).load(forex.get(position).secondflag).into(holder.itemView.img2)

        if (!TextUtils.isEmpty(forex.get(position).changeper)) {
            if (forex.get(position).changeper.contains("-")) {
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.redcolor))
                holder.itemView.tv_change.setTextColor(ContextCompat.getColor(mContext, R.color.redcolor))
                Glide.with(mContext).load(R.drawable.ic_down_arrow).into(holder.itemView.graph)
                holder.itemView.tv_change_percentage.setText(
                    forex!!.get(position).changeper + "%" + "  (" + forex!!.get(
                        position
                    ).daychange + ")"
                )
            } else {
                Glide.with(mContext).load(R.drawable.ic_arrow_up).into(holder.itemView.graph)
                holder.itemView.tv_change_percentage.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change.setTextColor(ContextCompat.getColor(mContext, R.color.green))
                holder.itemView.tv_change_percentage.setText(
                    forex!!.get(position).changeper + "%" + "  (" + forex!!.get(
                        position
                    ).daychange + ")"
                )
            }
        }
        if (forex.get(position).cryptoType.equals("1")) {
            holder.itemView.llAdd.visibility = View.GONE
            holder.itemView.img_check.visibility = View.VISIBLE
            holder.itemView.llwatch.isEnabled = false
        } else {
            holder.itemView.llAdd.visibility = View.VISIBLE
            holder.itemView.img_check.visibility = View.GONE
        }
        holder.itemView.name.setText(forex.get(position).symbol)
        holder.itemView.bid.setText(forex.get(position).bid)
        holder.itemView.ask.setText(forex.get(position).ask)
        holder.itemView.tv_company.setText("(" + forex.get(position).name + ")")

        holder.itemView.llwatch.setOnClickListener {
            frgament.saveToWatchList(forex.get(position).currencyid)
            forex.get(position).cryptoType = "1"
            holder.itemView.llAdd.visibility = View.GONE
            holder.itemView.img_check.visibility = View.VISIBLE
            holder.itemView.llwatch.isEnabled = false
        }


        holder.itemView.setOnClickListener {
            var intent = Intent(mContext, ActivityMarketDetail::class.java);
            intent.putExtra("cryptoId", forex.get(position).currencyid)
            intent.putExtra(StockConstant.MARKETLIST, forex)
            intent.putExtra(StockConstant.SELECTEDSTOCK, 0)
            intent.putExtra("flag", 2)
            ActivityCompat.startActivityForResult(mContext as Activity, intent, 410, null);
        }

    }

    override fun getItemCount(): Int {
        return search!!.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }


    fun View.blink(
        times: Int = Animation.INFINITE,
        duration: Long = 50L,
        offset: Long = 20L,
        minAlpha: Float = 0.0f,
        maxAlpha: Float = 1.0f,
        repeatMode: Int = Animation.REVERSE
    ) {
        startAnimation(AlphaAnimation(minAlpha, maxAlpha).also {
            it.duration = duration
            it.startOffset = offset
            it.repeatMode = repeatMode
            it.repeatCount = times
        })
    }
}