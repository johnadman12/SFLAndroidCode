package stock.com.ui.contest.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import stock.com.R
import stock.com.ui.contest.activity.AllContestActivity

class NasdaqFragment : Fragment(){

    private lateinit var viewOneFragment: View
    private lateinit var  ctx: AllContestActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewOneFragment = inflater.inflate(R.layout.one_fragment, container, false)
        ctx = activity as AllContestActivity

        return viewOneFragment
    }
}