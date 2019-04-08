package stock.com.ui.support

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_support.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.dashboard.more.activity.WebViewActivity

class SupportActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_support)

        img_btn_back.setOnClickListener {
            onBackPressed();
        }

        ll_faq.setOnClickListener {

            var intent=Intent(this,WebViewActivity::class.java);
            intent.putExtra("PAGE_SLUG","faq");
            startActivity(intent);

        }

        ll_how_to_play.setOnClickListener {

            var intent=Intent(this,WebViewActivity::class.java);
            intent.putExtra("PAGE_SLUG","how-to-play");
            startActivity(intent);

        }

        tv_rules_scoring.setOnClickListener {
            var intent=Intent(this,WebViewActivity::class.java);
            intent.putExtra("PAGE_SLUG","rules-and-winnings");
            startActivity(intent);
        }
        ll_support.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL);
            intent.data = Uri.parse("tel:180011112222")
            if (ActivityCompat.checkSelfPermission(applicationContext, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
               //displayToast("permission not granted")
                val permissions = arrayOf(android.Manifest.permission.CALL_PHONE)
                ActivityCompat.requestPermissions(this, permissions,10)
            }else{
                startActivity(intent);
            }
        }
    }
}
