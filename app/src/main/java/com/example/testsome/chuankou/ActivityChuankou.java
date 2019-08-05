package com.example.testsome.chuankou;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.testsome.R;

/**
 * Created by philer on 2019/7/22.
 * Describe:
 */
public class ActivityChuankou extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chuankou);
        startListener();
    }

    private void startListener(){
        //串口数据监听事件
        SerialPortUtils serialPortUtils = new SerialPortUtils();
        serialPortUtils.setOnDataReceiveListener(new SerialPortUtils.OnDataReceiveListener() {
            @Override
            public void onDataReceive(byte[] buffer, int size) {
                Log.d("", "进入数据监听事件中。。。" + new String(buffer));
                Toast.makeText(ActivityChuankou.this, "串口接收到数据:"+new String(buffer), Toast.LENGTH_SHORT).show();
                //
                //在线程中直接操作UI会报异常：ViewRootImpl$CalledFromWrongThreadException
                //解决方法：handler
                //
            }
        });
        serialPortUtils.openSerialPort();
    }
}
