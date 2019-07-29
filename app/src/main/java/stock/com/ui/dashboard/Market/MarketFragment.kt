package stock.com.ui.dashboard.Market

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_market.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.ExchangeList
import stock.com.ui.pojo.StockTeamPojo
import stock.com.ui.watch_list.WatchListActivity
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class MarketFragment : BaseFragment(), View.OnClickListener {
    var position: Int = 0
    private var cryptoAdapter: CurrencyAdapter? = null;
    private var list: List<StockTeamPojo.Stock>? = null
    var isMarket: Int = 0
    var flagData: String = ""

    private var fragment: Fragment? = null;
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tv_crypto -> {
                isMarket = 0
                et_search.setText("")
                filtertext.text = "Crypto \n Filter"
                changeTextColor(tv_crypto, ContextCompat.getColor(activity!!, R.color.white));
                changeTextColor(tv_forex, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

                changeBackGroundColor(tv_crypto, ContextCompat.getColor(activity!!, R.color.colorbutton));
                changeBackGroundColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_forex, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.white));

                setFragment(CryptoCurrencyFragment(), Bundle())
                position = 0
            }
            R.id.tv_commodity -> {
                isMarket = 0
                filtertext.text = "Commodity \n Filter"
                changeTextColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.white));
                changeTextColor(tv_crypto, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_forex, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

                changeBackGroundColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.colorbutton));
                changeBackGroundColor(tv_crypto, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_forex, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.white));

                setFragment(CommodityFragment(), Bundle())
                position = 2
            }
            R.id.tv_forex -> {
                isMarket = 0
                filtertext.text = "Forex \n Filter"
                changeTextColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_forex, ContextCompat.getColor(activity!!, R.color.white));
                changeTextColor(tv_crypto, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

                changeBackGroundColor(tv_crypto, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_forex, ContextCompat.getColor(activity!!, R.color.colorbutton));
                changeBackGroundColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.white));

                setFragment(CurrencyFragment(), Bundle())
                position = 3
            }
            R.id.tv_stocks -> {
                isMarket = 1
                et_search.setText("")
                filtertext.text = "Stocks \n Filter"
                changeTextColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_forex, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_crypto, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
                changeTextColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.white));

                changeBackGroundColor(tv_crypto, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_forex, ContextCompat.getColor(activity!!, R.color.white));
                changeBackGroundColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.colorbutton));

                setFragment(StocksFragment(), Bundle());
                position = 4

            }

            R.id.ll_filter -> {
                val intent = Intent(context, ActivityMarketTypeFilter::class.java)
                    .putExtra("isMarket", isMarket)
                startActivityForResult(intent, StockConstant.RESULT_CODE_MARKET_FILTER)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_market, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()
        tv_forex.setOnClickListener(this);
        tv_commodity.setOnClickListener(this);
        tv_stocks.setOnClickListener(this);
        tv_crypto.setOnClickListener(this);
        ll_filter.setOnClickListener(this);


        filtertext.text = "Crypto \n Filter"
        changeTextColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
        changeTextColor(tv_forex, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));
        changeTextColor(tv_crypto, ContextCompat.getColor(activity!!, R.color.white));
        changeTextColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.textColorLightBlack));

        changeBackGroundColor(tv_commodity, ContextCompat.getColor(activity!!, R.color.white));
        changeBackGroundColor(tv_forex, ContextCompat.getColor(activity!!, R.color.white));
        changeBackGroundColor(tv_crypto, ContextCompat.getColor(activity!!, R.color.colorbutton));
        changeBackGroundColor(tv_stocks, ContextCompat.getColor(activity!!, R.color.white));
        isMarket = 0
        setFragment(CryptoCurrencyFragment(), Bundle());

        ll_watchlist.setOnClickListener {
            startActivity(Intent(activity!!, WatchListActivity::class.java))
        }

        ll_sort.setOnClickListener {
            startActivityForResult(
                Intent(activity!!, ActivityMarketSort::class.java).putExtra("flagStatus", flagData),
                StockConstant.RESULT_CODE_SORT_MARKET
            )
        }
        getExchangeNamelist()

        et_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (fragment is StocksFragment)
                    (fragment as StocksFragment).setFilter(s!!)
                else if (fragment is CryptoCurrencyFragment)
                    (fragment as CryptoCurrencyFragment).setFilter(s!!)
            }
        })

        imgcross.setOnClickListener {
            et_search.setText("")
            AppDelegate.hideKeyBoard(activity!!)
            if (fragment is StocksFragment)
                (fragment as StocksFragment).setFilter("")
            else if (fragment is CryptoCurrencyFragment)
                (fragment as CryptoCurrencyFragment).setFilter("")
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
                        if (response.body()!!.exchange != null)
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

    /* fun setStockNameAdapter(exchangeList: List<ExchangeList.Exchange>) {
         if (!isAdded)
             return
         rvstock.adapter = ExchangeAdapter(context, exchangeList!!)
         rvstock.setScrollDuration(1000);
         rvstock.startAutoScroll(true);
     } */

    fun setStockNameAdapter(exchangeList: List<ExchangeList.Exchange>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.HORIZONTAL
        rvstock!!.layoutManager = llm
        rvstock.visibility = View.VISIBLE
        if (rvstock != null)
            rvstock!!.adapter = ExchangeAdapter(context!!, exchangeList!!)

        // call function news
        autoScrollNews(llm)

        //recyclerView_stock_name.getAdapter()!!.notifyDataSetChanged();
//        rvstock.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL))
//        rvstock.setItemAnimator(DefaultItemAnimator())
        rvstock!!.adapter!!.notifyDataSetChanged();

    }

    fun autoScrollNews(llm: LinearLayoutManager) {
        val handler = Handler()
        val runnable = object : Runnable {
            var count = 0
            var flag = true
            override fun run() {
                if (rvstock == null)
                    return
                if (count < rvstock!!.adapter!!.getItemCount()) {
                    if (count == rvstock!!.adapter!!.getItemCount() - 1) {
                        flag = false;
                    } else
                        if (count == 0) {
                            flag = true;
                        }
                    if (flag)
                        count++;
                    else
                        count--;
                    var visibleItemCount = rvstock.getChildCount();
                    var totalItemCount = llm.getItemCount();
//                    recyclerView_stock_name.smoothScrollToPosition(count);
                    var dx = count
                    rvstock.scrollBy(count, visibleItemCount + totalItemCount)
                    handler.postDelayed(this, 300);

                }
            }
        }
        handler.postDelayed(runnable, 0);

    }

    private fun setFragment(fragment: Fragment, bundle: Bundle) {
        this.fragment = fragment;
        fragment!!.arguments = bundle;
        val fragmentManager: FragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentManager
            .setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out)
            .replace(R.id.container, fragment)
            .commitAllowingStateLoss()
    }

    private fun changeTextColor(textView: TextView, color: Int) {
        textView.setTextColor(color)
    }

    private fun changeBackGroundColor(textView: TextView, color: Int) {
        textView.setBackgroundColor(color);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.RESULT_CODE_SORT_MARKET) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                flagData = (data.getStringExtra("flag"));
                if (data.getStringExtra("flag").equals("Alpha")) {
                    if (fragment is StocksFragment)
                        (fragment as StocksFragment).setSorting(data.getStringExtra("flag"))
                    else if (fragment is CryptoCurrencyFragment)
                        (fragment as CryptoCurrencyFragment).setSorting(data.getStringExtra("flag"))


                } else if (data.getStringExtra("flag").equals("dayChange")) {
                    if (fragment is StocksFragment)
                        (fragment as StocksFragment).setSorting(data.getStringExtra("flag"))
                    else if (fragment is CryptoCurrencyFragment)
                        (fragment as CryptoCurrencyFragment).setSorting(data.getStringExtra("flag"))


                } else if (data.getStringExtra("flag").equals("price")) {
                    if (fragment is StocksFragment)
                        (fragment as StocksFragment).setSorting(data.getStringExtra("flag"))
                    else if (fragment is CryptoCurrencyFragment)
                        (fragment as CryptoCurrencyFragment).setSorting(data.getStringExtra("flag"))


                } else if (data.getStringExtra("flag").equals("HighToLow")) {
                    if (fragment is StocksFragment)
                        (fragment as StocksFragment).setSorting(data.getStringExtra("flag"))
                    else if (fragment is CryptoCurrencyFragment)
                        (fragment as CryptoCurrencyFragment).setSorting(data.getStringExtra("flag"))


                } else if (data.getStringExtra("flag").equals("DayHighToLow")) {
                    if (fragment is StocksFragment)
                        (fragment as StocksFragment).setSorting(data.getStringExtra("flag"))
                    else if (fragment is CryptoCurrencyFragment)
                        (fragment as CryptoCurrencyFragment).setSorting(data.getStringExtra("flag"))


                } else if (data.getStringExtra("flag").equals("nodata", true)) {
                    if (fragment is StocksFragment)
                        (fragment as StocksFragment).setSorting(data.getStringExtra("flag"))
                    else if (fragment is CryptoCurrencyFragment)
                        (fragment as CryptoCurrencyFragment).setSorting(data.getStringExtra("flag"))


                }
            }

            // 1- change percent filter for market
            //2- change percent filter for stocks
            //3-
        } else if (requestCode == StockConstant.RESULT_CODE_MARKET_FILTER) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                if (data.getStringExtra("resetfiltermarket").equals("0")) {
                    //remove inactive like change percent is 0.00
                    if (fragment is CryptoCurrencyFragment)
                        (fragment as CryptoCurrencyFragment).changePercentFilter(data.getStringExtra("resetfiltermarket"))
                } else if (data.getStringExtra("resetfiltermarket").equals("1")) {
                    if (fragment is CryptoCurrencyFragment)
                        (fragment as CryptoCurrencyFragment).changePercentFilter(data.getStringExtra("resetfiltermarket"))

                } else if (data.getStringExtra("resetfiltermarket").equals("3")) {
                    var sector: String = data.getStringExtra("sectorlist")
                    var exchange: String = data.getStringExtra("exchangelist")
                    var country: String = data.getStringExtra("countrylist")

                    if (fragment is StocksFragment)
                        (fragment as StocksFragment).applyFilter(sector, exchange, country)

                } else if (data.getStringExtra("resetfiltermarket").equals("2")) {
                    if (fragment is StocksFragment)
                        (fragment as StocksFragment).changePercentFilter("1")

                }
            }

        }
    }
}

