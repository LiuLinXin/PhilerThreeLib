package com.example.testsome.designmode.mvc;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testsome.R;
import com.example.testsome.tencentcos.GlideEngine;
import com.example.testsome.tencentcos.HelperTencentCos;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.List;

/**
 * Created by philer on 2019/7/9.
 * Describe:
 */
public class ActivityDesignmodeMvc extends AppCompatActivity implements View.OnClickListener, RequestCall {

    TextView tvShow;
    Button buRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designmodemvc);
        tvShow = findViewById(R.id.tvShow);
        buRequest = findViewById(R.id.buRequest);
        buRequest.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 231);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.buRequest) {
//            new Model().requestData(this);
            showImage();
        }
    }

    public void showImage() {
        Matisse.from(this)
                .choose(MimeType.ofAll(), false)
                .countable(true)
                .maxSelectable(9)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.album_item_height))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(13);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<String> strings = Matisse.obtainPathResult(data);
        if(strings.size() > 0){
            String s = strings.get(0);
            Log.i("philer", "file path : "+s);
            HelperTencentCos.getInstance().init(this);
            HelperTencentCos.getInstance().updateFile(s);
        }
    }

    @Override
    public void requestOK(Model model) {
        tvShow.setText(model.name + " - " + model.age);
    }

    @Override
    public void requestFail() {
        tvShow.setText("request fail");
    }
}
