package com.zte.reader;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

public class ShowActivity extends AppCompatActivity {

    // 拿到 用于展示图片的控件
    ImageView iv;
    // 准备即将要展示的所有图片控件
    int[] imgs = {R.drawable.n3, R.drawable.n2, R.drawable.n1};
    int pos = 0;

    public void countdown(View view){

        new Thread(()-> {

            while (pos != 3){

                Message m = new Message();
                ShowActivity.this.handler.sendMessage(m);

                try {
                    Thread.sleep(999);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                pos++;
            }


        }).start();

    }

    private void change(){
        iv.setImageResource(imgs[pos]);
        iv.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom));
    }

    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        TextView tv = (TextView) findViewById(R.id.titler);
        String content = getIntent().getStringExtra("content");
        tv.setText(content);

        iv = (ImageView)findViewById(R.id.numis);

        // 搭建一个 父线程 和 子线程 之间的桥梁
        handler = new Handler(){

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                change();
            }
        };


    }
}
