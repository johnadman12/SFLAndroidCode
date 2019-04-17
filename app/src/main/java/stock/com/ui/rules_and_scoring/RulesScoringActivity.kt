package stock.com.ui.rules_and_scoring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_rules_scoring.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class RulesScoringActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rules_scoring)



        img_btn_back.setOnClickListener{
            onBackPressed();
        }

        ll_first.setOnClickListener {
            if(ll_expand.visibility== View.VISIBLE){
                ll_expand.visibility=View.GONE;
                imgBtnArrow.setImageResource(R.mipmap.arrowright)
            }else{
                ll_expand.visibility=View.VISIBLE;
                imgBtnArrow.setImageResource(R.mipmap.arrowdown)
            }
        }
    }
}
