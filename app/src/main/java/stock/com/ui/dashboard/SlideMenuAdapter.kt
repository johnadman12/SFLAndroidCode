package stock.com.ui.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_match.view.*
import kotlinx.android.synthetic.main.row_view_slide_menu.view.*
import stock.com.R
import stock.com.ui.contact_us.ContactUsActivity
import stock.com.ui.edit_profile.EditProfileActivity
import stock.com.ui.friends.FriendsActivity
import stock.com.ui.my_contest.MyContestActivity
import stock.com.ui.offer_list.OfferListActivity
import stock.com.ui.rules_and_scoring.RulesScoringActivity
import stock.com.ui.setting.SettingActivity
import stock.com.ui.social_network.SocialNetworkActivity
import stock.com.ui.support.SupportActivity
import stock.com.ui.watch_list.WatchListActivity

class SlideMenuAdapter(val mContext: Context, val mContest: List<String>, var activity: DashBoardActivity) :
    RecyclerView.Adapter<SlideMenuAdapter.SlideMenuHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideMenuHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_view_slide_menu, parent, false)
        return SlideMenuHolder(view)
    }

    override fun onBindViewHolder(holder: SlideMenuHolder, position: Int) {
        holder.itemView.tv_title_menu.setText(mContest.get(position));
        holder.itemView.tv_title_menu.setOnClickListener {
            if (activity != null && holder.itemView.tv_title_menu.text.toString().equals(mContext.getString(R.string.logout))) {
                //activity.showDialog1();
                activity.setIntent(holder.itemView.tv_title_menu.text.toString());
            }
            if (activity != null && holder.itemView.tv_title_menu.text.toString().equals(mContext.getString(R.string.support))) {
                var intent = Intent(mContext, SupportActivity::class.java);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
            if (activity != null && holder.itemView.tv_title_menu.text.toString().equals(mContext.getString(R.string.how_to_play_))) {

              /* var intent= Intent(mContext, ContactUsActivity::class.java);
                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 mContext.startActivity(intent);*/

            }
            if (activity != null && holder.itemView.tv_title_menu.text.toString().equals(mContext.getString(R.string.social_network))) {
                var intent = Intent(mContext, SocialNetworkActivity::class.java);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
            if (activity != null && holder.itemView.tv_title_menu.text.toString().equals(mContext.getString(R.string.offers))) {
                var intent = Intent(mContext, OfferListActivity::class.java);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }

            if (activity != null && holder.itemView.tv_title_menu.text.toString().equals(mContext.getString(R.string.rules_winning))) {
                var intent = Intent(mContext, RulesScoringActivity::class.java);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }


        }
    }

    override fun getItemCount(): Int {
        return mContest.size;
    }

    inner class SlideMenuHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}

