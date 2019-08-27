package stock.com.ui.dashboard.ContestNewBottom

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.row_my_team.view.*
import stock.com.R
import stock.com.networkCall.ApiConstant
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.dashboard.Team.ActivityCreateTeam
import stock.com.ui.dashboard.home.Currency.ActivityCurrencyTeam
import stock.com.ui.dashboard.home.Currency.CurrencyPreviewTeamActivity
import stock.com.ui.dashboard.home.MarketList.ActivityMarketTeam
import stock.com.ui.dashboard.home.MarketList.MarketTeamPreviewActivity
import stock.com.ui.pojo.MyTeamsPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import androidx.constraintlayout.solver.widgets.WidgetContainer.getBounds
import androidx.core.app.ActivityCompat.startActivityForResult


class MyTeamAdapter(
    val mContext: Context,
    val myteam: MutableList<MyTeamsPojo.Myteamss>,
    val activityMyTeam: ActivityMyTeam,
    val contestId: Int
) :
    RecyclerView.Adapter<MyTeamAdapter.FeatureListHolder>() {

    var teamName: String = ""
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeatureListHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_my_team, parent, false)
        return FeatureListHolder(view)
    }

    override fun onBindViewHolder(holder: FeatureListHolder, position: Int) {
        holder.itemView.tvDate.setText(parseDateToddMMyyyy(myteam.get(position).created))
        holder.itemView.totalChange.setText(myteam.get(position).totalchangePercentage.toString() + " %")

        holder.itemView.txt_MyTeam.visibility = View.VISIBLE
        holder.itemView.edt_teamname.visibility = View.GONE
        if (!TextUtils.isEmpty(myteam.get(position).userteamname)) {
            holder.itemView.txt_MyTeam.setText(myteam.get(position).userteamname)
            holder.itemView.edt_teamname.setText(myteam.get(position).userteamname)
            teamName = myteam.get(position).userteamname
        }


        holder.itemView.edt_teamname.setOnTouchListener(object : View.OnTouchListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_RIGHT = 2
//                holder.itemView.edt_teamname.setFocusable(false);
                if (event.getAction() === MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= holder.itemView.edt_teamname.getRight() - holder.itemView.edt_teamname.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {
                        if (TextUtils.isEmpty(holder.itemView.edt_teamname.text.toString()))
                            AppDelegate.showToast(mContext, "Please Enter Team Name")
                        else {
                            holder.itemView.txt_MyTeam.setText(holder.itemView.edt_teamname.text.toString())
                            activityMyTeam.saveTeamName(
                                myteam.get(position).teamId.toInt(),
                                holder.itemView.txt_MyTeam.text.toString()
                            )
                            teamName = myteam.get(position).userteamname
                        }
                        holder.itemView.edt_teamname.visibility = View.GONE
                        holder.itemView.txt_MyTeam.visibility = View.VISIBLE
                        return true
                    }
                }
                return false
            }
        })

        holder.itemView.txt_MyTeam.setOnClickListener {
            holder.itemView.edt_teamname.visibility = View.VISIBLE
            holder.itemView.txt_MyTeam.visibility = View.GONE
        }

        if (myteam.get(position).stock.size == 0) {
            holder.itemView.stockName.setText(myteam.get(position).marketname)
        } else {
            holder.itemView.stockName.setText(myteam.get(position).exchangename)
            if (!TextUtils.isEmpty(myteam.get(position).exchangeimage))
                Glide.with(mContext).load(AppDelegate.EXCHANGE_URL + myteam.get(position).exchangeimage.trim())
                    .into(holder.itemView.ivStock)
        }


        holder.itemView.rel_team.setOnClickListener {
            if (myteam.get(position).crypto.size > 0) {
                mContext.startActivity(
                    Intent(mContext, ActivityMarketTeam::class.java)
                        .putExtra(StockConstant.TEAMID, myteam.get(position).teamId.toInt())
                        .putExtra(StockConstant.CONTESTID, myteam.get(position).contestId)
                        .putExtra(StockConstant.TEAMNAME, holder.itemView.txt_MyTeam.text.toString())
                        .putExtra(StockConstant.MARKETID, myteam.get(position).mid)
                        .putExtra("isCloning", 2)
                        .putParcelableArrayListExtra(StockConstant.MARKETLIST, myteam.get(position).crypto)
                )

                Log.d("Change_per", "--" + myteam!!.get(position).crypto.get(0).changeper);

            } else if (myteam.get(position).currencies.size > 0) {
                mContext.startActivity(
                    Intent(mContext, ActivityCurrencyTeam::class.java)
                        .putParcelableArrayListExtra(StockConstant.MARKETLIST, myteam.get(position).currencies)
                        .putExtra(StockConstant.TEAMID, myteam.get(position).teamId.toInt())
                        .putExtra(StockConstant.CONTESTID, myteam.get(position).contestId)
                        .putExtra(StockConstant.TEAMNAME, teamName)
                        .putExtra(StockConstant.MARKETID, myteam.get(position).mid)
                        .putExtra("isCloning", 2)
                )
            } else if (myteam.get(position).stock.size > 0) {
                mContext.startActivity(
                    Intent(mContext, ActivityCreateTeam::class.java)
                        .putExtra(StockConstant.STOCKLIST, myteam.get(position).stock)
                        .putExtra(StockConstant.TEAMID, myteam.get(position).teamId.toInt())
                        .putExtra(StockConstant.CONTESTID, myteam.get(position).contestId)
                        .putExtra(StockConstant.TEAMNAME, teamName)
                        .putExtra(StockConstant.MARKETID, myteam.get(position).mid)
                        .putExtra(StockConstant.EXCHANGEID, myteam.get(position).exchangeid)
                        .putExtra("isCloning", 2)
                )
            }
        }

        holder.itemView.relClone.setOnClickListener {
            if (myteam.get(position).crypto.size > 0)
                activityMyTeam.makeCloneMarket(myteam.get(position).teamId.toInt(), myteam.get(position).contestId)
            else if (myteam.get(position).currencies.size > 0) {
                activityMyTeam.makeCloneMarket(myteam.get(position).teamId.toInt(), myteam.get(position).contestId)
            } else
                activityMyTeam.makeCloneMarket(myteam.get(position).teamId.toInt(), myteam.get(position).contestId)

        }

        holder.itemView.setOnClickListener {
            teamName= holder.itemView.edt_teamname.text.toString()
            if (myteam.get(position).crypto.size > 0) {
                var intent = Intent(mContext, ActivityMarketTeam::class.java)
                intent
                    .putExtra(StockConstant.MARKETLIST, myteam.get(position).crypto)
                    .putExtra(StockConstant.TEAMID, myteam.get(position).teamId.toInt())
                    .putExtra(StockConstant.CONTESTID, contestId)
                    .putExtra(StockConstant.TEAMNAME, teamName)
                    .putExtra("isCloning", 1)
                    .putExtra(StockConstant.MARKETID, myteam.get(position).mid)
                startActivityForResult(mContext as Activity, intent, StockConstant.REDIRECT_UPCOMING_MARKET, null)


            } else if (myteam.get(position).currencies.size > 0) {
                var intent = Intent(mContext, ActivityCurrencyTeam::class.java)
                intent.putExtra(StockConstant.MARKETLIST, myteam.get(position).currencies)
                    .putExtra(StockConstant.TEAMID, myteam.get(position).teamId.toInt())
                    .putExtra(StockConstant.CONTESTID, contestId)
                    .putExtra("isCloning", 1)
                    .putExtra(StockConstant.TEAMNAME,teamName)
                    .putExtra(StockConstant.MARKETID, myteam.get(position).mid)
                startActivityForResult(mContext as Activity, intent, StockConstant.REDIRECT_UPCOMING_MARKET, null)

            } else if (myteam.get(position).stock.size > 0) {
                var intent = Intent(mContext, ActivityCreateTeam::class.java)
                intent.putExtra(StockConstant.STOCKLIST, myteam.get(position).stock)
                    .putExtra(StockConstant.TEAMID, myteam.get(position).teamId.toInt())
                    .putExtra(StockConstant.CONTESTID, contestId)
                    .putExtra(StockConstant.TEAMNAME, teamName)
                    .putExtra("isCloning", 1)
                    .putExtra(StockConstant.EXCHANGEID, myteam.get(position).exchangeid)
                    .putExtra(StockConstant.MARKETID, myteam.get(position).mid)
                startActivityForResult(mContext as Activity, intent, StockConstant.REDIRECT_UPCOMING_MARKET, null)
            }

        }

        holder.itemView.relViewTeam.setOnClickListener {
            teamName= holder.itemView.txt_MyTeam.text.toString()
            if (myteam.get(position).crypto.size > 0) {
                mContext.startActivity(
                    Intent(mContext, MarketTeamPreviewActivity::class.java)
                        .putExtra(StockConstant.MARKETLIST, myteam.get(position).crypto)
                        //.putExtra(StockConstant.TEAMNAME, teamName)
                        .putExtra(StockConstant.TEAMNAME, teamName)
                        .putExtra(StockConstant.TOTALCHANGE, myteam.get(position).totalchangePercentage)
                )
            } else if (myteam.get(position).stock.size > 0) {
                mContext.startActivity(
                    Intent(mContext, TeamPreviewActivity::class.java)
                        .putExtra(StockConstant.STOCKLIST, myteam.get(position).stock)
                        .putExtra(StockConstant.TEAMNAME, teamName)
                        .putExtra(StockConstant.TOTALCHANGE, myteam.get(position).totalchangePercentage)
//                    .putExtra(StockConstant.TEAMID, myteam.get(position).teamId)
                )
            } else if (myteam.get(position).currencies.size > 0) {
                mContext.startActivity(
                    Intent(mContext, CurrencyPreviewTeamActivity::class.java)
                        .putExtra(StockConstant.MARKETLIST, myteam.get(position).currencies)
                        .putExtra(StockConstant.TEAMNAME, teamName)
                        .putExtra(StockConstant.TOTALCHANGE, myteam.get(position).totalchangePercentage)
                )
            }


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
        var timeZone: String = Calendar.getInstance().getTimeZone().getID();
        var date: Date? = null
        var str: String? = null

        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date.time + TimeZone.getTimeZone(timeZone).getOffset(date.getTime()))
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }

    fun shareIntent() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT,
            "Join [DFX], Fantasy Stock League Application [ " + ApiConstant.BASE_URL + " ]"
        );
        mContext.startActivity(Intent.createChooser(shareIntent, mContext.getString(R.string.send_to)))
    }


}