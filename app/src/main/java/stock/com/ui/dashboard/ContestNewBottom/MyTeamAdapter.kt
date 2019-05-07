package stock.com.ui.dashboard.ContestNewBottom

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_my_team.view.*
import stock.com.R
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.dashboard.Team.ActivityEditTeam
import stock.com.ui.dashboard.Team.ActivityMyTeamView
import stock.com.ui.pojo.MyTeamsPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MyTeamAdapter(
    val mContext: Context,
    val myteam: MutableList<MyTeamsPojo.Myteam>
) :
    RecyclerView.Adapter<MyTeamAdapter.FeatureListHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_my_team, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.stockName.setText(myteam.get(position).exchangename)
        holder.itemView.totalChange.setText(myteam.get(position).totalchangePercentage.toString() + " %")
        holder.itemView.tvDate.setText(parseDateToddMMyyyy(myteam.get(position).created))
        Glide.with(mContext).load(AppDelegate.EXCHANGE_URL + myteam.get(position).exchangeimage.trim())
            .into(holder.itemView.ivStock)

        holder.itemView.txt_team.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, ActivityEditTeam::class.java)
                    .putExtra(StockConstant.STOCKLIST, myteam.get(position).stock)
                    .putExtra(StockConstant.TEAMID, myteam.get(position).teamId)
                    .putExtra(StockConstant.EXCHANGEID, myteam.get(position).exchangeid)
            )
        }

        holder.itemView.relClone.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, ActivityEditTeam::class.java)
                    .putExtra(StockConstant.STOCKLIST, myteam.get(position).stock)
                    .putExtra(StockConstant.TEAMID, myteam.get(position).teamId)
                    .putExtra(StockConstant.EXCHANGEID, myteam.get(position).exchangeid)
            )
        }

        holder.itemView.relViewTeam.setOnClickListener {
            mContext.startActivity(
                Intent(mContext, TeamPreviewActivity::class.java)
                    .putExtra(StockConstant.STOCKLIST, myteam.get(position).stock)
//                    .putExtra(StockConstant.TEAMID, myteam.get(position).teamId)
            )
        }


        holder.itemView.relShare.setOnClickListener {
            shareIntent()
        }
    }


    override fun getItemCount(): Int {
        return myteam.size
    }

    inner class FeatureListHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    fun parseDateToddMMyyyy(time: String): String? {
        val inputPattern = "yyyy-MM-dd HH:mm:ss"
        val outputPattern = "dd MMM h:mm a"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)

        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }

    fun shareIntent() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, "share your team");
        mContext.startActivity(Intent.createChooser(shareIntent, mContext.getString(R.string.send_to)))
    }
}