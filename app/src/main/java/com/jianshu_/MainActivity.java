package com.jianshu_;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.jianshu_.base.BaseActivity;
import com.jianshu_.base.BaseFragment;
import com.jianshu_.fragment.main.CenterFragment;
import com.jianshu_.fragment.main.FindFragment;
import com.jianshu_.fragment.main.FollowFragment;
import com.jianshu_.fragment.main.MessageFragment;
import com.jianshu_.fragment.main.MyFragment;

import butterknife.Bind;

public class MainActivity extends BaseActivity {


    @Bind(R.id.fl_content)
    FrameLayout mFlContent;
    @Bind(R.id.bb_bottom)
    BottomNavigationBar mBbBottom;

    private BaseFragment mFragment;
    private FindFragment mFindFragment;
    private FollowFragment mFollowFragment;
    private CenterFragment mCenterFragment;
    private MessageFragment mMessageFragment;
    private MyFragment mMyFragment;
    private FragmentTransaction ft;
    private FragmentManager fm;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        mBbBottom.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBbBottom
                .setActiveColor("#FFFFFF")
                .setInActiveColor("#FFFFFF")
                .setBarBackgroundColor("#FF888888");

        mBbBottom
                .addItem(new BottomNavigationItem(R.mipmap.bottom_house, "发现"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_book, "关注"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_center, ""))
                .addItem(new BottomNavigationItem(R.mipmap.home_message, "消息"))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_my, "我的"))
                .initialise();

//        mBbBottom.setMode(BottomNavigationBar.MODE_FIXED);


        mBbBottom.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                fm = MainActivity.this.getFragmentManager();
                ft = fm.beginTransaction();
                switch (position) {
                    case 0:
                        if (null == mFindFragment) {
                            mFindFragment = FindFragment.newInstance("发现");
                        }
                        ft.replace(R.id.fl_content, mFindFragment);
                        break;
                    case 1:
                        if (null == mFollowFragment) {
                            mFollowFragment = FollowFragment.newInstance("关注");
                        }
                        ft.replace(R.id.fl_content, mFollowFragment);
                        break;
                    case 2:
                        if (null == mCenterFragment) {
                            mCenterFragment = CenterFragment.newInstance("");
                        }
                        ft.replace(R.id.fl_content, mCenterFragment);
                        break;
                    case 3:
                        if (null == mMessageFragment) {
                            mMessageFragment = MessageFragment.newInstance("消息");
                        }
                        ft.replace(R.id.fl_content, mMessageFragment);
                        break;
                    case 4:
                        if (null == mMyFragment) {
                            mMyFragment = MyFragment.newInstance("我的");
                        }
                        ft.replace(R.id.fl_content, mMyFragment);
                        break;
                    default:
                        if (null == mFindFragment) {
                            mFindFragment = FindFragment.newInstance("发现");
                        }
                        ft.add(R.id.fl_content, mFindFragment);
                        break;
                }
                ft.commit();
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }
}
