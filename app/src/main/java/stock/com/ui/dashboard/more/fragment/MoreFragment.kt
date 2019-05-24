package stock.com.ui.dashboard.more.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_more.*
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.dashboard.more.activity.WebViewActivity
import stock.com.ui.invite.activity.InviteCodeActivity
import stock.com.ui.invite.activity.InviteFriendsActivity
import stock.com.ui.login.activity.LoginActivity


class MoreFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.tv_invite_friends -> startActivity(Intent(activity, InviteFriendsActivity::class.java))
            R.id.tv_contest_invite_code -> startActivity(Intent(activity, InviteCodeActivity::class.java))
            R.id.tv_fantasy_point_system -> startActivity(
                Intent(
                    activity,
                    WebViewActivity::class.java
                ).putExtra("PAGE_SLUG", "Fantasy Point System")
            )
            R.id.tv_how_to_play -> startActivity(
                Intent(activity, WebViewActivity::class.java).putExtra(
                    "PAGE_SLUG",
                    "How to Play"
                )
            )
            R.id.tv_helpDesk -> startActivity(
                Intent(activity, WebViewActivity::class.java).putExtra(
                    "PAGE_SLUG",
                    "Helpdesk"
                )
            )
            R.id.tv_about -> startActivity(
                Intent(activity, WebViewActivity::class.java).putExtra(
                    "PAGE_SLUG",
                    "About Us"
                )
            )
            R.id.tv_rules -> startActivity(
                Intent(activity, WebViewActivity::class.java).putExtra(
                    "PAGE_SLUG",
                    "Legality"
                )
            )
            R.id.tv_logout -> {
                val intent = Intent(activity, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                activity!!.finish()
            }

        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.fragment_more, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        if (activity is DashBoardActivity)
            (activity as DashBoardActivity).setMenu(true, true, false, false, false, false, false)
        tv_invite_friends.setOnClickListener(this)
        tv_contest_invite_code.setOnClickListener(this)
        tv_fantasy_point_system.setOnClickListener(this)
        tv_how_to_play.setOnClickListener(this)
        tv_helpDesk.setOnClickListener(this)
        tv_about.setOnClickListener(this)
        tv_rules.setOnClickListener(this)
        tv_logout.setOnClickListener(this)


//        startActivity(Intent(this, WebViewActivity::class.java).putExtra(IntentConstant.PAGE_SLUG, "terms-of-use"))
    }


}