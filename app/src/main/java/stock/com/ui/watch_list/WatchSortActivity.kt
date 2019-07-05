package stock.com.ui.watch_list

import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import kotlinx.android.synthetic.main.activity_sort_watch.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.StockConstant

class WatchSortActivity : BaseActivity(),CompoundButton.OnCheckedChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sort_watch)
        img_btn_close.visibility=View.VISIBLE;

        img_btn_back.setOnClickListener {
           onBackPressed()
           /* val resultIntent = Intent()
            setResult(Activity.RESULT_OK, resultIntent)
            finish()*/
        }
        img_btn_close.setOnClickListener {
            finish()
        }

        checkBoxDailyChanges.setOnCheckedChangeListener(this);
        cb_weekly.setOnCheckedChangeListener(this);
        cb_monthly.setOnCheckedChangeListener(this);
        cb_price_low.setOnCheckedChangeListener(this);
        cb_price_high.setOnCheckedChangeListener(this);



        var temp="";

        btn_Apply.setOnClickListener {
            if(cb_price_high.isChecked){
                temp="high";
            }
            if(cb_price_low.isChecked){
                temp="low";
            }

            if(checkBoxDailyChanges.isChecked){
                temp="daily";
            }

            if(cb_monthly.isChecked){
                temp="dailyLTH";
            }

            var intent=Intent();
            intent.putExtra("flag",temp)
            setResult(Activity.RESULT_OK,intent);
            finish();

        }





    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when(buttonView!!.id){
            R.id.checkBoxDailyChanges->{
                if(isChecked){
                    cb_weekly.isChecked=false;
                    cb_monthly.isChecked=false;
                    cb_price_low.isChecked=false;
                    cb_price_high.isChecked=false;
                }
            }  R.id.cb_weekly->{
                if(isChecked){
                    checkBoxDailyChanges.isChecked=false;
                    cb_monthly.isChecked=false;
                    cb_price_low.isChecked=false;
                    cb_price_high.isChecked=false;
                }
            }
            R.id.cb_monthly->{
                if(isChecked){
                    cb_weekly.isChecked=false;
                    checkBoxDailyChanges.isChecked=false;
                    cb_price_low.isChecked=false;
                    cb_price_high.isChecked=false;
                }
            } R.id.cb_price_low->{
                if(isChecked){
                    cb_weekly.isChecked=false;
                    checkBoxDailyChanges.isChecked=false;
                    cb_monthly.isChecked=false;
                    cb_price_high.isChecked=false;
                }
            }R.id.cb_price_high->{
                if(isChecked){
                    cb_weekly.isChecked=false;
                    checkBoxDailyChanges.isChecked=false;
                    cb_monthly.isChecked=false;
                    cb_price_low.isChecked=false;
                }
            }


        }
    }

}
