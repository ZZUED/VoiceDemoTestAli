package com.alibaba.idst.nlsdemo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.alibaba.idst.R;


public class MainActivity extends Activity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 获取页面点击对应的appkey
     * 将appkey传入后面的服务
     * Asr
     * */
    public void AsrDemo(View view){
        Intent intent = new Intent(this, PublicAsrActivity.class);
        startActivity(intent);
    }

    public void NluDemo(View view){
        Intent intent = new Intent(this, PublicNluActivity.class);
        startActivity(intent);
    }

    public void TtsDemo(View v){
        Intent intent = new Intent(this, PublicTtsActivity.class);
        startActivity(intent);
    }



}