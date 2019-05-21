package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_stocks.*
import stock.com.AppBase.BaseFragment
import stock.com.R
class StocksFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stocks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMarketAdapter()
        setTechnologyAdapter()
    }

    @SuppressLint("WrongConstant")
    fun setTechnologyAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
          rv_Technology!!.layoutManager = llm
           rv_Technology.visibility = View.VISIBLE
           rv_Technology!!.adapter = MarketAdapter(context!!)
    }

    @SuppressLint("WrongConstant")
    fun setMarketAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
         rv_market_movers!!.layoutManager = llm
         rv_market_movers.visibility = View.VISIBLE
         rv_market_movers!!.adapter = MarketAdapter(context!!)
    }


}
