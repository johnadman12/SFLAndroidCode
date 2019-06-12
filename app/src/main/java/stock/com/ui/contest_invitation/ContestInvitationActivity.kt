package stock.com.ui.contest_invitation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_contest_invitation.*
import stock.com.R
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity


class ContestInvitationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contest_invitation)
        img_btn_back.setOnClickListener {
            onBackPressed();
        }
        ll_share.setOnClickListener {
            shareIntent()
        }
        ll_tap.setOnClickListener {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", textToCopy.text)
            clipboard.setPrimaryClip(clip)
            displayToast("Text Copied!!! " + textToCopy.text, "warning")
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
