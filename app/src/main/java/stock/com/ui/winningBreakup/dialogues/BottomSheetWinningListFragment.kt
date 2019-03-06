package stock.com.ui.winningBreakup.dialogues

import android.annotation.SuppressLint
import android.app.Dialog
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_winninglist.*
import stock.com.R
import stock.com.ui.winningBreakup.adapter.WinningsListAdapter

class BottomSheetWinningListFragment : BottomSheetDialogFragment() {
    //Bottom Sheet Callback
    private val mBottomSheetBehaviorCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        //Get the content View
        val contentView = View.inflate(context, R.layout.bottom_sheet_winninglist, null)
        dialog.setContentView(contentView)

        //Set the coordinator layout behavior
        val params = (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior

        //Set callback
        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }
        setAdapter()
        dialog.img_Close.setOnClickListener { dismiss() }
    }

    @SuppressLint("WrongConstant")
    private fun setAdapter() {
        val llm = LinearLayoutManager(context)
        llm.orientation = LinearLayoutManager.VERTICAL
        dialog. rv_Prize!!.layoutManager = llm
        dialog.rv_Prize!!.adapter = WinningsListAdapter(context!!)
    }
}