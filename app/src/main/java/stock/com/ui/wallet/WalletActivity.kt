package stock.com.ui.wallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_wallet.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.wallet.adapter.WalletAdapter

class WalletActivity : BaseActivity(), View.OnClickListener {

    private var watchListAdapter: WalletAdapter? = null;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wallet)



        tv_deposit.setOnClickListener(this);
        tv_withdrawal.setOnClickListener(this);
        tv_transaction.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);


        watchListAdapter = WalletAdapter(applicationContext!!)
        val llm = LinearLayoutManager(applicationContext)
        llm.orientation = LinearLayoutManager.VERTICAL
        recyclerView_wallet!!.layoutManager = llm
        recyclerView_wallet!!.adapter = watchListAdapter;

    }

    override fun onClick(v: View?) {
        when (v!!.id) {

            R.id.tv_deposit -> {

                changeTextViewColor(ContextCompat.getColor(applicationContext, R.color.white), tv_deposit);
                changeTextViewColor(ContextCompat.getColor(applicationContext, R.color.black), tv_transaction);
                changeTextViewColor(ContextCompat.getColor(applicationContext, R.color.black), tv_withdrawal);

                changeTextViewBackground(ContextCompat.getColor(applicationContext, R.color.history_selector), tv_deposit);
                changeTextViewBackground(ContextCompat.getColor(applicationContext, R.color.white), tv_withdrawal);
                changeTextViewBackground(ContextCompat.getColor(applicationContext, R.color.white), tv_transaction);

            }
            R.id.tv_withdrawal -> {

                changeTextViewColor(ContextCompat.getColor(applicationContext, R.color.white), tv_withdrawal);
                changeTextViewColor(ContextCompat.getColor(applicationContext, R.color.black), tv_transaction);
                changeTextViewColor(ContextCompat.getColor(applicationContext, R.color.black), tv_deposit);


                changeTextViewBackground(ContextCompat.getColor(applicationContext, R.color.white), tv_deposit);
                changeTextViewBackground(ContextCompat.getColor(applicationContext, R.color.history_selector),tv_withdrawal);
                changeTextViewBackground(ContextCompat.getColor(applicationContext, R.color.white), tv_transaction);


            }
            R.id.tv_transaction -> {
                changeTextViewColor(ContextCompat.getColor(applicationContext, R.color.white), tv_transaction);
                changeTextViewColor(ContextCompat.getColor(applicationContext, R.color.black), tv_withdrawal);
                changeTextViewColor(ContextCompat.getColor(applicationContext, R.color.black), tv_deposit);

                changeTextViewBackground(ContextCompat.getColor(applicationContext, R.color.white), tv_deposit)
                changeTextViewBackground(ContextCompat.getColor(applicationContext, R.color.white), tv_withdrawal)
                changeTextViewBackground(ContextCompat.getColor(applicationContext, R.color.history_selector), tv_transaction)

            }

            R.id.img_btn_back->{
                onBackPressed()
            }


        }
    }

    private fun changeTextViewColor(color: Int, textView: TextView) {
        textView.setTextColor(color);
    }

    private fun changeTextViewBackground(color: Int, textView: TextView) {
        textView.setBackgroundColor(color);
    }


}
