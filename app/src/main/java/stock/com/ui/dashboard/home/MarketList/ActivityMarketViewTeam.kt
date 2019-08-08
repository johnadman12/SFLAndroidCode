package stock.com.ui.dashboard.home.MarketList

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
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import kotlinx.android.synthetic.main.activity_view_team.*
import kotlinx.android.synthetic.main.dialog_join_contest.*
import kotlinx.android.synthetic.main.dialogue_wallet_new.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.createTeam.activity.TeamPreviewActivity
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.ui.dashboard.Lobby.ActivityAddCash
import stock.com.ui.dashboard.Team.ActivityMarketDetail
import stock.com.ui.dashboard.Team.ActivitySortTeam
import stock.com.ui.pojo.BasePojo
import stock.com.ui.pojo.ContestDetail
import stock.com.ui.pojo.MarketList
import stock.com.utils.AppDelegate
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog

class ActivityMarketViewTeam : BaseActivity(), View.OnClickListener {
    private var list: ArrayList<MarketList.Crypto>? = null;
    private var marketSelectedItem: ArrayList<MarketList.Crypto>? = null;
    private var viewTeamAdapter: MarketViewTeamAdapter? = null;
    var contestId: Int = 0
    var teamId: Int = 0
    var marketId: Int = 0
    var flagCloning: Int = 0
    var contestFee: String = ""
    var array: JsonArray = JsonArray()
    var jsonparams: JsonObject = JsonObject()

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.ll_sort -> {
                startActivityForResult(
                    Intent(this@ActivityMarketViewTeam, ActivitySortTeam::class.java),
                    StockConstant.RESULT_CODE_SORT_MARKETVIEW_TEAM
                )
            }
            R.id.btn_Join -> {
                showJoinContestDialogue()
            }
            R.id.relFieldView -> {
                startActivity(
                    Intent(this@ActivityMarketViewTeam, MarketTeamPreviewActivity::class.java)
                        .putExtra(StockConstant.MARKETLIST, list)
                        .putExtra(StockConstant.TEAMNAME, edtTeamName.text.toString())
                        .putExtra(StockConstant.TOTALCHANGE, "0.0%")
                )
            }
            R.id.ivedit -> {
                edtTeamName.isEnabled = true
                ivRight.visibility = View.VISIBLE
                ivedit.visibility = View.GONE
            }
            R.id.ivRight -> {
                edtTeamName.isEnabled = false
                ivedit.visibility = View.VISIBLE
                ivRight.visibility = View.GONE
            }
            R.id.txtTeamname -> {
                edtTeamName.visibility = View.VISIBLE
                txtTeamname.visibility = View.GONE
            }
            R.id.ll_save -> {
                if (marketSelectedItem!!.size > 0) {
                    for (i in 0 until marketSelectedItem!!.size) {
                        var postData1 = JsonObject();
                        try {
                            postData1.addProperty("crypto_id", marketSelectedItem!!.get(i).cryptocurrencyid.toString());
                            postData1.addProperty("price", marketSelectedItem!!.get(i).latestPrice.toString());
                            postData1.addProperty("change_percent", marketSelectedItem!!.get(i).changeper.toString());
                            postData1.addProperty("stock_type", marketSelectedItem!!.get(i).cryptoType);
                            Log.e("savedlist", postData1.toString())
                        } catch (e: Exception) {

                        }
                        Log.d("finaldata", array.toString())
                        array.add(postData1)
                    }
                    if (!TextUtils.isEmpty(edtTeamName.text.toString()))
                        saveTeamList()
                    else
                        displayToast("Please Enter Team name", "error")
                } else {
                    displayToast("please select Crypto first", "warning")
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_team)
        list = ArrayList()
        list!!.clear()
        if (intent != null) {
            list = intent.getParcelableArrayListExtra(StockConstant.MARKETLIST)
            teamId = intent.getIntExtra(StockConstant.TEAMID, 0)
            flagCloning = intent.getIntExtra("isCloning", 0)
            contestFee = intent.getStringExtra(StockConstant.CONTESTFEE)
            contestId = intent.getIntExtra(StockConstant.CONTESTID, 0)
            marketId = intent.getIntExtra(StockConstant.MARKETID, 0)

        }
        StockConstant.ACTIVITIES.add(this)
        initView()
        Log.e("updatedafterlist", list!!.get(0).addedToList.toString())
        viewTeamAdapter!!.notifyDataSetChanged()

        if (flagCloning == 1)
            getContestDetail()

        edtTeamName.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val DRAWABLE_RIGHT = 2
                if (event.getAction() === MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= edtTeamName.getRight() - edtTeamName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()) {
                        if (TextUtils.isEmpty(edtTeamName.text.toString())) {
                            displayToast("Please enter team name", "warning")
                        } else {
                            edtTeamName.visibility = View.GONE
                            txtTeamname.visibility = View.VISIBLE
                            txtTeamname.setText(edtTeamName.text.toString())
                        }
                        return true
                    }
                }
                return false
            }
        })

    }

    fun getContestDetail() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<ContestDetail> =
            apiService.getContestDetail(
                contestId.toString()
                , getFromPrefsString(StockConstant.USERID).toString(), "", marketId.toString()
            )
        call.enqueue(object : Callback<ContestDetail> {

            override fun onResponse(call: Call<ContestDetail>, response: Response<ContestDetail>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        contestFee = response.body()!!.contest.get(0).entryFees
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<ContestDetail>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    private fun initView() {
        marketSelectedItem = ArrayList();
        array = JsonArray()
        jsonparams = JsonObject()
        ivedit.setOnClickListener(this)
        img_btn_back.setOnClickListener(this)
        btn_Join.setOnClickListener(this)
        ll_save.setOnClickListener(this)
        ivRight.setOnClickListener(this)
        ll_sort.setOnClickListener(this)
        txtTeamname.setOnClickListener(this)
        relFieldView.setOnClickListener(this)
        ll_sort.visibility = View.GONE
        marketSelectedItem = list
        viewTeamAdapter = MarketViewTeamAdapter(
            this, list as ArrayList, object : MarketViewTeamAdapter.OnItemCheckListener {
                override fun onRemoveIteam(item: MarketList.Crypto) {
                    if (marketSelectedItem!!.size > 0) {
                        marketSelectedItem!!.remove(item)
                        setTeamText(marketSelectedItem!!.size)
                        val intent = Intent();
                        intent.putExtra("removedlist", list)
                        intent.putExtra("flag", "1")
                        setResult(Activity.RESULT_OK, intent);
                    }
                }

                override fun onToggleBuy(item: MarketList.Crypto) {
                    for (i in 0 until marketSelectedItem!!.size) {
                        if (marketSelectedItem!!.get(i).cryptocurrencyid == item.cryptocurrencyid) {
                            marketSelectedItem!!.get(i).cryptoType = item.cryptoType
                        }
                    }
                }

                override fun onToggleSell(item: MarketList.Crypto) {
                    for (i in 0 until marketSelectedItem!!.size) {
                        if (marketSelectedItem!!.get(i).cryptocurrencyid == item.cryptocurrencyid) {
                            marketSelectedItem!!.get(i).cryptoType = item.cryptoType
                        }
                    }
                }

                override fun onItemClick(item: MarketList.Crypto) {
                    startActivityForResult(
                        Intent(
                            this@ActivityMarketViewTeam,
                            ActivityMarketDetail::class.java
                        )
                            .putExtra("cryptoId", item.cryptocurrencyid)
                            .putExtra("flag", 1)
                            .putExtra(StockConstant.MARKETLIST, list)
                            .putExtra(StockConstant.SELECTEDSTOCK, marketSelectedItem!!.size)
                        , StockConstant.RESULT_CODE_CREATE_TEAM
                    )
                }

                override fun onItemCheck(item: Int, itemcontest: MarketList.Crypto) {
                    marketSelectedItem!!.remove(itemcontest)
                    setTeamText(item)
                    val intent = Intent();
                    intent.putExtra("removedlist", list)
                    intent.putExtra("flag", "1")
                    setResult(Activity.RESULT_OK, intent);
                    Log.e("stocklist", marketSelectedItem!!.get(0).cryptocurrencyid.toString())
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
        dialogue.entreefee.setText(contestFee)
        dialogue.tvEntryFee.setText(contestFee)
        dialogue.tv_yes1.setOnClickListener {
            dialogue.dismiss()
            showDialogue()
        }

        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
    }


    fun joinContest() {
        if (marketSelectedItem!!.size > 0) {
            for (i in 0 until marketSelectedItem!!.size) {
                var postData: JsonObject = JsonObject()
                try {
                    postData.addProperty("crypto_id", marketSelectedItem!!.get(i).cryptocurrencyid.toString());
                    postData.addProperty("price", marketSelectedItem!!.get(i).latestPrice.toString());
                    postData.addProperty("change_percent", marketSelectedItem!!.get(i).changeper.toString());
                    postData.addProperty("stock_type", marketSelectedItem!!.get(i).cryptoType);

                } catch (e: Exception) {

                }
                array.add(postData)
            }
//                    Log.e("savedlist", array.toString())

            if (flagCloning == 1) {
                joinWithThisTeamID()
            } else
                joinWithThisTeam()
            Log.e("savedlist", array.toString())
        } else {
            displayToast("please select Crpto first", "warning")
        }
    }

    public fun showDialogue() {
        var dialogue = Dialog(this)
        dialogue.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogue.setContentView(R.layout.dialogue_wallet_new)
        dialogue.window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialogue.window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogue.window.setGravity(Gravity.BOTTOM)
        dialogue.setCancelable(true)
        dialogue.setCanceledOnTouchOutside(false)
        dialogue.setTitle(null)
        dialogue.imgbtnCancle.setOnClickListener {
            dialogue.dismiss()
        }
        dialogue.tv_yes.setOnClickListener {
            dialogue.dismiss()
            if (!TextUtils.isEmpty(edtTeamName.text.toString()))
                joinContest()
            else
                displayToast("Please Enter Team name", "error")

        }
        dialogue.txt_Withdraw.setOnClickListener {
            dialogue.dismiss()
            startActivity(Intent(this@ActivityMarketViewTeam, ActivityAddCash::class.java))
        }

        if (dialogue.isShowing)
            dialogue.dismiss()
        dialogue.show()
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

    @SuppressLint("WrongConstant")
    fun setStockAdapter() {
        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_team!!.layoutManager = llm
        rv_team.visibility = View.VISIBLE
        rv_team!!.adapter = viewTeamAdapter;
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == StockConstant.RESULT_CODE_SORT_MARKETVIEW_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                if (data.getStringExtra("flag").equals("Volume")) {
                    var sortedList = list!!.sortedBy { it.latestVolume!!.toDouble() }

                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_team!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("price")) {
                    var sortedList = list!!.sortedWith(compareBy { it.latestPrice })
                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_team!!.adapter!!.notifyDataSetChanged()
                    }
                } else if (data.getStringExtra("flag").equals("Alpha")) {
                    var sortedList = list!!.sortedBy { it.symbol?.toString() }
                    for (obj in sortedList) {
                        list!!.clear()
                        list!!.addAll(sortedList)
                        rv_team!!.adapter!!.notifyDataSetChanged()
                    }
                }
            }
        }

        if (requestCode == StockConstant.RESULT_CODE_CREATE_TEAM) {
            if (resultCode == RESULT_OK && data != null) {
                /* if (getTeamText() > 12) {
                     Toast.makeText(this, "You have selected maximum number of stocks for your team.", 10000).show()
                 } else {*/
                list!!.clear()
                list!!.addAll(data.getParcelableArrayListExtra("list"))
                rv_team!!.adapter!!.notifyDataSetChanged()
            }
        }

    }


    fun saveTeamList() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        jsonparams.addProperty("contest_id", contestId.toString())
        jsonparams.addProperty("team_id", "")
        jsonparams.addProperty("join_var", 0)
        jsonparams.addProperty("market_id", marketId)
        jsonparams.addProperty("user_team_name", txtTeamname.text.toString())
        jsonparams.addProperty("user_id", getFromPrefsString(StockConstant.USERID).toString())
        jsonparams.add("marketdatas", array)

        Log.e("savedlist", array.toString())

        val call: Call<BasePojo> =
            apiService.saveMarketTeam(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                jsonparams
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityMarketViewTeam, response.body()!!.message)
                            teamId = response.body()!!.team_id.toInt()
                            var intent = Intent();
                            intent.putExtra("flag", "3")
                            intent.putExtra(StockConstant.TEAMID, teamId)
                            setResult(Activity.RESULT_OK, intent);
                        }, 1000)
                    } else if (response.body()!!.status == "0") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityMarketViewTeam, response.body()!!.message)
                        }, 1000)
                    } else if (response.body()!!.status == "2") {
                        Handler().postDelayed(
                            Runnable {
                                AppDelegate.showAlert(this@ActivityMarketViewTeam, response.body()!!.message)
                                appLogout()
                            },
                            1000
                        )


                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    fun joinWithThisTeam() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        jsonparams.addProperty("contest_id", contestId.toString())
        jsonparams.addProperty("team_id", teamId)
        jsonparams.addProperty("join_var", 1)
        jsonparams.addProperty("market_id", marketId)
        jsonparams.addProperty("user_team_name", txtTeamname.text.toString())
        jsonparams.addProperty("user_id", getFromPrefsString(StockConstant.USERID).toString())
        jsonparams.add("marketdatas", array)

        Log.e("savedlist", array.toString())

        val call: Call<BasePojo> =
            apiService.saveMarketTeam(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                jsonparams
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityMarketViewTeam, response.body()!!.message)
                            var intent = Intent();
                            intent.putExtra("flag", "2")
                            setResult(Activity.RESULT_OK, intent);
                            finish()
                        }, 500)
                    } else if (response.body()!!.status == "0") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityMarketViewTeam, response.body()!!.message)
                        }, 500)

                    } else if (response.body()!!.status == "2") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityMarketViewTeam, response.body()!!.message)
                            appLogout()
                        }, 500)
                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }

    fun joinWithThisTeamID() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        jsonparams.addProperty("contest_id", contestId.toString())
        jsonparams.addProperty("team_id", teamId)
        jsonparams.addProperty("market_id", marketId)
        jsonparams.addProperty("join_var", 1)
        jsonparams.addProperty("user_team_name", txtTeamname.text.toString())
        jsonparams.addProperty("user_id", getFromPrefsString(StockConstant.USERID).toString())
        jsonparams.add("marketdatas", array)

        Log.e("savedlist", array.toString())

        val call: Call<BasePojo> =
            apiService.saveMarketTeam(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(),
                jsonparams
            )
        call.enqueue(object : Callback<BasePojo> {

            override fun onResponse(call: Call<BasePojo>, response: Response<BasePojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityMarketViewTeam, response.body()!!.message)
                            var intent = Intent();
                            intent.putExtra("flag", "2")
                            setResult(Activity.RESULT_OK, intent);
                            finish();
                        }, 500)

                    } else if (response.body()!!.status == "0") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityMarketViewTeam, response.body()!!.message)
                        }, 500)

                    } else if (response.body()!!.status == "2") {
                        Handler().postDelayed(Runnable {
                            AppDelegate.showAlert(this@ActivityMarketViewTeam, response.body()!!.message)
                            appLogout()
                        }, 500)

                    }
                } else {
                    displayToast(resources.getString(R.string.something_went_wrong), "error")
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<BasePojo>, t: Throwable) {
                println(t.toString())
                displayToast(resources.getString(R.string.something_went_wrong), "error")
                d.dismiss()
            }
        })
    }
}