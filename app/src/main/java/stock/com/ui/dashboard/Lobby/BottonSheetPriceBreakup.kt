package stock.com.ui.dashboard.Lobby

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_pricebreakup.*
import stock.com.R
import stock.com.ui.pojo.WinningList

@SuppressLint("ValidFragment")
public class BottonSheetPriceBreakup
    (
    val count: Int,
    var list: ArrayList<WinningList.Pricebreaklist>,
    var activityPriceBreak: ActivityPriceBreak
) : BottomSheetDialogFragment() {


    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }


    @SuppressLint("RestrictedApi", "WrongConstant")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        //Get the content View
        val contentView = View.inflate(context, R.layout.bottom_sheet_pricebreakup, null)
        dialog.setContentView(contentView)
        //Set the coordinator layout behavior
        val params = (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior

        //Set callback
        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }

        dialog.img_Close.setOnClickListener { dismiss() }

        val llm = LinearLayoutManager(activity)
        llm.orientation = LinearLayoutManager.VERTICAL
        dialog.rv_Prize!!.layoutManager = llm
        dialog.rv_Prize!!.adapter = BottomSheetAdapter(activity!!, count, list, this)

    }

    fun callAdapter(
        item: java.util.ArrayList<WinningList.Winner>,
        winner: String,
        usercontestSizeId: String
    ) {
        activityPriceBreak.setAdapter(item)
        activityPriceBreak.contestSizeId = usercontestSizeId
        activityPriceBreak.contestSizeWinner = winner
        dismiss()
    }

}