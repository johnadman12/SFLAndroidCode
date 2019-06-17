package stock.com.ui.dashboard.my_contest.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.madapps.liquid.LiquidRefreshLayout
import kotlinx.android.synthetic.main.fragment_finished.*
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.ui.dashboard.my_contest.adapter.InvitedAdapter

class InvitedFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_finished, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        getContests()
        refreshData.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                //TODO make api call here
                Handler().postDelayed({
                }, 5000)
//                getContests()
            }
        })
        setAdapter()
    }

    private fun setAdapter(/*contest: ArrayList<LobbyContestPojo.Contest>*/) {
        val llm = GridLayoutManager(context, 2)
        //llm.orientation = GridLayoutManager(applicationContext,2)
        recycler_finished!!.layoutManager = llm
        recycler_finished!!.adapter = InvitedAdapter(context!!);
    }


}