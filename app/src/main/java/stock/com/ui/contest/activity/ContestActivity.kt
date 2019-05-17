package stock.com.ui.contest.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.activity_contest.*
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_contest.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.contest.adapter.ContestAdapter.ContestMainAdapter
import stock.com.ui.contest.dialogues.BottomSheetFilterFragment
import stock.com.ui.createTeam.activity.myTeam.MyTeamActivity
import stock.com.ui.dashboard.Lobby.ActivityCreateContest
import stock.com.ui.invite.activity.InviteCodeActivity
import stock.com.ui.joinedContest.activity.FixtureJoinedContestActivity


class ContestActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.ll_myteam -> {
                startActivity(Intent(this, MyTeamActivity::class.java))
            }
            R.id.rl_enterContestCode -> {
                startActivity(Intent(this, InviteCodeActivity::class.java))
            }
            R.id.rl_createContest -> {
                startActivity(Intent(this, ActivityCreateContest::class.java))
            }
            R.id.ll_joinedContests -> {
                startActivity(Intent(this, FixtureJoinedContestActivity::class.java))
            }
            R.id.ll_AllContest -> {
                startActivity(Intent(this, AllContestActivity::class.java))
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contest)
        initViews()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText(R.string.contest)
        setMenu(false, true, true, false, false, false, false)
       // setAdapter()
        rl_enterContestCode.setOnClickListener(this)
        rl_createContest.setOnClickListener(this)
        btn_CreateTeam.setOnClickListener(this)
        ll_myteam.setOnClickListener(this)
        ll_joinedContests.setOnClickListener(this)
        ll_AllContest.setOnClickListener(this)
        val mBottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        //By default set BottomSheet Behavior as Collapsed and Height 0
        mBottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        mBottomSheetBehavior.peekHeight = 0
        //If you want to handle callback of Sheet Behavior you can use below code
        mBottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
//                    BottomSheetBehavior.STATE_COLLAPSED -> Log.d(FragmentActivity.TAG, "State Collapsed")
//                    BottomSheetBehavior.STATE_DRAGGING -> Log.d(FragmentActivity.TAG, "State Dragging")
//                    BottomSheetBehavior.STATE_EXPANDED -> Log.d(FragmentActivity.TAG, "State Expanded")
//                    BottomSheetBehavior.STATE_HIDDEN -> Log.d(FragmentActivity.TAG, "State Hidden")
//                    BottomSheetBehavior.STATE_SETTLING -> Log.d(FragmentActivity.TAG, "State Settling")
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
        filterBootomSheet()
    }

     fun filterBootomSheet() {
        val mBottomSheetBehaviorfilter = BottomSheetBehavior.from(bottom_sheet_filter)
        mBottomSheetBehaviorfilter.state = BottomSheetBehavior.STATE_COLLAPSED
        mBottomSheetBehaviorfilter.peekHeight = 0
        //If you want to handle callback of Sheet Behavior you can use below code
        mBottomSheetBehaviorfilter.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                }
            }
            override fun onSlide(bottomSheet: View, slideOffset: Float) {}
        })
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_filter -> {
                val view = findViewById<View>(R.id.menu_filter)
                val bottomSheetDialogFragment = BottomSheetFilterFragment()
                bottomSheetDialogFragment.show(supportFragmentManager, "Bottom Sheet Dialog Fragment")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Contest!!.layoutManager = llm
        rv_Contest!!.adapter = ContestMainAdapter(this)
    }
}