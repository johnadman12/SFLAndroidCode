package stock.com.ui.createTeam.activity

import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import kotlinx.android.synthetic.main.activity_team_preview.*
import stock.com.AppBase.BaseActivity
import stock.com.R

class TeamPreviewActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.img_Edit -> {
                finish()
            }
            R.id.img_Close -> {
                finish()
            }
        }
    }

    var isEdit = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_preview)
        initViews()
    }


    private fun initViews() {
        isEdit = intent.getIntExtra("show", 0)
        if (isEdit == 1)
            img_Edit.visibility = GONE
        else
            img_Edit.visibility = VISIBLE
        img_Edit.setOnClickListener(this)
        img_Close.setOnClickListener(this)
    }


}