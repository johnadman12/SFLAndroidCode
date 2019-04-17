package stock.com.ui.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.wallet.WalletActivity

class SettingActivity : BaseActivity(),View.OnClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        ll_wallet.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);


    }
    override fun onClick(v: View?) {
            when(v!!.id){
                R.id.ll_wallet->{
                    var intent=Intent(this,WalletActivity::class.java);
                    startActivity(intent)
                }

                R.id.img_btn_back->{
                    onBackPressed()
                }


            }
    }


}
