package com.example.testsome.testencode;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testsome.R;
import com.philerlib.eventbus.EventbusPhiler;
import com.philerlib.eventbus.SubscribePhiler;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * Created by philer on 2019/7/25.
 * Describe:测试防软件复用
 */
public class ActivityTestCode extends AppCompatActivity implements View.OnClickListener {

    TextView tvMacCode, tvDecode;
    EditText etPermissionCode, etDecodeWord;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testcode);
        tvMacCode = findViewById(R.id.tvMacCode);
        tvMacCode.setOnClickListener(this);
        tvDecode = findViewById(R.id.tvDecode);
        etPermissionCode = findViewById(R.id.etPermissionCode);
        etDecodeWord = findViewById(R.id.etDecodeWord);
        findViewById(R.id.buCheck).setOnClickListener(this);
        initData();
        EventbusPhiler.getInstance().registEvent(this);
    }

    private void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 11);
        }
        tvMacCode.setText("手机唯一标示码 : " + getUnicode());
    }

    private String getUnicode() {
        String phoneSign = HelperUnicCode.getPhoneSign(this);
        return phoneSign;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tvMacCode) {
            EventbusPhiler.getInstance().pushEvent("测试: "+System.currentTimeMillis());
            if(true){
                return;
            }
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
// 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", getUnicode());
// 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            Toast.makeText(this, "手机唯一标识码已复制", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.buCheck) {
            EventbusPhiler.getInstance().unregistEvent(this);
            if(true){
                return;
            }
            String permissionCode = etPermissionCode.getText().toString();
            if (TextUtils.isEmpty(permissionCode)) {
                Toast.makeText(this, "授权码未输入!", Toast.LENGTH_SHORT).show();
                return;
            }
            String password = etDecodeWord.getText().toString();
            if (TextUtils.isEmpty(password) || password.length() != 16) {
                Toast.makeText(this, "请输入16位的密码!", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                String s = Main.aesDecrypt(permissionCode, password);
                if (getUnicode().equals(s)) {
                    Toast.makeText(this, "授权成功!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, "授权失败!", Toast.LENGTH_LONG).show();
                }
                tvDecode.setText("解码后得到 : " + s);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SubscribePhiler
    public void getParames(String params){
        tvMacCode.setText("接收到数据 : "+params);
    }
}
