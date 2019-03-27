package stock.com.ui.GoThroughScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import org.jetbrains.annotations.Nullable;
import stock.com.AppBase.BaseActivity;
import stock.com.R;
import stock.com.ui.splash.activity.WelcomeActivity;
import stock.com.utils.StockConstant;


public class ActivityGoThrough1 extends BaseActivity {
    AppCompatButton btn_Skip, btn_Next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_throgh_1);
        StockConstant.getACTIVITIES().add(this);
        btn_Next = findViewById(R.id.btn_Next);
        btn_Skip = findViewById(R.id.btn_Skip);
        btn_Skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIntoPrefsString(StockConstant.INSTANCE.getUSERFIRSTTIME(), "no");
                startActivity(new Intent(ActivityGoThrough1.this, WelcomeActivity.class));
                finish();
            }
        });
        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                saveIntoPrefsString(StockConstant.INSTANCE.getUSERFIRSTTIME(), "no");
                startActivity(new Intent(ActivityGoThrough1.this, ActivityGoThrough2.class));
                finish();
            }
        });
    }
}
