package stock.com.ui.my_contest.fragment

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import com.madapps.liquid.LiquidRefreshLayout
import kotlinx.android.synthetic.main.fragment_created.*
import kotlinx.android.synthetic.main.fragment_finished.*
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.ui.my_contest.adapter.CreatedAdapter

class CreatedFragment : BaseFragment() {



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_created, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        /*refreshData.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                //TODO make api call here
                Handler().postDelayed({
                    refreshData.finishRefreshing()
                }, 5000)
//                getContests()
            }
        })*/
    }

    private fun setAdapter(){
        val llm = GridLayoutManager(context,2)
        //llm.orientation = GridLayoutManager(applicationContext,2)
        recycler_my_contest!!.layoutManager = llm
        recycler_my_contest!!.adapter = CreatedAdapter(context!!);
    }



}