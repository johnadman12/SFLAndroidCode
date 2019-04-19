package stock.com.ui.profile

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.edit_profile.EditProfileActivity
import stock.com.ui.friends.FriendsActivity
import stock.com.ui.share.ShareActivity
import stock.com.ui.statistics.StatisticsActivity
import stock.com.ui.wallet.WalletActivity

class ProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        showDialog();

        tv_statics.setOnClickListener {
            var intent = Intent(this, StatisticsActivity::class.java);
            startActivity(intent)
        }

        tv_invite_friends.setOnClickListener {
            var intent = Intent(this, ShareActivity::class.java);
            startActivity(intent);
        }

        ll_friend.setOnClickListener {

            var intent=Intent(this,FriendsActivity::class.java);
            startActivity(intent);

        }

        ll_wallet.setOnClickListener {

            var  intent=Intent(this,WalletActivity::class.java);
            startActivity(intent);

        }
        ll_edit.setOnClickListener {
            var intent=Intent(this,EditProfileActivity::class.java);
            startActivity(intent)
        }
        img_btn_back.setOnClickListener {
            onBackPressed();
        }

    }

    public fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_verification)
        dialog.getWindow().setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.setGravity(Gravity.CENTER)
        dialog.setCancelable(true)
        val lp = WindowManager.LayoutParams()
        val window = dialog.window
        lp.copyFrom(window!!.attributes)
        lp.width = WindowManager.LayoutParams.MATCH_PARENT
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT
        window.attributes = lp
        dialog.setCanceledOnTouchOutside(true);

        val tv_yes = dialog.findViewById(R.id.tv_yes) as TextView
        val tv_cancel = dialog.findViewById(R.id.tv_cancel) as TextView

        tv_yes.setOnClickListener {
            //appLogout();
            dialog.dismiss();
        }
        tv_cancel.setOnClickListener {
            dialog.dismiss();
        }
        dialog.show();
    }
}
