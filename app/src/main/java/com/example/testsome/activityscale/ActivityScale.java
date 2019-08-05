package com.example.testsome.activityscale;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testsome.R;

/**
 * Created by philer on 2019/7/23.
 * Describe:
 */
public class ActivityScale extends AppCompatActivity {

    ImageView ivBack;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        beforSetcontent();
        setContentView(R.layout.activity_scale);
        ivBack = findViewById(R.id.ivBack);
    }


    protected void beforSetcontent(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().getDecorView().setSystemUiVisibility(Window.FEATURE_ACTION_BAR_OVERLAY);

        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        getWindow().setFlags(0x80000000,0x80000000);
    }
}
