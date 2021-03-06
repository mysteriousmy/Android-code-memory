package com.zte.reader;

import android.Manifest;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class BottomActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:


                    createNewData();
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }

    };


    private ImageSwitcher is;
    private TextView sentence, close;
    private ListView view;

    private FloatingActionButton fab;


    int[] imgs = {R.drawable.new1, R.drawable.new2, R.drawable.new3};
    String[] sentences = {"???????????????????????????", "???????????????????????????", "??????????????????????????????"};
    int pos = 1;

    private float start, end;

    private List<HashMap<String, String>> datas = new ArrayList<>();

    private void createNewData(){
        HashMap<String, String> h1 = new HashMap<>();
        h1.put("content","????????????1");
        h1.put("time","2018-05-17");

        HashMap<String, String> h2 = new HashMap<>();
        h2.put("content","????????????2");
        h2.put("time","2018-05-17");

        HashMap<String, String> h3 = new HashMap<>();
        h3.put("content","????????????3");
        h3.put("time","2018-05-17");

        HashMap<String, String> h4 = new HashMap<>();
        h4.put("content","????????????4");
        h4.put("time","2018-05-17");
        
        datas.clear();
        datas.addAll(Arrays.asList(h1,h2,h3,h4));
        sa.notifyDataSetChanged();

    }

    private void createData(){
        HashMap<String, String> h1 = new HashMap<>();
        h1.put("content","????????????1");
        h1.put("time","2018-05-17");

        HashMap<String, String> h2 = new HashMap<>();
        h2.put("content","????????????2");
        h2.put("time","2018-05-17");

        HashMap<String, String> h3 = new HashMap<>();
        h3.put("content","????????????3");
        h3.put("time","2018-05-16");

        HashMap<String, String> h4 = new HashMap<>();
        h4.put("content","????????????4");
        h4.put("time","2018-05-16");

        HashMap<String, String> h5 = new HashMap<>();
        h5.put("content","????????????5");
        h5.put("time","2018-05-15");

        HashMap<String, String> h6 = new HashMap<>();
        h6.put("content","????????????6");
        h6.put("time","2018-05-14");

        datas.add(h1);
        datas.add(h2);
        datas.add(h3);
        datas.add(h4);
        datas.add(h5);
        datas.add(h6);
    }


    NavigationView nv;
    int log = 0;
    SimpleAdapter sa;

    private void validateUserinfo() {

        if (nv == null) {
            nv = (NavigationView) findViewById(R.id.nav_view);
        }
        MenuItem mi = nv.getMenu().findItem(R.id.nav_login);

        if (MainActivity.UI.getPhone() == null || MainActivity.UI.getPhone().isEmpty()) {
            // ??????????????????
            mi.setTitle("??????");
            log = 0;

        } else {
            mi.setTitle("??????");
            log = 1;
        }

        mi.setOnMenuItemClickListener((menuItem) -> {

            if(log == 0){
                // ????????????
                Intent it = new Intent();
                it.setClass(BottomActivity.this, MainActivity.class);
                startActivity(it);
                return true;
            }else{
                MainActivity.UI.setPhone(null);
                mi.setTitle("??????");
                log = 0;
                return true;
            }

        });
    }

    private SwipeRefreshLayout srl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // ???????????????????????????
        validateUserinfo();

        srl = (SwipeRefreshLayout)findViewById(R.id.srl);

        srl.setOnRefreshListener(()->{
//            Toast.makeText(BottomActivity.this, "U touched",Toast.LENGTH_SHORT).show();
              createNewData();
            srl.setRefreshing(false);
        });


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        is = (ImageSwitcher) findViewById(R.id.is);
        sentence = (TextView) findViewById(R.id.sentence);
        close = (TextView) findViewById(R.id.close);
        view = (ListView) findViewById(R.id.lv);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener((view)->{
        // ???????????? ??????
            if(ContextCompat.checkSelfPermission(BottomActivity.this,Manifest.permission.CALL_PHONE)
                    == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(BottomActivity.this, "?????????????????????",Toast.LENGTH_SHORT).show();
                // ????????????

                Intent it = new Intent();
                it.setAction(Intent.ACTION_CALL);
                it.setData(Uri.parse("tel:40088008899"));
                startActivity(it);

            }else{
                Toast.makeText(BottomActivity.this, "????????? ??????????????????",Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(BottomActivity.this ,new String[]{Manifest.permission.CALL_PHONE},10001);

            }

        });

        // ??????????????????????????????
        createData();

        sa = new SimpleAdapter(getApplicationContext(),datas,R.layout.every_list,
                new String[]{"content","time"}, new int[]{R.id.msgname,R.id.datetimer});

        view.setAdapter(sa);


        // ???????????? item ???????????????
        view.setOnItemClickListener((adapterView, view, i, l) -> {
            TextView tv = view.findViewById(R.id.msgname);
            String content = tv.getText().toString();


            Intent it = new Intent();
            it.setClass(BottomActivity.this, ShowActivity.class);
            it.putExtra("content", content);


            startActivity(it);

        });


        close.setOnClickListener((view) -> {
            LinearLayout ll = (LinearLayout) findViewById(R.id.linemsg);
            ll.setVisibility(View.GONE);
        });


        // ???????????? ??????   jdk 1.8  lambda ??????????????????
        is.setFactory(() -> {
            ImageView iv = new ImageView(BottomActivity.this);
            iv.setImageResource(imgs[0]);
            sentence.setText(sentences[0]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);

            iv.setLayoutParams(
                    new ImageSwitcher.LayoutParams(
                            ImageSwitcher.LayoutParams.MATCH_PARENT,
                            ImageSwitcher.LayoutParams.MATCH_PARENT));
            return iv;

        });

        is.setOnTouchListener(
                (view, motionEvent) -> {
                    switch (motionEvent.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            // ?????? ??????????????????
                            start = motionEvent.getX();
                            break;
                        case MotionEvent.ACTION_UP:
                            end = motionEvent.getX();
                            // ???????????? ?????? ???????????????  ?????? ????????????

                            if (start > end && start - end > 200) {
                                is.setInAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_in));
                                is.setOutAnimation(AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_out));
                                sentence.setText(sentences[pos]);
                                is.setImageResource(imgs[pos++]);

                                if (pos == 3) {
                                    pos = 0;
                                }

                            } else if (end > start && end - start > 200) {
                                //Toast.makeText(getApplicationContext(),"????????????????????????",Toast.LENGTH_SHORT).show();

                            }
                            break;
                    }
                    return true;
                }
        );


    }

}
