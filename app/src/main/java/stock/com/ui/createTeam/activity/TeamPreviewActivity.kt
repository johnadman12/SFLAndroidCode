package stock.com.ui.createTeam.activity

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_team_preview.*
import kotlinx.android.synthetic.main.outside_toolbar.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class TeamPreviewActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.img_back -> {
                finish()
            }
            R.id.img_close -> {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_preview)
        initViews()
    }


    private fun initViews() {
        img_back.setOnClickListener(this)
        img_close.visibility = VISIBLE
        img_close.setOnClickListener(this)

    }


}