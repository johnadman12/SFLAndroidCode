package stock.com.ui.dashboard.profile.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.content_myprofile.*
import kotlinx.android.synthetic.main.fragment_myprofile.*
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.ui.addfunds.activity.AddFundsActivity
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.dashboard.profile.activity.MyAccountActivity

class ProfileFragment : BaseFragment(), View.OnClickListener {

    private lateinit var ctx: DashBoardActivity
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.txt_MyAccount -> {
                startActivity(Intent(activity, MyAccountActivity::class.java))
            }

            R.id.txt_Add -> {
                startActivity(Intent(activity, AddFundsActivity::class.java))
            }

            R.id.logout_profile -> {
                ctx.appLogout()
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
        return inflater.inflate(R.layout.fragment_myprofile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed(Runnable {
            initViews()
        }, 10)

    }



    private fun initViews() {
        ctx = activity as DashBoardActivity
        coordinator.visibility = VISIBLE
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
        })
        txt_MyAccount.setOnClickListener(this)
        txt_Add.setOnClickListener(this)
        logout_profile.setOnClickListener(this)
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