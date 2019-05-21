package stock.com.ui.dashboard.Market

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_market.*
import kotlinx.android.synthetic.main.home_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.Team.ActivitySortTeam
import stock.com.ui.pojo.ExchangeList
import stock.com.ui.pojo.HomePojo
import stock.com.ui.watch_list.WatchListActivity
import stock.com.utils.StockDialog

class MarketFragment : BaseFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_currency -> {
//                ll_my_team.visibility = View.VISIBLE
                changeTextColor(tv_currency, ContextCompat.getColor(activity!!, R.color.white));
                changeTextColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_indices, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

                changeBackGroundColor(tv_currency, ContextCompat.getColor(activity!!, R.color.colorbutton));
                changeBackGroundColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_indices, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.white));

                setFragment(CurrencyFragment(), Bundle())
            }
            R.id.tv_commodity -> {

//                ll_my_team.visibility = View.GONE
                changeTextColor(tv_currency, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.white));
                changeTextColor(tv_indices, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

                changeBackGroundColor(tv_currency, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.colorbutton));
                changeBackGroundColor(tv_indices, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.white));

                setFragment(CommodityFragment(), Bundle())
            }
            R.id.tv_indices -> {
//                ll_my_team.visibility = View.GONE
                changeTextColor(tv_currency, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_indices, ContextCompat.getColor(activity!!, R.color.white));
                changeTextColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

                changeBackGroundColor(tv_currency, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_indices, ContextCompat.getColor(activity!!, R.color.colorbutton));
                changeBackGroundColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.white));

                setFragment(IndicesFragment(), Bundle())
            }
            R.id.tv_stocks -> {

//             ll_my_team.visibility = View.GONE
                changeTextColor(tv_currency, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_indices, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.white));

                changeBackGroundColor(tv_currency, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_indices, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.colorbutton));

                setFragment(StocksFragment(), Bundle());

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_market, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getExchangeNamelist()
        //setMarketAdapter()
        // setTechnologyAdapter()

        tv_currency.setOnClickListener(this);
        tv_indices.setOnClickListener(this);
        tv_commodity.setOnClickListener(this);
        tv_stocks.setOnClickListener(this);

        changeTextColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
        changeTextColor(tv_indices, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
        changeTextColor(tv_currency, ContextCompat.getColor(activity!!, R.color.white));
        changeTextColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

        changeBackGroundColor(tv_indices, ContextCompat.getColor(activity!!, R.color.white));
        changeBackGroundColor(tv_currency, ContextCompat.getColor(activity!!, R.color.white));
        changeBackGroundColor(tv_currency, ContextCompat.getColor(activity!!, R.color.colorbutton));
        changeBackGroundColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.white));

        setFragment(CurrencyFragment(), Bundle());

        ll_watchlist.setOnClickListener {
            startActivity(Intent(activity!!, WatchListActivity::class.java))
        }
        ll_sort.setOnClickListener {
            startActivity(Intent(activity!!, ActivityMarketSort::class.java))
        }

    }

    fun getExchangeNamelist() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<ExchangeList> =
            apiService.getExchangelist()
        call.enqueue(object : Callback<ExchangeList> {
            override fun onResponse(call: Call<ExchangeList>, response: Response<ExchangeList>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        setStockNameAdapter(response.body()!!.exchange)
                    } else {
                        displayToast(resources.getString(R.string.internal_server_error), "error")
                        d.dismiss()
                    }
                }
            }

            override fun onFailure(call: Call<ExchangeList>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })

    }

    fun setStockNameAdapter(exchangeList: List<ExchangeList.Exchange>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        rvstock!!.layoutManager = llm
        rvstock.visibility = View.VISIBLE
        rvstock!!.adapter = ExchangeAdapter(context!!, exchangeList)
    }
/*

    @SuppressLint("WrongConstant")
    fun setMarketAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
       */
/* rv_market_movers!!.layoutManager = llm
        rv_market_movers.visibility = View.VISIBLE
        rv_market_movers!!.adapter = MarketAdapter(context!!)*//*

    }

    @SuppressLint("WrongConstant")
    fun setTechnologyAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
     */
/*   rv_Technology!!.layoutManager = llm
        rv_Technology.visibility = View.VISIBLE
        rv_Technology!!.adapter = MarketAdapter(context!!)*//*

    }
*/

    private fun setFragment(fragment: Fragment, bundle: Bundle) {
        val fragment: Fragment? = fragment;
        fragment!!.arguments = bundle;
        val fragmentManager: FragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentManager
//            .setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out)
            .replace(R.id.container, fragment)
            .commitAllowingStateLoss()
    }

    private fun changeTextColor(textView: TextView, color: Int) {
        textView.setTextColor(color)
    }

    private fun changeBackGroundColor(textView: TextView, color: Int) {
        textView.setBackgroundColor(color);
    }
}

