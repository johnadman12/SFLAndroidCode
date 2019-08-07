package stock.com.ui.dashboard

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_web_url.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class WebviewActivity:BaseActivity(){

    var url= "https://dfxchange.com/dfxchange/api/controllers/graph.php"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_url)
        webview.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(url)

    }
}
