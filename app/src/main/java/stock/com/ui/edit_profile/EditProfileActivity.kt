package stock.com.ui.edit_profile

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.AppBase.BaseActivity
import stock.com.R
import stock.com.ui.edit_profile.fragment.ContactInfoFragment
import stock.com.ui.edit_profile.fragment.CredentialsFragment
import stock.com.ui.edit_profile.fragment.ProfileFragment

class EditProfileActivity : BaseActivity(), View.OnClickListener {


    private var fragment: Fragment? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        setFragment(ProfileFragment());

        ll_profile.setOnClickListener(this);
        ll_contact_info.setOnClickListener(this);
        ll_credentials.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.ll_profile -> {
                if (fragment is ProfileFragment)
                    return;

                setFragment(ProfileFragment());
                setLinearLayoutColor(ll_contact_info, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_profile, ContextCompat.getColor(this, R.color.colorbutton));
                setLinearLayoutColor(ll_credentials, ContextCompat.getColor(this, R.color.white));

                setTextViewColor(tv_profile, ContextCompat.getColor(this, R.color.white));
                setTextViewColor(tv_contact_info, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_crediantals, ContextCompat.getColor(this, R.color.textColorLightBlack));
                img_btn_profile.setColorFilter(ContextCompat.getColor(this, R.color.white));
                img_btn_contact_info.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_credentials.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));


            }
            R.id.ll_contact_info -> {
                if (fragment is ContactInfoFragment)
                    return;
                setFragment(ContactInfoFragment());
                setLinearLayoutColor(ll_profile, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_contact_info, ContextCompat.getColor(this, R.color.colorbutton))
                setLinearLayoutColor(ll_credentials, ContextCompat.getColor(this, R.color.white));

                setTextViewColor(tv_contact_info, ContextCompat.getColor(this, R.color.white));
                setTextViewColor(tv_profile, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_crediantals, ContextCompat.getColor(this, R.color.textColorLightBlack));

                img_btn_contact_info.setColorFilter(ContextCompat.getColor(this, R.color.white));
                img_btn_profile.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_credentials.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));


            }

            R.id.ll_credentials -> {
                if (fragment is CredentialsFragment)
                    return;
                setFragment(CredentialsFragment());

                setLinearLayoutColor(ll_profile, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_contact_info, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_credentials, ContextCompat.getColor(this, R.color.colorbutton))

                setTextViewColor(tv_contact_info, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_profile, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_crediantals, ContextCompat.getColor(this, R.color.white));

                img_btn_contact_info.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_profile.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_credentials.setColorFilter(ContextCompat.getColor(this, R.color.white));

            }

            R.id.img_btn_back -> {
                onBackPressed()
            }
        }
    }

    private fun setLinearLayoutColor(ll: LinearLayout, color: Int) {
        ll.setBackgroundColor(color);
    }

    private fun setTextViewColor(tv: TextView, color: Int) {
        tv.setTextColor(color);
    }

    private fun setFragment(fragment: Fragment) {
        this.fragment = fragment;
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
//            .setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out)
            .replace(R.id.container, fragment)
            .commitAllowingStateLoss()
    }
}
