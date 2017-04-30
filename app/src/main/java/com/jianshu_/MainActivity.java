package com.jianshu_;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BadgeItem;
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
    private BadgeItem badge;

    private BaseFragment mFragment;
    private FindFragment mFindFragment;
    private FollowFragment mFollowFragment;
    private CenterFragment mCenterFragment;
    private MessageFragment mMessageFragment;
    private MyFragment mMyFragment;
    private FragmentTransaction ft;
    private FragmentManager fm;

    private String user_hash;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);

        Intent intent = getIntent();
        user_hash = intent.getStringExtra("user_hash");

        mBbBottom.setMode(BottomNavigationBar.MODE_SHIFTING);
        mBbBottom.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBbBottom
                .setInActiveColor(R.color.colorInActive)//设置未选中的Item的颜色，包括图片和文字
                .setActiveColor(R.color.colorActive) //选中时的颜色
                .setBarBackgroundColor(R.color.colorBarBg);//设置整个控件的背景色

        //设置徽章
        //badge=new BadgeItem()
//                .setBorderWidth(2)//Badge的Border(边界)宽度
//                .setBorderColor("#FF0000")//Badge的Border颜色
//                .setBackgroundColor("#9ACD32")//Badge背景颜色
//                .setGravity(Gravity.RIGHT| Gravity.TOP)//位置，默认右上角
//                .setText("2")//显示的文本
//                .setTextColor("#F0F8FF")//文本颜色
//                .setAnimationDuration(2000)
//                .setHideOnSelect(true)//当选中状态时消失，非选中状态显示
        ;

        mBbBottom
                .addItem(new BottomNavigationItem(R.mipmap.house, "主页"))
                .addItem(new BottomNavigationItem(R.mipmap.category, "分类").setBadgeItem(badge))
                .addItem(new BottomNavigationItem(R.mipmap.pencil, "写文章"))
                .addItem(new BottomNavigationItem(R.mipmap.timeline, "时间轴"))
                .addItem(new BottomNavigationItem(R.mipmap.person, "个人中心"))
                .initialise();//初始化BottomNavigationButton,所有设置需在调用该方法前完成

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
                        /*if (null == mCenterFragment) {
                            mCenterFragment = CenterFragment.newInstance("");
                        }
                        ft.replace(R.id.fl_content, mCenterFragment);*/
                        Intent intent1 = new Intent(MainActivity.this,EditorActivity.class);
                        intent1.putExtra("user_hash",user_hash);
                        startActivity(intent1);
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
