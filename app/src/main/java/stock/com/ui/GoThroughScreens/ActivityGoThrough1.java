package stock.com.ui.GoThroughScreens;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import org.jetbrains.annotations.Nullable;
import stock.com.AppBase.BaseActivity;
import stock.com.R;
import stock.com.ui.splash.activity.WelcomeActivity;
import stock.com.utils.StockConstant;

import java.util.ArrayList;


public class ActivityGoThrough1 extends BaseActivity {
    AppCompatButton btn_Skip, btn_Next;
    TextView tv_title, into_des, into_des_small;


    boolean b = false;

    private static final String TAG = "ActivityGoThrough1";
    private ArrayList<Drawable> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_throgh);
        StockConstant.getACTIVITIES().add(this);


        list = new ArrayList<>();

        Drawable drawable1 = ContextCompat.getDrawable(this, R.mipmap.intro1);
        Drawable drawable2 = ContextCompat.getDrawable(this, R.mipmap.intro2);
        Drawable drawable3 = ContextCompat.getDrawable(this, R.mipmap.intro3);
        Drawable drawable4 = ContextCompat.getDrawable(this, R.mipmap.intro4);
        Drawable drawable5 = ContextCompat.getDrawable(this, R.mipmap.intro5);
        Drawable drawable6 = ContextCompat.getDrawable(this, R.mipmap.intro6);


        list.add(drawable1);
        list.add(drawable2);
        list.add(drawable3);
        list.add(drawable4);
        list.add(drawable5);
        list.add(drawable6);

        final ViewPager viewPager = findViewById(R.id.viewPager);
        final TabLayout tab = findViewById(R.id.tab_layout);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, list);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);


        btn_Next = findViewById(R.id.btn_Next);
        btn_Skip = findViewById(R.id.btn_Skip);
        tv_title = findViewById(R.id.tv_title);
        into_des = findViewById(R.id.into_des);
        into_des_small = findViewById(R.id.into_des_small);
        btn_Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIntoPrefsString(StockConstant.INSTANCE.getUSERFIRSTTIME(), "no");
                startActivity(new Intent(ActivityGoThrough1.this, WelcomeActivity.class));
                finish();
            }
        });


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    btn_Skip.setVisibility(View.VISIBLE);
                    btn_Next.setText("Next");
                    tv_title.setText(getResources().getString(R.string.deposit_funds));
                    into_des.setText(getResources().getString(R.string.intro1));
                } else {
                    btn_Skip.setVisibility(View.INVISIBLE);
                }
                if (position == 5) {
                    b = true;
                    btn_Next.setText("Finish");
                    tv_title.setText(getResources().getString(R.string.intro6));
                    into_des.setText("");
                    into_des_small.setText("");
                } else if (position == 1) {
                    b = false;
                    btn_Next.setText("Next");
                    tv_title.setText(getResources().getString(R.string.join_contest));
                    into_des.setText(getResources().getString(R.string.intro2));
                    into_des_small.setText("");
                } else if (position == 2) {
                    b = false;
                    btn_Next.setText("Next");
                    tv_title.setText(getResources().getString(R.string.win_cash));
                    into_des.setText(getResources().getString(R.string.intro3));
                    into_des_small.setText(getResources().getString(R.string.intro3_1));
                } else if (position == 3) {
                    b = false;
                    btn_Next.setText("Next");
                    tv_title.setText(getResources().getString(R.string.form_team));
                    into_des.setText(getResources().getString(R.string.intro4));
                    into_des_small.setText("");
                } else if (position == 4) {
                    b = false;
                    btn_Next.setText("Next");
                    tv_title.setText(getResources().getString(R.string.reffral_bonus));
                    into_des.setText(getResources().getString(R.string.intro5));
                    into_des_small.setText(getResources().getString(R.string.intro5_1));
                }

                Log.d(TAG, "onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  saveIntoPrefsString(StockConstant.INSTANCE.getUSERFIRSTTIME(), "no");
                /*startActivity(new Intent(ActivityGoThrough1.this, ActivityGoThrough2.class));
                finish();*/
                if (b) {
                    saveIntoPrefsString(StockConstant.INSTANCE.getUSERFIRSTTIME(), "no");
                    startActivity(new Intent(ActivityGoThrough1.this, WelcomeActivity.class));
                    finish();
                } else {
                    int tab = viewPager.getCurrentItem();
                    tab++;
                    viewPager.setCurrentItem(tab);
                }
            }
        });
    }
}
