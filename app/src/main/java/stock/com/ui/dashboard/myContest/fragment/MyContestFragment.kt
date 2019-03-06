package stock.com.ui.dashboard.myContest.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import kotlinx.android.synthetic.main.home_fragment.*
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.ui.dashboard.myContest.adapter.MyContestCompletedAdapter
import stock.com.ui.dashboard.myContest.adapter.MyContestFixturesAdapter
import stock.com.ui.dashboard.myContest.adapter.MyContestLiveAdapter


class MyContestFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.txt_Fixtures -> matchSelector(FIXTURES)
            R.id.txt_Live -> matchSelector(LIVE)
            R.id.txt_Results -> matchSelector(RESULTS)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        recyclerView_Match.visibility = View.VISIBLE
        ll_expandable_view.visibility = View.GONE
        txt_title.visibility = View.GONE
        entry_fee.visibility = View.GONE
        rl_banner.visibility= GONE
        matchSelector(FIXTURES)
//        setHomeBannerAdapter()
        txt_Fixtures.setOnClickListener(this)
        txt_Live.setOnClickListener(this)
        txt_Results.setOnClickListener(this)
    }


    private var LIVE = 1
    private var FIXTURES = 2
    private var RESULTS = 3

    fun matchSelector(value: Int) {
        txt_Fixtures.isSelected = false
        txt_Live.isSelected = false
        txt_Results.isSelected = false
        view1.visibility = View.VISIBLE
        view2.visibility = View.VISIBLE
        when (value) {
            LIVE -> {
                txt_title.visibility = GONE
                txt_Live.isSelected = true
                view1.visibility = View.GONE
                view2.visibility = View.GONE
                setLiveAdapter()
            }
            FIXTURES -> {
                txt_title.visibility = GONE
                txt_title.setText(R.string.select_a_match)
                txt_Fixtures.isSelected = true
                view1.visibility = View.GONE
                setFixturesAdapter()
            }
            RESULTS -> {
                txt_title.visibility = GONE
                txt_title.setText(R.string.results)
                txt_Results.isSelected = true
                view2.visibility = View.GONE
                setCompletedAdapter()
            }
        }
    }

    @SuppressLint("WrongConstant")
    private fun setFixturesAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = VERTICAL
        recyclerView_Match!!.layoutManager = llm
        recyclerView_Match!!.adapter = MyContestFixturesAdapter(context!!)
    }
    @SuppressLint("WrongConstant")
    private fun setLiveAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = VERTICAL
        recyclerView_Match!!.layoutManager = llm
        recyclerView_Match!!.adapter = MyContestLiveAdapter(context!!)
    }
    @SuppressLint("WrongConstant")
    private fun setCompletedAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = VERTICAL
        recyclerView_Match!!.layoutManager = llm
        recyclerView_Match!!.adapter = MyContestCompletedAdapter(context!!)
    }
}