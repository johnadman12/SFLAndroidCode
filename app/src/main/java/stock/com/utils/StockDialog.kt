package stock.com.utils

import android.app.Activity
import android.app.ProgressDialog

class StockDialog(context: Activity) {
    var ctx: Activity = context

    companion object {
        fun showLoading(activity: Activity): ProgressDialog {
            val mProgressDialog = ProgressDialog(activity)
            mProgressDialog.isIndeterminate = true
            mProgressDialog.setMessage("Loading...")
            if (!activity.isFinishing && !mProgressDialog.isShowing)
                mProgressDialog.show()
            return mProgressDialog
        }
    }

}