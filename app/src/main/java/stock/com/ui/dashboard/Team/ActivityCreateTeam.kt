package stock.com.ui.dashboard.Team

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_create_team.*
import kotlinx.android.synthetic.main.include_back.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.networkCall.ApiClient
import stock.com.networkCall.ApiInterface
import stock.com.ui.pojo.StockTeamPojo
import stock.com.utils.StockConstant
import stock.com.utils.StockDialog


class ActivityCreateTeam : BaseActivity(), View.OnClickListener {
    private var stockSelectedItems: ArrayList<StockTeamPojo.Stock>? = null
    private var stockSelectedWizardItems: ArrayList<StockTeamPojo.Stock>? = null
    private var stockTeamAdapter: StockTeamAdapter? = null;
    private var list: ArrayList<StockTeamPojo.Stock>? = null;
    private var parseList: ArrayList<StockTeamPojo.Stock>? = null;
    var flag: Boolean = false

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.img_btn_back -> {
                finish()
            }
            R.id.imgButtonWizard -> {
                getWizardStocklist()
            }
            R.id.tvViewteam -> {
                if (flag) {
                    startActivity(
                        Intent(this@ActivityCreateTeam, ActivityViewTeam::class.java)
                            .putExtra(StockConstant.STOCKLIST, stockSelectedWizardItems)
                            .putExtra(
                                StockConstant.EXCHANGEID,
                                exchangeId
                            )
                    )
                } else {
                    /*  if (stockSelectedItems!!.size > 0) {
                          for (i in 0 until stockSelectedItems!!.size) {
                              parseList!!.add(list!!.get(stockSelectedItems!![i]))
                              Log.e("parselist", parseList!!.get(i).companyName)
                          }
                      }*/
                    startActivity(
                        Intent(this@ActivityCreateTeam, ActivityViewTeam::class.java)
                            .putExtra(StockConstant.STOCKLIST, stockSelectedItems)
                            .putExtra(
                                StockConstant.EXCHANGEID,
                                exchangeId
                            )
                    )
                }
            }
        }
    }

    var exchangeId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)
        StockConstant.ACTIVITIES.add(this)
        list = ArrayList();
        initView()
    }

    @SuppressLint("WrongConstant")
    private fun initView() {
        stockSelectedItems = ArrayList();
        stockSelectedWizardItems = ArrayList();
        parseList = ArrayList();
        img_btn_back.setOnClickListener(this)
        tvViewteam.setOnClickListener(this)
        tvViewteam.isEnabled = false
        imgButtonWizard.setOnClickListener(this)
        if (intent != null) {
            exchangeId = intent.getStringExtra(StockConstant.EXCHANGEID)
        }

        /*val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        rv_Players.visibility = View.VISIBLE
        rv_Players!!.adapter
        */

        stockTeamAdapter = StockTeamAdapter(
            this, list as ArrayList, "no",
            object : StockTeamAdapter.OnItemCheckListener {
                override fun onItemUncheck(item: StockTeamPojo.Stock) {
                    stockSelectedItems?.remove(item);
                    setTeamText(stockSelectedItems!!.size.toString())
                    Log.e("stocklistremove", stockSelectedItems.toString())
                }

                override fun onItemCheck(item: StockTeamPojo.Stock) {
                    stockSelectedItems?.add(item);
                    setTeamText(stockSelectedItems!!.size.toString())
                    Log.e("stocklist", stockSelectedItems.toString())
                }
            });

        et_search_stock.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                stockTeamAdapter!!.getFilter().filter(s);
            }
        })

        val llm = LinearLayoutManager(this)
        llm.orientation = LinearLayoutManager.VERTICAL
        rv_Players!!.layoutManager = llm
        rv_Players.visibility = View.VISIBLE
        rv_Players!!.adapter = stockTeamAdapter;

        getTeamlist()
    }

    fun getTeamlist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockTeamPojo> =
            apiService.getStockList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), exchangeId,
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<StockTeamPojo> {

            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        Handler().postDelayed(Runnable {
                        }, 100)
                        list!!.addAll(response.body()!!.stock!!);

//                        displayToast(list!!.size.toString())
                        rv_Players.adapter!!.notifyDataSetChanged();

                        //  setStockTeamAdapter(response.body()!!.stock!!)

                    }
                } else {
                    Toast.makeText(
                        this@ActivityCreateTeam,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<StockTeamPojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivityCreateTeam,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }


    fun getWizardStocklist() {
        val d = StockDialog.showLoading(this)
        d.setCanceledOnTouchOutside(false)
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<StockTeamPojo> =
            apiService.getWizardStockList(
                getFromPrefsString(StockConstant.ACCESSTOKEN).toString(), exchangeId,
                getFromPrefsString(StockConstant.USERID).toString()
            )
        call.enqueue(object : Callback<StockTeamPojo> {

            override fun onResponse(call: Call<StockTeamPojo>, response: Response<StockTeamPojo>) {
                d.dismiss()
                if (response.body() != null) {
                    if (response.body()!!.status == "1") {
                        flag = true
                        Handler().postDelayed(Runnable {
                        }, 100)
                        list!!.clear()
                        list!!.addAll(response.body()!!.stock!!);
                        setTeamText(list!!.size.toString())
                        stockSelectedWizardItems!!.addAll(list!!)
//                        stockSelectedItems!!.addAll(response.body()!!.stock!!)
//                        displayToast(list!!.size.toString())
                        setWizardAdapter()

                        //  setStockTeamAdapter(response.body()!!.stock!!)

                    }
                } else {
                    Toast.makeText(
                        this@ActivityCreateTeam,
                        resources.getString(R.string.internal_server_error),
                        Toast.LENGTH_LONG
                    ).show()
                    d.dismiss()
                }
            }

            override fun onFailure(call: Call<StockTeamPojo>, t: Throwable) {
                println(t.toString())
                Toast.makeText(
                    this@ActivityCreateTeam,
                    resources.getString(R.string.something_went_wrong),
                    Toast.LENGTH_LONG
                ).show()
                displayToast(resources.getString(R.string.something_went_wrong))
                d.dismiss()
            }
        })
    }

    /* @SuppressLint("WrongConstant")
     private fun setStockTeamAdapter(stock: List<StockTeamPojo.Stock>) {
         val llm = LinearLayoutManager(this)
         llm.orientation = LinearLayoutManager.VERTICAL
         rv_Players!!.layoutManager = llm
         rv_Players.visibility = View.VISIBLE
         rv_Players!!.adapter = StockTeamAdapter(
             this,
             stock,
             object : StockTeamAdapter.OnItemCheckListener {
                 override fun onItemUncheck(item: String) {
                     stockSelectedItems?.remove(item);
                     setTeamText(stockSelectedItems!!.size.toString())
                     Log.e("stocklistremove", stockSelectedItems.toString())
                 }

                 override fun onItemCheck(item: String) {
                     stockSelectedItems?.add(item);
                     setTeamText(stockSelectedItems!!.size.toString())
                     Log.e("stocklist", stockSelectedItems.toString())
                 }
             });
     }*/

    fun setTeamText(add: String) {
        tvteamplayer.setText(add + "/12")
        if (add.toInt() == 12) {
            tvViewteam.isEnabled = true
            tvViewteam.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.white_fill_button))
        } else {
            tvViewteam.isEnabled = false
            tvViewteam.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.grey_fill_button))
        }
    }

    fun setWizardAdapter() {
        rv_Players!!.adapter = WizardStockTeamAdapter(
            this,
            list as ArrayList,
            object : WizardStockTeamAdapter.OnItemCheckListener {
                override fun onItemUncheck(item: StockTeamPojo.Stock) {
                    stockSelectedWizardItems?.remove(item);
                    setTeamText(stockSelectedWizardItems!!.size.toString())
                    Log.e("stocklistremove", stockSelectedWizardItems.toString())
                }

                override fun onItemCheck(item: StockTeamPojo.Stock) {
                    stockSelectedWizardItems?.add(item);
                    setTeamText(stockSelectedWizardItems!!.size.toString())
                    Log.e("stocklist", stockSelectedItems.toString())
                }
            });
    }


}