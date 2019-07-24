package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.madapps.liquid.LiquidRefreshLayout
import kotlinx.android.synthetic.main.dialog_view_contest.*
import kotlinx.android.synthetic.main.fragment_lobby.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseFragment
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.pojo.LobbyContestPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockConstant.RESULT_CODE_FILTER
import stock.com.utils.StockDialog
import kotlin.collections.ArrayList


class LobbyFragment : BaseFragment() {
    var categoryId: String = ""
    var flag: String = ""
    var contest: ArrayList<LobbyContestPojo.Contest>? = null;

    private var dashBoardActivity: DashBoardActivity? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews();
    }

    private fun initViews() {
        contest = ArrayList();

        if (arguments!!.getString("id") != null)
            categoryId = arguments!!.getString("id");

        dashBoardActivity = activity as DashBoardActivity?


        if (!TextUtils.isEmpty(categoryId)) {
            setFilters(categoryId)
        } else {
            getContestlist()
        }


        ll_filter.setOnClickListener {
            val intent = Intent(context, ActivityFilter::class.java)
            startActivityForResult(intent, RESULT_CODE_FILTER)
        }

        ll_codejoin.setOnClickListener {
            startActivity(Intent(activity!!, ActivityCodeJoin::class.java))
        }

        ll_createContest.setOnClickListener {
            startActivityForResult(Intent(activity, ActivityCreateContest::class.java), StockConstant.REDIRECT_CREATED)
        }

        ll_sort.setOnClickListener {
            startActivityForResult(
                Intent(context, ActivitySort::class.java).putExtra("flagStatus", flag),
                StockConstant.RESULT_CODE_SORT
            )
        }

        refreshLayout.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }
            override fun refreshing() {
                //TODO make api call here
                Handler().postDelayed({
                    getContestlist()
                }, 1500)
            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lobby, container, false)
    }

    fun getContestlist() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<LobbyContestPojo> =
            apiService.getContestList(getFromPrefsString(StockConstant.ACCESSTOKEN).toString())
        call.enqueue(object : Callback<LobbyContestPojo> {
            override fun onResponse(call: Call<LobbyContestPojo>, response: Response<LobbyContestPojo>) {
                d.dismiss()
                if (refreshLayout != null)
                    refreshLayout.finishRefreshing()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        try {
                            if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.CONTEST_TYPE))) {
                                setContestType(" ")
                            }
                            if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.MARKET_TYPE))) {
                                setMarketContest(" ")
                            }
                            if (!TextUtils.isEmpty(getFromPrefsString(StockConstant.COUNTRY_TYPE))) {
                                setCountryContest(" ")
                            }
                        } catch (e: Exception) {

                        }
                        contest = response.body()!!.contest!!;
                        setContestAdapter(response.body()!!.contest!!)
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<LobbyContestPojo>, t: Throwable) {
                /*if (refreshLayout != null)
                    refreshLayout.finishRefreshing()*/
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }

        })
    }

    @SuppressLint("WrongConstant")
    private fun setContestAdapter(contest: List<LobbyContestPojo.Contest>) {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_contest!!.layoutManager = llm
        recyclerView_contest?.itemAnimator = DefaultItemAnimator()
        recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, contest)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.RESULT_CODE_SORT) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                flag = data.getStringExtra("flag")
                if (data.getStringExtra("flag").equals("Entry")) {
                    var sortedList = contest!!.sortedWith(compareBy({ it.fees.toDouble() }))
                    for (obj in sortedList) {
                        Log.d("sdadada---", "--" + obj.fees)
                        recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        recyclerView_contest!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("time")) {

                    var sortedList = contest!!.sortedWith(compareBy { it.getDate() })
                    for (obj in sortedList) {
                        Log.d("sdadada---", "--" + obj.fees)
                        recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        recyclerView_contest!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("price")) {

                    contest!!.sortByDescending { it.win_amount.toDouble() };
                    //var sortedList1 = tempList!!.sortByDescending { it.getWinningAmount_temp().toDouble() };
                    recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, contest!!)
                    recyclerView_contest!!.adapter!!.notifyDataSetChanged();

                } else if (data.getStringExtra("flag").equals("position")) {
                    val sortedList = contest!!.sortedWith(compareBy({ it.getCalculatePosition() }))
                    for (obj in sortedList) {
                        Log.d("sdadada---", "--" + obj.calculatePosition)
                        recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        recyclerView_contest!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("nodata")) {
                    getContestlist()
                }
            }
        } else if (requestCode == RESULT_CODE_FILTER) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                //contest!!.clear()
                var flagreset = data.getStringExtra("resetfilter")
                if (flagreset.equals("0")) {
                    var testing = data.getSerializableExtra("contestlist") as ArrayList<LobbyContestPojo.Contest>;
                    try {
                        if (testing.size > 0) {
                            Log.d("sdadada---Filter", "--" + testing.size)
                            recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, testing)
                            recyclerView_contest!!.adapter!!.notifyDataSetChanged();
                        } else {
                            testing.clear()
                            recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, testing)
                            recyclerView_contest!!.adapter!!.notifyDataSetChanged();
                            AppDelegate.showAlert(activity!!, "No result Found\n Please Pull down to get Contests")
                        }
                    } catch (e: java.lang.Exception) {

                    }


                } else {
                    getContestlist()
                }
                /*recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, contest!!)
                recyclerView_contest!!.adapter!!.notifyDataSetChanged();*/
            }
        } else if (requestCode == StockConstant.REDIRECT_CREATED) {
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                var intent = Intent();
                //activity!!.startActivityForResult(intent,111);
                activity!!.setResult(Activity.RESULT_OK, intent);
                activity!!.finish();
                dashBoardActivity!!.test()

            }
        }
    }

    fun showViewContestDialogue() {
        var dialogue = Dialog(activity!!)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialog_view_contest)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.setCancelable(true)
        dialogue.setCanceledOnTouchOutside(false)
        dialogue.setTitle(null)
        dialogue.tvViewContest.setOnClickListener {

            dialogue.dismiss()
        }
        dialogue.tv_hide.setOnClickListener {
            dialogue.dismiss()
        }

        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }

    fun setFilters(categoryId: String) {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<LobbyContestPojo> =
            apiService.setContestFilter(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                getFromPrefsString(StockConstant.USERID).toString(),
                categoryId,
                "", "",
                "",
                ""
            )
        call.enqueue(object : Callback<LobbyContestPojo> {
            override fun onResponse(call: Call<LobbyContestPojo>, response: Response<LobbyContestPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        contest = response.body()!!.contest
                        setContestAdapter(contest!!)
                    } else if (response.body()!!.status == "2") {
                        appLogout();
                    } else {
                        displayToast("no data exist", "warning")

                    }
                } else {
                    displayToast(resources.getString(stock.com.R.string.internal_server_error), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<LobbyContestPojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

}