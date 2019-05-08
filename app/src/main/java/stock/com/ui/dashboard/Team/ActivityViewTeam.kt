package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_view_team.*
import kotlinx.android.synthetic.main.dialog_join_contest.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog
import com.google.gson.JsonObject
import com.google.gson.JsonArray
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.dashboard.Team.Stock.ActivityStockDetail


class ActivityViewTeam : BaseActivity(), View.OnClickListener {
    private var list: ArrayList<StockTeamPojo.Stock>? = null;
    private var ids: ArrayList<String>? = null;
    var activity: DashBoardActivity = DashBoardActivity()
    private var viewTeamAdapter: ViewTeamAdapter? = null;
    var array: JsonArray = JsonArray()
    var jsonparams: JsonObject = JsonObject()
    private var stockSelectedItems: ArrayList<StockTeamPojo.Stock>? = null
    var exchangeId: Int = 0
    var contestId: Int = 0
    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.btn_Join -> {
                showJoinContestDialogue()
//                startActivity(Intent(this@ActivityViewTeam, ActivityJoiningConfirmation::class.java))
            }
            R.id.relFieldView -> {
                startActivity(
                    Intent(this@ActivityViewTeam, TeamPreviewActivity::class.java)
                        .putExtra(StockConstant.STOCKLIST, list)
                )
            }
            R.id.ivedit -> {
                edtTeamName.isEnabled = true
                ivRight.visibility = VISIBLE
                ivedit.visibility = GONE
            }
            R.id.ivRight -> {
                edtTeamName.isEnabled = false
                ivedit.visibility = VISIBLE
                ivRight.visibility = GONE
            }
            R.id.ll_save -> {
                if (stockSelectedItems!!.size > 0) {
                    for (i in 0 until stockSelectedItems!!.size) {
                        var postData1 = JsonObject();
                        try {
                            postData1.addProperty("stock_id", stockSelectedItems!!.get(i).stockid.toString());
                            postData1.addProperty("price", stockSelectedItems!!.get(i).latestPrice.toString());
                            postData1.addProperty("stock_status", stockSelectedItems!!.get(i).stock_type);
                            Log.e("savedlist", postData1.toString())
                        } catch (e: Exception) {

                        }
                        Log.d("finaldata", array.toString())
                        array.add(postData1)
                    }


                    saveTeamList()
                } else {
                    displayToast("please select Stock first")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_team)
        activity = DashBoardActivity()
        list = ArrayList()
        ids = ArrayList()
        list!!.clear()
        if (intent != null) {
            list = intent.getParcelableArrayListExtra(StockConstant.STOCKLIST)
            contestId = intent.getIntExtra(StockConstant.CONTESTID,0)
            exchangeId = intent.getIntExtra(StockConstant.EXCHANGEID, 0)

        }
        StockConstant.ACTIVITIES.add(this)
        initView()
        Log.e("updatedafterlist", list!!.get(0).addedToList.toString())
        viewTeamAdapter!!.notifyDataSetChanged()
    }

    private fun initView() {
        stockSelectedItems = ArrayList();
        array = JsonArray()
        jsonparams = JsonObject()
        ivedit.setOnClickListener(this)
        img_btn_back.setOnClickListener(this)
        btn_Join.setOnClickListener(this)
        ll_save.setOnClickListener(this)
        ivRight.setOnClickListener(this)
        relFieldView.setOnClickListener(this)

        stockSelectedItems = list
        viewTeamAdapter = ViewTeamAdapter(
            this, list as ArrayList, object : ViewTeamAdapter.OnItemCheckListener {
                override fun onItemClick(item: StockTeamPojo.Stock) {
                    startActivityForResult(
                        Intent(
                            this@ActivityViewTeam,
                            ActivityStockDetail::class.java
                        )
                            .putExtra("Stockid", item.stockid)
                            .putExtra(StockConstant.STOCKLIST, list)
                        , StockConstant.RESULT_CODE_VIEW_TEAM
                    )
                }

                override fun onItemCheck(item: Int, itemcontest: StockTeamPojo.Stock) {
                    setTeamText(item)
                    val intent = Intent();
                    intent.putExtra("removedlist", list)
                    intent.putExtra("flag", "1")
                    setResult(Activity.RESULT_OK, intent);
                    Log.e("stocklist", stockSelectedItems!!.get(0).stockid.toString())
                }
            });
        setStockAdapter()
    }

