package stock.com.ui.edit_profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.edit_profile.fragment.ProfileFragment

class EditProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        setFragment(ProfileFragment())

    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
//            .setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out)
            .replace(R.id.container, fragment)
            .commitAllowingStateLoss()
    }
}
