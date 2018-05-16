package com.example.com.myjingdong;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FrameMetricsAggregator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.com.myjingdong.R;
import com.example.com.myjingdong.home.ui.HomeFragment;
import com.example.com.myjingdong.my.ui.MyFragment;
import com.example.com.myjingdong.myclass.ui.ClassFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RadioGroup group;
    private List<Fragment> list;
    private FragmentManager myview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取资源id
        initView();
        //创建要显示的页面
        list=new ArrayList<Fragment>();
        list.add(new HomeFragment());
        list.add(new ClassFragment());
        list.add(new MyFragment());
        myview=getSupportFragmentManager();
        myview.beginTransaction().replace(R.id.mymainview,list.get(0)).commit();
        //设置点击选择
        setListlenter();
    }

    private void setListlenter() {
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.tab_home:
                        //首页
                        //Toast.makeText(MainActivity.this,"首页",Toast.LENGTH_SHORT).show();
                        myview.beginTransaction().replace(R.id.mymainview,list.get(0)).commit();
                        break;
                    case R.id.tab_class:
                        //分类
                        myview.beginTransaction().replace(R.id.mymainview,list.get(1)).commit();
                        break;
                    case R.id.tab_find:
                        //发现
                        myview.beginTransaction().replace(R.id.mymainview,list.get(3)).commit();
                        break;
                    case R.id.tab_shopcar:
                        //购物车
                        myview.beginTransaction().replace(R.id.mymainview,list.get(4)).commit();
                        break;
                    case R.id.tab_main:
                        //我的
                        myview.beginTransaction().replace(R.id.mymainview,list.get(2)).commit();
                        break;
                }
            }
        });
    }

    private void initView() {
        group=findViewById(R.id.group);
    }
}
