package stock.com.ui.contest.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.app_toolbar.*
import kotlinx.android.synthetic.main.content_all_contest.*
import kotlinx.android.synthetic.main.four_tab_layout.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.contest.adapter.AllContentsPagerAdapter
import stock.com.ui.contest.adapter.AllContestAdapter
import stock.com.ui.createTeam.activity.myTeam.MyTeamActivity
import stock.com.ui.joinedContest.activity.FixtureJoinedContestActivity


class AllContestActivity : BaseActivity(), View.OnClickListener {

    private var mAdapter: AllContentsPagerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_contest)
        initViews()
        //setListener()
    }

    private fun initViews() {
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        toolbarTitleTv.setText("NASDAQ: Sun 7/17 PMET")
        setMenu(true, false, false, false, false, false, false)
      //  mAdapter = AllContentsPagerAdapter(supportFragmentManager)
        setAdapter()
        ll_myteam.setOnClickListener(this)
        ll_joinedContests.setOnClickListener(this)
        txt_winning.setOnClickListener(this)
        txt_Winners.setOnClickListener(this)
        txt_Team.setOnClickListener(this)
        txt_EntryFee.setOnClickListener(this)
        ll_fees.setOnClickListener(this)
        content_type.setOnClickListener(this)
        sortBySelector(WINNING)
    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Contest!!.layoutManager = llm
        rv_Contest!!.adapter = AllContestAdapter(this)
    }

    private var WINNERS = 1
    private var WINNING = 2
    private var ENTRY_FEE = 3
    private var TEAMS = 4
    fun sortBySelector(value: Int) {
        txt_winning.isSelected = false
        txt_Winners.isSelected = false
        txt_Team.isSelected = false
        txt_EntryFee.isSelected = false
        when (value) {
            WINNERS -> {
                txt_Winners.isSelected = true
            }
            WINNING -> {
                txt_winning.isSelected = true
            }
            ENTRY_FEE -> {
                txt_EntryFee.isSelected = true
            }
            TEAMS -> {
                txt_Team.isSelected = true
            }
        }

    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.txt_winning -> {
                sortBySelector(WINNING)
            }
            R.id.txt_Winners -> {
                sortBySelector(WINNERS)
            }
            R.id.txt_Team -> {
                sortBySelector(TEAMS)
            }
            R.id.txt_EntryFee -> {
                sortBySelector(ENTRY_FEE)
            }
            R.id.ll_myteam -> {
                startActivity(Intent(this, MyTeamActivity::class.java))
            }
            R.id.ll_joinedContests -> {
                startActivity(Intent(this, FixtureJoinedContestActivity::class.java))
            }
            R.id.content_type ->{
                contestTypePopWindow()
            }
            R.id.ll_fees ->{
                llFeesPopWindow()
            }
//            R.id.txt_Signup -> {
//                startActivity(Intent(this, SignUpActivity::class.java))
//            }
        }
    }

    fun contestTypePopWindow(){
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.popup_window_view, null)
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val popUp = PopupWindow(popupView, width, height, true)
        popUp.showAsDropDown(content_type, Gravity.LEFT, 0, 0)
    }

    fun llFeesPopWindow(){
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.fees_filter_pop_up_layout, null)
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val popUp = PopupWindow(popupView, width, height, true)
        popUp.showAsDropDown(ll_fees, Gravity.CENTER, 0, 0)
    }

    /*private fun setListener() {
        contestPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
                setStyle(position)
            }k

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {}

            override fun onPageScrollStateChanged(arg0: Int) {}
        })

        ll_nasdaq.setOnClickListener {
            if (contestPager.currentItem != 0){
                contestPager.currentItem = 0
                setStyle(0)
            }
        }

        ll_nse.setOnClickListener {
            if (contestPager.currentItem != 1){
                contestPager.currentItem = 1
                setStyle(1)
            }
        }

        ll_dowjones.setOnClickListener {
            if (contestPager.currentItem != 2){
                contestPager.currentItem = 2
                setStyle(2)
            }
        }

        ll_ftse.setOnClickListener {
            if (contestPager.currentItem != 3){
                contestPager.currentItem = 3
                setStyle(3)
            }
        }
    }

    private fun setStyle(position: Int) {
        when (position) {
            0 -> {
                nasdaqSelector.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbutton))
                nseSelecto.setBackgroundColor(Color.parseColor(null))
                dowjonesSelector.setBackgroundColor(Color.parseColor(null))
                ftseSelector.setBackgroundColor(Color.parseColor(null))
            }
            1 -> {
                nasdaqSelector.setBackgroundColor(Color.parseColor(null))
                nseSelecto.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbutton))
                dowjonesSelector.setBackgroundColor(Color.parseColor(null))
                ftseSelector.setBackgroundColor(Color.parseColor(null))
            }
            2 -> {
                nasdaqSelector.setBackgroundColor(Color.parseColor(null))
                nseSelecto.setBackgroundColor(Color.parseColor(null))
                dowjonesSelector.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbutton))
                ftseSelector.setBackgroundColor(Color.parseColor(null))
            }
            3 -> {
                nasdaqSelector.setBackgroundColor(Color.parseColor(null))
                nseSelecto.setBackgroundColor(Color.parseColor(null))
                dowjonesSelector.setBackgroundColor(Color.parseColor(null))
                ftseSelector.setBackgroundColor(ContextCompat.getColor(this, R.color.colorbutton))
            }
        }
    }*/


}