package stock.com.ui.GoThroughScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.widget.AppCompatButton;
import org.jetbrains.annotations.Nullable;
import stock.com.AppBase.BaseActivity;
import stock.com.R;
import stock.com.utils.StockConstant;

public class ActivityGoThrough2 extends BaseActivity {
    AppCompatButton btn_Next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_go_through2);
        StockConstant.getACTIVITIES().add(this);
        btn_Next = findViewById(R.id.btn_Next);
        btn_Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActivityGoThrough2.this, ActivityGoThrough3.class));
                finish();
            }
        });

    }
}
