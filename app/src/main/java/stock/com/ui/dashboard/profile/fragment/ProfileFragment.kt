package stock.com.ui.dashboard.profile.fragment

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.madapps.liquid.LiquidRefreshLayout
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.content_myprofile.*
import kotlinx.android.synthetic.main.fragment_myprofile.*
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.dashboard.profile.activity.MyAccountActivity
import stock.com.ui.edit_profile.EditProfileActivity
import stock.com.ui.friends.FriendsActivity
import stock.com.ui.share.ShareActivity
import stock.com.ui.statistics.StatisticsActivity
import stock.com.ui.wallet.WalletActivity

class ProfileFragment : BaseFragment(), View.OnClickListener {

    private lateinit var ctx: DashBoardActivity

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.tv_statics -> {
                startActivity(Intent(activity, StatisticsActivity::class.java))
            }

            R.id.tv_invite_friends -> {
                startActivity(Intent(activity, ShareActivity::class.java))
            }

            R.id.ll_friend -> {
                startActivity(Intent(activity, FriendsActivity::class.java))
            }
            R.id.ll_wallet -> {
                startActivity(Intent(activity, WalletActivity::class.java))
            }
            R.id.ll_edit -> {
                startActivity(Intent(activity, EditProfileActivity::class.java))
            }
        }
    }


//    private val PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR = 0.9f
//    private val PERCENTAGE_TO_HIDE_TITLE_DETAILS = 0.3f
//    private val ALPHA_ANIMATIONS_DURATION = 200
//
//    private var mIsTheTitleVisible = false
//    private var mIsTheTitleContainerVisible = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.activity_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed(Runnable {
            initViews()
        }, 10)

    }

    private fun initViews() {
        ctx = activity as DashBoardActivity
        tv_pro.setText(getUserData().level_type)
        text_user.setText(getUserData().username)
        Glide.with(activity!!).load(getUserData().profile_image).into(profile_image)
        /*  coordinator.visibility = VISIBLE
          app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
              if (Math.abs(verticalOffset) - appBarLayout.totalScrollRange == 0) {
                  //  Collapsed
                  toolbar.visibility = VISIBLE
                  ll_main.visibility = GONE
              } else {
                  //Expanded
                  toolbar.visibility = GONE
                  ll_main.visibility = VISIBLE
              }
          })*/


        refreshLayout.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                refreshLayout.finishRefreshing()
            }
        })
        tv_statics.setOnClickListener(this);
        tv_invite_friends.setOnClickListener(this);
        ll_friend.setOnClickListener(this);
        ll_wallet.setOnClickListener(this);
        ll_edit.setOnClickListener(this);
        showDialog();
    }


    public fun showDialog() {
        val dialog = Dialog(activity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_verification)
        dialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        val window = dialog.window
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        dialog.setCanceledOnTouchOutside(true);

        val tv_yes = dialog.findViewById(R.id.tv_yes) as TextView
        val tv_cancel = dialog.findViewById(R.id.tv_cancel) as TextView

        tv_yes.setOnClickListener {
            //appLogout();
            dialog.dismiss();
        }
        tv_cancel.setOnClickListener {
            dialog.dismiss();
        }
        dialog.show();
    }

//    private fun initViews() {
//        main_appbar.addOnOffsetChangedListener(this)
//
//        startAlphaAnimation(main_textview_title, 0, View.INVISIBLE)
//    }
//
//  override  fun onOffsetChanged(appBarLayout: AppBarLayout, offset: Int) {
//        val maxScroll = appBarLayout.getTotalScrollRange()
//        val percentage = Math.abs(offset).toFloat() / maxScroll.toFloat()
//
//        handleAlphaOnTitle(percentage)
//        handleToolbarTitleVisibility(percentage)
//    }
//
//    private fun handleToolbarTitleVisibility(percentage: Float) {
//        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
//
//            if (!mIsTheTitleVisible) {
//                startAlphaAnimation(main_textview_title, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
//                mIsTheTitleVisible = true
//            }
//
//        } else {
//
//            if (mIsTheTitleVisible) {
//                startAlphaAnimation(main_textview_title, ALPHA_ANIMATIONS_DURATION.toLong(), View.INVISIBLE)
//                mIsTheTitleVisible = false
//            }
//        }
//    }
//
//    private fun handleAlphaOnTitle(percentage: Float) {
//        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
//            if (mIsTheTitleContainerVisible) {
//                startAlphaAnimation(main_linearlayout_title, ALPHA_ANIMATIONS_DURATION.toLong(), View.INVISIBLE)
//                mIsTheTitleContainerVisible = false
//            }
//
//        } else {
//
//            if (!mIsTheTitleContainerVisible) {
//                startAlphaAnimation(main_linearlayout_title, ALPHA_ANIMATIONS_DURATION.toLong(), View.VISIBLE)
//                mIsTheTitleContainerVisible = true
//            }
//        }
//    }
//
//    fun startAlphaAnimation(v: View, duration: Long, visibility: Int) {
//        val alphaAnimation = if (visibility == View.VISIBLE)
//            AlphaAnimation(0f, 1f)
//        else
//            AlphaAnimation(1f, 0f)
//
//        alphaAnimation.duration = duration
//        alphaAnimation.fillAfter = true
//        v.startAnimation(alphaAnimation)
//    }

}