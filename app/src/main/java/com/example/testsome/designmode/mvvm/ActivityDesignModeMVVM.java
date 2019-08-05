package com.example.testsome.designmode.mvvm;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TimingLogger;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import com.example.testsome.R;
import com.example.testsome.databinding.ActivityDesignmodemvvmBinding;
import android.Manifest;
import java.lang.reflect.Method;
import java.util.Random;

/**
 * Created by philer on 2019/7/10.
 * Describe:
 */
public class ActivityDesignModeMVVM extends AppCompatActivity implements View.OnClickListener {
    BeanMVVM beanMVVM = new BeanMVVM("philer", -1, "chengdu");
    ActivityDesignmodemvvmBinding viewDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewDataBinding =
                DataBindingUtil.setContentView(this, R.layout.activity_designmodemvvm);
        viewDataBinding.setBeanMVVM(beanMVVM);
        viewDataBinding.buChange.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.STATUS_BAR}, 2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewDataBinding.buChange.postDelayed(new Runnable() {
            @Override
            public void run() {
                Object service = getSystemService("statusbar");
                try {
                    Class<?> statusBarManager = Class.forName
                            ("android.app.StatusBarManager");
                    Method expand = statusBarManager.getMethod("disable", int.class);
                    expand.invoke(service, 1);
                    Log.w("philer", "invoke finish");
                } catch (Exception e) {
                    //unBanStatusBar();
                    //e.printStackTrace();
                    Log.w("philer", "exception "+e.getMessage());
                }
            }
        }, 1000);
    }

    private static View getRootView(Activity context) {
        return ((ViewGroup) context.findViewById(android.R.id.content)).getChildAt(0);
    }

    @Override
    public void onClick(View view) {
        beanMVVM.address = "test";
        beanMVVM.notifyChange();
    }
}
