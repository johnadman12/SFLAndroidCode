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

public class ActivityGoThrough6 extends BaseActivity {
    AppCompatButton btn_Start;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_through6);
        StockConstant.getACTIVITIES().add(this);
        btn_Start = findViewById(R.id.btn_Start);
        btn_Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveIntoPrefsString(StockConstant.INSTANCE.getUSERFIRSTTIME(), "no");
                startActivity(new Intent(ActivityGoThrough6.this, WelcomeActivity.class));
                finish();
            }
        });

    }
}
