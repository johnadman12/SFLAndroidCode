package stock.com.ui.splash.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.GoThroughScreens.ActivityGoThrough1
import stock.com.ui.dashboard.DashBoardActivity
import stock.com.utils.StockConstant


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            try {
                if (getFromPrefsString(StockConstant.USERFIRSTTIME).toString().equals("no")) {
                    // if (getFromPrefsString(StockConstant.USERID).toString() != "") {
                    if (!getFromPrefsString(StockConstant.USERID).toString().equals("")) {
                        startActivity(Intent(this@SplashActivity, DashBoardActivity::class.java))
                        finish()
                    } else {

                        startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
                        finish()
                    }
                } else {
                    startActivity(Intent(this@SplashActivity, ActivityGoThrough1::class.java))
                    finish()
                }


            } catch (e: Exception) {
            }
        }, 2000)
//        if (BuildConfig.APPLICATION_ID == "os.cashfantasy") {
//            Handler().postDelayed({
//                try {
//                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
//                    finish()
//                } catch (e: Exception) {
//                }
//            }, 1000)
//        } else if (BuildConfig.APPLICATION_ID == "os.crickset") {
//            Handler().postDelayed({
//                try {
//                    startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
//                    finish()
//                } catch (e: Exception) {
//                }
//            }, 1000)
//        }

    }


}
