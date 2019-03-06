package stock.com.ui.contest.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import stock.com.ui.contest.fragment.*

class AllContentsPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    override fun getItem(position: Int): Fragment? {
        when (position) {
            0 -> return NasdaqFragment()
            1 -> return NseFragment()
            2 -> return DonjonesFragment()
            3 -> return FtseFragment()
        }
        return null
    }

    override fun getCount(): Int {
        return 4
    }
}