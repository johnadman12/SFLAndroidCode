package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
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
import stock.com.utils.StockDialog


class LobbyFragment : BaseFragment() {
    val RESULT_DATA = 101


   var contest: List<LobbyContestPojo.Contest>?=null;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {

        contest=ArrayList();

        getTrainingContentlist()
        ll_filter.setOnClickListener {
            //            startActivity(Intent(context, ActivityFilter::class.java))
            val intent = Intent(context, ActivityFilter::class.java)
            startActivityForResult(intent, RESULT_DATA)
        }

        ll_sort.setOnClickListener { startActivity(Intent(context, ActivitySort::class.java)) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_lobby, container, false)
    }

    inline fun <reified T : Activity> Activity.myStartActivityForResult(requestCode: Int) {
        val intent = Intent(this, T::class.java)
        startActivityForResult(intent, requestCode)
    }

    fun getTrainingContentlist() {
        val d = StockDialog.showLoading(activity!!)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<LobbyContestPojo> =
            apiService.getContestList(getFromPrefsString(StockConstant.ACCESSTOKEN).toString())
        call.enqueue(object : Callback<LobbyContestPojo> {

            override fun onResponse(call: Call<LobbyContestPojo>, response: Response<LobbyContestPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        setContestAdapter(response.body()!!.contest!!)
                    }
                } else {
                    displayToast(resources.getString(R.string.internal_server_error))
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<LobbyContestPojo>, t: Throwable) {
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
        recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, contest)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==101) {
            if(data!=null&&resultCode==RESULT_OK) {
                var testing = data!!.getSerializableExtra(StockConstant.CONTEST) as ArrayList<LobbyContestPojo.Contest>;
                recyclerView_contest!!.adapter = LobbyContestAdapter(context!!, testing)
                recyclerView_contest!!.adapter!!.notifyDataSetChanged();
                displayToast(testing.size.toString())
            }
        }
    }
}