    fun showJoinContestDialogue() {
        var dialogue = Dialog(this)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialog_join_contest)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.setCancelable(true)
        dialogue.setCanceledOnTouchOutside(true)
        dialogue.setTitle(null)
        dialogue.entreefee.setText("")
        dialogue.tvEntryFee.setText("")
        dialogue.tv_yes.setOnClickListener {
            dialogue.dismiss()
            if (stockSelectedItems!!.size > 0) {
                for (i in 0 until stockSelectedItems!!.size) {
                    /*if (stockSelectedItems!!.get(i).addedStock.equals("yes")) {
                        stockSelectedItems!!.get(i).addedStock="0"
                    } else if (stockSelectedItems!!.get(i).addedStock.equals("no")) {
                        stockSelectedItems!!.get(i).addedStock="1"
                    }*/
                    var postData: JsonObject = JsonObject()
                    try {
                        postData.addProperty("stock_id", stockSelectedItems!!.get(i).stockid.toString());
                        postData.addProperty("price", stockSelectedItems!!.get(i).latestPrice.toString());
                        postData.addProperty("stock_status", stockSelectedItems!!.get(i).stock_type);

                    } catch (e: Exception) {

                    }
                    array.add(postData)
                }
//                    Log.e("savedlist", array.toString())
                joinWithThisTeam()
            } else {
                displayToast("please select Stock first")
            }

        }

        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }

    @SuppressLint("WrongConstant")
    fun setStockAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_team!!.layoutManager = llm
        rv_team.visibility = View.VISIBLE
        rv_team!!.adapter = viewTeamAdapter;
    }

    fun saveTeamList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        jsonparams.addProperty("contest_id", contestId.toString())
        jsonparams.addProperty("team_id", "")
        jsonparams.addProperty("join_var", 0)
        jsonparams.addProperty("user_id", getFromPrefsString(StockConstant.USERID).toString())
        jsonparams.add("stocks", array)

        Log.e("savedlist", array.toString())

        val call: Call<BasePojo> =
            apiService.saveTeam(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                jsonparams
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        AppDelegate.showAlert(this@ActivityViewTeam, response.body()!!.message)
                    } else if (response.body()!!.status == "0") {
                        AppDelegate.showAlert(this@ActivityViewTeam, response.body()!!.message)
                    } else if (response.body()!!.status == "2") {
                        appLogout()
                    }
                } else {
                    Toast.makeText(
                        this@ActivityViewTeam,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivityViewTeam,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    fun joinWithThisTeam() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        jsonparams.addProperty("contest_id", contestId.toString())
        jsonparams.addProperty("team_id", "")
        jsonparams.addProperty("join_var", 1)
        jsonparams.addProperty("user_id", getFromPrefsString(StockConstant.USERID).toString())
        jsonparams.add("stocks", array)
        val call: Call<BasePojo> =
            apiService.saveTeam(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                jsonparams
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        AppDelegate.showAlert(this@ActivityViewTeam, response.body()!!.message)
                        var intent = Intent();
                        intent.putExtra("flag", "2")
                        setResult(Activity.RESULT_OK, intent);
                        finish();
                    } /*else if (response.body()!!.status == "0") {
                        AppDelegate.showAlert(this@ActivityViewTeam, response.message())
                        activity.setFragmentForActivity()
                        finish()
                    }*/
                } else {
                    Toast.makeText(
                        this@ActivityViewTeam,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivityViewTeam,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    fun setTeamText(total: Int) {
        if (total >= 12) {
            ll_save.isEnabled = true
            btn_Join.isEnabled = true
            save.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.white_fill_button))
            btn_Join.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.green_fill_button))
        } else {
            ll_save.isEnabled = false
            btn_Join.isEnabled = false
            save.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.grey_fill_button))
            btn_Join.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.grey_fill_button))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.RESULT_CODE_VIEW_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                list!!.clear()
                list!!.addAll(data.getParcelableArrayListExtra("list"))
                rv_team!!.adapter!!.notifyDataSetChanged()

            }
        }
    }

}