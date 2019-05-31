package com.lx.zfklx5;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ZfView mZfView;
    private TextView mTvPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvPsw = findViewById(R.id.tv_psw);
        mZfView = findViewById(R.id.zfView);
        mZfView.setZfListener(new ZfView.ZfListener() {
            @Override
            public void onInput(String str) {
                mTvPsw.setText(str);
            }

            @Override
            public void onCompeleted(String str) {
                mTvPsw.setText("输入完成，密码是:" + str);
//                Toast.makeText(MainActivity.this, "输入完成，密码是:" + str, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
