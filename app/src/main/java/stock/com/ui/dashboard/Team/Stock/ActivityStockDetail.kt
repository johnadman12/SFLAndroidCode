package stock.com.ui.dashboard.Team.Stock

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_stock_detail_page.*
import kotlinx.android.synthetic.main.include_back.*
import stock.com.R


class ActivityStockDetail : AppCompatActivity(), View.OnClickListener {

    private var fragment: Fragment? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stock_detail_page)

        setFragment(ChartFragment());

        ll_news.setOnClickListener(this);
        img_btn_back.setOnClickListener(this);
        ll_data.setOnClickListener(this);
        ll_chart.setOnClickListener(this);
        ll_analystics.setOnClickListener(this);
        ll_comments.setOnClickListener(this);
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.img_btn_back -> {
                onBackPressed()
            }
            R.id.ll_chart -> {
                if (fragment is ChartFragment)
                    return;
                setFragment(ChartFragment());
                setLinearLayoutColor(ll_news, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_chart, ContextCompat.getColor(this, R.color.colorbutton))
                setLinearLayoutColor(ll_analystics, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_comments, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_data, ContextCompat.getColor(this, R.color.white));

                setTextViewColor(tv_news, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_data, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_chart, ContextCompat.getColor(this, R.color.white));
                setTextViewColor(tv_analystics, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_comments, ContextCompat.getColor(this, R.color.textColorLightBlack));

                img_btn_news.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_data.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_chart.setColorFilter(ContextCompat.getColor(this, R.color.white));
                img_btn_analystics.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_comments.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));

            }

            R.id.ll_news -> {
                if (fragment is NewsFragment)
                    return;
                setFragment(NewsFragment());
                setLinearLayoutColor(ll_news, ContextCompat.getColor(this, R.color.colorbutton));
                setLinearLayoutColor(ll_chart, ContextCompat.getColor(this, R.color.white))
                setLinearLayoutColor(ll_analystics, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_comments, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_data, ContextCompat.getColor(this, R.color.white));

                setTextViewColor(tv_news, ContextCompat.getColor(this, R.color.white));
                setTextViewColor(tv_data, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_chart, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_analystics, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_comments, ContextCompat.getColor(this, R.color.textColorLightBlack));

                img_btn_news.setColorFilter(ContextCompat.getColor(this, R.color.white));
                img_btn_data.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_chart.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_analystics.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_comments.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
            }

            R.id.ll_analystics -> {
                if (fragment is AnalysticFragment)
                    return;
                setFragment(AnalysticFragment());
                setLinearLayoutColor(ll_news, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_chart, ContextCompat.getColor(this, R.color.white))
                setLinearLayoutColor(ll_analystics, ContextCompat.getColor(this, R.color.colorbutton));
                setLinearLayoutColor(ll_comments, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_data, ContextCompat.getColor(this, R.color.white));

                setTextViewColor(tv_news, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_data, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_chart, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_analystics, ContextCompat.getColor(this, R.color.white));
                setTextViewColor(tv_comments, ContextCompat.getColor(this, R.color.textColorLightBlack));

                img_btn_news.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_data.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_chart.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_analystics.setColorFilter(ContextCompat.getColor(this, R.color.white));
                img_btn_comments.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
            }
            R.id.ll_data -> {
                if (fragment is DataFragment)
                    return;
                setFragment(DataFragment());
                setLinearLayoutColor(ll_news, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_chart, ContextCompat.getColor(this, R.color.white))
                setLinearLayoutColor(ll_analystics, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_comments, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_data, ContextCompat.getColor(this, R.color.colorbutton));

                setTextViewColor(tv_news, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_data, ContextCompat.getColor(this, R.color.white));
                setTextViewColor(tv_chart, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_analystics, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_comments, ContextCompat.getColor(this, R.color.textColorLightBlack));

                img_btn_news.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_data.setColorFilter(ContextCompat.getColor(this, R.color.white));
                img_btn_chart.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_analystics.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_comments.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
            }
            R.id.ll_comments -> {
                if (fragment is CommentsFragment)
                    return;
                setFragment(CommentsFragment());
                setLinearLayoutColor(ll_news, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_chart, ContextCompat.getColor(this, R.color.white))
                setLinearLayoutColor(ll_analystics, ContextCompat.getColor(this, R.color.white));
                setLinearLayoutColor(ll_comments, ContextCompat.getColor(this, R.color.colorbutton));
                setLinearLayoutColor(ll_data, ContextCompat.getColor(this, R.color.white));

                setTextViewColor(tv_news, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_data, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_chart, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_analystics, ContextCompat.getColor(this, R.color.textColorLightBlack));
                setTextViewColor(tv_comments, ContextCompat.getColor(this, R.color.white));

                img_btn_news.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_data.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_chart.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_analystics.setColorFilter(ContextCompat.getColor(this, R.color.GrayColor));
                img_btn_comments.setColorFilter(ContextCompat.getColor(this, R.color.white));
            }
            R.id.img_btn_back -> {
                onBackPressed();
            }
        }
    }

    private fun setTextViewColor(tv: TextView, color: Int) {
        tv.setTextColor(color);
    }

    private fun setLinearLayoutColor(ll: LinearLayoutCompat, color: Int) {
        ll.setBackgroundColor(color);
    }

    private fun setFragment(fragment: Fragment) {
        this.fragment = fragment;
        val fragmentManager = supportFragmentManager
        fragmentManager
            .beginTransaction()
//            .setCustomAnimations(R.anim.bottom_in, R.anim.bottom_out)
            .replace(R.id.container1, fragment)
            .commitAllowingStateLoss()
    }
}
