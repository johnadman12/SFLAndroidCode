package stock.com.ui.contest_invitation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_contest_invitation.*
import stock.com.R
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.ui.dashboard.my_contest.ActivityInviteUser
import stock.com.utils.StockConstant


class ContestInvitationActivity : BaseActivity() {
    var inviteCode: String = ""
    var contestId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contest_invitation)
        img_btn_back.setOnClickListener {
            onBackPressed();
        }

        if (intent != null) {
            inviteCode = intent.getStringExtra(StockConstant.CONTESTCODE)
            contestId = intent.getStringExtra(StockConstant.CONTESTID)
        }
        ll_share.setOnClickListener {
            shareIntent()
        }
        if (!TextUtils.isEmpty(inviteCode))
            textToCopy.setText(inviteCode)

        ll_tap.setOnClickListener {
            val sdk = android.os.Build.VERSION.SDK_INT
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
                clipboard.text = textToCopy.text
            } else {
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = android.content.ClipData.newPlainText("text label", textToCopy.text)
                clipboard.primaryClip = clip
            }
            displayToast("Text Copied!!! " + textToCopy.text, "warning")
        }

        ll_invite.setOnClickListener {
            startActivity(
                Intent(this@ContestInvitationActivity, ActivityInviteUser::class.java)
                    .putExtra(StockConstant.CONTESTID, contestId)
            )
        }

    }

    fun shareIntent() {
        val shareIntent = Intent()
        shareIntent.action = Intent.ACTION_SEND
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_TEXT, textToCopy.text);
        startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
    }
}
