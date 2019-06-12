package stock.com.ui.dashboard.Lobby

import android.os.Bundle
import android.view.View
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_code_join.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.utils.StockConstant


class ActivityCodeJoin : BaseActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
       when(v!!.id){
           R.id.img_btn_back ->{
               finish()
           }
           R.id.btn_Join ->{
               displayToast(getString(R.string.join), "message")
           }
       }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code_join)
        StockConstant.ACTIVITIES.add(this)
        initViews()
    }

    private fun initViews() {
        img_btn_back.setOnClickListener(this)
        et_code_join.setOnClickListener(this)
        btn_Join.setOnClickListener(this)

    }

}



