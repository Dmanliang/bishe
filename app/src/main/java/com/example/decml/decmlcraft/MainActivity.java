package com.example.decml.decmlcraft;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private MyMap myMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //显示自定义的SurfaceView视图
        myMap   =   new MyMap(this);
        setContentView(myMap);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
