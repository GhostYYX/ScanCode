package com.yyx.scancode;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.yyx.scancode.fragment.FunctionFragmen;
import com.yyx.scancode.fragment.MyFragmen;
import com.yyx.scancode.fragment.ScanCodeFragmen;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager view;
    private List<Fragment> fragmentList = new ArrayList<>();
    //当前选中的项
    int currenttab=-1;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeView(0);
                    return true;
                case R.id.navigation_dashboard:
                    changeView(1);
                    return true;
                case R.id.navigation_notifications:
                    changeView(2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentList.add(new ScanCodeFragmen());
        fragmentList.add(new FunctionFragmen());
        fragmentList.add(new MyFragmen());

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        view = (ViewPager) findViewById(R.id.content);
        view.setAdapter(new MyFrageStatePagerAdapter(getSupportFragmentManager()));
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;   //false 可以滑动，true  禁止滑动
            }
        });
    }






    /**
    * 定义自己的ViewPager适配器。
    * 也可以使用FragmentPagerAdapter。关于这两者之间的区别，可以自己去搜一下。
    */
    class MyFrageStatePagerAdapter extends FragmentStatePagerAdapter {

        public MyFrageStatePagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        /**
         * 每次更新完成ViewPager的内容后，调用该接口，此处复写主要是为了让导航按钮上层的覆盖层能够动态的移动
         */
        @Override
        public void finishUpdate(ViewGroup container)
        {
            super.finishUpdate(container);//这句话要放在最前面，否则会报错
            //获取当前的视图是位于ViewGroup的第几个位置，用来更新对应的覆盖层所在的位置
            int currentItem=view.getCurrentItem();
            if (currentItem==currenttab)
            {
                return ;
            }
            currenttab=view.getCurrentItem();
        }

    }

    //手动设置ViewPager要显示的视图
    private void changeView(int desTab)
    {
        view.setCurrentItem(desTab, true);
    }

}
