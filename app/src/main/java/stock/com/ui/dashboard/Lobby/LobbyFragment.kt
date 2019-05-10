package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
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
import stock.com.ui.pojo.LobbyContestPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockConstant.RESULT_CODE_FILTER
import stock.com.utils.StockDialog
import java.util.*
import kotlin.collections.ArrayList


class LobbyFragment : BaseFragment() {


    var contest: ArrayList<LobbyContestPojo.Contest>? = null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews();
    }

    private fun initViews() {
        contest = ArrayList();
        getContestlist()
        ll_filter.setOnClickListener {
            //            startActivity(Intent(context, ActivityFilter::class.java))
            val intent = Intent(context, ActivityFilter::class.java)
            startActivityForResult(intent, RESULT_CODE_FILTER)
        }

        ll_codejoin.setOnClickListener { showViewContestDialogue() }
        ll_createContest.setOnClickListener {
            startActivity(Intent(activity, ActivityCreateContest::class.java))
        }
        ll_sort.setOnClickListener {
            startActivityForResult(Intent(context, ActivitySort::class.java), StockConstant.RESULT_CODE_SORT)
        }

        refreshLayout.setOnRefreshListener(object : LiquidRefreshLayout.OnRefreshListener {
            override fun completeRefresh() {
            }

            override fun refreshing() {
                //TODO make api call here
                Handler().postDelayed({
                }, 5000)
                getContestlist()
            }
        })

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lobby, container, false)
    }

    inline fun <reified T : Activity> Activity.myStartActivityForResult(requestCode: Int) {
        val intent = Intent(this, T::class.java)
        startActivityForResult(intent, requestCode)
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
                        setContestAdapter(response.body()!!.contest!!)
                        contest = response.body()!!.contest!!;
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<LobbyContestPojo>, t: Throwable) {
                if (refreshLayout != null)
                    refreshLayout.finishRefreshing()
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong))
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
                if (data.getStringExtra("flag").equals("Entry")) {
                    var sortedList = contest!!.sortedWith(compareBy({ it.fees }))
                    for (obj in sortedList) {
                        Log.d("sdadada---", "--" + obj.fees)
                        recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        recyclerView_contest!!.adapter!!.notifyDataSetChanged()
                    }
                }
                else if (data.getStringExtra("flag").equals("time")) {
                    var sortedList = contest!!.sortedWith(compareBy { it.getDate() })
                    for (obj in sortedList) {
                        Log.d("sdadada---", "--" + obj.fees)
                        recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        recyclerView_contest!!.adapter!!.notifyDataSetChanged()
                    }
                }
                else if (data.getStringExtra("flag").equals("price")) {


                   //  val sortedList = contest!!.sortedWith(compareBy({ it.winningAmount}));
                    //val sortedList1 = contest!!.sortedWith(compareBy({ it.winningAmount.replace("$","") }));
                    //val sortedList1 = sortedList!!.sortedWith(compareBy({ it.winningAmount.replace(",","") }));
                    recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, contest!!)
                    recyclerView_contest!!.adapter!!.notifyDataSetChanged();

                    //val sortedList = contest!!.sortedWith(compareBy({ it.winningAmount.substring(0, 1) })).asReversed();

                } else if (data.getStringExtra("flag").equals("position")) {
                    val sortedList = contest!!.sortedWith(compareBy({ it.getCalculatePosition() }))
                    for (obj in sortedList) {
                        Log.d("sdadada---", "--" + obj.calculatePosition)
                        recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, sortedList)
                        recyclerView_contest!!.adapter!!.notifyDataSetChanged()
                    }
                }
            }

        } else if (requestCode == RESULT_CODE_FILTER) {

            if (resultCode == Activity.RESULT_OK && data != null) {
                /*contest!!.clear()
                var testing = data.getSerializableExtra(StockConstant.CONTEST) as ArrayList<LobbyContestPojo.Contest>;
                Log.d("sdadada---", "--" + testing.size)
                recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, testing)
                recyclerView_contest!!.adapter!!.notifyDataSetChanged();
            */
                recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, contest!!)
                recyclerView_contest!!.adapter!!.notifyDataSetChanged();
            }
        }

        /*fun convertTime(time:String ): Long {
            val inputPattern = "yyyy-MM-dd HH:mm:ss"
            val inputFormat = SimpleDateFormat(inputPattern)
            var date: Date? = null
            date = inputFormat.parse(time)
            val thatDay = Calendar.getInstance()
            thatDay.setTime(date);
            val today = Calendar.getInstance()
            val diff =  thatDay.timeInMillis -today.timeInMillis
            val days = diff / (24 * 60 * 60 * 1000)
            val day = TimeUnit.SECONDS.toDays(diff).toInt()
            val hour = TimeUnit.SECONDS.toHours(diff) - (day * 24)
            return hour
        }*/
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
}