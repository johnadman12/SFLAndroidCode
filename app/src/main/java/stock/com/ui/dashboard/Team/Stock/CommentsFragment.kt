package stock.com.ui.dashboard.Team.Stock

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_comments.*
import stock.com.AppBase.BaseFragment
import stock.com.R

class CommentsFragment : BaseFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_comments, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCommentsAdapter()
    }


    @SuppressLint("WrongConstant")
    private fun setCommentsAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        rvComments!!.layoutManager = llm
        rvComments.visibility = View.VISIBLE
        rvComments!!.adapter = StockCommentsAdapter(context!!);
    }

}