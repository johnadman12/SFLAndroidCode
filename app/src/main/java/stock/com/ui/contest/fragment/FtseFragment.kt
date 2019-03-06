package stock.com.ui.contest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import stock.com.R
import stock.com.ui.contest.activity.AllContestActivity

class FtseFragment : Fragment(){

    private lateinit var viewFourFragment: View
    private lateinit var  ctx: AllContestActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewFourFragment = inflater.inflate(R.layout.one_fragment, container, false)
        ctx = activity as AllContestActivity

        return viewFourFragment
    }
}